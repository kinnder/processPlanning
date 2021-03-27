package application.storage.owl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.util.iterator.NiceIterator;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.method.Node;
import planning.model.System;

public class NodeOWLSchemaTest {

	@RegisterExtension
	JUnit5Mockery context = new JUnit5Mockery() {
		{
			setImposteriser(ByteBuddyClassImposteriser.INSTANCE);
		}
	};

	@AfterEach
	public void teardown() {
		context.assertIsSatisfied();
	}

	NodeOWLSchema testable;

	NodeNetworkOWLModel owlModel_mock;

	SystemOWLSchema systemOWLSchema_mock;

	@BeforeEach
	public void setup() {
		owlModel_mock = context.mock(NodeNetworkOWLModel.class);
		systemOWLSchema_mock = context.mock(SystemOWLSchema.class);

		testable = new NodeOWLSchema(owlModel_mock, systemOWLSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new NodeOWLSchema(new NodeNetworkOWLModel());
	}

	@Test
	public void combine() {
		final System system_mock = context.mock(System.class, "system");
		final Node node_mock = new Node("node-id", system_mock, true);
		final Individual i_node_mock = context.mock(Individual.class, "i-node");
		final Individual i_system_mock = context.mock(Individual.class, "i-system");
		final DatatypeProperty dp_id_mock = context.mock(DatatypeProperty.class, "dp-id");
		final DatatypeProperty dp_checked_mock = context.mock(DatatypeProperty.class, "dp-checked");
		final ObjectProperty op_hasSystem_mock = context.mock(ObjectProperty.class, "op-hasSystem");
		final ObjectProperty op_isSystemOf_mock = context.mock(ObjectProperty.class, "op-isSystemOf");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_Node();
				will(returnValue(i_node_mock));

				oneOf(i_node_mock).addLabel("Node", "en");

				oneOf(i_node_mock).addLabel("Узел", "ru");

				oneOf(owlModel_mock).getDataProperty_id();
				will(returnValue(dp_id_mock));

				oneOf(i_node_mock).addProperty(dp_id_mock, "node-id");

				oneOf(owlModel_mock).getDataProperty_checked();
				will(returnValue(dp_checked_mock));

				oneOf(i_node_mock).addProperty(dp_checked_mock, "true", XSDDatatype.XSDboolean);

				oneOf(systemOWLSchema_mock).combine(system_mock);
				will(returnValue(i_system_mock));

				oneOf(owlModel_mock).getObjectProperty_hasSystem();
				will(returnValue(op_hasSystem_mock));

				oneOf(i_node_mock).addProperty(op_hasSystem_mock, i_system_mock);

				oneOf(owlModel_mock).getObjectProperty_isSystemOf();
				will(returnValue(op_isSystemOf_mock));

				oneOf(i_system_mock).addProperty(op_isSystemOf_mock, i_node_mock);
			}
		});

		assertEquals(i_node_mock, testable.combine(node_mock));
	}

	@Test
	public void parse() {
		final System system_mock = context.mock(System.class, "system");
		new Node("node-id", system_mock, true);
		final Individual i_node_mock = context.mock(Individual.class, "i-node");
		final DatatypeProperty dp_id_mock = context.mock(DatatypeProperty.class, "dp-id");
		final DatatypeProperty dp_checked_mock = context.mock(DatatypeProperty.class, "dp-checked");
		final Statement st_id_mock = context.mock(Statement.class, "st-id");
		final Statement st_checked_mock = context.mock(Statement.class, "st-checked");
		final ObjectProperty op_hasSystem_mock = context.mock(ObjectProperty.class, "op-hasSystem");
		context.mock(ObjectProperty.class, "op-isSystemOf");
		final OntClass oc_system_mock = context.mock(OntClass.class, "oc-system");

		final Individual i_system_mock = context.mock(Individual.class, "i-system");
		final ExtendedIterator<Individual> systemIterator = new NiceIterator<Individual>()
				.andThen(Arrays.asList(i_system_mock).iterator());

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getDataProperty_id();
				will(returnValue(dp_id_mock));

				oneOf(i_node_mock).getProperty(dp_id_mock);
				will(returnValue(st_id_mock));

				oneOf(st_id_mock).getString();
				will(returnValue("node-id"));

				oneOf(owlModel_mock).getDataProperty_checked();
				will(returnValue(dp_checked_mock));

				oneOf(i_node_mock).getProperty(dp_checked_mock);
				will(returnValue(st_checked_mock));

				oneOf(st_checked_mock).getBoolean();
				will(returnValue(true));

				oneOf(owlModel_mock).getClass_System();
				will(returnValue(oc_system_mock));

				oneOf(oc_system_mock).listInstances();
				will(returnValue(systemIterator));

				oneOf(owlModel_mock).getObjectProperty_hasSystem();
				will(returnValue(op_hasSystem_mock));

				oneOf(i_node_mock).hasProperty(op_hasSystem_mock, i_system_mock);
				will(returnValue(true));

				oneOf(i_system_mock).asIndividual();
				will(returnValue(i_system_mock));

				oneOf(systemOWLSchema_mock).parse(i_system_mock);
				will(returnValue(system_mock));
			}
		});

		Node result = testable.parse(i_node_mock);
		assertEquals("node-id", result.getId());
		assertEquals(true, result.getChecked());
		assertEquals(system_mock, result.getSystem());
	}
}

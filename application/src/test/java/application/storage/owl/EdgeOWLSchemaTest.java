package application.storage.owl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

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

import planning.method.Edge;
import planning.model.SystemOperation;

public class EdgeOWLSchemaTest {

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

	EdgeOWLSchema testable;

	NodeNetworkOWLModel owlModel_mock;

	SystemOperationOWLSchema systemOperationOWLSchema_mock;

	@BeforeEach
	public void setup() {
		owlModel_mock = context.mock(NodeNetworkOWLModel.class);
		systemOperationOWLSchema_mock = context.mock(SystemOperationOWLSchema.class);

		testable = new EdgeOWLSchema(owlModel_mock, systemOperationOWLSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new EdgeOWLSchema(new NodeNetworkOWLModel());
	}

	@Test
	public void combine() {
		final SystemOperation systemOperation_mock = context.mock(SystemOperation.class, "systemOperation");
		final Edge edge = new Edge("edge-id", "beginNode-id", "endNode-id", systemOperation_mock);
		final Individual i_edge_mock = context.mock(Individual.class, "i-edge");
		final Individual i_systemOperation_mock = context.mock(Individual.class, "i-systemOperation");
		final DatatypeProperty dp_id_mock = context.mock(DatatypeProperty.class, "dp-id");
		final DatatypeProperty dp_beginNodeId_mock = context.mock(DatatypeProperty.class, "dp-beginNodeId");
		final DatatypeProperty dp_endNodeId_mock = context.mock(DatatypeProperty.class, "dp-endNodeId");
		final ObjectProperty op_hasSystemOperation_mock = context.mock(ObjectProperty.class, "op-hasSystemOperation");
		final ObjectProperty op_isSystemOperationOf_mock = context.mock(ObjectProperty.class, "op-isSystemOperationOf");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_Edge();
				will(returnValue(i_edge_mock));

				oneOf(i_edge_mock).addLabel("Edge", "en");

				oneOf(i_edge_mock).addLabel("Ребро", "ru");

				oneOf(owlModel_mock).getDataProperty_id();
				will(returnValue(dp_id_mock));

				oneOf(i_edge_mock).addProperty(dp_id_mock, "edge-id");

				oneOf(owlModel_mock).getDataProperty_beginNodeId();
				will(returnValue(dp_beginNodeId_mock));

				oneOf(i_edge_mock).addProperty(dp_beginNodeId_mock, "beginNode-id");

				oneOf(owlModel_mock).getDataProperty_endNodeId();
				will(returnValue(dp_endNodeId_mock));

				oneOf(i_edge_mock).addProperty(dp_endNodeId_mock, "endNode-id");

				oneOf(systemOperationOWLSchema_mock).combine(systemOperation_mock);
				will(returnValue(i_systemOperation_mock));

				oneOf(owlModel_mock).getObjectProperty_hasSystemOperation();
				will(returnValue(op_hasSystemOperation_mock));

				oneOf(i_edge_mock).addProperty(op_hasSystemOperation_mock, i_systemOperation_mock);

				oneOf(owlModel_mock).getObjectProperty_isSystemOperationOf();
				will(returnValue(op_isSystemOperationOf_mock));

				oneOf(i_systemOperation_mock).addProperty(op_isSystemOperationOf_mock, i_edge_mock);
			}
		});

		assertEquals(i_edge_mock, testable.combine(edge));
	}

	@Test
	public void parse() {
		final SystemOperation systemOperation_mock = context.mock(SystemOperation.class, "systemOperation");
		new Edge("edge-id", "beginNode-id", "endNode-id", systemOperation_mock);
		final Individual i_edge_mock = context.mock(Individual.class, "i-edge");
		final DatatypeProperty dp_id_mock = context.mock(DatatypeProperty.class, "dp-id");
		final DatatypeProperty dp_beginNodeId_mock = context.mock(DatatypeProperty.class, "dp-beginNodeId");
		final DatatypeProperty dp_endNodeId_mock = context.mock(DatatypeProperty.class, "dp-endNodeId");
		final Statement st_id_mock = context.mock(Statement.class, "st-id");
		final Statement st_beginNodeId_mock = context.mock(Statement.class, "st-beginNodeId");
		final Statement st_endNodeId_mock = context.mock(Statement.class, "st-endNodeId");
		final ObjectProperty op_hasSystemOperation_mock = context.mock(ObjectProperty.class, "op-hasSystemOperation");
		context.mock(ObjectProperty.class, "op-isSystemOperationOf");
		final OntClass oc_systemOperation_mock = context.mock(OntClass.class, "oc-systemOperation");

		final Individual i_systemOperation_mock = context.mock(Individual.class, "i-systemOperation");
		final ExtendedIterator<Individual> systemOperationIterator = new NiceIterator<Individual>()
				.andThen(Arrays.asList(i_systemOperation_mock).iterator());

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getDataProperty_id();
				will(returnValue(dp_id_mock));

				oneOf(i_edge_mock).getProperty(dp_id_mock);
				will(returnValue(st_id_mock));

				oneOf(st_id_mock).getString();
				will(returnValue("edge-id"));

				oneOf(owlModel_mock).getDataProperty_beginNodeId();
				will(returnValue(dp_beginNodeId_mock));

				oneOf(i_edge_mock).getProperty(dp_beginNodeId_mock);
				will(returnValue(st_beginNodeId_mock));

				oneOf(st_beginNodeId_mock).getString();
				will(returnValue("beginNode-id"));

				oneOf(owlModel_mock).getDataProperty_endNodeId();
				will(returnValue(dp_endNodeId_mock));

				oneOf(i_edge_mock).getProperty(dp_endNodeId_mock);
				will(returnValue(st_endNodeId_mock));

				oneOf(st_endNodeId_mock).getString();
				will(returnValue("endNode-id"));

				oneOf(owlModel_mock).getClass_SystemOperation();
				will(returnValue(oc_systemOperation_mock));

				oneOf(oc_systemOperation_mock).listInstances();
				will(returnValue(systemOperationIterator));

				oneOf(owlModel_mock).getObjectProperty_hasSystemOperation();
				will(returnValue(op_hasSystemOperation_mock));

				oneOf(i_edge_mock).hasProperty(op_hasSystemOperation_mock, i_systemOperation_mock);
				will(returnValue(true));

				oneOf(i_systemOperation_mock).asIndividual();
				will(returnValue(i_systemOperation_mock));

				oneOf(systemOperationOWLSchema_mock).parse(i_systemOperation_mock);
				will(returnValue(systemOperation_mock));
			}
		});

		Edge result = testable.parse(i_edge_mock);
		assertEquals("edge-id", result.getId());
		assertEquals("beginNode-id", result.getBeginNodeId());
		assertEquals("endNode-id", result.getEndNodeId());
		assertEquals(systemOperation_mock, result.getSystemOperation());
	}
}

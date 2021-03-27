package application.storage.owl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

import planning.model.Action;
import planning.model.SystemOperation;

public class SystemOperationOWLSchemaTest {

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

	SystemOperationOWLSchema testable;

	NodeNetworkOWLModel owlModel_mock;

	ActionParametersOWLSchema actionParametersOWLSchema_mock;

	@BeforeEach
	public void setup() {
		owlModel_mock = context.mock(NodeNetworkOWLModel.class);
		actionParametersOWLSchema_mock = context.mock(ActionParametersOWLSchema.class);

		testable = new SystemOperationOWLSchema(owlModel_mock, actionParametersOWLSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new SystemOperationOWLSchema(new NodeNetworkOWLModel());
	}

	@Test
	public void combine() {
		final Action action = new Action("operation-name");
		final Map<String, String> actionParameters = new HashMap<>();
		final SystemOperation systemOperation = new SystemOperation(action, actionParameters);
		final Individual i_systemOperation_mock = context.mock(Individual.class, "i-systemOperation");
		final Individual i_actionParameters_mock = context.mock(Individual.class, "i-actionParameters");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final ObjectProperty op_hasActionParameters_mock = context.mock(ObjectProperty.class, "op-hasActionParameters");
		final ObjectProperty op_areActionParametersOf_mock = context.mock(ObjectProperty.class,
				"op-areActionParametersOf");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_SystemOperation();
				will(returnValue(i_systemOperation_mock));

				oneOf(i_systemOperation_mock).addLabel("System operation", "en");

				oneOf(i_systemOperation_mock).addLabel("Операция системы", "ru");

				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_systemOperation_mock).addProperty(dp_name_mock, "operation-name");

				oneOf(actionParametersOWLSchema_mock).combine(actionParameters);
				will(returnValue(i_actionParameters_mock));

				oneOf(owlModel_mock).getObjectProperty_hasActionParameters();
				will(returnValue(op_hasActionParameters_mock));

				oneOf(i_systemOperation_mock).addProperty(op_hasActionParameters_mock, i_actionParameters_mock);

				oneOf(owlModel_mock).getObjectProperty_areActionParametersOf();
				will(returnValue(op_areActionParametersOf_mock));

				oneOf(i_actionParameters_mock).addProperty(op_areActionParametersOf_mock, i_systemOperation_mock);
			}
		});

		assertEquals(i_systemOperation_mock, testable.combine(systemOperation));
	}

	@Test
	public void parse() {
		final Action action = new Action("operation-name");
		final Map<String, String> actionParameters = new HashMap<>();
		new SystemOperation(action, actionParameters);
		final Individual i_systemOperation_mock = context.mock(Individual.class, "i-systemOperation");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final Statement st_name_mock = context.mock(Statement.class, "st-name");
		final ObjectProperty op_hasActionParameters_mock = context.mock(ObjectProperty.class, "op-hasActionParameters");
		context.mock(ObjectProperty.class, "op-areActionParametersOf");
		final OntClass oc_actionParameters_mock = context.mock(OntClass.class, "oc-actionParameters");

		final Individual i_actionParameters_mock = context.mock(Individual.class, "i-actionParameters");
		final ExtendedIterator<Individual> actionParametersIterator = new NiceIterator<Individual>()
				.andThen(Arrays.asList(i_actionParameters_mock).iterator());

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_systemOperation_mock).getProperty(dp_name_mock);
				will(returnValue(st_name_mock));

				oneOf(st_name_mock).getString();
				will(returnValue("operation-name"));

				oneOf(owlModel_mock).getClass_ActionParameters();
				will(returnValue(oc_actionParameters_mock));

				oneOf(oc_actionParameters_mock).listInstances();
				will(returnValue(actionParametersIterator));

				oneOf(owlModel_mock).getObjectProperty_hasActionParameters();
				will(returnValue(op_hasActionParameters_mock));

				oneOf(i_systemOperation_mock).hasProperty(op_hasActionParameters_mock, i_actionParameters_mock);
				will(returnValue(true));

				oneOf(i_actionParameters_mock).asIndividual();
				will(returnValue(i_actionParameters_mock));

				oneOf(actionParametersOWLSchema_mock).parse(i_actionParameters_mock);
				will(returnValue(actionParameters));
			}
		});

		SystemOperation result = testable.parse(i_systemOperation_mock);
		assertEquals("operation-name", result.getName());
		assertEquals(actionParameters, result.getActionParameters());
	}
}

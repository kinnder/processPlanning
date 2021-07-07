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

	@BeforeEach
	public void setup() {
		owlModel_mock = context.mock(NodeNetworkOWLModel.class);

		testable = new SystemOperationOWLSchema(owlModel_mock);
	}

	@Test
	public void newInstance() {
		testable = new SystemOperationOWLSchema(new NodeNetworkOWLModel());
	}

	@Test
	public void combine() {
		final Action action = new Action("operation-name");
		final Map<String, String> actionParameters = new HashMap<>();
		actionParameters.put("parameter-name", "parameter-value");
		final SystemOperation systemOperation = new SystemOperation(action, actionParameters);
		final Individual i_systemOperation_mock = context.mock(Individual.class, "i-systemOperation");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final Individual i_parameter_mock = context.mock(Individual.class, "i-parameter");
		final DatatypeProperty dp_key_mock = context.mock(DatatypeProperty.class, "dp-key");
		final DatatypeProperty dp_value_mock = context.mock(DatatypeProperty.class, "dp-value");
		final ObjectProperty op_hasParameter_mock = context.mock(ObjectProperty.class, "op-hasParameter");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_SystemOperation();
				will(returnValue(i_systemOperation_mock));

				oneOf(i_systemOperation_mock).addLabel("System operation \"operation-name\"", "en");

				oneOf(i_systemOperation_mock).addLabel("Операция системы \"operation-name\"", "ru");

				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_systemOperation_mock).addProperty(dp_name_mock, "operation-name");

				oneOf(owlModel_mock).newIndividual_Parameter();
				will(returnValue(i_parameter_mock));

				oneOf(i_parameter_mock).addLabel("Parameter \"parameter-name\"", "en");

				oneOf(i_parameter_mock).addLabel("Параметр \"parameter-name\"", "ru");

				oneOf(owlModel_mock).getDataProperty_key();
				will(returnValue(dp_key_mock));

				oneOf(i_parameter_mock).addProperty(dp_key_mock, "parameter-name");

				oneOf(owlModel_mock).getDataProperty_value();
				will(returnValue(dp_value_mock));

				oneOf(i_parameter_mock).addProperty(dp_value_mock, "parameter-value");

				oneOf(owlModel_mock).getObjectProperty_hasParameter();
				will(returnValue(op_hasParameter_mock));

				oneOf(i_systemOperation_mock).addProperty(op_hasParameter_mock, i_parameter_mock);
			}
		});

		assertEquals(i_systemOperation_mock, testable.combine(systemOperation));
	}

	@Test
	public void parse() {
		final Individual i_systemOperation_mock = context.mock(Individual.class, "i-systemOperation");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final Statement st_name_mock = context.mock(Statement.class, "st-name");
		final DatatypeProperty dp_key_mock = context.mock(DatatypeProperty.class, "dp-key");
		final DatatypeProperty dp_value_mock = context.mock(DatatypeProperty.class, "dp-value");
		final Statement st_key_mock = context.mock(Statement.class, "st-key");
		final Statement st_value_mock = context.mock(Statement.class, "st-value");
		final ObjectProperty op_hasParameter_mock = context.mock(ObjectProperty.class, "op-hasParameter");
		final OntClass oc_parameter_mock = context.mock(OntClass.class, "oc-parameter");

		final Individual i_parameter_mock = context.mock(Individual.class, "i-parameter");
		final ExtendedIterator<Individual> parameterIterator = new NiceIterator<Individual>()
				.andThen(Arrays.asList(i_parameter_mock).iterator());

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_systemOperation_mock).getProperty(dp_name_mock);
				will(returnValue(st_name_mock));

				oneOf(st_name_mock).getString();
				will(returnValue("operation-name"));

				oneOf(owlModel_mock).getClass_Parameter();
				will(returnValue(oc_parameter_mock));

				oneOf(oc_parameter_mock).listInstances();
				will(returnValue(parameterIterator));

				oneOf(owlModel_mock).getObjectProperty_hasParameter();
				will(returnValue(op_hasParameter_mock));

				oneOf(i_systemOperation_mock).hasProperty(op_hasParameter_mock, i_parameter_mock);
				will(returnValue(true));

				oneOf(owlModel_mock).getDataProperty_key();
				will(returnValue(dp_key_mock));

				oneOf(i_parameter_mock).getProperty(dp_key_mock);
				will(returnValue(st_key_mock));

				oneOf(st_key_mock).getString();
				will(returnValue("parameter-name"));

				oneOf(owlModel_mock).getDataProperty_value();
				will(returnValue(dp_value_mock));

				oneOf(i_parameter_mock).getProperty(dp_value_mock);
				will(returnValue(st_value_mock));

				oneOf(st_value_mock).getString();
				will(returnValue("parameter-value"));
			}
		});

		SystemOperation result = testable.parse(i_systemOperation_mock);
		assertEquals("operation-name", result.getName());
		assertEquals("parameter-value", result.getActionParameters().get("parameter-name"));
	}
}

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

public class ActionParametersOWLSchemaTest {

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

	ActionParametersOWLSchema testable;

	NodeNetworkOWLModel owlModel_mock;

	@BeforeEach
	public void setup() {
		owlModel_mock = context.mock(NodeNetworkOWLModel.class);

		testable = new ActionParametersOWLSchema(owlModel_mock);
	}

	@Test
	public void combine() {
		final Map<String, String> actionParameters = new HashMap<>();
		actionParameters.put("parameter-name", "parameter-value");
		final Individual i_actionParameters_mock = context.mock(Individual.class, "i-actionParameters");
		final Individual i_parameter_mock = context.mock(Individual.class, "i-parameter");
		final DatatypeProperty dp_key_mock = context.mock(DatatypeProperty.class, "dp-key");
		final DatatypeProperty dp_value_mock = context.mock(DatatypeProperty.class, "dp-value");
		final ObjectProperty op_hasParameter_mock = context.mock(ObjectProperty.class, "op-hasParameter");
		final ObjectProperty op_isParameterOf_mock = context.mock(ObjectProperty.class, "op-isParameterOf");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_ActionParameters();
				will(returnValue(i_actionParameters_mock));

				oneOf(i_actionParameters_mock).addLabel("Action parameters", "en");

				oneOf(i_actionParameters_mock).addLabel("Параметры действия", "ru");

				oneOf(owlModel_mock).newIndividual_Parameter();
				will(returnValue(i_parameter_mock));

				oneOf(i_parameter_mock).addLabel("Parameter", "en");

				oneOf(i_parameter_mock).addLabel("Параметр", "ru");

				oneOf(owlModel_mock).getDataProperty_key();
				will(returnValue(dp_key_mock));

				oneOf(i_parameter_mock).addProperty(dp_key_mock, "parameter-name");

				oneOf(owlModel_mock).getDataProperty_value();
				will(returnValue(dp_value_mock));

				oneOf(i_parameter_mock).addProperty(dp_value_mock, "parameter-value");

				oneOf(owlModel_mock).getObjectProperty_hasParameter();
				will(returnValue(op_hasParameter_mock));

				oneOf(i_actionParameters_mock).addProperty(op_hasParameter_mock, i_parameter_mock);

				oneOf(owlModel_mock).getObjectProperty_isParameterOf();
				will(returnValue(op_isParameterOf_mock));

				oneOf(i_parameter_mock).addProperty(op_isParameterOf_mock, i_actionParameters_mock);
			}
		});

		assertEquals(i_actionParameters_mock, testable.combine(actionParameters));
	}

	@Test
	public void parse() {
		final Map<String, String> actionParameters = new HashMap<>();
		actionParameters.put("parameter-name", "parameter-value");
		final Individual i_actionParameters_mock = context.mock(Individual.class, "i-actionParameters");
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
				oneOf(owlModel_mock).getClass_Parameter();
				will(returnValue(oc_parameter_mock));

				oneOf(oc_parameter_mock).listInstances();
				will(returnValue(parameterIterator));

				oneOf(owlModel_mock).getObjectProperty_hasParameter();
				will(returnValue(op_hasParameter_mock));

				oneOf(i_actionParameters_mock).hasProperty(op_hasParameter_mock, i_parameter_mock);
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

		Map<String, String> result = testable.parse(i_actionParameters_mock);
		assertEquals("parameter-value", result.get("parameter-name"));
	}
}

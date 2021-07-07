package application.storage.owl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

import planning.model.Action;
import planning.model.LuaScriptActionFunction;

public class ActionOWLSchemaTest {

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

	ActionOWLSchema testable;

	SystemTransformationsOWLModel owlModel_mock;

	ActionFunctionOWLSchema actionFunctionOWLSchema_mock;

	@BeforeEach
	public void setup() {
		owlModel_mock = context.mock(SystemTransformationsOWLModel.class);
		actionFunctionOWLSchema_mock = context.mock(ActionFunctionOWLSchema.class);

		testable = new ActionOWLSchema(owlModel_mock, actionFunctionOWLSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new ActionOWLSchema(new SystemTransformationsOWLModel());
	}

	@Test
	public void combine() {
		final Action action = new Action("action-name");
		final LuaScriptActionFunction actionPreConditionChecker_mock = context.mock(LuaScriptActionFunction.class, "preConditionChekcer");
		final LuaScriptActionFunction actionParameterUpdater_mock = context.mock(LuaScriptActionFunction.class, "parameterUpdater");
		action.registerParameterUpdater(actionParameterUpdater_mock);
		action.registerPreConditionChecker(actionPreConditionChecker_mock);
		final Individual i_action_mock = context.mock(Individual.class, "i-action");
		final Individual i_preConditionChecker_mock = context.mock(Individual.class, "i-preConditionChecker");
		final Individual i_parameterUpdater_mock = context.mock(Individual.class, "i-parameterUdpater");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final ObjectProperty op_hasPreConditionChecker_mock = context.mock(ObjectProperty.class, "op-hasPreConditionChecker");
		final ObjectProperty op_hasParameterUpdater_mock = context.mock(ObjectProperty.class, "op-hasParameterUpdater");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_Action();
				will(returnValue(i_action_mock));

				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_action_mock).addLabel("Действие \"action-name\"", "ru");

				oneOf(i_action_mock).addLabel("Action \"action-name\"", "en");

				oneOf(i_action_mock).addProperty(dp_name_mock, "action-name");

				oneOf(actionFunctionOWLSchema_mock).combine(actionPreConditionChecker_mock);
				will(returnValue(i_preConditionChecker_mock));

				oneOf(owlModel_mock).getObjectProperty_hasPreConditionChecker();
				will(returnValue(op_hasPreConditionChecker_mock));

				oneOf(i_action_mock).addProperty(op_hasPreConditionChecker_mock, i_preConditionChecker_mock);

				oneOf(actionFunctionOWLSchema_mock).combine(actionParameterUpdater_mock);
				will(returnValue(i_parameterUpdater_mock));

				oneOf(owlModel_mock).getObjectProperty_hasParameterUpdater();
				will(returnValue(op_hasParameterUpdater_mock));

				oneOf(i_action_mock).addProperty(op_hasParameterUpdater_mock, i_parameterUpdater_mock);
			}
		});

		assertEquals(i_action_mock, testable.combine(action));
	}

	@Test
	public void parse() {
		final Action action = new Action("action-name");
		final LuaScriptActionFunction actionPreConditionChecker_mock = context.mock(LuaScriptActionFunction.class, "preConditionChecker");
		final LuaScriptActionFunction actionParameterUpdater_mock = context.mock(LuaScriptActionFunction.class, "parameterUpdater");
		action.registerParameterUpdater(actionParameterUpdater_mock);
		action.registerPreConditionChecker(actionPreConditionChecker_mock);
		final Individual i_action_mock = context.mock(Individual.class, "i-action");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final ObjectProperty op_hasPreConditionChecker_mock = context.mock(ObjectProperty.class,
				"op-hasPreConditionChecker");
		final ObjectProperty op_hasParameterUpdater_mock = context.mock(ObjectProperty.class, "op-hasParameterUpdater");
		final Statement st_name_mock = context.mock(Statement.class, "st-name");
		final OntClass oc_parameterUpdater_mock = context.mock(OntClass.class, "oc-parameterUpdater");
		final OntClass oc_preConditionChecker_mock = context.mock(OntClass.class, "oc-preConditionChecker");

		final Individual i_preConditionChecker_mock = context.mock(Individual.class, "i-preConditionChecker");
		final ExtendedIterator<Individual> preConditionCheckerIterator = new NiceIterator<Individual>()
				.andThen(Arrays.asList(i_preConditionChecker_mock).iterator());

		final Individual i_parameterUpdater_mock = context.mock(Individual.class, "i-parameterUdpater");
		final ExtendedIterator<Individual> parameterUpdaterIterator = new NiceIterator<Individual>()
				.andThen(Arrays.asList(i_parameterUpdater_mock).iterator());

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_action_mock).getProperty(dp_name_mock);
				will(returnValue(st_name_mock));

				oneOf(st_name_mock).getString();
				will(returnValue("action-name"));

				oneOf(owlModel_mock).getClass_ActionFunction();
				will(returnValue(oc_preConditionChecker_mock));

				oneOf(oc_preConditionChecker_mock).listInstances();
				will(returnValue(preConditionCheckerIterator));

				oneOf(owlModel_mock).getObjectProperty_hasPreConditionChecker();
				will(returnValue(op_hasPreConditionChecker_mock));

				oneOf(i_action_mock).hasProperty(op_hasPreConditionChecker_mock, i_preConditionChecker_mock);
				will(returnValue(true));

				oneOf(i_preConditionChecker_mock).asIndividual();
				will(returnValue(i_preConditionChecker_mock));

				oneOf(actionFunctionOWLSchema_mock).parse(i_preConditionChecker_mock);
				will(returnValue(actionPreConditionChecker_mock));

				oneOf(owlModel_mock).getClass_ActionFunction();
				will(returnValue(oc_parameterUpdater_mock));

				oneOf(oc_parameterUpdater_mock).listInstances();
				will(returnValue(parameterUpdaterIterator));

				oneOf(owlModel_mock).getObjectProperty_hasParameterUpdater();
				will(returnValue(op_hasParameterUpdater_mock));

				oneOf(i_action_mock).hasProperty(op_hasParameterUpdater_mock, i_parameterUpdater_mock);
				will(returnValue(true));

				oneOf(i_parameterUpdater_mock).asIndividual();
				will(returnValue(i_parameterUpdater_mock));

				oneOf(actionFunctionOWLSchema_mock).parse(i_parameterUpdater_mock);
				will(returnValue(actionParameterUpdater_mock));
			}
		});

		Action result = testable.parse(i_action_mock);
		assertEquals("action-name", result.getName());
		assertTrue(result.getParameterUpdaters().contains(actionParameterUpdater_mock));
		assertTrue(result.getPreConditionCheckers().contains(actionPreConditionChecker_mock));
	}
}

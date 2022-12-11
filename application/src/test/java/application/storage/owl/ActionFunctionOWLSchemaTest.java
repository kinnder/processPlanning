package application.storage.owl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.util.iterator.NiceIterator;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

import planning.model.ActionFunction;
import planning.model.LuaScriptLine;

public class ActionFunctionOWLSchemaTest {

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

	ActionFunctionOWLSchema testable;

	LuaScriptLineOWLSchema luaScriptLineOWLSchema_mock;

	PlanningOWLModel owlModel_mock;

	@BeforeEach
	public void setup() {
		owlModel_mock = context.mock(PlanningOWLModel.class);
		luaScriptLineOWLSchema_mock = context.mock(LuaScriptLineOWLSchema.class);

		testable = new ActionFunctionOWLSchema(owlModel_mock, luaScriptLineOWLSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new ActionFunctionOWLSchema(new PlanningOWLModel());
	}

	// TODO (2022-12-07 #73): пересмотреть положение globals
	private static Globals globals = JsePlatform.standardGlobals();

	@Test
	public void combine() {
		final LuaScriptLine scriptLine = new LuaScriptLine(10, "line-text");
		final List<LuaScriptLine> scriptLines = Arrays.asList(scriptLine);
		final ActionFunction actionFunction = new ActionFunction(globals, scriptLines);
		final Individual i_actionFunction = context.mock(Individual.class, "i-actionFunction");
		final Individual i_line_mock = context.mock(Individual.class, "i-line");
		final ObjectProperty op_hasLine_mock = context.mock(ObjectProperty.class, "op-hasLine");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_ActionFunction();
				will(returnValue(i_actionFunction));

				// TODO (2021-03-23 #31): добавить Matcher для LuaScriptLine
				oneOf(luaScriptLineOWLSchema_mock).combine(with(any(LuaScriptLine.class)));
				will(returnValue(i_line_mock));

				oneOf(owlModel_mock).getObjectProperty_hasLine();
				will(returnValue(op_hasLine_mock));

				oneOf(i_actionFunction).addProperty(op_hasLine_mock, i_line_mock);
			}
		});

		assertEquals(i_actionFunction, testable.combine(actionFunction));
	}

	@Test
	public void parse() {
		final LuaScriptLine scriptLine = new LuaScriptLine(10, "line-text");
		final Individual i_actionFunction = context.mock(Individual.class, "i-actionFunction");
		final ObjectProperty op_hasLine_mock = context.mock(ObjectProperty.class, "op-hasLine");
		final OntClass oc_line_mock = context.mock(OntClass.class, "oc-line");

		final Individual i_line_mock = context.mock(Individual.class, "i-line");
		final ExtendedIterator<Individual> lineIterator = new NiceIterator<Individual>()
				.andThen(Arrays.asList(i_line_mock).iterator());

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getClass_Line();
				will(returnValue(oc_line_mock));

				oneOf(oc_line_mock).listInstances();
				will(returnValue(lineIterator));

				oneOf(owlModel_mock).getObjectProperty_hasLine();
				will(returnValue(op_hasLine_mock));

				oneOf(i_actionFunction).hasProperty(op_hasLine_mock, i_line_mock);
				will(returnValue(true));

				oneOf(i_line_mock).asIndividual();
				will(returnValue(i_line_mock));

				oneOf(luaScriptLineOWLSchema_mock).parse(i_line_mock);
				will(returnValue(scriptLine));
			}
		});

		ActionFunction result = testable.parse(i_actionFunction);
		assertEquals("line-text\n", result.getScript());
	}
}

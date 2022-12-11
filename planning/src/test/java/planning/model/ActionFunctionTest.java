package planning.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

public class ActionFunctionTest {

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

	ActionFunction testable;

	Globals globals;

	StringBuilder script;

	@BeforeEach
	public void setup() {
		globals = JsePlatform.standardGlobals();
		script = new StringBuilder();
	}

	@Test
	public void test_PreConditionChecker() {
		script.append("local systemVariant = ...");
		script.append("\n");
		script.append("return true");
		script.append("\n");

		SystemVariant systemVariant_mock = context.mock(SystemVariant.class);

		testable = new ActionFunction(globals, script.toString());
		assertTrue(testable.test(systemVariant_mock));
	}

	@Test
	public void accept_ParameterUpdater() {
		script.append("local systemVariant = ...");
		script.append("\n");
		script.append("local object = systemVariant:getObjectByIdMatch('plane-x-id')");
		script.append("\n");
		script.append("systemVariant:setActionParameter('parameter-target', object:getName())");
		script.append("\n");

		SystemVariant systemVariant_mock = context.mock(SystemVariant.class);
		SystemObject systemObject_mock = context.mock(SystemObject.class);
		context.checking(new Expectations() {
			{
				oneOf(systemVariant_mock).getObjectByIdMatch("plane-x-id");
				will(returnValue(systemObject_mock));

				oneOf(systemObject_mock).getName();
				will(returnValue("plane-x"));

				oneOf(systemVariant_mock).setActionParameter("parameter-target", "plane-x");
			}
		});

		testable = new ActionFunction(globals, script.toString());
		testable.accept(systemVariant_mock);
	}

	@Test
	public void newInstance() {
		final LuaScriptLine line_1 = new LuaScriptLine(1, "local a = ...");
		final LuaScriptLine line_2 = new LuaScriptLine(2, "return a + 2");
		final Collection<LuaScriptLine> lines = Arrays.asList(line_1, line_2);

		testable = new ActionFunction(globals, lines);
		assertEquals("local a = ...\nreturn a + 2\n", testable.getScript());
	}
}

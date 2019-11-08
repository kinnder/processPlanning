package planning.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

public class LuaScriptActionParameterUpdaterTest {

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

	LuaScriptActionParameterUpdater testable;

	Globals globals;

	StringBuilder script;

	@BeforeEach
	public void setup() {
		globals = JsePlatform.standardGlobals();
		script = new StringBuilder();
	}

	@Test
	public void invoke() {
		testable = new LuaScriptActionParameterUpdater(globals, script.toString());
		testable.invoke(null);
	}

	@Test
	public void invoke_with_script() {
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

		testable = new LuaScriptActionParameterUpdater(globals, script.toString());
		testable.invoke(systemVariant_mock);
	}

	@Test
	public void getScript() {
		script.append("lua-code");
		testable = new LuaScriptActionParameterUpdater(globals, script.toString());

		assertEquals("lua-code", testable.getScript());
	}
}

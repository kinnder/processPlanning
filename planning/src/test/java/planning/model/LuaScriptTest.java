package planning.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

public class LuaScriptTest {

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

	LuaScript testable;

	Globals globals;

	StringBuilder script;

	@BeforeEach
	public void setup() {
		globals = JsePlatform.standardGlobals();
		script = new StringBuilder();
	}

	@Test
	public void call() {
		script.append("local a = ...");
		script.append("\n");
		script.append("return a + 2");
		script.append("\n");

		testable = new LuaScript(globals, script.toString());
		assertEquals(5, testable.call(LuaValue.valueOf(3)).toint());
	}

	@Test
	public void getScript() {
		script.append("lua-code");
		testable = new LuaScriptActionPreConditionChecker(globals, script.toString());

		assertEquals("lua-code", testable.getScript());
	}
}

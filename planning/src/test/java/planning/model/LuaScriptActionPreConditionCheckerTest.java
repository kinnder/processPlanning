package planning.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

public class LuaScriptActionPreConditionCheckerTest {

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

	LuaScriptActionPreConditionChecker testable;

	Globals globals;

	StringBuilder script;

	@BeforeEach
	public void setup() {
		globals = JsePlatform.standardGlobals();
		script = new StringBuilder();
	}

	@Test
	public void invoke() {
		script.append("local systemVariant = ...");
		script.append("\n");
		script.append("return true");
		script.append("\n");

		SystemVariant systemVariant_mock = context.mock(SystemVariant.class);

		testable = new LuaScriptActionPreConditionChecker(globals, script.toString());
		assertTrue(testable.invoke(systemVariant_mock));
	}

	@Test
	public void newInstance() {
		final LuaScriptLine line_1 = new LuaScriptLine(1, "local a = ...");
		final LuaScriptLine line_2 = new LuaScriptLine(2, "return a + 2");
		final Collection<LuaScriptLine> lines = Arrays.asList(line_1, line_2);

		testable = new LuaScriptActionPreConditionChecker(globals, lines);
		assertEquals("local a = ...\nreturn a + 2\n", testable.getScript());
	}
}

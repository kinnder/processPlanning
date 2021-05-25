package planning.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
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
		testable = new LuaScript(globals, script.toString());

		assertEquals("lua-code", testable.getScript());
	}

	@Test
	public void newInstance() {
		final LuaScriptLine line_1 = new LuaScriptLine(1, "local a = ...");
		final LuaScriptLine line_2 = new LuaScriptLine(2, "return a + 2");
		final Collection<LuaScriptLine> lines = Arrays.asList(line_1, line_2);

		testable = new LuaScript(globals, lines);
		assertEquals("local a = ...\nreturn a + 2\n", testable.getScript());
	}

	@Test
	public void getScriptLines() {
		testable = new LuaScript(globals, "local a = ...\nreturn a + 2\n");

		final Collection<LuaScriptLine> lines = testable.getScriptLines();
		assertEquals(2, lines.size());
		Iterator<LuaScriptLine> linesIterator = lines.iterator();

		final LuaScriptLine line_1 = linesIterator.next();
		final LuaScriptLine line_2 = linesIterator.next();

		assertEquals(1, line_1.getNumber());
		assertEquals("local a = ...", line_1.getText());

		assertEquals(2, line_2.getNumber());
		assertEquals("return a + 2", line_2.getText());
	}
}

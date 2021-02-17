package planning.model;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;

public class LuaScript {

	private Globals globals;

	private String script;

	public LuaScript(Globals globals, String script) {
		this.globals = globals;
		this.script = script;
	}

	public LuaScript(Globals globals, Collection<LuaScriptLine> scriptLines) {
		this.globals = globals;
		StringBuilder scriptBuilder = new StringBuilder();
		for (LuaScriptLine scriptLine : scriptLines) {
			scriptBuilder.append(scriptLine.getText()).append("\n");
		}
		this.script = scriptBuilder.toString();
	}

	public String getScript() {
		return script;
	}

	public LuaValue call(LuaValue parameter) {
		return globals.load(script).call(parameter);
	}

	public Collection<LuaScriptLine> getScriptLines() {
		String lines[] = script.split("\n");
		Map<Integer, LuaScriptLine> scriptLines = new TreeMap<Integer, LuaScriptLine>();
		int number = 1;
		for (String line : lines) {
			scriptLines.put(number, new LuaScriptLine(number, line));
			number++;
		}
		return scriptLines.values();
	}
}

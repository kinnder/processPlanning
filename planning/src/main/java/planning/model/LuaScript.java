package planning.model;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;

public class LuaScript {

	private Globals globals;

	private String script;

	public LuaScript(Globals globals, String script) {
		this.globals = globals;
		this.script = script;
	}

	public String getScript() {
		return script;
	}

	public LuaValue call(LuaValue parameter) {
		return globals.load(script).call(parameter);
	}
}

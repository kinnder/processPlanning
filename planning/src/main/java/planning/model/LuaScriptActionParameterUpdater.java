package planning.model;

import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class LuaScriptActionParameterUpdater implements ActionParameterUpdater {

	private Globals globals;

	private String script;

	public LuaScriptActionParameterUpdater(Globals globals, String script) {
		this.globals = globals;
		this.script = script;
	}

	@Override
	public void invoke(SystemVariant systemVariant) {
		globals.load(script).call(CoerceJavaToLua.coerce(systemVariant));
	}

	public String getScript() {
		return script;
	}
}

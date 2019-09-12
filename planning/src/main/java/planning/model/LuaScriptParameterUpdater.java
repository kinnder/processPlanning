package planning.model;

import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class LuaScriptParameterUpdater implements ParameterUpdater {

	private Globals globals;

	private String script;

	public LuaScriptParameterUpdater(Globals globals, String script) {
		this.globals = globals;
		this.script = script;
	}

	@Override
	public void invoke(SystemVariant systemVariant) {
		globals.load(script).call(CoerceJavaToLua.coerce(systemVariant));
	}
}

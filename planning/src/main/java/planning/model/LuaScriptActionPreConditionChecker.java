package planning.model;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class LuaScriptActionPreConditionChecker implements ActionPreConditionChecker {

	private Globals globals;

	private String script;

	public LuaScriptActionPreConditionChecker(Globals globals, String script) {
		this.globals = globals;
		this.script = script;
	}

	@Override
	public boolean invoke(SystemVariant systemVariant) {
		LuaValue result = globals.load(script).call(CoerceJavaToLua.coerce(systemVariant));
		return result.toboolean();
	}
}

package planning.model;

import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class LuaScriptActionPreConditionChecker extends LuaScript implements ActionPreConditionChecker {

	public LuaScriptActionPreConditionChecker(Globals globals, String script) {
		super(globals, script);
	}

	@Override
	public boolean invoke(SystemVariant systemVariant) {
		return call(CoerceJavaToLua.coerce(systemVariant)).toboolean();
	}
}

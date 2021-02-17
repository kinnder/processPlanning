package planning.model;

import java.util.Collection;

import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class LuaScriptActionPreConditionChecker extends LuaScript implements ActionPreConditionChecker {

	public LuaScriptActionPreConditionChecker(Globals globals, String script) {
		super(globals, script);
	}

	public LuaScriptActionPreConditionChecker(Globals globals, Collection<LuaScriptLine> scriptLines) {
		super(globals, scriptLines);
	}

	@Override
	public boolean invoke(SystemVariant systemVariant) {
		return call(CoerceJavaToLua.coerce(systemVariant)).toboolean();
	}
}

package planning.model;

import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class LuaScriptActionParameterUpdater extends LuaScript implements ActionParameterUpdater {

	public LuaScriptActionParameterUpdater(Globals globals, String script) {
		super(globals, script);
	}

	@Override
	public void invoke(SystemVariant systemVariant) {
		call(CoerceJavaToLua.coerce(systemVariant));
	}
}

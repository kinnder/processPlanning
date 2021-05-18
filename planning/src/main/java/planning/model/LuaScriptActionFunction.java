package planning.model;

import java.util.Collection;

import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class LuaScriptActionFunction extends LuaScript implements ActionFunction {

	public LuaScriptActionFunction(Globals globals, String script) {
		super(globals, script);
	}

	public LuaScriptActionFunction(Globals globals, Collection<LuaScriptLine> scriptLines) {
		super(globals, scriptLines);
	}

	@Override
	public Object invoke(SystemVariant systemVariant) {
		return call(CoerceJavaToLua.coerce(systemVariant));
	}
}

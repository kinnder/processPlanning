package planning.model;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class ActionFunction extends LuaScript implements Consumer<SystemVariant>, Predicate<SystemVariant> {

	public ActionFunction(Globals globals, String script) {
		super(globals, script);
	}

	public ActionFunction(Globals globals, Collection<LuaScriptLine> scriptLines) {
		super(globals, scriptLines);
	}

	@Override
	public boolean test(SystemVariant systemVariant) {
		return call(CoerceJavaToLua.coerce(systemVariant)).toboolean();
	}

	@Override
	public void accept(SystemVariant systemVariant) {
		call(CoerceJavaToLua.coerce(systemVariant));
	}

	@Override
	public String toString() {
		return "action-function";
	}
}

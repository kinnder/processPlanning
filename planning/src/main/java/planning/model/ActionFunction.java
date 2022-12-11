package planning.model;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class ActionFunction extends LuaScript implements Consumer<SystemVariant>, Predicate<SystemVariant> {

	public final static int TYPE_UNKNOWN = 0;

	public final static int TYPE_PARAMETER_UPDATER = 1;

	public final static int TYPE_PRECONDITION_CHECKER = 2;

	// TODO (2022-12-11 #73): добавить поле в модель данных
	private int type = TYPE_UNKNOWN;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

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

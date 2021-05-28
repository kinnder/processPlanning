package application.storage.owl;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import org.apache.jena.ontology.Individual;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

import planning.model.ActionFunction;
import planning.model.LuaScriptActionFunction;
import planning.model.LuaScriptLine;

public class ActionFunctionOWLSchema implements OWLSchema<ActionFunction> {

	private SystemTransformationsOWLModel owlModel;

	private LuaScriptLineOWLSchema luaScriptLineOWLSchema;

	public ActionFunctionOWLSchema(SystemTransformationsOWLModel owlModel) {
		this(owlModel, new LuaScriptLineOWLSchema(owlModel));
	}

	ActionFunctionOWLSchema(SystemTransformationsOWLModel owlModel, LuaScriptLineOWLSchema luaScriptLineOWLSchema) {
		this.owlModel = owlModel;
		this.luaScriptLineOWLSchema = luaScriptLineOWLSchema;
	}

	@Override
	public Individual combine(ActionFunction actionFunction) {
		Individual ind_actionFunction = owlModel.newIndividual_ActionFunction();
		LuaScriptActionFunction luaActionFunction = (LuaScriptActionFunction) actionFunction;
		Collection<LuaScriptLine> scriptLines = luaActionFunction.getScriptLines();
		for (LuaScriptLine scriptLine : scriptLines) {
			Individual ind_line = luaScriptLineOWLSchema.combine(scriptLine);
			ind_line.addProperty(owlModel.getObjectProperty_isLineOf(), ind_actionFunction);
			ind_actionFunction.addProperty(owlModel.getObjectProperty_hasLine(), ind_line);
		}
		return ind_actionFunction;
	}

	// TODO : пересмотреть положение globals
	private static Globals globals = JsePlatform.standardGlobals();

	@Override
	public ActionFunction parse(Individual ind_actionFunction) {
		Map<Integer, LuaScriptLine> scriptLines = new TreeMap<Integer, LuaScriptLine>();
		owlModel.getClass_Line().listInstances().filterKeep((ind_line) -> {
			return ind_actionFunction.hasProperty(owlModel.getObjectProperty_hasLine(), ind_line);
		}).forEachRemaining((ind_line) -> {
			LuaScriptLine scriptLine = luaScriptLineOWLSchema.parse(ind_line.asIndividual());
			scriptLines.put(scriptLine.getNumber(), scriptLine);
		});
		return new LuaScriptActionFunction(globals, scriptLines.values());
	}
}

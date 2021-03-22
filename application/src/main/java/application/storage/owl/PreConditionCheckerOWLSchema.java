package application.storage.owl;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import org.apache.jena.ontology.Individual;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

import planning.model.ActionPreConditionChecker;
import planning.model.LuaScriptActionPreConditionChecker;
import planning.model.LuaScriptLine;

public class PreConditionCheckerOWLSchema implements OWLSchema<ActionPreConditionChecker> {

	private SystemTransformationsOWLModel owlModel;

	private LuaScriptLineOWLSchema luaScriptLineOWLSchema;

	public PreConditionCheckerOWLSchema(SystemTransformationsOWLModel owlModel) {
		this.owlModel = owlModel;
		this.luaScriptLineOWLSchema = new LuaScriptLineOWLSchema(owlModel);
	}

	@Override
	public Individual combine(ActionPreConditionChecker preConditionChecker) {
		Individual ind_preConditionChecker = owlModel.newIndividual_PreConditionChecker();
		LuaScriptActionPreConditionChecker luaPreConditionChecker = (LuaScriptActionPreConditionChecker) preConditionChecker;
		Collection<LuaScriptLine> scriptLines = luaPreConditionChecker.getScriptLines();
		for (LuaScriptLine scriptLine : scriptLines) {
			Individual ind_line = luaScriptLineOWLSchema.combine(scriptLine);
			ind_line.addProperty(owlModel.getObjectProperty_isLineOf(), ind_preConditionChecker);
			ind_preConditionChecker.addProperty(owlModel.getObjectProperty_hasLine(), ind_line);
		}
		return ind_preConditionChecker;
	}

	// TODO : пересмотреть положение globals
	private static Globals globals = JsePlatform.standardGlobals();

	@Override
	public ActionPreConditionChecker parse(Individual ind_preConditionChecker) {
		Map<Integer, LuaScriptLine> scriptLines = new TreeMap<Integer, LuaScriptLine>();
		owlModel.getClass_Line().listInstances().filterKeep((ind_line) -> {
			return ind_preConditionChecker.hasProperty(owlModel.getObjectProperty_hasLine(), ind_line);
		}).forEachRemaining((ind_line) -> {
			LuaScriptLine scriptLine = luaScriptLineOWLSchema.parse(ind_line.asIndividual());
			scriptLines.put(scriptLine.getNumber(), scriptLine);
		});
		return new LuaScriptActionPreConditionChecker(globals, scriptLines.values());
	}
}

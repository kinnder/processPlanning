package application.storage.owl;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import org.apache.jena.ontology.Individual;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

import planning.model.ActionParameterUpdater;
import planning.model.LuaScriptActionParameterUpdater;
import planning.model.LuaScriptLine;

//TODO : rename schema to match generic class
public class ParameterUpdaterOWLSchema implements OWLSchema<ActionParameterUpdater> {

	private SystemTransformationsOWLModel owlModel;

	private LuaScriptLineOWLSchema luaScriptLineOWLSchema;

	public ParameterUpdaterOWLSchema(SystemTransformationsOWLModel owlModel) {
		this.owlModel = owlModel;
		this.luaScriptLineOWLSchema = new LuaScriptLineOWLSchema(owlModel);
	}

	@Override
	public Individual combine(ActionParameterUpdater parameterUpdater) {
		Individual ind_parameterUpdater = owlModel.getClass_ParameterUpdater().createIndividual(owlModel.getUniqueURI());
		LuaScriptActionParameterUpdater luaParameterUpdater = (LuaScriptActionParameterUpdater) parameterUpdater;
		Collection<LuaScriptLine> scriptLines = luaParameterUpdater.getScriptLines();
		for (LuaScriptLine scriptLine : scriptLines) {
			Individual ind_line = luaScriptLineOWLSchema.combine(scriptLine);
			ind_line.addProperty(owlModel.getObjectProperty_isLineOf(), ind_parameterUpdater);
			ind_parameterUpdater.addProperty(owlModel.getObjectProperty_hasLine(), ind_line);
		}
		return ind_parameterUpdater;
	}

	// TODO : пересмотреть положение globals
	private static Globals globals = JsePlatform.standardGlobals();

	@Override
	public ActionParameterUpdater parse(Individual ind_parameterUpdater) {
		Map<Integer, LuaScriptLine> scriptLines = new TreeMap<Integer, LuaScriptLine>();
		owlModel.getClass_Line().listInstances().filterKeep((ind_line) -> {
			return ind_parameterUpdater.hasProperty(owlModel.getObjectProperty_hasLine(), ind_line);
		}).forEachRemaining((ind_line) -> {
			LuaScriptLine scriptLine = luaScriptLineOWLSchema.parse(ind_line.asIndividual());
			scriptLines.put(scriptLine.getNumber(), scriptLine);
		});
		return new LuaScriptActionParameterUpdater(globals, scriptLines.values());
	}
}

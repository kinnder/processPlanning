package application.storage.owl;

import java.util.HashMap;
import java.util.Map;

import org.apache.jena.ontology.Individual;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

import planning.model.ActionParameterUpdater;
import planning.model.LuaScriptActionParameterUpdater;

//TODO : rename schema to match generic class
public class ParameterUpdaterOWLSchema implements OWLSchema<ActionParameterUpdater> {

	private SystemTransformationsOWLModel owlModel;

	public ParameterUpdaterOWLSchema(SystemTransformationsOWLModel owlModel) {
		this.owlModel = owlModel;
	}

	@Override
	public Individual combine(ActionParameterUpdater parameterUpdater) {
		Individual ind_parameterUpdater = owlModel.getClass_ParameterUpdater()
				.createIndividual(owlModel.getUniqueURI());
		LuaScriptActionParameterUpdater luaParameterUpdater = (LuaScriptActionParameterUpdater) parameterUpdater;
		String lines[] = luaParameterUpdater.getScript().split("\n");
		for (int i = 0; i < lines.length; i++) {
			// << Individual: Line
			Individual ind_line = owlModel.getClass_line().createIndividual(owlModel.getUniqueURI());
			ind_line.addProperty(owlModel.getDataProperty_number(), Integer.toString(i + 1));
			ind_line.addProperty(owlModel.getDataProperty_text(), lines[i]);
			ind_line.addProperty(owlModel.getObjectProperty_isLineOf(), ind_parameterUpdater);
			ind_parameterUpdater.addProperty(owlModel.getObjectProperty_hasLine(), ind_line);
			// >> Individual: Line
		}
		return ind_parameterUpdater;
	}

	// TODO : пересмотреть положение globals
	private static Globals globals = JsePlatform.standardGlobals();

	// TODO : lines has separate schema

	@Override
	public ActionParameterUpdater parse(Individual ind_parameterUpdater) {
		Map<Integer, String> lines = new HashMap<>();
		owlModel.getClass_line().listInstances().filterKeep((ind_line) -> {
			return ind_parameterUpdater.hasProperty(owlModel.getObjectProperty_hasLine(), ind_line);
		}).forEachRemaining((ind_line) -> {
			int id = ind_line.getProperty(owlModel.getDataProperty_number()).getInt() - 1;
			String text = ind_line.getProperty(owlModel.getDataProperty_text()).getString();
			lines.put(id, text);
		});
		int amount = lines.keySet().size();
		StringBuilder script = new StringBuilder();
		for (int i = 0; i < amount; i++) {
			script.append(lines.get(i)).append("\n");
		}
		return new LuaScriptActionParameterUpdater(globals, script.toString());
	}
}

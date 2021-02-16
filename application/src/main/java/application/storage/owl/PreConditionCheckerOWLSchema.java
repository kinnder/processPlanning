package application.storage.owl;

import java.util.HashMap;
import java.util.Map;

import org.apache.jena.ontology.Individual;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

import planning.model.ActionPreConditionChecker;
import planning.model.LuaScriptActionPreConditionChecker;

public class PreConditionCheckerOWLSchema implements OWLSchema<ActionPreConditionChecker> {

	private SystemTransformationsOWLModel owlModel;

	public PreConditionCheckerOWLSchema(SystemTransformationsOWLModel owlModel) {
		this.owlModel = owlModel;
	}

	@Override
	public Individual combine(ActionPreConditionChecker preConditionChecker) {
		Individual ind_preConditionChecker = owlModel.getClass_PreConditionChecker()
				.createIndividual(owlModel.getUniqueURI());
		LuaScriptActionPreConditionChecker luaPreConditionChecker = (LuaScriptActionPreConditionChecker) preConditionChecker;
		String lines[] = luaPreConditionChecker.getScript().split("\n");
		for (int i = 0; i < lines.length; i++) {
			// << Individual: Line
			Individual ind_line = owlModel.getClass_line().createIndividual(owlModel.getUniqueURI());
			ind_line.addProperty(owlModel.getDataProperty_number(), Integer.toString(i + 1));
			ind_line.addProperty(owlModel.getDataProperty_text(), lines[i]);
			ind_line.addProperty(owlModel.getObjectProperty_isLineOf(), ind_preConditionChecker);
			ind_preConditionChecker.addProperty(owlModel.getObjectProperty_hasLine(), ind_line);
			// >> Individual: Line
		}
		return ind_preConditionChecker;
	}

	// TODO : пересмотреть положение globals
	private static Globals globals = JsePlatform.standardGlobals();

	// TODO : lines has separate schema

	@Override
	public ActionPreConditionChecker parse(Individual individual) {
		Map<Integer, String> lines = new HashMap<>();
		owlModel.getClass_line().listInstances().filterKeep((ind_line) -> {
			return individual.hasProperty(owlModel.getObjectProperty_hasLine(), ind_line);
		}).forEachRemaining((int_line) -> {
			int id = int_line.getProperty(owlModel.getDataProperty_number()).getInt() - 1;
			String text = int_line.getProperty(owlModel.getDataProperty_text()).getString();
			lines.put(id, text);
		});
		int amount = lines.keySet().size();
		StringBuilder script = new StringBuilder();
		for (int i = 0; i < amount; i++) {
			script.append(lines.get(i)).append("\n");
		}
		return new LuaScriptActionPreConditionChecker(globals, script.toString());
	}

}

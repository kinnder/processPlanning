package application.storage.owl;

import org.apache.jena.ontology.Individual;

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

	@Override
	public ActionPreConditionChecker parse(Individual individual) {
		// TODO Auto-generated method stub
		return null;
	}

}

package application.storage.owl;

import java.util.UUID;

import org.apache.jena.ontology.Individual;
import planning.model.Action;
import planning.model.ActionParameterUpdater;
import planning.model.ActionPreConditionChecker;
import planning.model.LuaScriptActionParameterUpdater;
import planning.model.LuaScriptActionPreConditionChecker;

public class ActionOWLSchema implements OWLSchema<Action> {

	private SystemTransformationsOWLModel owlModel;

	public ActionOWLSchema(SystemTransformationsOWLModel owlModel) {
		this.owlModel = owlModel;
	}

	@Override
	public Individual combine(Action action) {
		Individual ind_action = owlModel.getClass_Action()
				.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
		ind_action.addProperty(owlModel.getDataProperty_name(), action.getName());
		for (ActionPreConditionChecker preConditionChecker : action.getPreConditionCheckers()) {
			// << Individual: PreConditionChecker
			Individual ind_preConditionChecker = owlModel.getClass_PreConditionChecker()
					.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
			LuaScriptActionPreConditionChecker luaPreConditionChecker = (LuaScriptActionPreConditionChecker) preConditionChecker;
			String lines[] = luaPreConditionChecker.getScript().split("\n");
			for (int i = 0; i < lines.length; i++) {
				// << Individual: Line
				Individual ind_line = owlModel.getClass_line()
						.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
				ind_line.addProperty(owlModel.getDataProperty_number(), Integer.toString(i + 1));
				ind_line.addProperty(owlModel.getDataProperty_text(), lines[i]);
				ind_line.addProperty(owlModel.getObjectProperty_isLineOf(), ind_preConditionChecker);
				ind_preConditionChecker.addProperty(owlModel.getObjectProperty_hasLine(), ind_line);
				// >> Individual: Line
			}
			ind_action.addProperty(owlModel.getObjectProperty_hasPreConditionChecker(), ind_preConditionChecker);
			ind_preConditionChecker.addProperty(owlModel.getObjectProperty_isPreConditionCheckerOf(), ind_action);
			// >> Individual: PreConditionChecker
		}
		for (ActionParameterUpdater parameterUpdater : action.getParameterUpdaters()) {
			// << Individual: ParameterUpdater
			Individual ind_parameterUpdater = owlModel.getClass_ParameterUpdater()
					.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
			LuaScriptActionParameterUpdater luaParameterUpdater = (LuaScriptActionParameterUpdater) parameterUpdater;
			String lines[] = luaParameterUpdater.getScript().split("\n");
			for (int i = 0; i < lines.length; i++) {
				// << Individual: Line
				Individual ind_line = owlModel.getClass_line()
						.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
				ind_line.addProperty(owlModel.getDataProperty_number(), Integer.toString(i + 1));
				ind_line.addProperty(owlModel.getDataProperty_text(), lines[i]);
				ind_line.addProperty(owlModel.getObjectProperty_isLineOf(), ind_parameterUpdater);
				ind_parameterUpdater.addProperty(owlModel.getObjectProperty_hasLine(), ind_line);
				// >> Individual: Line
			}
			ind_action.addProperty(owlModel.getObjectProperty_hasParameterUpdater(), ind_parameterUpdater);
			ind_parameterUpdater.addProperty(owlModel.getObjectProperty_isParameterUpdaterOf(), ind_action);
			// >> Individual: ParameterUpdater
		}
		return ind_action;
	}

	@Override
	public Action parse(Individual individual) {
		// TODO Auto-generated method stub
		return null;
	}
}

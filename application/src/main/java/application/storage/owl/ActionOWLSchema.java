package application.storage.owl;

import org.apache.jena.ontology.Individual;
import planning.model.Action;
import planning.model.ActionFunction;
import planning.model.LuaScriptActionFunction;

public class ActionOWLSchema implements OWLSchema<Action> {

	private SystemTransformationsOWLModel owlModel;

	private LuaScriptActionFunctionOWLSchema luaScriptActionFunctionOWLSchema;

	public ActionOWLSchema(SystemTransformationsOWLModel owlModel) {
		this(owlModel, new LuaScriptActionFunctionOWLSchema(owlModel));
	}

	ActionOWLSchema(SystemTransformationsOWLModel owlModel, LuaScriptActionFunctionOWLSchema luaScriptActionFunctionOWLSchema) {
		this.owlModel = owlModel;
		this.luaScriptActionFunctionOWLSchema = luaScriptActionFunctionOWLSchema;
	}

	@Override
	public Individual combine(Action action) {
		Individual ind_action = owlModel.newIndividual_Action();
		String name = action.getName();
		ind_action.addLabel(String.format("Действие \"%s\"", name), "ru");
		ind_action.addLabel(String.format("Action \"%s\"", name), "en");
		ind_action.addProperty(owlModel.getDataProperty_name(), name);
		for (ActionFunction preConditionChecker : action.getPreConditionCheckers()) {
			Individual ind_preConditionChecker = luaScriptActionFunctionOWLSchema.combine((LuaScriptActionFunction) preConditionChecker);
			ind_action.addProperty(owlModel.getObjectProperty_hasPreConditionChecker(), ind_preConditionChecker);
			ind_preConditionChecker.addProperty(owlModel.getObjectProperty_isPreConditionCheckerOf(), ind_action);
		}
		for (ActionFunction parameterUpdater : action.getParameterUpdaters()) {
			Individual ind_parameterUpdater = luaScriptActionFunctionOWLSchema.combine((LuaScriptActionFunction) parameterUpdater);
			ind_action.addProperty(owlModel.getObjectProperty_hasParameterUpdater(), ind_parameterUpdater);
			ind_parameterUpdater.addProperty(owlModel.getObjectProperty_isParameterUpdaterOf(), ind_action);
		}
		return ind_action;
	}

	@Override
	public Action parse(Individual ind_action) {
		String name = ind_action.getProperty(owlModel.getDataProperty_name()).getString();
		Action action = new Action(name);
		owlModel.getClass_ActionFunction().listInstances().filterKeep((ind_preConditionChecker) -> {
			return ind_action.hasProperty(owlModel.getObjectProperty_hasPreConditionChecker(), ind_preConditionChecker);
		}).forEachRemaining((ind_preConditionChecker) -> {
			ActionFunction preConditionChecker = luaScriptActionFunctionOWLSchema.parse(ind_preConditionChecker.asIndividual());
			action.registerPreConditionChecker(preConditionChecker);
		});
		owlModel.getClass_ActionFunction().listInstances().filterKeep((ind_parameterUpdater) -> {
			return ind_action.hasProperty(owlModel.getObjectProperty_hasParameterUpdater(), ind_parameterUpdater);
		}).forEachRemaining((ind_parameterUpdater) -> {
			ActionFunction parameterUpdater = luaScriptActionFunctionOWLSchema.parse(ind_parameterUpdater.asIndividual());
			action.registerParameterUpdater(parameterUpdater);
		});
		return action;
	}
}

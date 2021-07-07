package application.storage.owl;

import org.apache.jena.ontology.Individual;
import planning.model.Action;
import planning.model.ActionFunction;
import planning.model.LuaScriptActionFunction;

public class ActionOWLSchema implements OWLSchema<Action> {

	private SystemTransformationsOWLModel owlModel;

	private ActionFunctionOWLSchema actionFunctionOWLSchema;

	public ActionOWLSchema(SystemTransformationsOWLModel owlModel) {
		this(owlModel, new ActionFunctionOWLSchema(owlModel));
	}

	ActionOWLSchema(SystemTransformationsOWLModel owlModel, ActionFunctionOWLSchema actionFunctionOWLSchema) {
		this.owlModel = owlModel;
		this.actionFunctionOWLSchema = actionFunctionOWLSchema;
	}

	@Override
	public Individual combine(Action action) {
		Individual ind_action = owlModel.newIndividual_Action();
		String name = action.getName();
		ind_action.addLabel(String.format("Действие \"%s\"", name), "ru");
		ind_action.addLabel(String.format("Action \"%s\"", name), "en");
		ind_action.addProperty(owlModel.getDataProperty_name(), name);
		for (ActionFunction preConditionChecker : action.getPreConditionCheckers()) {
			Individual ind_preConditionChecker = actionFunctionOWLSchema.combine((LuaScriptActionFunction) preConditionChecker);
			ind_action.addProperty(owlModel.getObjectProperty_hasPreConditionChecker(), ind_preConditionChecker);
		}
		for (ActionFunction parameterUpdater : action.getParameterUpdaters()) {
			Individual ind_parameterUpdater = actionFunctionOWLSchema.combine((LuaScriptActionFunction) parameterUpdater);
			ind_action.addProperty(owlModel.getObjectProperty_hasParameterUpdater(), ind_parameterUpdater);
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
			ActionFunction preConditionChecker = actionFunctionOWLSchema.parse(ind_preConditionChecker.asIndividual());
			action.registerPreConditionChecker(preConditionChecker);
		});
		owlModel.getClass_ActionFunction().listInstances().filterKeep((ind_parameterUpdater) -> {
			return ind_action.hasProperty(owlModel.getObjectProperty_hasParameterUpdater(), ind_parameterUpdater);
		}).forEachRemaining((ind_parameterUpdater) -> {
			ActionFunction parameterUpdater = actionFunctionOWLSchema.parse(ind_parameterUpdater.asIndividual());
			action.registerParameterUpdater(parameterUpdater);
		});
		return action;
	}
}

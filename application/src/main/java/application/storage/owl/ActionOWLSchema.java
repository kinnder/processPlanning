package application.storage.owl;

import org.apache.jena.ontology.Individual;
import planning.model.Action;
import planning.model.ActionParameterUpdater;
import planning.model.ActionPreConditionChecker;

public class ActionOWLSchema implements OWLSchema<Action> {

	private SystemTransformationsOWLModel owlModel;

	private ParameterUpdaterOWLSchema parameterUpdaterOWLSchema;

	private PreConditionCheckerOWLSchema preConditionCheckerOWLSchema;

	public ActionOWLSchema(SystemTransformationsOWLModel owlModel) {
		this(owlModel, new ParameterUpdaterOWLSchema(owlModel), new PreConditionCheckerOWLSchema(owlModel));
	}

	ActionOWLSchema(SystemTransformationsOWLModel owlModel, ParameterUpdaterOWLSchema parameterUpdaterOWLSchema, PreConditionCheckerOWLSchema preConditionCheckerOWLSchema) {
		this.owlModel = owlModel;
		this.parameterUpdaterOWLSchema = parameterUpdaterOWLSchema;
		this.preConditionCheckerOWLSchema = preConditionCheckerOWLSchema;
	}

	@Override
	public Individual combine(Action action) {
		Individual ind_action = owlModel.newIndividual_Action();
		ind_action.addProperty(owlModel.getDataProperty_name(), action.getName());
		for (ActionPreConditionChecker preConditionChecker : action.getPreConditionCheckers()) {
			Individual ind_preConditionChecker = preConditionCheckerOWLSchema.combine(preConditionChecker);
			ind_action.addProperty(owlModel.getObjectProperty_hasPreConditionChecker(), ind_preConditionChecker);
			ind_preConditionChecker.addProperty(owlModel.getObjectProperty_isPreConditionCheckerOf(), ind_action);
		}
		for (ActionParameterUpdater parameterUpdater : action.getParameterUpdaters()) {
			Individual ind_parameterUpdater = parameterUpdaterOWLSchema.combine(parameterUpdater);
			ind_action.addProperty(owlModel.getObjectProperty_hasParameterUpdater(), ind_parameterUpdater);
			ind_parameterUpdater.addProperty(owlModel.getObjectProperty_isParameterUpdaterOf(), ind_action);
		}
		return ind_action;
	}

	@Override
	public Action parse(Individual ind_action) {
		String name = ind_action.getProperty(owlModel.getDataProperty_name()).getString();
		Action action = new Action(name);
		owlModel.getClass_PreConditionChecker().listInstances().filterKeep((ind_preConditionChecker) -> {
			return ind_action.hasProperty(owlModel.getObjectProperty_hasPreConditionChecker(), ind_preConditionChecker);
		}).forEachRemaining((ind_preConditionChecker) -> {
			ActionPreConditionChecker preConditionChecker = preConditionCheckerOWLSchema.parse(ind_preConditionChecker.asIndividual());
			action.registerActionPreConditionChecker(preConditionChecker);
		});
		owlModel.getClass_ParameterUpdater().listInstances().filterKeep((ind_parameterUpdater) -> {
			return ind_action.hasProperty(owlModel.getObjectProperty_hasParameterUpdater(), ind_parameterUpdater);
		}).forEachRemaining((ind_parameterUpdater) -> {
			ActionParameterUpdater parameterUpdater = parameterUpdaterOWLSchema.parse(ind_parameterUpdater.asIndividual());
			action.registerActionParameterUpdater(parameterUpdater);
		});
		return action;
	}
}

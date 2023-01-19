package application.storage.owl;

import org.apache.jena.ontology.Individual;
import planning.model.Action;
import planning.model.SystemTemplate;
import planning.model.SystemTransformation;
import planning.model.Transformations;

public class SystemTransformationOWLSchema implements OWLSchema<SystemTransformation> {

	private ActionOWLSchema actionOWLSchema;

	private SystemTemplateOWLSchema systemTemplateOWLSchema;

	private TransformationsOWLSchema transformationsOWLSchema;

	private PlanningOWLModel owlModel;

	public SystemTransformationOWLSchema(PlanningOWLModel owlModel) {
		this(owlModel, new ActionOWLSchema(owlModel), new SystemTemplateOWLSchema(owlModel),
				new TransformationsOWLSchema(owlModel));
	}

	SystemTransformationOWLSchema(PlanningOWLModel owlModel, ActionOWLSchema actionOWLSchema,
			SystemTemplateOWLSchema systemTemplateOWLSchema, TransformationsOWLSchema transformationOWLSchema) {
		this.owlModel = owlModel;
		this.actionOWLSchema = actionOWLSchema;
		this.systemTemplateOWLSchema = systemTemplateOWLSchema;
		this.transformationsOWLSchema = transformationOWLSchema;
	}

	@Override
	public Individual combine(SystemTransformation systemTransformation) {
		final Individual ind_systemTransformation = owlModel.newIndividual_SystemTransformation();
		final String name = systemTransformation.getName();
		ind_systemTransformation.addLabel(String.format("System Transformation \"%s\"", name), "en");
		ind_systemTransformation.addLabel(String.format("Трансформация системы \"%s\"", name), "ru");
		ind_systemTransformation.addProperty(owlModel.getDataProperty_name(), name);

		final Individual ind_action = actionOWLSchema.combine(systemTransformation.getAction());
		ind_systemTransformation.addProperty(owlModel.getObjectProperty_hasAction(), ind_action);

		final Individual ind_systemTemplate = systemTemplateOWLSchema.combine(systemTransformation.getSystemTemplate());
		ind_systemTransformation.addProperty(owlModel.getObjectProperty_hasSystemTemplate(), ind_systemTemplate);

		final Individual ind_transformations = transformationsOWLSchema.combine(systemTransformation.getTransformations());
		ind_systemTransformation.addProperty(owlModel.getObjectProperty_hasTransformations(), ind_transformations);

		return ind_systemTransformation;
	}

	@Override
	public SystemTransformation parse(Individual ind_systemTransformation) {
		final String name = ind_systemTransformation.getProperty(owlModel.getDataProperty_name()).getString();
		final SystemTransformation systemTransformation = new SystemTransformation(name);
		owlModel.getClass_Action().listInstances().filterKeep((ind_action) -> {
			return ind_systemTransformation.hasProperty(owlModel.getObjectProperty_hasAction(), ind_action);
		}).forEachRemaining((ind_action) -> {
			final Action action = actionOWLSchema.parse(ind_action.asIndividual());
			systemTransformation.setAction(action);
		});
		owlModel.getClass_SystemTemplate().listInstances().filterKeep((ind_systemTemplate) -> {
			return ind_systemTransformation.hasProperty(owlModel.getObjectProperty_hasSystemTemplate(), ind_systemTemplate);
		}).forEachRemaining((ind_systemTemplate) -> {
			final SystemTemplate systemTemplate = systemTemplateOWLSchema.parse(ind_systemTemplate.asIndividual());
			systemTransformation.setSystemTemplate(systemTemplate);
		});
		owlModel.getClass_Transformations().listInstances().filterKeep((ind_transformations) -> {
			return ind_systemTransformation.hasProperty(owlModel.getObjectProperty_hasTransformations(), ind_transformations);
		}).forEachRemaining((ind_transformations) -> {
			final Transformations transformations = transformationsOWLSchema.parse(ind_transformations.asIndividual());
			systemTransformation.setTransformations(transformations);
		});
		return systemTransformation;
	}
}

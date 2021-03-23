package application.storage.owl;

import org.apache.jena.ontology.Individual;
import planning.model.Action;
import planning.model.SystemTemplate;
import planning.model.SystemTransformation;
import planning.model.Transformation;

public class SystemTransformationOWLSchema implements OWLSchema<SystemTransformation> {

	private ActionOWLSchema actionOWLSchema;

	private SystemTemplateOWLSchema systemTemplateOWLSchema;

	private TransformationsOWLSchema transformationsOWLSchema;

	private SystemTransformationsOWLModel owlModel;

	public SystemTransformationOWLSchema(SystemTransformationsOWLModel owlModel) {
		this(owlModel, new ActionOWLSchema(owlModel), new SystemTemplateOWLSchema(owlModel),
				new TransformationsOWLSchema(owlModel));
	}

	SystemTransformationOWLSchema(SystemTransformationsOWLModel owlModel, ActionOWLSchema actionOWLSchema,
			SystemTemplateOWLSchema systemTemplateOWLSchema, TransformationsOWLSchema transformationOWLSchema) {
		this.owlModel = owlModel;
		this.actionOWLSchema = actionOWLSchema;
		this.systemTemplateOWLSchema = systemTemplateOWLSchema;
		this.transformationsOWLSchema = transformationOWLSchema;
	}

	@Override
	public Individual combine(SystemTransformation systemTransformation) {
		Individual ind_systemTransformation = owlModel.newIndividual_SystemTransformation();
		ind_systemTransformation.addProperty(owlModel.getDataProperty_name(), systemTransformation.getName());

		Individual ind_action = actionOWLSchema.combine(systemTransformation.getAction());
		ind_action.addProperty(owlModel.getObjectProperty_isActionOf(), ind_systemTransformation);
		ind_systemTransformation.addProperty(owlModel.getObjectProperty_hasAction(), ind_action);

		Individual ind_systemTemplate = systemTemplateOWLSchema.combine(systemTransformation.getSystemTemplate());
		ind_systemTemplate.addProperty(owlModel.getObjectProperty_isSystemTemplateOf(), ind_systemTransformation);
		ind_systemTransformation.addProperty(owlModel.getObjectProperty_hasSystemTemplate(), ind_systemTemplate);

		Individual ind_transformations = transformationsOWLSchema.combine(systemTransformation.getTransformations());
		ind_transformations.addProperty(owlModel.getObjectProperty_areTransformationsOf(), ind_systemTransformation);
		ind_systemTransformation.addProperty(owlModel.getObjectProperty_hasTransformations(), ind_transformations);

		return ind_systemTransformation;
	}

	@Override
	public SystemTransformation parse(Individual ind_systemTransformation) {
		String name = ind_systemTransformation.getProperty(owlModel.getDataProperty_name()).getString();
		SystemTransformation systemTransformation = new SystemTransformation(name);
		owlModel.getClass_Action().listInstances().filterKeep((ind_action) -> {
			return ind_systemTransformation.hasProperty(owlModel.getObjectProperty_hasAction(), ind_action);
		}).forEachRemaining((ind_action) -> {
			Action action = actionOWLSchema.parse(ind_action.asIndividual());
			systemTransformation.setAction(action);
		});
		owlModel.getClass_SystemTemplate().listInstances().filterKeep((ind_systemTemplate) -> {
			return ind_systemTransformation.hasProperty(owlModel.getObjectProperty_hasSystemTemplate(), ind_systemTemplate);
		}).forEachRemaining((ind_systemTemplate) -> {
			SystemTemplate systemTemplate = systemTemplateOWLSchema.parse(ind_systemTemplate.asIndividual());
			systemTransformation.setSystemTemplate(systemTemplate);
		});
		owlModel.getClass_Transformations().listInstances().filterKeep((ind_transformations) -> {
			return ind_systemTransformation.hasProperty(owlModel.getObjectProperty_hasTransformations(), ind_transformations);
		}).forEachRemaining((ind_transformations) -> {
			Transformation[] transformations = transformationsOWLSchema.parse(ind_transformations.asIndividual());
			systemTransformation.setTransformations(transformations);
		});
		return systemTransformation;
	}
}

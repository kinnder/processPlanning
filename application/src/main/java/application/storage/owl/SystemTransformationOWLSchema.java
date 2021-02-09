package application.storage.owl;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;

import planning.model.Action;
import planning.model.SystemTemplate;
import planning.model.SystemTransformation;
import planning.model.Transformation;

public class SystemTransformationOWLSchema implements OWLSchema<SystemTransformation> {

	private ActionOWLSchema actionOWLSchema;

	private SystemTemplateOWLSchema systemTemplateOWLSchema;

	private TransformationsOWLSchema transformationsOWLSchema;

	public SystemTransformationOWLSchema() {
		actionOWLSchema = new ActionOWLSchema();
		systemTemplateOWLSchema = new SystemTemplateOWLSchema();
		transformationsOWLSchema = new TransformationsOWLSchema();
	}

	private OntClass class_SystemTransformation;

	private OntClass class_SystemTemplate;

	private OntClass class_Transformations;

	private OntClass class_Action;

	private ObjectProperty objectProperty_hasSystemTemplate;

	private ObjectProperty objectProperty_isSystemTemplateOf;

	private ObjectProperty objectProperty_hasTransformations;

	private ObjectProperty objectProperty_areTransformationsOf;

	private ObjectProperty objectProperty_hasAction;

	private ObjectProperty objectProperty_isActionOf;

	private DatatypeProperty dataProperty_name;

	@Override
	public Individual combine(SystemTransformation systemTransformation) {
		Individual ind_systemTransformation = class_SystemTransformation.createIndividual(SystemTransformationsOWLModel.getUniqueIndividualURI());
		ind_systemTransformation.addProperty(dataProperty_name, systemTransformation.getName());

		Individual ind_action = actionOWLSchema.combine(systemTransformation.getAction());
		ind_action.addProperty(objectProperty_isActionOf, ind_systemTransformation);
		ind_systemTransformation.addProperty(objectProperty_hasAction, ind_action);

		Individual ind_systemTemplate = systemTemplateOWLSchema.combine(systemTransformation.getSystemTemplate());
		ind_systemTemplate.addProperty(objectProperty_isSystemTemplateOf, ind_systemTransformation);
		ind_systemTransformation.addProperty(objectProperty_hasSystemTemplate, ind_systemTemplate);

		Individual ind_transformations = transformationsOWLSchema.combine(systemTransformation.getTransformations());
		ind_transformations.addProperty(objectProperty_areTransformationsOf, ind_systemTransformation);
		ind_systemTransformation.addProperty(objectProperty_hasTransformations, ind_transformations);

		return ind_systemTransformation;
	}

	@Override
	public SystemTransformation parse(Individual ind_systemTransformation) {
		String name = ind_systemTransformation.getProperty(dataProperty_name).getString();
		SystemTransformation systemTransformation = new SystemTransformation(name);
		class_Action.listInstances().filterKeep((ind_action) -> {
			return ind_systemTransformation.hasProperty(objectProperty_hasAction, ind_action);
		}).forEachRemaining((ind_action) -> {
			Action action = actionOWLSchema.parse(ind_action.asIndividual());
			systemTransformation.setAction(action);
		});
		class_SystemTemplate.listInstances().filterKeep((ind_systemTemplate) -> {
			return ind_systemTransformation.hasProperty(objectProperty_hasSystemTemplate, ind_systemTemplate);
		}).forEachRemaining((ind_systemTemplate) -> {
			SystemTemplate systemTemplate = systemTemplateOWLSchema.parse(ind_systemTemplate.asIndividual());
			systemTransformation.setSystemTemplate(systemTemplate);
		});
		class_Transformations.listInstances().filterKeep((ind_transformations) -> {
			return ind_systemTransformation.hasProperty(objectProperty_hasTransformations, ind_transformations);
		}).forEachRemaining((ind_transformations) -> {
			Transformation[] transformations = transformationsOWLSchema.parse(ind_transformations.asIndividual());
			systemTransformation.setTransformations(transformations);
		});
		return systemTransformation;
	}

	@Override
	public void connectOntologyModel(OntModel ontModel) {
		class_SystemTransformation = ontModel.getOntClass(SystemTransformationsOWLModel.URI_SystemTransformation);
		class_SystemTemplate = ontModel.getOntClass(SystemTransformationsOWLModel.URI_SystemTemplate);
		class_Transformations = ontModel.getOntClass(SystemTransformationsOWLModel.URI_Transformations);
		class_Action = ontModel.getOntClass(SystemTransformationsOWLModel.URI_Action);

		objectProperty_hasSystemTemplate = ontModel.getObjectProperty(SystemTransformationsOWLModel.URI_hasSystemTemplate);
		objectProperty_isSystemTemplateOf = ontModel.getObjectProperty(SystemTransformationsOWLModel.URI_isSystemTemplateOf);
		objectProperty_hasTransformations = ontModel.getObjectProperty(SystemTransformationsOWLModel.URI_hasTransformations);
		objectProperty_areTransformationsOf = ontModel.getObjectProperty(SystemTransformationsOWLModel.URI_areTransformationsOf);
		objectProperty_hasAction = ontModel.getObjectProperty(SystemTransformationsOWLModel.URI_hasAction);
		objectProperty_isActionOf = ontModel.getObjectProperty(SystemTransformationsOWLModel.URI_isActionOf);

		dataProperty_name = ontModel.getDatatypeProperty(SystemTransformationsOWLModel.URI_name);

		actionOWLSchema.connectOntologyModel(ontModel);
		systemTemplateOWLSchema.connectOntologyModel(ontModel);
		transformationsOWLSchema.connectOntologyModel(ontModel);
	}
}

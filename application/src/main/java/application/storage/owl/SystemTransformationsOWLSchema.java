package application.storage.owl;

import org.apache.jena.ontology.Individual;
import planning.method.SystemTransformations;
import planning.model.SystemTransformation;

public class SystemTransformationsOWLSchema implements OWLSchema<SystemTransformations> {

	private SystemTransformationOWLSchema systemTransformationOWLSchema;

	private PlanningOWLModel owlModel;

	public SystemTransformationsOWLSchema(PlanningOWLModel owlModel) {
		this(owlModel, new SystemTransformationOWLSchema(owlModel));
	}

	SystemTransformationsOWLSchema(PlanningOWLModel owlModel,
			SystemTransformationOWLSchema systemTransformationOWLSchema) {
		this.owlModel = owlModel;
		this.systemTransformationOWLSchema = systemTransformationOWLSchema;
	}

	@Override
	public Individual combine(SystemTransformations systemTransformations) {
		Individual ind_systemTransformations = owlModel.newIndividual_SystemTransformations();
		ind_systemTransformations.addLabel("System Transformations", "en");
		ind_systemTransformations.addLabel("Трансформации системы", "ru");

		for (SystemTransformation systemTransformation : systemTransformations) {
			Individual ind_systemTransformation = systemTransformationOWLSchema.combine(systemTransformation);
			ind_systemTransformations.addProperty(owlModel.getObjectProperty_hasSystemTransformation(), ind_systemTransformation);
		}
		return ind_systemTransformations;
	}

	@Override
	public SystemTransformations parse(Individual individual) {
		SystemTransformations systemTransformations = new SystemTransformations();
		owlModel.getClass_SystemTransformations().listInstances().forEachRemaining((ind_systemTransformations) -> {
			owlModel.getClass_SystemTransformation().listInstances().filterKeep((ind_systemTransformation) -> {
				return ind_systemTransformations.hasProperty(owlModel.getObjectProperty_hasSystemTransformation(), ind_systemTransformation);
			}).forEachRemaining((ind_systemTransformation) -> {
				SystemTransformation systemTransformation = systemTransformationOWLSchema.parse(ind_systemTransformation.asIndividual());
				systemTransformations.add(systemTransformation);
			});
		});
		return systemTransformations;
	}
}

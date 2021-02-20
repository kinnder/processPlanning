package application.storage.owl;

import org.apache.jena.ontology.Individual;
import planning.method.SystemTransformations;
import planning.model.SystemTransformation;

public class SystemTransformationsOWLSchema implements OWLSchema<SystemTransformations> {

	private SystemTransformationOWLSchema systemTransformationOWLSchema;

	private SystemTransformationsOWLModel owlModel;

	public SystemTransformationsOWLSchema(SystemTransformationsOWLModel owlModel) {
		this(owlModel, new SystemTransformationOWLSchema(owlModel));
	}

	SystemTransformationsOWLSchema(SystemTransformationsOWLModel owlModel,
			SystemTransformationOWLSchema systemTransformationOWLSchema) {
		this.owlModel = owlModel;
		this.systemTransformationOWLSchema = systemTransformationOWLSchema;
	}

	@Override
	public Individual combine(SystemTransformations systemTransformations) {
		Individual ind_systemTransformations = owlModel.getClass_SystemTransformations().createIndividual(owlModel.getUniqueURI());
		ind_systemTransformations.addLabel("System Transformations", "en");
		ind_systemTransformations.addLabel("Трансформации системы", "ru");

		for (SystemTransformation systemTransformation : systemTransformations) {
			Individual ind_systemTransformation = systemTransformationOWLSchema.combine(systemTransformation);
			ind_systemTransformations.addProperty(owlModel.getObjectProperty_hasSystemTransformation(), ind_systemTransformation);
			ind_systemTransformation.addProperty(owlModel.getObjectProperty_isSystemTransformationOf(), ind_systemTransformations);
		}
		return ind_systemTransformations;
	}

	@Override
	public SystemTransformations parse(Individual individual) {
		SystemTransformations systemTransformations = new SystemTransformations();
		owlModel.getClass_SystemTransformations().listInstances().forEachRemaining((ind_systemTransformations) -> {
			owlModel.getClass_SystemTransformation().listInstances().filterKeep((ind_systemTransformation) -> {
				return ind_systemTransformations.hasProperty(owlModel.getObjectProperty_hasSystemTransformation(), ind_systemTransformation);
			}).forEachRemaining((z) -> {
				SystemTransformation systemTransformation = systemTransformationOWLSchema.parse(z.asIndividual());
				systemTransformations.add(systemTransformation);
			});
		});
		return systemTransformations;
	}
}

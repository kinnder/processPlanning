package application.storage.owl;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import planning.method.SystemTransformations;
import planning.model.SystemTransformation;

public class SystemTransformationsOWLSchema implements OWLSchema<SystemTransformations> {

	private SystemTransformationOWLSchema systemTransformationOWLSchema;

	public SystemTransformationsOWLSchema() {
		systemTransformationOWLSchema = new SystemTransformationOWLSchema();
	}

	SystemTransformationsOWLSchema(SystemTransformationOWLSchema systemTransformationOWLSchema) {
		this.systemTransformationOWLSchema = systemTransformationOWLSchema;
	}

	@Override
	public Individual combine(SystemTransformations systemTransformations) {
		Individual ind_systemTransformations = owlModel.getClass_SystemTransformations().createIndividual(SystemTransformationsOWLModel.getUniqueIndividualURI());
		ind_systemTransformations.addLabel("System Transformations 1", "en");
		ind_systemTransformations.addLabel("Трансформации системы 1", "ru");

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

	private SystemTransformationsOWLModel owlModel = new SystemTransformationsOWLModel();

	@Override
	public void connectOntologyModel(OntModel ontModel) {
		owlModel.connectOntologyModel(ontModel);

		systemTransformationOWLSchema.connectOntologyModel(ontModel);
	}
}

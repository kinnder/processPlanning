package application.storage.owl;

import java.util.UUID;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import planning.method.SystemTransformations;
import planning.model.SystemTransformation;

public class SystemTransformationsOWLSchema implements OWLSchema<SystemTransformations> {

	private OntClass class_SystemTransformations;

	private OntClass class_SystemTransformation;

	private ObjectProperty objectProperty_hasSystemTransformation;

	private ObjectProperty objectProperty_isSystemTransformationOf;

	private SystemTransformationOWLSchema systemTransformationOWLSchema;

	public SystemTransformationsOWLSchema() {
		systemTransformationOWLSchema = new SystemTransformationOWLSchema();
	}

	@Override
	public Individual combine(SystemTransformations object) {
		Individual ind_systemTransformations = class_SystemTransformations
				.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
		ind_systemTransformations.addLabel("System Transformations 1", "en");
		ind_systemTransformations.addLabel("Трансформации системы 1", "ru");

		for (SystemTransformation systemTransformation : object) {
			Individual ind_systemTransformation = systemTransformationOWLSchema.combine(systemTransformation);
			ind_systemTransformations.addProperty(objectProperty_hasSystemTransformation, ind_systemTransformation);
			ind_systemTransformation.addProperty(objectProperty_isSystemTransformationOf, ind_systemTransformations);
		}
		return ind_systemTransformations;
	}

	@Override
	public SystemTransformations parse(Individual individual) {
		SystemTransformations systemTransformations = new SystemTransformations();
		class_SystemTransformations.listInstances().forEachRemaining((ind_systemTransformations) -> {
			class_SystemTransformation.listInstances().filterKeep((ind_systemTransformation) -> {
				return ind_systemTransformations.hasProperty(objectProperty_hasSystemTransformation,
						ind_systemTransformation);
			}).forEachRemaining((z) -> {
				SystemTransformation systemTransformation = systemTransformationOWLSchema.parse(z.asIndividual());
				systemTransformations.add(systemTransformation);
			});
		});
		return systemTransformations;
	}

	@Override
	public void connectOntologyModel(OntModel ontModel) {
		class_SystemTransformations = ontModel.getOntClass(SystemTransformationsOWLModel.URI_SystemTransformations);
		class_SystemTransformation = ontModel.getOntClass(SystemTransformationsOWLModel.URI_SystemTransformation);

		objectProperty_hasSystemTransformation = ontModel
				.getObjectProperty(SystemTransformationsOWLModel.URI_hasSystemTransformation);
		objectProperty_isSystemTransformationOf = ontModel
				.getObjectProperty(SystemTransformationsOWLModel.URI_isSystemTransformationOf);

		systemTransformationOWLSchema.connectOntologyModel(ontModel);
	}
}

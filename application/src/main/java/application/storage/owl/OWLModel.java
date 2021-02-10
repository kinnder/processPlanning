package application.storage.owl;

import org.apache.jena.ontology.OntModel;

public interface OWLModel<T> {

	OntModel createOntologyModelBase();

	void createOntologyModel();

	void connectOntologyModel(OntModel ontModel);

	OntModel getOntologyModel();

	OWLSchema<T> getOWLSchema();

	String getUniqueURI();
}

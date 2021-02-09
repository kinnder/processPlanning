package application.storage.owl;

import org.apache.jena.ontology.OntModel;

public interface OWLModel {

	OntModel createOntologyModel();

	void connectOntologyModel(OntModel ontModel);
}

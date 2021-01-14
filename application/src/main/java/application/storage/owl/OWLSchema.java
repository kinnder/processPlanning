package application.storage.owl;

import org.apache.jena.ontology.OntModel;

public interface OWLSchema<T> {

	void combine(T object);

	T parse();

	void connectOntologyModel(OntModel ontModel);
}

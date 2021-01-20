package application.storage.owl;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;

public interface OWLSchema<T> {

	Individual combine(T object);

	T parse();

	void connectOntologyModel(OntModel ontModel);
}

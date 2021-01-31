package application.storage.owl;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;

public interface OWLSchema<T> {

	Individual combine(T object);

	T parse(Individual individual);

	void connectOntologyModel(OntModel ontModel);
}

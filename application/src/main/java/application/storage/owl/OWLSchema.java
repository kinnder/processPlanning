package application.storage.owl;

import org.apache.jena.ontology.Individual;

public interface OWLSchema<T> {

	Individual combine(T object);

	T parse(Individual individual);
}

package application.storage.owl;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;

public interface OWLSchema<T> {

	Individual combine(T object);

	T parse(Individual individual);

	// TODO (2021-02-01 #31): рассмотреть перенос в конструктор
	@Deprecated
	void connectOntologyModel(OntModel ontModel);
}

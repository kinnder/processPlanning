package application.storage.owl;

import org.apache.jena.ontology.OntModel;

public interface OWLSchema <T> {

	OntModel combine(T object);
}

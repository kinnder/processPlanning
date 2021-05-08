package application.storage.owl;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;

public interface OWLModelCommonPart2 {

	String getUniqueURI();

	OntClass getClass_Process();

	ObjectProperty getObjectProperty_hasSystemOperation();

	ObjectProperty getObjectProperty_isSystemOperationOf();

	OntClass getClass_SystemOperation();

	DatatypeProperty getDataProperty_name();

	OntClass getClass_Parameter();

	DatatypeProperty getDataProperty_key();

	DatatypeProperty getDataProperty_value();

	ObjectProperty getObjectProperty_hasParameter();

	ObjectProperty getObjectProperty_isParameterOf();

	Individual newIndividual_SystemOperation();

	Individual newIndividual_Parameter();

	Individual newIndividual_Process();
}

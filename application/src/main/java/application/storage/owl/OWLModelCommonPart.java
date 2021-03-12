package application.storage.owl;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;

public interface OWLModelCommonPart {

	OntClass getClass_System();

	String getUniqueURI();

	ObjectProperty getObjectProperty_isSystemObjectOf();

	ObjectProperty getObjectProperty_hasSystemObject();

	ObjectProperty getObjectProperty_isLinkOf();

	ObjectProperty getObjectProperty_hasLink();

	OntClass getClass_SystemObject();

	OntClass getClass_Link();

	DatatypeProperty getDataProperty_name();

	DatatypeProperty getDataProperty_id();

	ObjectProperty getObjectProperty_isAttributeOf();

	ObjectProperty getObjectProperty_hasAttribute();

	OntClass getClass_Attribute();

	DatatypeProperty getDataProperty_objectId1();

	DatatypeProperty getDataProperty_objectId2();

	DatatypeProperty getDataProperty_value();
}

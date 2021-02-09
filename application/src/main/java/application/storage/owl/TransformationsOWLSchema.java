package application.storage.owl;

import java.util.UUID;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;

import planning.model.AttributeTransformation;
import planning.model.LinkTransformation;
import planning.model.Transformation;

public class TransformationsOWLSchema implements OWLSchema<Transformation[]> {

	@Override
	public Individual combine(Transformation[] transformations) {
		Individual ind_transformations = class_Transformations
				.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
		for (Transformation transformation : transformations) {
			if (transformation instanceof AttributeTransformation) {
				AttributeTransformation attributeTransformation = (AttributeTransformation) transformation;
				// >> Individual: AttributeTransformation
				Individual ind_attributeTransformation = class_AttributeTransformation
						.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
				ind_attributeTransformation.addProperty(dataProperty_objectId, attributeTransformation.getObjectId());
				ind_attributeTransformation.addProperty(dataProperty_name, attributeTransformation.getAttributeName());
				// TODO (2021-01-13 #31): поддержка других типов данных
				ind_attributeTransformation.addProperty(dataProperty_value,
						attributeTransformation.getAttributeValue().toString());
				ind_attributeTransformation.addProperty(objectProperty_isAttributeTransformationOf,
						ind_transformations);
				ind_transformations.addProperty(objectProperty_hasAttributeTransformation, ind_attributeTransformation);
				// << Individual: AttributeTransformation
			} else if (transformation instanceof LinkTransformation) {
				LinkTransformation linkTransformation = (LinkTransformation) transformation;
				// >> Individual: LinkTransformation
				Individual ind_linkTransformation = class_LinkTransformation
						.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
				ind_linkTransformation.addProperty(dataProperty_objectId, linkTransformation.getObjectId());
				ind_linkTransformation.addProperty(dataProperty_name, linkTransformation.getLinkName());
				String objectIdOld = linkTransformation.getLinkObjectId2Old();
				if (objectIdOld != null) {
					ind_linkTransformation.addProperty(dataProperty_oldValue, objectIdOld);
				}
				String objectIdNew = linkTransformation.getLinkObjectId2New();
				if (objectIdNew != null) {
					ind_linkTransformation.addProperty(dataProperty_newValue, objectIdNew);
				}
				ind_linkTransformation.addProperty(objectProperty_isLinkTransformationOf, ind_transformations);
				ind_transformations.addProperty(objectProperty_hasLinkTransformation, ind_linkTransformation);
				// << Individual: LinkTransformation
			} else {
				// TODO (2021-01-13 #31): remove this or update systemTransformations.xsd
			}
		}
		return ind_transformations;
	}

	@Override
	public Transformation[] parse(Individual individual) {
		// TODO Auto-generated method stub
		return null;
	}

	private OntClass class_Transformations;

	private OntClass class_AttributeTransformation;

	private OntClass class_LinkTransformation;

	private ObjectProperty objectProperty_hasAttributeTransformation;

	private ObjectProperty objectProperty_isAttributeTransformationOf;

	private ObjectProperty objectProperty_hasLinkTransformation;

	private ObjectProperty objectProperty_isLinkTransformationOf;

	private DatatypeProperty dataProperty_name;

	private DatatypeProperty dataProperty_objectId;

	private DatatypeProperty dataProperty_value;

	private DatatypeProperty dataProperty_oldValue;

	private DatatypeProperty dataProperty_newValue;

	@Override
	public void connectOntologyModel(OntModel ontModel) {
		class_Transformations = ontModel.getOntClass(SystemTransformationsOWLModel.URI_Transformations);
		class_AttributeTransformation = ontModel.getOntClass(SystemTransformationsOWLModel.URI_AttributeTransformation);
		class_LinkTransformation = ontModel.getOntClass(SystemTransformationsOWLModel.URI_LinkTransformation);

		objectProperty_hasAttributeTransformation = ontModel
				.getObjectProperty(SystemTransformationsOWLModel.URI_hasAttributeTransformation);
		objectProperty_isAttributeTransformationOf = ontModel
				.getObjectProperty(SystemTransformationsOWLModel.URI_isAttributeTransformationOf);
		objectProperty_hasLinkTransformation = ontModel
				.getObjectProperty(SystemTransformationsOWLModel.URI_hasLinkTransformation);
		objectProperty_isLinkTransformationOf = ontModel
				.getObjectProperty(SystemTransformationsOWLModel.URI_isLinkTransformationOf);

		dataProperty_name = ontModel.getDatatypeProperty(SystemTransformationsOWLModel.URI_name);
		dataProperty_objectId = ontModel.getDatatypeProperty(SystemTransformationsOWLModel.URI_objectId);
		dataProperty_value = ontModel.getDatatypeProperty(SystemTransformationsOWLModel.URI_value);
		dataProperty_oldValue = ontModel.getDatatypeProperty(SystemTransformationsOWLModel.URI_oldValue);
		dataProperty_newValue = ontModel.getDatatypeProperty(SystemTransformationsOWLModel.URI_newValue);
	}
}

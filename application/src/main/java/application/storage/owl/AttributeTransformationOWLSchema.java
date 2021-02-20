package application.storage.owl;

import java.util.UUID;

import org.apache.jena.ontology.Individual;

import planning.model.AttributeTransformation;

public class AttributeTransformationOWLSchema implements OWLSchema<AttributeTransformation> {

	private SystemTransformationsOWLModel owlModel;

	public AttributeTransformationOWLSchema(SystemTransformationsOWLModel owlModel) {
		this.owlModel = owlModel;
	}

	@Override
	public Individual combine(AttributeTransformation attributeTransformation) {
		// >> Individual: AttributeTransformation
		Individual ind_attributeTransformation = owlModel.getClass_AttributeTransformation()
				.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
		ind_attributeTransformation.addProperty(owlModel.getDataProperty_objectId(),
				attributeTransformation.getObjectId());
		ind_attributeTransformation.addProperty(owlModel.getDataProperty_name(),
				attributeTransformation.getAttributeName());
		// TODO (2021-01-13 #31): поддержка других типов данных
		ind_attributeTransformation.addProperty(owlModel.getDataProperty_value(),
				attributeTransformation.getAttributeValue().toString());
		// << Individual: AttributeTransformation
		return ind_attributeTransformation;
	}

	@Override
	public AttributeTransformation parse(Individual individual) {
		// TODO Auto-generated method stub
		return null;
	}
}

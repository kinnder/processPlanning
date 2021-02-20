package application.storage.owl;

import org.apache.jena.ontology.Individual;

import planning.model.AttributeTransformation;

public class AttributeTransformationOWLSchema implements OWLSchema<AttributeTransformation> {

	private SystemTransformationsOWLModel owlModel;

	public AttributeTransformationOWLSchema(SystemTransformationsOWLModel owlModel) {
		this.owlModel = owlModel;
	}

	@Override
	public Individual combine(AttributeTransformation attributeTransformation) {
		Individual ind_attributeTransformation = owlModel.getClass_AttributeTransformation().createIndividual(owlModel.getUniqueURI());
		ind_attributeTransformation.addProperty(owlModel.getDataProperty_objectId(), attributeTransformation.getObjectId());
		ind_attributeTransformation.addProperty(owlModel.getDataProperty_name(), attributeTransformation.getAttributeName());
		Object value = attributeTransformation.getAttributeValue();
		if (value == null) {
			ind_attributeTransformation.addProperty(owlModel.getDataProperty_value(), "");
			ind_attributeTransformation.addProperty(owlModel.getDataProperty_type(), "null");
		} else if (value instanceof Boolean) {
			ind_attributeTransformation.addProperty(owlModel.getDataProperty_value(), value.toString());
			ind_attributeTransformation.addProperty(owlModel.getDataProperty_type(), "boolean");
		} else if (value instanceof Integer) {
			ind_attributeTransformation.addProperty(owlModel.getDataProperty_value(), value.toString());
			ind_attributeTransformation.addProperty(owlModel.getDataProperty_type(), "integer");
		} else {
			ind_attributeTransformation.addProperty(owlModel.getDataProperty_value(), value.toString());
			ind_attributeTransformation.addProperty(owlModel.getDataProperty_type(), "string");
		}
		return ind_attributeTransformation;
	}

	@Override
	public AttributeTransformation parse(Individual ind_attributeTransformation) {
		String name = ind_attributeTransformation.getProperty(owlModel.getDataProperty_name()).toString();
		String objectId = ind_attributeTransformation.getProperty(owlModel.getDataProperty_objectId()).toString();
		String type = ind_attributeTransformation.getProperty(owlModel.getDataProperty_type()).getString();
		if ("string".equals(type)) {
			String value = ind_attributeTransformation.getProperty(owlModel.getDataProperty_value()).getString();
			return new AttributeTransformation(objectId, name, value);
		}
		if ("integer".equals(type)) {
			Integer value = ind_attributeTransformation.getProperty(owlModel.getDataProperty_value()).getInt();
			return new AttributeTransformation(objectId, name, value);
		}
		if ("boolean".equals(type)) {
			Boolean value = ind_attributeTransformation.getProperty(owlModel.getDataProperty_value()).getBoolean();
			return new AttributeTransformation(objectId, name, value);
		}
		return new AttributeTransformation(objectId, name, null);
	}
}

package application.storage.owl;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Statement;

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
		if (value instanceof Boolean) {
			ind_attributeTransformation.addProperty(owlModel.getDataProperty_value(), value.toString(), XSDDatatype.XSDboolean);
		} else if (value instanceof Integer) {
			ind_attributeTransformation.addProperty(owlModel.getDataProperty_value(), value.toString(), XSDDatatype.XSDinteger);
		} else if (value instanceof String) {
			ind_attributeTransformation.addProperty(owlModel.getDataProperty_value(), value.toString(), XSDDatatype.XSDstring);
		}
		return ind_attributeTransformation;
	}

	@Override
	public AttributeTransformation parse(Individual ind_attributeTransformation) {
		String name = ind_attributeTransformation.getProperty(owlModel.getDataProperty_name()).toString();
		String objectId = ind_attributeTransformation.getProperty(owlModel.getDataProperty_objectId()).toString();

		Statement valueStatement = ind_attributeTransformation.getProperty(owlModel.getDataProperty_value());
		if (valueStatement != null) {
			Literal valueLiteral = valueStatement.getLiteral();
			if (valueLiteral.getDatatype() == XSDDatatype.XSDboolean) {
				return new AttributeTransformation(objectId, name, valueLiteral.getBoolean());
			}
			if (valueLiteral.getDatatype() == XSDDatatype.XSDstring) {
				return new AttributeTransformation(objectId, name, valueLiteral.getString());
			}
			if (valueLiteral.getDatatype() == XSDDatatype.XSDinteger) {
				return new AttributeTransformation(objectId, name, valueLiteral.getInt());
			}
		}
		return new AttributeTransformation(objectId, name, null);
	}
}

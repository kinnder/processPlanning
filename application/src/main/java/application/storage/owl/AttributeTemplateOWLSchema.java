package application.storage.owl;

import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Statement;

import planning.model.AttributeTemplate;

public class AttributeTemplateOWLSchema implements OWLSchema<AttributeTemplate> {

	private PlanningOWLModel owlModel;

	public AttributeTemplateOWLSchema(PlanningOWLModel owlModel) {
		this.owlModel = owlModel;
	}

	@Override
	public Individual combine(AttributeTemplate attributeTemplate) {
		Individual ind_attributeTemplate = owlModel.newIndividual_AttributeTemplate();
		String name = attributeTemplate.getName();
		ind_attributeTemplate.addLabel(String.format("Attribute template \"%s\"", name), "en");
		ind_attributeTemplate.addLabel(String.format("Шаблон атрибута \"%s\"", name), "ru");
		ind_attributeTemplate.addProperty(owlModel.getDataProperty_name(), name);
		Object value = attributeTemplate.getValue();
		if (value instanceof Boolean) {
			ind_attributeTemplate.addProperty(owlModel.getDataProperty_value(), value.toString(), XSDDatatype.XSDboolean);
		} else if (value instanceof Integer) {
			ind_attributeTemplate.addProperty(owlModel.getDataProperty_value(), value.toString(), XSDDatatype.XSDinteger);
		} else if (value instanceof String) {
			ind_attributeTemplate.addProperty(owlModel.getDataProperty_value(), value.toString(), XSDDatatype.XSDstring);
		}
		return ind_attributeTemplate;
	}

	@Override
	public AttributeTemplate parse(Individual ind_attributeTemplate) {
		String name = ind_attributeTemplate.getProperty(owlModel.getDataProperty_name()).getString();

		Statement valueStatement = ind_attributeTemplate.getProperty(owlModel.getDataProperty_value());
		if (valueStatement != null) {
			Literal valueLiteral = valueStatement.getLiteral();
			RDFDatatype valueType = valueLiteral.getDatatype();
			if (valueType == XSDDatatype.XSDboolean) {
				return new AttributeTemplate(name, valueLiteral.getBoolean());
			}
			if (valueType == XSDDatatype.XSDstring) {
				return new AttributeTemplate(name, valueLiteral.getString());
			}
			if (valueType == XSDDatatype.XSDinteger) {
				return new AttributeTemplate(name, valueLiteral.getInt());
			}
		}
		return new AttributeTemplate(name);
	}
}

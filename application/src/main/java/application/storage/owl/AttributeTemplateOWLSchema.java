package application.storage.owl;

import org.apache.jena.ontology.Individual;

import planning.model.AttributeTemplate;

public class AttributeTemplateOWLSchema implements OWLSchema<AttributeTemplate> {

	private SystemTransformationsOWLModel owlModel;

	public AttributeTemplateOWLSchema(SystemTransformationsOWLModel owlModel) {
		this.owlModel = owlModel;
	}

	@Override
	public Individual combine(AttributeTemplate attributeTemplate) {
		Individual ind_attributeTemplate = owlModel.getClass_AttributeTemplate().createIndividual(owlModel.getUniqueURI());
		ind_attributeTemplate.addLabel("Attribute Template", "en");
		ind_attributeTemplate.addLabel("Шаблон атрибута", "ru");
		ind_attributeTemplate.addProperty(owlModel.getDataProperty_name(), attributeTemplate.getName());
		Object value = attributeTemplate.getValue();
		if (value == null) {
			ind_attributeTemplate.addProperty(owlModel.getDataProperty_value(), "");
			ind_attributeTemplate.addProperty(owlModel.getDataProperty_type(), "null");
		} else if (value instanceof Boolean) {
			ind_attributeTemplate.addProperty(owlModel.getDataProperty_value(), value.toString());
			ind_attributeTemplate.addProperty(owlModel.getDataProperty_type(), "boolean");
		} else if (value instanceof Integer) {
			ind_attributeTemplate.addProperty(owlModel.getDataProperty_value(), value.toString());
			ind_attributeTemplate.addProperty(owlModel.getDataProperty_type(), "integer");
		} else {
			ind_attributeTemplate.addProperty(owlModel.getDataProperty_value(), value.toString());
			ind_attributeTemplate.addProperty(owlModel.getDataProperty_type(), "string");
		}
		return ind_attributeTemplate;
	}

	@Override
	public AttributeTemplate parse(Individual ind_attributeTemplate) {
		String name = ind_attributeTemplate.getProperty(owlModel.getDataProperty_name()).getString();
		String type = ind_attributeTemplate.getProperty(owlModel.getDataProperty_type()).getString();
		if ("string".equals(type)) {
			String value = ind_attributeTemplate.getProperty(owlModel.getDataProperty_value()).getString();
			return new AttributeTemplate(name, value);
		}
		if ("integer".equals(type)) {
			Integer value = ind_attributeTemplate.getProperty(owlModel.getDataProperty_value()).getInt();
			return new AttributeTemplate(name, value);
		}
		if ("boolean".equals(type)) {
			Boolean value = ind_attributeTemplate.getProperty(owlModel.getDataProperty_value()).getBoolean();
			return new AttributeTemplate(name, value);
		}
		return new AttributeTemplate(name);
	}
}

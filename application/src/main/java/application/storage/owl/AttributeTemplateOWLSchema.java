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
		int i = 0;
		int j = 0;
		// >> Individual: AttributeTemplate
		Individual ind_attributeTemplate = owlModel.getClass_AttributeTemplate()
				.createIndividual(owlModel.getUniqueURI());
		ind_attributeTemplate.addLabel(
				"Attribute Template ".concat(Integer.toString(i).concat(" ").concat(Integer.toString(j))), "en");
		ind_attributeTemplate
				.addLabel("Шаблон атрибута ".concat(Integer.toString(i).concat(" ").concat(Integer.toString(j))), "ru");
		ind_attributeTemplate.addProperty(owlModel.getDataProperty_name(), attributeTemplate.getName());
		Object value = attributeTemplate.getValue();
		if (value != null) {
			// TODO (2021-01-13 #31): поддержка других DataType
			ind_attributeTemplate.addProperty(owlModel.getDataProperty_value(), value.toString());
		}
		// << Individual: AttributeTemplate
		return ind_attributeTemplate;
	}

	@Override
	public AttributeTemplate parse(Individual individual) {
		// TODO Auto-generated method stub
		return null;
	}
}

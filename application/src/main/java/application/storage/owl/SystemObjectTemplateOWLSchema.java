package application.storage.owl;

import org.apache.jena.ontology.Individual;

import planning.model.AttributeTemplate;
import planning.model.SystemObjectTemplate;

public class SystemObjectTemplateOWLSchema implements OWLSchema<SystemObjectTemplate> {

	private SystemTransformationsOWLModel owlModel;

	public SystemObjectTemplateOWLSchema(SystemTransformationsOWLModel owlModel) {
		this.owlModel = owlModel;
	}

	@Override
	public Individual combine(SystemObjectTemplate objectTemplate) {
		int i = 0;
		Individual ind_objectTemplate = owlModel.getClass_ObjectTemplate().createIndividual(owlModel.getUniqueURI());
		ind_objectTemplate.addLabel("Шаблон объекта", "ru");
		ind_objectTemplate.addLabel("Object Template", "en");
		ind_objectTemplate.addProperty(owlModel.getDataProperty_objectId(), objectTemplate.getId());
		int j = 0;
		for (AttributeTemplate attributeTemplate : objectTemplate.getAttributeTemplates()) {
			// >> Individual: AttributeTemplate
			Individual ind_attributeTemplate = owlModel.getClass_AttributeTemplate().createIndividual(owlModel.getUniqueURI());
			ind_attributeTemplate.addLabel(
					"Attribute Template ".concat(Integer.toString(i).concat(" ").concat(Integer.toString(j))), "en");
			ind_attributeTemplate.addLabel(
					"Шаблон атрибута ".concat(Integer.toString(i).concat(" ").concat(Integer.toString(j))), "ru");
			ind_attributeTemplate.addProperty(owlModel.getDataProperty_name(), attributeTemplate.getName());
			Object value = attributeTemplate.getValue();
			if (value != null) {
				// TODO (2021-01-13 #31): поддержка других DataType
				ind_attributeTemplate.addProperty(owlModel.getDataProperty_value(), value.toString());
			}
			ind_attributeTemplate.addProperty(owlModel.getObjectProperty_isAttributeTemplateOf(), ind_objectTemplate);
			ind_objectTemplate.addProperty(owlModel.getObjectProperty_hasAttributeTemplate(), ind_attributeTemplate);
			// << Individual: AttributeTemplate
		}
		return ind_objectTemplate;
	}

	@Override
	public SystemObjectTemplate parse(Individual individual) {
		// TODO Auto-generated method stub
		return null;
	}
}

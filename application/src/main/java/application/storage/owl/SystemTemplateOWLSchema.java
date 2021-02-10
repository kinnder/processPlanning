package application.storage.owl;

import java.util.UUID;

import org.apache.jena.ontology.Individual;
import planning.model.AttributeTemplate;
import planning.model.LinkTemplate;
import planning.model.SystemObjectTemplate;
import planning.model.SystemTemplate;

public class SystemTemplateOWLSchema implements OWLSchema<SystemTemplate> {

	private SystemTransformationsOWLModel owlModel;

	public SystemTemplateOWLSchema(SystemTransformationsOWLModel owlModel) {
		this.owlModel = owlModel;
	}

	@Override
	public Individual combine(SystemTemplate systemTemplate) {
		Individual ind_systemTemplate = owlModel.getClass_SystemTemplate()
				.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
		ind_systemTemplate.addLabel("System Template 1", "en");
		ind_systemTemplate.addLabel("Шаблон системы 1", "ru");
		int i = 0;
		for (SystemObjectTemplate objectTemplate : systemTemplate.getObjectTemplates()) {
			// >> Individual: SystemObjectTemplate
			i++;
			Individual ind_objectTemplate = owlModel.getClass_ObjectTemplate()
					.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
			ind_objectTemplate.addLabel("Object Template ".concat(Integer.toString(i)), "en");
			ind_objectTemplate.addLabel("Шаблон объекта ".concat(Integer.toString(i)), "ru");
			ind_objectTemplate.addProperty(owlModel.getDataProperty_objectId(), objectTemplate.getId());
			int j = 0;
			for (AttributeTemplate attributeTemplate : objectTemplate.getAttributeTemplates()) {
				// >> Individual: AttributeTemplate
				Individual ind_attributeTemplate = owlModel.getClass_AttributeTemplate()
						.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
				ind_attributeTemplate.addLabel(
						"Attribute Template ".concat(Integer.toString(i).concat(" ").concat(Integer.toString(j))),
						"en");
				ind_attributeTemplate.addLabel(
						"Шаблон атрибута ".concat(Integer.toString(i).concat(" ").concat(Integer.toString(j))), "ru");
				ind_attributeTemplate.addProperty(owlModel.getDataProperty_name(), attributeTemplate.getName());
				Object value = attributeTemplate.getValue();
				if (value != null) {
					// TODO (2021-01-13 #31): поддержка других DataType
					ind_attributeTemplate.addProperty(owlModel.getDataProperty_value(), value.toString());
				}
				ind_attributeTemplate.addProperty(owlModel.getObjectProperty_isAttributeTemplateOf(),
						ind_objectTemplate);
				ind_objectTemplate.addProperty(owlModel.getObjectProperty_hasAttributeTemplate(),
						ind_attributeTemplate);
				// << Individual: AttributeTemplate
			}
			ind_objectTemplate.addProperty(owlModel.getObjectProperty_isObjectTemplateOf(), ind_systemTemplate);
			ind_systemTemplate.addProperty(owlModel.getObjectProperty_hasObjectTemplate(), ind_objectTemplate);
			// << Individual: SystemObjectTemplate
		}
		i = 0;
		for (LinkTemplate linkTemplate : systemTemplate.getLinkTemplates()) {
			// >> Individual: LinkTemplate
			Individual ind_linkTemplate = owlModel.getClass_LinkTemplate()
					.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
			ind_linkTemplate.addLabel("Link Template ".concat(Integer.toString(i)), "en");
			ind_linkTemplate.addLabel("Шаблон связи ".concat(Integer.toString(i)), "ru");
			ind_linkTemplate.addProperty(owlModel.getDataProperty_name(), linkTemplate.getName());
			String objectId1 = linkTemplate.getObjectId1();
			if (objectId1 != null) {
				ind_linkTemplate.addProperty(owlModel.getDataProperty_objectId1(), objectId1);
			}
			String objectId2 = linkTemplate.getObjectId2();
			if (objectId2 != null) {
				ind_linkTemplate.addProperty(owlModel.getDataProperty_objectId2(), objectId2);
			}
			ind_linkTemplate.addProperty(owlModel.getObjectProperty_isLinkTemplateOf(), ind_systemTemplate);
			ind_systemTemplate.addProperty(owlModel.getObjectProperty_hasLinkTemplate(), ind_linkTemplate);
			// << Individual: LinkTemplate
		}
		return ind_systemTemplate;
	}

	@Override
	public SystemTemplate parse(Individual individual) {
		// TODO Auto-generated method stub
		return null;
	}
}

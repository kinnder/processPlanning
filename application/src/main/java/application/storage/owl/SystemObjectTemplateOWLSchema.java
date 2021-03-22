package application.storage.owl;

import org.apache.jena.ontology.Individual;

import planning.model.AttributeTemplate;
import planning.model.SystemObjectTemplate;

public class SystemObjectTemplateOWLSchema implements OWLSchema<SystemObjectTemplate> {

	private SystemTransformationsOWLModel owlModel;

	private AttributeTemplateOWLSchema attributeTemplateOWLSchema;

	public SystemObjectTemplateOWLSchema(SystemTransformationsOWLModel owlModel) {
		this.owlModel = owlModel;

		this.attributeTemplateOWLSchema = new AttributeTemplateOWLSchema(owlModel);
	}

	@Override
	public Individual combine(SystemObjectTemplate objectTemplate) {
		Individual ind_objectTemplate = owlModel.newIndividual_ObjectTemplate();
		ind_objectTemplate.addLabel("Шаблон объекта", "ru");
		ind_objectTemplate.addLabel("Object Template", "en");
		ind_objectTemplate.addProperty(owlModel.getDataProperty_objectId(), objectTemplate.getId());

		for (AttributeTemplate attributeTemplate : objectTemplate.getAttributeTemplates()) {
			Individual ind_attributeTemplate = attributeTemplateOWLSchema.combine(attributeTemplate);
			ind_attributeTemplate.addProperty(owlModel.getObjectProperty_isAttributeTemplateOf(), ind_objectTemplate);
			ind_objectTemplate.addProperty(owlModel.getObjectProperty_hasAttributeTemplate(), ind_attributeTemplate);
		}
		return ind_objectTemplate;
	}

	@Override
	public SystemObjectTemplate parse(Individual ind_objectTemplate) {
		String objectId = ind_objectTemplate.getProperty(owlModel.getDataProperty_objectId()).getString();
		SystemObjectTemplate objectTemplate = new SystemObjectTemplate(objectId);
		owlModel.getClass_AttributeTemplate().listInstances().filterKeep((ind_attributeTemplate) -> {
			return ind_objectTemplate.hasProperty(owlModel.getObjectProperty_hasAttributeTemplate(), ind_attributeTemplate);
		}).forEachRemaining((ind_attributeTemplate) -> {
			AttributeTemplate attributeTemplate = attributeTemplateOWLSchema.parse(ind_attributeTemplate.asIndividual());
			objectTemplate.addAttributeTemplate(attributeTemplate);
		});
		return objectTemplate;
	}
}

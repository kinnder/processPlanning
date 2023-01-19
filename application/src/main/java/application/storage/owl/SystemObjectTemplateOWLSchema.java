package application.storage.owl;

import org.apache.jena.ontology.Individual;

import planning.model.AttributeTemplate;
import planning.model.SystemObjectTemplate;

public class SystemObjectTemplateOWLSchema implements OWLSchema<SystemObjectTemplate> {

	private PlanningOWLModel owlModel;

	private AttributeTemplateOWLSchema attributeTemplateOWLSchema;

	public SystemObjectTemplateOWLSchema(PlanningOWLModel owlModel) {
		this(owlModel, new AttributeTemplateOWLSchema(owlModel));
	}

	SystemObjectTemplateOWLSchema(PlanningOWLModel owlModel,
			AttributeTemplateOWLSchema attributeTemplateOWLSchema) {
		this.owlModel = owlModel;
		this.attributeTemplateOWLSchema = attributeTemplateOWLSchema;
	}

	@Override
	public Individual combine(SystemObjectTemplate objectTemplate) {
		final Individual ind_objectTemplate = owlModel.newIndividual_ObjectTemplate();
		final String id = objectTemplate.getId();
		ind_objectTemplate.addLabel(String.format("Шаблон объекта \"%s\"", id), "ru");
		ind_objectTemplate.addLabel(String.format("Object template \"%s\"", id), "en");
		ind_objectTemplate.addProperty(owlModel.getDataProperty_id(), id);

		for (AttributeTemplate attributeTemplate : objectTemplate.getAttributeTemplates()) {
			final Individual ind_attributeTemplate = attributeTemplateOWLSchema.combine(attributeTemplate);
			ind_objectTemplate.addProperty(owlModel.getObjectProperty_hasAttributeTemplate(), ind_attributeTemplate);
		}
		return ind_objectTemplate;
	}

	@Override
	public SystemObjectTemplate parse(Individual ind_objectTemplate) {
		final String id = ind_objectTemplate.getProperty(owlModel.getDataProperty_id()).getString();

		final SystemObjectTemplate objectTemplate = new SystemObjectTemplate(id);

		owlModel.getClass_AttributeTemplate().listInstances().filterKeep((ind_attributeTemplate) -> {
			return ind_objectTemplate.hasProperty(owlModel.getObjectProperty_hasAttributeTemplate(), ind_attributeTemplate);
		}).forEachRemaining((ind_attributeTemplate) -> {
			final AttributeTemplate attributeTemplate = attributeTemplateOWLSchema.parse(ind_attributeTemplate.asIndividual());
			objectTemplate.addAttributeTemplate(attributeTemplate);
		});

		return objectTemplate;
	}
}

package application.storage.owl;

import java.util.HashMap;
import java.util.Map;

import org.apache.jena.ontology.Individual;
import planning.model.LinkTemplate;
import planning.model.SystemObjectTemplate;
import planning.model.SystemTemplate;

public class SystemTemplateOWLSchema implements OWLSchema<SystemTemplate> {

	private SystemTransformationsOWLModel owlModel;

	private SystemObjectTemplateOWLSchema systemObjectTemplateOWLSchema;

	private LinkTemplateOWLSchema linkTemplateOWLSchema;

	public SystemTemplateOWLSchema(SystemTransformationsOWLModel owlModel) {
		this(owlModel, new SystemObjectTemplateOWLSchema(owlModel), new LinkTemplateOWLSchema(owlModel));
	}

	SystemTemplateOWLSchema(SystemTransformationsOWLModel owlModel,
			SystemObjectTemplateOWLSchema systemObjectTemplateOWLSchema, LinkTemplateOWLSchema linkTemplateOWLSchema) {
		this.owlModel = owlModel;
		this.systemObjectTemplateOWLSchema = systemObjectTemplateOWLSchema;
		this.linkTemplateOWLSchema = linkTemplateOWLSchema;
	}

	@Override
	public Individual combine(SystemTemplate systemTemplate) {
		Individual ind_systemTemplate = owlModel.newIndividual_SystemTemplate();
		ind_systemTemplate.addLabel("System template", "en");
		ind_systemTemplate.addLabel("Шаблон системы", "ru");

		Map<String, Individual> ind_objectTemplates = new HashMap<String, Individual>();

		for (SystemObjectTemplate objectTemplate : systemTemplate.getObjectTemplates()) {
			Individual ind_objectTemplate = systemObjectTemplateOWLSchema.combine(objectTemplate);
			ind_systemTemplate.addProperty(owlModel.getObjectProperty_hasObjectTemplate(), ind_objectTemplate);

			ind_objectTemplates.put(objectTemplate.getId(), ind_objectTemplate);
		}

		for (LinkTemplate linkTemplate : systemTemplate.getLinkTemplates()) {
			Individual ind_linkTemplate = linkTemplateOWLSchema.combine(linkTemplate);
			ind_systemTemplate.addProperty(owlModel.getObjectProperty_hasLinkTemplate(), ind_linkTemplate);

			String objectId1 = linkTemplate.getObjectId1();
			if (objectId1 != null) {
				ind_linkTemplate.addProperty(owlModel.getObjectProperty_hasObjectTemplate1(), ind_objectTemplates.get(objectId1));
			}
			String objectId2 = linkTemplate.getObjectId2();
			if (objectId2 != null) {
				ind_linkTemplate.addProperty(owlModel.getObjectProperty_hasObjectTemplate2(), ind_objectTemplates.get(objectId2));
			}
		}

		return ind_systemTemplate;
	}

	@Override
	public SystemTemplate parse(Individual ind_systemTemplate) {
		SystemTemplate systemTemplate = new SystemTemplate();

		owlModel.getClass_ObjectTemplate().listInstances().filterKeep((ind_objectTemplate) -> {
			return ind_systemTemplate.hasProperty(owlModel.getObjectProperty_hasObjectTemplate(), ind_objectTemplate);
		}).forEachRemaining((ind_objectTemplate) -> {
			SystemObjectTemplate systemObjectTemplate = systemObjectTemplateOWLSchema.parse(ind_objectTemplate.asIndividual());
			systemTemplate.addObjectTemplate(systemObjectTemplate);
		});

		owlModel.getClass_LinkTemplate().listInstances().filterKeep((ind_linkTemplate) -> {
			return ind_systemTemplate.hasProperty(owlModel.getObjectProperty_hasLinkTemplate(), ind_linkTemplate);
		}).forEachRemaining((ind_linkTemplate) -> {
			LinkTemplate linkTemplate = linkTemplateOWLSchema.parse(ind_linkTemplate.asIndividual());
			systemTemplate.addLinkTemplate(linkTemplate);
		});

		return systemTemplate;
	}
}

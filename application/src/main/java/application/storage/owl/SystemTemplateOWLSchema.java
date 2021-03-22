package application.storage.owl;

import org.apache.jena.ontology.Individual;
import planning.model.LinkTemplate;
import planning.model.SystemObjectTemplate;
import planning.model.SystemTemplate;

public class SystemTemplateOWLSchema implements OWLSchema<SystemTemplate> {

	private SystemTransformationsOWLModel owlModel;

	private SystemObjectTemplateOWLSchema systemObjectTemplateOWLSchema;

	private LinkTemplateOWLSchema linkTemplateOWLSchema;

	public SystemTemplateOWLSchema(SystemTransformationsOWLModel owlModel) {
		this.owlModel = owlModel;

		this.systemObjectTemplateOWLSchema = new SystemObjectTemplateOWLSchema(owlModel);
		this.linkTemplateOWLSchema = new LinkTemplateOWLSchema(owlModel);
	}

	@Override
	public Individual combine(SystemTemplate systemTemplate) {
		Individual ind_systemTemplate = owlModel.newIndividual_SystemTemplate();
		ind_systemTemplate.addLabel("Шаблон системы", "ru");
		ind_systemTemplate.addLabel("System template", "en");

		for (SystemObjectTemplate objectTemplate : systemTemplate.getObjectTemplates()) {
			Individual ind_objectTemplate = systemObjectTemplateOWLSchema.combine(objectTemplate);
			ind_objectTemplate.addProperty(owlModel.getObjectProperty_isObjectTemplateOf(), ind_systemTemplate);
			ind_systemTemplate.addProperty(owlModel.getObjectProperty_hasObjectTemplate(), ind_objectTemplate);
		}

		for (LinkTemplate linkTemplate : systemTemplate.getLinkTemplates()) {
			Individual ind_linkTemplate = linkTemplateOWLSchema.combine(linkTemplate);
			ind_linkTemplate.addProperty(owlModel.getObjectProperty_isLinkTemplateOf(), ind_systemTemplate);
			ind_systemTemplate.addProperty(owlModel.getObjectProperty_hasLinkTemplate(), ind_linkTemplate);
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

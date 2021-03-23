package application.storage.owl;

import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Statement;

import planning.model.LinkTemplate;

public class LinkTemplateOWLSchema implements OWLSchema<LinkTemplate> {

	private SystemTransformationsOWLModel owlModel;

	public LinkTemplateOWLSchema(SystemTransformationsOWLModel owlModel) {
		this.owlModel = owlModel;
	}

	@Override
	public Individual combine(LinkTemplate linkTemplate) {
		Individual ind_linkTemplate = owlModel.newIndividual_LinkTemplate();
		ind_linkTemplate.addLabel("Link template", "en");
		ind_linkTemplate.addLabel("Шаблон связи", "ru");
		ind_linkTemplate.addProperty(owlModel.getDataProperty_name(), linkTemplate.getName());
		String objectId1 = linkTemplate.getObjectId1();
		if (objectId1 != null) {
			ind_linkTemplate.addProperty(owlModel.getDataProperty_objectId1(), objectId1);
		}
		String objectId2 = linkTemplate.getObjectId2();
		if (objectId2 != null) {
			ind_linkTemplate.addProperty(owlModel.getDataProperty_objectId2(), objectId2);
		}
		return ind_linkTemplate;
	}

	@Override
	public LinkTemplate parse(Individual ind_linkTemplate) {
		String name = ind_linkTemplate.getProperty(owlModel.getDataProperty_name()).getString();
		Statement objectId1Property = ind_linkTemplate.getProperty(owlModel.getDataProperty_objectId1());
		Statement objectId2Property = ind_linkTemplate.getProperty(owlModel.getDataProperty_objectId2());
		String objectId1 = objectId1Property == null ? null : objectId1Property.getString();
		String objectId2 = objectId2Property == null ? null : objectId2Property.getString();
		return new LinkTemplate(name, objectId1, objectId2);
	}
}

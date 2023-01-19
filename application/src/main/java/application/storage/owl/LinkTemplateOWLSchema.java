package application.storage.owl;

import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Statement;

import planning.model.LinkTemplate;

public class LinkTemplateOWLSchema implements OWLSchema<LinkTemplate> {

	private PlanningOWLModel owlModel;

	public LinkTemplateOWLSchema(PlanningOWLModel owlModel) {
		this.owlModel = owlModel;
	}

	@Override
	public Individual combine(LinkTemplate linkTemplate) {
		final Individual ind_linkTemplate = owlModel.newIndividual_LinkTemplate();
		final String name = linkTemplate.getName();
		ind_linkTemplate.addLabel(String.format("Link template \"%s\"", name), "en");
		ind_linkTemplate.addLabel(String.format("Шаблон связи \"%s\"", name), "ru");
		ind_linkTemplate.addProperty(owlModel.getDataProperty_name(), name);
		final String id1 = linkTemplate.getId1();
		if (id1 != null) {
			ind_linkTemplate.addProperty(owlModel.getDataProperty_id1(), id1);
		}
		final String id2 = linkTemplate.getId2();
		if (id2 != null) {
			ind_linkTemplate.addProperty(owlModel.getDataProperty_id2(), id2);
		}
		return ind_linkTemplate;
	}

	@Override
	public LinkTemplate parse(Individual ind_linkTemplate) {
		final String name = ind_linkTemplate.getProperty(owlModel.getDataProperty_name()).getString();
		final Statement objectId1Property = ind_linkTemplate.getProperty(owlModel.getDataProperty_id1());
		final Statement objectId2Property = ind_linkTemplate.getProperty(owlModel.getDataProperty_id2());
		final String objectId1 = objectId1Property == null ? null : objectId1Property.getString();
		final String objectId2 = objectId2Property == null ? null : objectId2Property.getString();
		return new LinkTemplate(name, objectId1, objectId2);
	}
}

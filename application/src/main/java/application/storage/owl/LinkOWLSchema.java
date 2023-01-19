package application.storage.owl;

import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Statement;

import planning.model.Link;

public class LinkOWLSchema implements OWLSchema<Link> {

	private PlanningOWLModel owlModel;

	public LinkOWLSchema(PlanningOWLModel owlModel) {
		this.owlModel = owlModel;
	}

	@Override
	public Individual combine(Link link) {
		final Individual ind_link = owlModel.newIndividual_Link();
		final String name = link.getName();
		ind_link.addLabel(String.format("Link \"%s\"", name), "en");
		ind_link.addLabel(String.format("Связь \"%s\"", name), "ru");
		ind_link.addProperty(owlModel.getDataProperty_name(), name);
		final String id1 = link.getId1();
		if (id1 != null) {
			ind_link.addProperty(owlModel.getDataProperty_id1(), id1);
		}
		final String id2 = link.getId2();
		if (id2 != null) {
			ind_link.addProperty(owlModel.getDataProperty_id2(), id2);
		}
		return ind_link;
	}

	@Override
	public Link parse(Individual ind_link) {
		final String name = ind_link.getProperty(owlModel.getDataProperty_name()).getString();
		final Statement objectId1Property = ind_link.getProperty(owlModel.getDataProperty_id1());
		final Statement objectId2Property = ind_link.getProperty(owlModel.getDataProperty_id2());
		final String objectId1 = objectId1Property == null ? null : objectId1Property.getString();
		final String objectId2 = objectId2Property == null ? null : objectId2Property.getString();

		return new Link(name, objectId1, objectId2);
	}
}

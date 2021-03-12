package application.storage.owl;

import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Statement;

import planning.model.Link;

public class LinkOWLSchema implements OWLSchema<Link> {

	private OWLModelCommonPart owlModel;

	public LinkOWLSchema(OWLModelCommonPart owlModel) {
		this.owlModel = owlModel;
	}

	@Override
	public Individual combine(Link link) {
		Individual ind_link = owlModel.getClass_Link().createIndividual(owlModel.getUniqueURI());
		ind_link.addLabel("Link", "en");
		ind_link.addLabel("Связь", "ru");
		ind_link.addProperty(owlModel.getDataProperty_name(), link.getName());
		String objectId1 = link.getObjectId1();
		if (objectId1 != null) {
			ind_link.addProperty(owlModel.getDataProperty_objectId1(), objectId1);
		}
		String objectId2 = link.getObjectId2();
		if (objectId2 != null) {
			ind_link.addProperty(owlModel.getDataProperty_objectId2(), objectId2);
		}
		return ind_link;
	}

	@Override
	public Link parse(Individual ind_link) {
		String name = ind_link.getProperty(owlModel.getDataProperty_name()).getString();
		Statement objectId1Property = ind_link.getProperty(owlModel.getDataProperty_objectId1());
		Statement objectId2Property = ind_link.getProperty(owlModel.getDataProperty_objectId2());
		String objectId1 = objectId1Property == null ? null : objectId1Property.toString();
		String objectId2 = objectId2Property == null ? null : objectId2Property.toString();

		return new Link(name, objectId1, objectId2);
	}
}

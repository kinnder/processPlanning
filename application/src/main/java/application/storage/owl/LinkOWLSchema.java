package application.storage.owl;

import org.apache.jena.ontology.Individual;

import planning.model.Link;

public class LinkOWLSchema implements OWLSchema<Link> {

	private TaskDescriptionOWLModel owlModel;

	public LinkOWLSchema(TaskDescriptionOWLModel owlModel) {
		this.owlModel = owlModel;
	}

	@Override
	public Individual combine(Link link) {
		int i = 0;
		Individual ind_link = owlModel.getClass_Link().createIndividual(owlModel.getUniqueURI());
		ind_link.addLabel("Link ".concat(Integer.toString(i)), "en");
		ind_link.addLabel("Связь ".concat(Integer.toString(i)), "ru");
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
	public Link parse(Individual individual) {
		// TODO Auto-generated method stub
		return null;
	}
}

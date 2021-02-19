package application.storage.owl;

import org.apache.jena.ontology.Individual;

import planning.model.LinkTemplate;

public class LinkTemplateOWLSchema implements OWLSchema<LinkTemplate> {

	private SystemTransformationsOWLModel owlModel;

	public LinkTemplateOWLSchema(SystemTransformationsOWLModel owlModel) {
		this.owlModel = owlModel;
	}

	@Override
	public Individual combine(LinkTemplate linkTemplate) {
		int i = 0;

		i = 0;
		Individual ind_linkTemplate = owlModel.getClass_LinkTemplate().createIndividual(owlModel.getUniqueURI());
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
		return ind_linkTemplate;
	}

	@Override
	public LinkTemplate parse(Individual individual) {
		// TODO Auto-generated method stub
		return null;
	}
}

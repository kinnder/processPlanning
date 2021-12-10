package application.storage.owl;

import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Statement;

import planning.model.LinkTransformation;

public class LinkTransformationOWLSchema implements OWLSchema<LinkTransformation> {

	private PlanningOWLModel owlModel;

	public LinkTransformationOWLSchema(PlanningOWLModel owlModel) {
		this.owlModel = owlModel;
	}

	@Override
	public Individual combine(LinkTransformation linkTransformation) {
		Individual ind_linkTransformation = owlModel.newIndividual_LinkTransformation();
		String name = linkTransformation.getLinkName();
		ind_linkTransformation.addLabel(String.format("Link transformation \"%s\"", name), "en");
		ind_linkTransformation.addLabel(String.format("Трансформация связи \"%s\"", name), "ru");
		ind_linkTransformation.addProperty(owlModel.getDataProperty_id(), linkTransformation.getId());
		ind_linkTransformation.addProperty(owlModel.getDataProperty_name(), name);
		String objectIdOld = linkTransformation.getLinkObjectId2Old();
		if (objectIdOld != null) {
			ind_linkTransformation.addProperty(owlModel.getDataProperty_oldValue(), objectIdOld);
		}
		String objectIdNew = linkTransformation.getLinkObjectId2New();
		if (objectIdNew != null) {
			ind_linkTransformation.addProperty(owlModel.getDataProperty_newValue(), objectIdNew);
		}
		return ind_linkTransformation;
	}

	@Override
	public LinkTransformation parse(Individual ind_linkTransformation) {
		String id = ind_linkTransformation.getProperty(owlModel.getDataProperty_id()).getString();
		String name = ind_linkTransformation.getProperty(owlModel.getDataProperty_name()).getString();
		Statement oldValueProperty = ind_linkTransformation.getProperty(owlModel.getDataProperty_oldValue());
		Statement newValueProperty = ind_linkTransformation.getProperty(owlModel.getDataProperty_newValue());
		String oldValue = oldValueProperty == null ? null : oldValueProperty.getString();
		String newValue = newValueProperty == null ? null : newValueProperty.getString();
		return new LinkTransformation(id, name, oldValue, newValue);
	}
}

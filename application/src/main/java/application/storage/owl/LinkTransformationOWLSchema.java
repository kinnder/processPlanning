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
		final Individual ind_linkTransformation = owlModel.newIndividual_LinkTransformation();
		final String name = linkTransformation.getLinkName();
		ind_linkTransformation.addLabel(String.format("Link transformation \"%s\"", name), "en");
		ind_linkTransformation.addLabel(String.format("Трансформация связи \"%s\"", name), "ru");
		ind_linkTransformation.addProperty(owlModel.getDataProperty_id(), linkTransformation.getId());
		ind_linkTransformation.addProperty(owlModel.getDataProperty_name(), name);
		final String id2Old = linkTransformation.getId2Old();
		if (id2Old != null) {
			ind_linkTransformation.addProperty(owlModel.getDataProperty_id2Old(), id2Old);
		}
		final String id2New = linkTransformation.getId2New();
		if (id2New != null) {
			ind_linkTransformation.addProperty(owlModel.getDataProperty_id2New(), id2New);
		}
		return ind_linkTransformation;
	}

	@Override
	public LinkTransformation parse(Individual ind_linkTransformation) {
		final String id = ind_linkTransformation.getProperty(owlModel.getDataProperty_id()).getString();
		final String name = ind_linkTransformation.getProperty(owlModel.getDataProperty_name()).getString();
		final Statement id2OldProperty = ind_linkTransformation.getProperty(owlModel.getDataProperty_id2Old());
		final Statement id2NewProperty = ind_linkTransformation.getProperty(owlModel.getDataProperty_id2New());
		final String id2Old = id2OldProperty == null ? null : id2OldProperty.getString();
		final String id2New = id2NewProperty == null ? null : id2NewProperty.getString();
		return new LinkTransformation(id, name, id2Old, id2New);
	}
}

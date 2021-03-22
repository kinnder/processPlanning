package application.storage.owl;

import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Statement;

import planning.model.LinkTransformation;

public class LinkTransformationOWLSchema implements OWLSchema<LinkTransformation> {

	private SystemTransformationsOWLModel owlModel;

	public LinkTransformationOWLSchema(SystemTransformationsOWLModel owlModel) {
		this.owlModel = owlModel;
	}

	@Override
	public Individual combine(LinkTransformation linkTransformation) {
		Individual ind_linkTransformation = owlModel.newIndividual_LinkTransformation();
		ind_linkTransformation.addProperty(owlModel.getDataProperty_objectId(), linkTransformation.getObjectId());
		ind_linkTransformation.addProperty(owlModel.getDataProperty_name(), linkTransformation.getLinkName());
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
		String objectId = ind_linkTransformation.getProperty(owlModel.getDataProperty_objectId()).getString();
		String name = ind_linkTransformation.getProperty(owlModel.getDataProperty_name()).getString();
		Statement oldValueProperty = ind_linkTransformation.getProperty(owlModel.getDataProperty_oldValue());
		Statement newValueProperty = ind_linkTransformation.getProperty(owlModel.getDataProperty_newValue());
		String oldValue = oldValueProperty == null ? null : oldValueProperty.toString();
		String newValue = newValueProperty == null ? null : newValueProperty.toString();
		return new LinkTransformation(objectId, name, oldValue, newValue);
	}
}

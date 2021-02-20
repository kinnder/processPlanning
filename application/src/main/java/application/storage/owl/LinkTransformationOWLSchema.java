package application.storage.owl;

import java.util.UUID;

import org.apache.jena.ontology.Individual;

import planning.model.LinkTransformation;

public class LinkTransformationOWLSchema implements OWLSchema<LinkTransformation> {

	private SystemTransformationsOWLModel owlModel;

	public LinkTransformationOWLSchema(SystemTransformationsOWLModel owlModel) {
		this.owlModel = owlModel;
	}

	@Override
	public Individual combine(LinkTransformation linkTransformation) {
		// >> Individual: LinkTransformation
		Individual ind_linkTransformation = owlModel.getClass_LinkTransformation()
				.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
		ind_linkTransformation.addProperty(owlModel.getDataProperty_objectId(),
				linkTransformation.getObjectId());
		ind_linkTransformation.addProperty(owlModel.getDataProperty_name(), linkTransformation.getLinkName());
		String objectIdOld = linkTransformation.getLinkObjectId2Old();
		if (objectIdOld != null) {
			ind_linkTransformation.addProperty(owlModel.getDataProperty_oldValue(), objectIdOld);
		}
		String objectIdNew = linkTransformation.getLinkObjectId2New();
		if (objectIdNew != null) {
			ind_linkTransformation.addProperty(owlModel.getDataProperty_newValue(), objectIdNew);
		}
		// << Individual: LinkTransformation
		return ind_linkTransformation;
	}

	@Override
	public LinkTransformation parse(Individual individual) {
		// TODO Auto-generated method stub
		return null;
	}
}

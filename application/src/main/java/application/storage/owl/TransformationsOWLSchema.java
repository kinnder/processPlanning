package application.storage.owl;

import java.util.UUID;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;

import planning.model.AttributeTransformation;
import planning.model.LinkTransformation;
import planning.model.Transformation;

public class TransformationsOWLSchema implements OWLSchema<Transformation[]> {

	@Override
	public Individual combine(Transformation[] transformations) {
		Individual ind_transformations = owlModel.getClass_Transformations()
				.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
		for (Transformation transformation : transformations) {
			if (transformation instanceof AttributeTransformation) {
				AttributeTransformation attributeTransformation = (AttributeTransformation) transformation;
				// >> Individual: AttributeTransformation
				Individual ind_attributeTransformation = owlModel.getClass_AttributeTransformation()
						.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
				ind_attributeTransformation.addProperty(owlModel.getDataProperty_objectId(),
						attributeTransformation.getObjectId());
				ind_attributeTransformation.addProperty(owlModel.getDataProperty_name(),
						attributeTransformation.getAttributeName());
				// TODO (2021-01-13 #31): поддержка других типов данных
				ind_attributeTransformation.addProperty(owlModel.getDataProperty_value(),
						attributeTransformation.getAttributeValue().toString());
				ind_attributeTransformation.addProperty(owlModel.getObjectProperty_isAttributeTransformationOf(),
						ind_transformations);
				ind_transformations.addProperty(owlModel.getObjectProperty_hasAttributeTransformation(),
						ind_attributeTransformation);
				// << Individual: AttributeTransformation
			} else if (transformation instanceof LinkTransformation) {
				LinkTransformation linkTransformation = (LinkTransformation) transformation;
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
				ind_linkTransformation.addProperty(owlModel.getObjectProperty_isLinkTransformationOf(),
						ind_transformations);
				ind_transformations.addProperty(owlModel.getObjectProperty_hasLinkTransformation(),
						ind_linkTransformation);
				// << Individual: LinkTransformation
			} else {
				// TODO (2021-01-13 #31): remove this or update systemTransformations.xsd
			}
		}
		return ind_transformations;
	}

	@Override
	public Transformation[] parse(Individual individual) {
		// TODO Auto-generated method stub
		return null;
	}

	private SystemTransformationsOWLModel owlModel = new SystemTransformationsOWLModel();

	@Override
	public void connectOntologyModel(OntModel ontModel) {
		owlModel.connectOntologyModel(ontModel);
	}
}

package application.storage.owl;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.ontology.Individual;
import planning.model.AttributeTransformation;
import planning.model.LinkTransformation;
import planning.model.Transformation;

public class TransformationsOWLSchema implements OWLSchema<Transformation[]> {

	private SystemTransformationsOWLModel owlModel;

	private AttributeTransformationOWLSchema attributeTransformationOWLSchema;

	private LinkTransformationOWLSchema linkTransformationOWLSchema;

	public TransformationsOWLSchema(SystemTransformationsOWLModel owlModel) {
		this.owlModel = owlModel;

		attributeTransformationOWLSchema = new AttributeTransformationOWLSchema(owlModel);
		linkTransformationOWLSchema = new LinkTransformationOWLSchema(owlModel);
	}

	@Override
	public Individual combine(Transformation[] transformations) {
		Individual ind_transformations = owlModel.newIndividual_Transformations();
		for (Transformation transformation : transformations) {
			if (transformation instanceof AttributeTransformation) {
				AttributeTransformation attributeTransformation = (AttributeTransformation) transformation;
				Individual ind_attributeTransformation = attributeTransformationOWLSchema.combine(attributeTransformation);
				ind_attributeTransformation.addProperty(owlModel.getObjectProperty_isAttributeTransformationOf(), ind_transformations);
				ind_transformations.addProperty(owlModel.getObjectProperty_hasAttributeTransformation(), ind_attributeTransformation);
			} else if (transformation instanceof LinkTransformation) {
				LinkTransformation linkTransformation = (LinkTransformation) transformation;
				Individual ind_linkTransformation = linkTransformationOWLSchema.combine(linkTransformation);
				ind_linkTransformation.addProperty(owlModel.getObjectProperty_isLinkTransformationOf(), ind_transformations);
				ind_transformations.addProperty(owlModel.getObjectProperty_hasLinkTransformation(), ind_linkTransformation);
			} else {
				Individual ind_transformation = combineTransformation(transformation);
				ind_transformation.addProperty(owlModel.getObjectProperty_isTransformationOf(), ind_transformations);
				ind_transformations.addProperty(owlModel.getObjectProperty_hasTransformation(), ind_transformation);
			}
		}
		return ind_transformations;
	}

	// TODO (2021-01-13 #31): remove this or update systemTransformations.xsd
	public Individual combineTransformation(Transformation transformation) {
		Individual ind_transformation = owlModel.newIndividual_Transformation();
		ind_transformation.addProperty(owlModel.getDataProperty_objectId(), transformation.getObjectId());
		return ind_transformation;
	}

	@Override
	public Transformation[] parse(Individual ind_transformations) {
		List<Transformation> transformations = new ArrayList<>();
		owlModel.getClass_LinkTransformation().listInstances().filterKeep((ind_linkTransformation) -> {
			return ind_transformations.hasProperty(owlModel.getObjectProperty_hasLinkTransformation(), ind_linkTransformation);
		}).forEachRemaining((ind_linkTransformation) -> {
			transformations.add(linkTransformationOWLSchema.parse(ind_linkTransformation.asIndividual()));
		});
		owlModel.getClass_AttributeTransformation().listInstances().filterKeep((ind_attributeTransformation) -> {
			return ind_transformations.hasProperty(owlModel.getObjectProperty_hasAttributeTransformation(), ind_attributeTransformation);
		}).forEachRemaining((ind_attributeTransformation) -> {
			transformations.add(attributeTransformationOWLSchema.parse(ind_attributeTransformation.asIndividual()));
		});
		return transformations.toArray(new Transformation[0]);
	}
}

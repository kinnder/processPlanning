package application.storage.owl;

import org.apache.jena.ontology.Individual;
import planning.model.AttributeTransformation;
import planning.model.LinkTransformation;
import planning.model.Transformation;
import planning.model.Transformations;

public class TransformationsOWLSchema implements OWLSchema<Transformations> {

	private PlanningOWLModel owlModel;

	private AttributeTransformationOWLSchema attributeTransformationOWLSchema;

	private LinkTransformationOWLSchema linkTransformationOWLSchema;

	public TransformationsOWLSchema(PlanningOWLModel owlModel) {
		this(owlModel, new AttributeTransformationOWLSchema(owlModel), new LinkTransformationOWLSchema(owlModel));
	}

	TransformationsOWLSchema(PlanningOWLModel owlModel,
			AttributeTransformationOWLSchema attributeTransformationOWLSchema,
			LinkTransformationOWLSchema linkTransformationOWLSchema) {
		this.owlModel = owlModel;
		this.attributeTransformationOWLSchema = attributeTransformationOWLSchema;
		this.linkTransformationOWLSchema = linkTransformationOWLSchema;
	}

	@Override
	public Individual combine(Transformations transformations) {
		final Individual ind_transformations = owlModel.newIndividual_Transformations();
		ind_transformations.addLabel("Transformations", "en");
		ind_transformations.addLabel("Трансформации", "ru");
		for (Transformation transformation : transformations) {
			if (transformation instanceof AttributeTransformation) {
				final AttributeTransformation attributeTransformation = (AttributeTransformation) transformation;
				final Individual ind_attributeTransformation = attributeTransformationOWLSchema.combine(attributeTransformation);
				ind_transformations.addProperty(owlModel.getObjectProperty_hasAttributeTransformation(), ind_attributeTransformation);
			} else if (transformation instanceof LinkTransformation) {
				final LinkTransformation linkTransformation = (LinkTransformation) transformation;
				final Individual ind_linkTransformation = linkTransformationOWLSchema.combine(linkTransformation);
				ind_transformations.addProperty(owlModel.getObjectProperty_hasLinkTransformation(), ind_linkTransformation);
			} else {
				final Individual ind_transformation = combineTransformation(transformation);
				ind_transformations.addProperty(owlModel.getObjectProperty_hasTransformation(), ind_transformation);
			}
		}
		return ind_transformations;
	}

	// TODO (2021-01-13 #31): remove this or update systemTransformations.xsd
	public Individual combineTransformation(Transformation transformation) {
		final Individual ind_transformation = owlModel.newIndividual_Transformation();
		ind_transformation.addProperty(owlModel.getDataProperty_id(), transformation.getId());
		return ind_transformation;
	}

	public Transformation parseTransformation(Individual ind_transformation) {
		final String id = ind_transformation.getProperty(owlModel.getDataProperty_id()).getString();
		return new Transformation(id);
	}

	@Override
	public Transformations parse(Individual ind_transformations) {
		final Transformations transformations = new Transformations();
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
		owlModel.getClass_Transformation().listInstances().filterKeep((ind_transformation) -> {
			return ind_transformations.hasProperty(owlModel.getObjectProperty_hasTransformation(), ind_transformation);
		}).forEachRemaining((ind_transformation) -> {
			transformations.add(parseTransformation(ind_transformation.asIndividual()));
		});
		return transformations;
	}
}

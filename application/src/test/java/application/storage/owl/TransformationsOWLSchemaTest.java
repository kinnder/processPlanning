package application.storage.owl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.util.iterator.NiceIterator;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.AttributeTransformation;
import planning.model.LinkTransformation;
import planning.model.Transformation;

public class TransformationsOWLSchemaTest {

	@RegisterExtension
	JUnit5Mockery context = new JUnit5Mockery() {
		{
			setImposteriser(ByteBuddyClassImposteriser.INSTANCE);
		}
	};

	@AfterEach
	public void teardown() {
		context.assertIsSatisfied();
	}

	PlanningOWLModel owlModel_mock;

	AttributeTransformationOWLSchema attributeTransformationOWLSchema_mock;

	LinkTransformationOWLSchema linkTransformationOWLSchema_mock;

	TransformationsOWLSchema testable;

	@BeforeEach
	public void setup() {
		owlModel_mock = context.mock(PlanningOWLModel.class);
		attributeTransformationOWLSchema_mock = context.mock(AttributeTransformationOWLSchema.class);
		linkTransformationOWLSchema_mock = context.mock(LinkTransformationOWLSchema.class);

		testable = new TransformationsOWLSchema(owlModel_mock, attributeTransformationOWLSchema_mock,
				linkTransformationOWLSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new TransformationsOWLSchema(new PlanningOWLModel());
	}

	@Test
	public void combine_attributeTransformation() {
		final AttributeTransformation attributeTransformation_mock = context.mock(AttributeTransformation.class, "attributeTransformation");
		final Transformation[] transformations = new Transformation[] { attributeTransformation_mock };
		final Individual i_transformations_mock = context.mock(Individual.class, "i-transformations");
		final Individual i_attributeTransformation_mock = context.mock(Individual.class, "i-attributeTransformation");
		final ObjectProperty op_hasAttributeTransformation_mock = context.mock(ObjectProperty.class, "op-hasAttributeTransformation");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_Transformations();
				will(returnValue(i_transformations_mock));

				oneOf(i_transformations_mock).addLabel("Transformations", "en");

				oneOf(i_transformations_mock).addLabel("Трансформации", "ru");

				oneOf(attributeTransformationOWLSchema_mock).combine(attributeTransformation_mock);
				will(returnValue(i_attributeTransformation_mock));

				oneOf(owlModel_mock).getObjectProperty_hasAttributeTransformation();
				will(returnValue(op_hasAttributeTransformation_mock));

				oneOf(i_transformations_mock).addProperty(op_hasAttributeTransformation_mock,
						i_attributeTransformation_mock);
			}
		});

		assertEquals(i_transformations_mock, testable.combine(transformations));
	}

	@Test
	public void combine_linkTransformation() {
		final LinkTransformation linkTransformation_mock = context.mock(LinkTransformation.class, "linkTransformation");
		final Transformation[] transformations = new Transformation[] { linkTransformation_mock };
		final Individual i_transformations_mock = context.mock(Individual.class, "i-transformations");
		final Individual i_linkTransformation_mock = context.mock(Individual.class, "i-linkTransformation");
		final ObjectProperty op_hasLinkTransformation_mock = context.mock(ObjectProperty.class, "op-hasLinkTransformation");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_Transformations();
				will(returnValue(i_transformations_mock));

				oneOf(i_transformations_mock).addLabel("Transformations", "en");

				oneOf(i_transformations_mock).addLabel("Трансформации", "ru");

				oneOf(linkTransformationOWLSchema_mock).combine(linkTransformation_mock);
				will(returnValue(i_linkTransformation_mock));

				oneOf(owlModel_mock).getObjectProperty_hasLinkTransformation();
				will(returnValue(op_hasLinkTransformation_mock));

				oneOf(i_transformations_mock).addProperty(op_hasLinkTransformation_mock, i_linkTransformation_mock);
			}
		});

		assertEquals(i_transformations_mock, testable.combine(transformations));
	}

	@Test
	public void combine_transformation() {
		final Transformation transformation_mock = context.mock(Transformation.class, "transformation");
		final Transformation[] transformations = new Transformation[] { transformation_mock };
		final Individual i_transformations_mock = context.mock(Individual.class, "i-transformations");
		final Individual i_transformation_mock = context.mock(Individual.class, "i-transformation");
		final ObjectProperty op_hasTransformation_mock = context.mock(ObjectProperty.class, "op-hasTransformation");
		final DatatypeProperty dp_id_mock = context.mock(DatatypeProperty.class, "dp-id");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_Transformations();
				will(returnValue(i_transformations_mock));

				oneOf(i_transformations_mock).addLabel("Transformations", "en");

				oneOf(i_transformations_mock).addLabel("Трансформации", "ru");

				oneOf(owlModel_mock).newIndividual_Transformation();
				will(returnValue(i_transformation_mock));

				oneOf(owlModel_mock).getDataProperty_id();
				will(returnValue(dp_id_mock));

				oneOf(transformation_mock).getId();
				will(returnValue("transformation-id"));

				oneOf(i_transformation_mock).addProperty(dp_id_mock, "transformation-id");

				oneOf(owlModel_mock).getObjectProperty_hasTransformation();
				will(returnValue(op_hasTransformation_mock));

				oneOf(i_transformations_mock).addProperty(op_hasTransformation_mock, i_transformation_mock);
			}
		});

		assertEquals(i_transformations_mock, testable.combine(transformations));
	}

	@Test
	public void parse_attributeTransformation() {
		final AttributeTransformation attributeTransformation_mock = context.mock(AttributeTransformation.class,
				"attributeTransformation");
		final Individual i_transformations_mock = context.mock(Individual.class, "i-transformations");
		final ObjectProperty op_hasAttributeTransformation_mock = context.mock(ObjectProperty.class,
				"op-hasAttributeTransformation");
		final OntClass oc_linkTransformation_mock = context.mock(OntClass.class, "oc-linkTransformation");
		final OntClass oc_attributeTransformation_mock = context.mock(OntClass.class, "oc-attributeTransformation");
		final OntClass oc_transformation_mock = context.mock(OntClass.class, "oc-transformation");

		final Individual i_attributeTransformation_mock = context.mock(Individual.class, "i-attributeTransformation");
		final ExtendedIterator<Individual> attributeTransformationIterator = new NiceIterator<Individual>()
				.andThen(Arrays.asList(i_attributeTransformation_mock).iterator());

		final ExtendedIterator<Individual> linkTransformationIterator = new NiceIterator<Individual>();

		final ExtendedIterator<Individual> transformationIterator = new NiceIterator<Individual>();

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getClass_LinkTransformation();
				will(returnValue(oc_linkTransformation_mock));

				oneOf(oc_linkTransformation_mock).listInstances();
				will(returnValue(linkTransformationIterator));

				oneOf(owlModel_mock).getClass_AttributeTransformation();
				will(returnValue(oc_attributeTransformation_mock));

				oneOf(oc_attributeTransformation_mock).listInstances();
				will(returnValue(attributeTransformationIterator));

				oneOf(owlModel_mock).getObjectProperty_hasAttributeTransformation();
				will(returnValue(op_hasAttributeTransformation_mock));

				oneOf(i_transformations_mock).hasProperty(op_hasAttributeTransformation_mock,
						i_attributeTransformation_mock);
				will(returnValue(true));

				oneOf(i_attributeTransformation_mock).asIndividual();
				will(returnValue(i_attributeTransformation_mock));

				oneOf(attributeTransformationOWLSchema_mock).parse(i_attributeTransformation_mock);
				will(returnValue(attributeTransformation_mock));

				oneOf(owlModel_mock).getClass_Transformation();
				will(returnValue(oc_transformation_mock));

				oneOf(oc_transformation_mock).listInstances();
				will(returnValue(transformationIterator));
			}
		});

		Transformation[] result = testable.parse(i_transformations_mock);
		assertEquals(attributeTransformation_mock, result[0]);
	}

	@Test
	public void parse_linkTransformation() {
		final LinkTransformation linkTransformation_mock = context.mock(LinkTransformation.class, "linkTransformation");
		final Individual i_transformations_mock = context.mock(Individual.class, "i-transformations");
		final ObjectProperty op_hasLinkTransformation_mock = context.mock(ObjectProperty.class,
				"op-hasLinkTransformation");
		final OntClass oc_linkTransformation_mock = context.mock(OntClass.class, "oc-linkTransformation");
		final OntClass oc_attributeTransformation_mock = context.mock(OntClass.class, "oc-attributeTransformation");
		final OntClass oc_transformation_mock = context.mock(OntClass.class, "oc-transformation");

		final ExtendedIterator<Individual> attributeTransformationIterator = new NiceIterator<Individual>();

		final Individual i_linkTransformation_mock = context.mock(Individual.class, "i-linkTransformation");
		final ExtendedIterator<Individual> linkTransformationIterator = new NiceIterator<Individual>()
				.andThen(Arrays.asList(i_linkTransformation_mock).iterator());

		final ExtendedIterator<Individual> transformationIterator = new NiceIterator<Individual>();

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getClass_LinkTransformation();
				will(returnValue(oc_linkTransformation_mock));

				oneOf(oc_linkTransformation_mock).listInstances();
				will(returnValue(linkTransformationIterator));

				oneOf(owlModel_mock).getObjectProperty_hasLinkTransformation();
				will(returnValue(op_hasLinkTransformation_mock));

				oneOf(i_transformations_mock).hasProperty(op_hasLinkTransformation_mock, i_linkTransformation_mock);
				will(returnValue(true));

				oneOf(i_linkTransformation_mock).asIndividual();
				will(returnValue(i_linkTransformation_mock));

				oneOf(linkTransformationOWLSchema_mock).parse(i_linkTransformation_mock);
				will(returnValue(linkTransformation_mock));

				oneOf(owlModel_mock).getClass_AttributeTransformation();
				will(returnValue(oc_attributeTransformation_mock));

				oneOf(oc_attributeTransformation_mock).listInstances();
				will(returnValue(attributeTransformationIterator));

				oneOf(owlModel_mock).getClass_Transformation();
				will(returnValue(oc_transformation_mock));

				oneOf(oc_transformation_mock).listInstances();
				will(returnValue(transformationIterator));
			}
		});

		Transformation[] result = testable.parse(i_transformations_mock);
		assertEquals(linkTransformation_mock, result[0]);
	}

	@Test
	public void parse_transformation() {
		final Individual i_transformations_mock = context.mock(Individual.class, "i-transformations");
		final ObjectProperty op_hasTransformation_mock = context.mock(ObjectProperty.class, "op-hasLinkTransformation");
		final OntClass oc_linkTransformation_mock = context.mock(OntClass.class, "oc-linkTransformation");
		final OntClass oc_attributeTransformation_mock = context.mock(OntClass.class, "oc-attributeTransformation");
		final OntClass oc_transformation_mock = context.mock(OntClass.class, "oc-transformation");
		final DatatypeProperty dp_id_mock = context.mock(DatatypeProperty.class, "dp-id");
		final Statement st_id_mock = context.mock(Statement.class, "st-id");

		final ExtendedIterator<Individual> attributeTransformationIterator = new NiceIterator<Individual>();

		final ExtendedIterator<Individual> linkTransformationIterator = new NiceIterator<Individual>();

		final Individual i_transformation_mock = context.mock(Individual.class, "i-transformation");
		final ExtendedIterator<Individual> transformationIterator = new NiceIterator<Individual>()
				.andThen(Arrays.asList(i_transformation_mock).iterator());

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getClass_LinkTransformation();
				will(returnValue(oc_linkTransformation_mock));

				oneOf(oc_linkTransformation_mock).listInstances();
				will(returnValue(linkTransformationIterator));

				oneOf(owlModel_mock).getClass_AttributeTransformation();
				will(returnValue(oc_attributeTransformation_mock));

				oneOf(oc_attributeTransformation_mock).listInstances();
				will(returnValue(attributeTransformationIterator));

				oneOf(owlModel_mock).getClass_Transformation();
				will(returnValue(oc_transformation_mock));

				oneOf(oc_transformation_mock).listInstances();
				will(returnValue(transformationIterator));

				oneOf(owlModel_mock).getObjectProperty_hasTransformation();
				will(returnValue(op_hasTransformation_mock));

				oneOf(i_transformations_mock).hasProperty(op_hasTransformation_mock, i_transformation_mock);
				will(returnValue(true));

				oneOf(i_transformation_mock).asIndividual();
				will(returnValue(i_transformation_mock));

				oneOf(owlModel_mock).getDataProperty_id();
				will(returnValue(dp_id_mock));

				oneOf(i_transformation_mock).getProperty(dp_id_mock);
				will(returnValue(st_id_mock));

				oneOf(st_id_mock).getString();
				will(returnValue("transformation-id"));
			}
		});

		Transformation[] result = testable.parse(i_transformations_mock);
		assertEquals("transformation-id", result[0].getId());
	}
}

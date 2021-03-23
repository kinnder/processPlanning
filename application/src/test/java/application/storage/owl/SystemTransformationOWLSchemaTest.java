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

import planning.model.Action;
import planning.model.SystemTemplate;
import planning.model.SystemTransformation;
import planning.model.Transformation;

public class SystemTransformationOWLSchemaTest {

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

	SystemTransformationOWLSchema testable;

	SystemTransformationsOWLModel owlModel_mock;

	ActionOWLSchema actionOWLSchema_mock;

	SystemTemplateOWLSchema systemTemplateOWLSchema_mock;

	TransformationsOWLSchema transformationsOWLSchema_mock;

	@BeforeEach
	public void setup() {
		owlModel_mock = context.mock(SystemTransformationsOWLModel.class);
		actionOWLSchema_mock = context.mock(ActionOWLSchema.class);
		systemTemplateOWLSchema_mock = context.mock(SystemTemplateOWLSchema.class);
		transformationsOWLSchema_mock = context.mock(TransformationsOWLSchema.class);

		testable = new SystemTransformationOWLSchema(owlModel_mock, actionOWLSchema_mock, systemTemplateOWLSchema_mock,
				transformationsOWLSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new SystemTransformationOWLSchema(new SystemTransformationsOWLModel());
	}

	@Test
	public void combine() {
		final Action action_mock = context.mock(Action.class);
		final SystemTemplate systemTemplate_mock = context.mock(SystemTemplate.class);
		final Transformation[] transformations_mock = new Transformation[] {};
		final SystemTransformation systemTransformation = new SystemTransformation("system-transformation", action_mock,
				systemTemplate_mock, transformations_mock);
		final Individual i_systemTransformation_mock = context.mock(Individual.class, "i-systemTransformation");
		final Individual i_action_mock = context.mock(Individual.class, "i-action");
		final Individual i_systemTemplate_mock = context.mock(Individual.class, "i-systemTemplate");
		final Individual i_transformations_mock = context.mock(Individual.class, "i-transformations");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final ObjectProperty op_isActionOf_mock = context.mock(ObjectProperty.class, "op-isActionOf");
		final ObjectProperty op_hasAction_mock = context.mock(ObjectProperty.class, "op-hasAction");
		final ObjectProperty op_isSystemTemplateOf_mock = context.mock(ObjectProperty.class, "op-isSystemTemplateOf");
		final ObjectProperty op_hasSystemTemplate_mock = context.mock(ObjectProperty.class, "op-hasSystemTemplate");
		final ObjectProperty op_areTransformationsOf_mock = context.mock(ObjectProperty.class,
				"op-areTransformationsOf");
		final ObjectProperty op_hasTransformations_mock = context.mock(ObjectProperty.class, "op-hasTransformations");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_SystemTransformation();
				will(returnValue(i_systemTransformation_mock));

				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_systemTransformation_mock).addProperty(dp_name_mock, "system-transformation");

				oneOf(actionOWLSchema_mock).combine(action_mock);
				will(returnValue(i_action_mock));

				oneOf(owlModel_mock).getObjectProperty_isActionOf();
				will(returnValue(op_isActionOf_mock));

				oneOf(i_action_mock).addProperty(op_isActionOf_mock, i_systemTransformation_mock);

				oneOf(owlModel_mock).getObjectProperty_hasAction();
				will(returnValue(op_hasAction_mock));

				oneOf(i_systemTransformation_mock).addProperty(op_hasAction_mock, i_action_mock);

				oneOf(systemTemplateOWLSchema_mock).combine(systemTemplate_mock);
				will(returnValue(i_systemTemplate_mock));

				oneOf(owlModel_mock).getObjectProperty_isSystemTemplateOf();
				will(returnValue(op_isSystemTemplateOf_mock));

				oneOf(i_systemTemplate_mock).addProperty(op_isSystemTemplateOf_mock, i_systemTransformation_mock);

				oneOf(owlModel_mock).getObjectProperty_hasSystemTemplate();
				will(returnValue(op_hasSystemTemplate_mock));

				oneOf(i_systemTransformation_mock).addProperty(op_hasSystemTemplate_mock, i_systemTemplate_mock);

				oneOf(transformationsOWLSchema_mock).combine(transformations_mock);
				will(returnValue(i_transformations_mock));

				oneOf(owlModel_mock).getObjectProperty_areTransformationsOf();
				will(returnValue(op_areTransformationsOf_mock));

				oneOf(i_transformations_mock).addProperty(op_areTransformationsOf_mock, i_systemTransformation_mock);

				oneOf(owlModel_mock).getObjectProperty_hasTransformations();
				will(returnValue(op_hasTransformations_mock));

				oneOf(i_systemTransformation_mock).addProperty(op_hasTransformations_mock, i_transformations_mock);
			}
		});

		assertEquals(i_systemTransformation_mock, testable.combine(systemTransformation));
	}

	@Test
	public void parse() {
		final Action action_mock = context.mock(Action.class);
		final SystemTemplate systemTemplate_mock = context.mock(SystemTemplate.class);
		final Transformation[] transformations_mock = new Transformation[] {};
		final Individual i_systemTransformation_mock = context.mock(Individual.class, "i-systemTransformation");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final ObjectProperty op_hasAction_mock = context.mock(ObjectProperty.class, "op-hasAction");
		final ObjectProperty op_hasSystemTemplate_mock = context.mock(ObjectProperty.class, "op-hasSystemTemplate");
		final ObjectProperty op_hasTransformations_mock = context.mock(ObjectProperty.class, "op-hasTransformations");
		final OntClass oc_action_mock = context.mock(OntClass.class, "oc-action");
		final OntClass oc_systemTemplate_mock = context.mock(OntClass.class, "oc-systemTemplate");
		final OntClass oc_transformation_mock = context.mock(OntClass.class, "oc-transformations");
		final Statement st_name_mock = context.mock(Statement.class, "st-name");

		final Individual i_action_mock = context.mock(Individual.class, "i-action");
		final ExtendedIterator<Individual> actionIterator = new NiceIterator<Individual>()
				.andThen(Arrays.asList(i_action_mock).iterator());

		final Individual i_systemTemplate_mock = context.mock(Individual.class, "i-systemTemplate");
		final ExtendedIterator<Individual> systemTemplateIterator = new NiceIterator<Individual>()
				.andThen(Arrays.asList(i_systemTemplate_mock).iterator());

		final Individual i_transformations_mock = context.mock(Individual.class, "i-transformations");
		final ExtendedIterator<Individual> transformationsIterator = new NiceIterator<Individual>()
				.andThen(Arrays.asList(i_transformations_mock).iterator());

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_systemTransformation_mock).getProperty(dp_name_mock);
				will(returnValue(st_name_mock));

				oneOf(st_name_mock).getString();
				will(returnValue("system-transformation"));

				oneOf(owlModel_mock).getClass_Action();
				will(returnValue(oc_action_mock));

				oneOf(oc_action_mock).listInstances();
				will(returnValue(actionIterator));

				oneOf(owlModel_mock).getObjectProperty_hasAction();
				will(returnValue(op_hasAction_mock));

				oneOf(i_systemTransformation_mock).hasProperty(op_hasAction_mock, i_action_mock);
				will(returnValue(true));

				oneOf(i_action_mock).asIndividual();
				will(returnValue(i_action_mock));

				oneOf(actionOWLSchema_mock).parse(i_action_mock);
				will(returnValue(action_mock));

				oneOf(owlModel_mock).getClass_SystemTemplate();
				will(returnValue(oc_systemTemplate_mock));

				oneOf(oc_systemTemplate_mock).listInstances();
				will(returnValue(systemTemplateIterator));

				oneOf(owlModel_mock).getObjectProperty_hasSystemTemplate();
				will(returnValue(op_hasSystemTemplate_mock));

				oneOf(i_systemTransformation_mock).hasProperty(op_hasSystemTemplate_mock, i_systemTemplate_mock);
				will(returnValue(true));

				oneOf(i_systemTemplate_mock).asIndividual();
				will(returnValue(i_systemTemplate_mock));

				oneOf(systemTemplateOWLSchema_mock).parse(i_systemTemplate_mock);
				will(returnValue(systemTemplate_mock));

				oneOf(owlModel_mock).getClass_Transformations();
				will(returnValue(oc_transformation_mock));

				oneOf(oc_transformation_mock).listInstances();
				will(returnValue(transformationsIterator));

				oneOf(owlModel_mock).getObjectProperty_hasTransformations();
				will(returnValue(op_hasTransformations_mock));

				oneOf(i_systemTransformation_mock).hasProperty(op_hasTransformations_mock, i_transformations_mock);
				will(returnValue(true));

				oneOf(i_transformations_mock).asIndividual();
				will(returnValue(i_transformations_mock));

				oneOf(transformationsOWLSchema_mock).parse(i_transformations_mock);
				will(returnValue(transformations_mock));
			}
		});

		SystemTransformation result = testable.parse(i_systemTransformation_mock);
		assertEquals("system-transformation", result.getName());
		assertEquals(action_mock, result.getAction());
		assertEquals(systemTemplate_mock, result.getSystemTemplate());
		assertEquals(transformations_mock, result.getTransformations());
	}
}

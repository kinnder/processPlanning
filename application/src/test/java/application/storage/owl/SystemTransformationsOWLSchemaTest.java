package application.storage.owl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.util.iterator.NiceIterator;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import application.domain.AssemblyLine;
import planning.method.SystemTransformations;
import planning.model.SystemTransformation;

public class SystemTransformationsOWLSchemaTest {

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

	SystemTransformationsOWLSchema testable;

	PlanningOWLModel owlModel_mock;

	SystemTransformationOWLSchema systemTransformationOWLSchema_mock;

	@BeforeEach
	public void setup() {
		systemTransformationOWLSchema_mock = context.mock(SystemTransformationOWLSchema.class);
		owlModel_mock = context.mock(PlanningOWLModel.class);

		testable = new SystemTransformationsOWLSchema(owlModel_mock, systemTransformationOWLSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new SystemTransformationsOWLSchema(new PlanningOWLModel());
	}

	@Test
	public void combine() {
		final SystemTransformation systemTransformation_mock = context.mock(SystemTransformation.class);
		final SystemTransformations systemTransformations = new SystemTransformations();
		systemTransformations.add(systemTransformation_mock);

		final ObjectProperty op_hasSystemTransformation_mock = context.mock(ObjectProperty.class, "op-hasSystemTransformation");
		final Individual i_systemTransformations_mock = context.mock(Individual.class, "i-systemTransformations");
		final Individual i_systemTransformation_mock = context.mock(Individual.class, "i-systemTransformation");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_SystemTransformations();
				will(returnValue(i_systemTransformations_mock));

				oneOf(i_systemTransformations_mock).addLabel("System Transformations", "en");

				oneOf(i_systemTransformations_mock).addLabel("Трансформации системы", "ru");

				oneOf(systemTransformationOWLSchema_mock).combine(systemTransformation_mock);
				will(returnValue(i_systemTransformation_mock));

				oneOf(owlModel_mock).getObjectProperty_hasSystemTransformation();
				will(returnValue(op_hasSystemTransformation_mock));

				oneOf(i_systemTransformations_mock).addProperty(op_hasSystemTransformation_mock,
						i_systemTransformation_mock);
			}
		});

		assertEquals(i_systemTransformations_mock, testable.combine(systemTransformations));
	}

	@Test
	public void parse() {
		final SystemTransformation systemTransformation_mock = context.mock(SystemTransformation.class);
		final OntClass oc_systemTransformations_mock = context.mock(OntClass.class, "oc-systemTransformations");
		final OntClass oc_systemTransformation_mock = context.mock(OntClass.class, "oc-systemTransformation");
		final ObjectProperty op_hasSystemTransformation_mock = context.mock(ObjectProperty.class,
				"op-hasSystemTransformation");

		final Individual i_systemTransformations_mock = context.mock(Individual.class, "i-systemTransformations");
		final ExtendedIterator<Individual> systemTransformationsIterator = new NiceIterator<Individual>()
				.andThen(Arrays.asList(i_systemTransformations_mock).iterator());

		final Individual i_systemTransformation_1_mock = context.mock(Individual.class, "i-systemTransformation-1");
		final Individual i_systemTransformation_2_mock = context.mock(Individual.class, "i-systemTransformation-2");
		final ExtendedIterator<Individual> systemTransformationIterator = new NiceIterator<Individual>()
				.andThen(Arrays.asList(i_systemTransformation_1_mock, i_systemTransformation_2_mock).iterator());

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getClass_SystemTransformations();
				will(returnValue(oc_systemTransformations_mock));

				oneOf(oc_systemTransformations_mock).listInstances();
				will(returnValue(systemTransformationsIterator));

				oneOf(owlModel_mock).getClass_SystemTransformation();
				will(returnValue(oc_systemTransformation_mock));

				oneOf(oc_systemTransformation_mock).listInstances();
				will(returnValue(systemTransformationIterator));

				oneOf(owlModel_mock).getObjectProperty_hasSystemTransformation();
				will(returnValue(op_hasSystemTransformation_mock));

				oneOf(i_systemTransformations_mock).hasProperty(op_hasSystemTransformation_mock,
						i_systemTransformation_1_mock);
				will(returnValue(true));

				oneOf(i_systemTransformation_1_mock).asIndividual();
				will(returnValue(i_systemTransformation_1_mock));

				oneOf(systemTransformationOWLSchema_mock).parse(i_systemTransformation_1_mock);
				will(returnValue(systemTransformation_mock));

				oneOf(owlModel_mock).getObjectProperty_hasSystemTransformation();
				will(returnValue(op_hasSystemTransformation_mock));

				oneOf(i_systemTransformations_mock).hasProperty(op_hasSystemTransformation_mock,
						i_systemTransformation_2_mock);
				will(returnValue(false));
			}
		});

		SystemTransformations result = testable.parse(null);
		assertTrue(result.contains(systemTransformation_mock));
	}

	@Test
	public void combine_full() {
		final SystemTransformations systemTransformations = new SystemTransformations();
		systemTransformations.add(AssemblyLine.turnWithLoad());

		final PlanningOWLModel owlModel = new PlanningOWLModel();
		final SystemTransformationsOWLSchema owlSchema = new SystemTransformationsOWLSchema(owlModel);

		owlModel.createOntologyModel();
		owlSchema.combine(systemTransformations);

		OntModel model = owlModel.getOntologyModel();
		assertNotNull(model);
		assertEquals(400, model.listObjects().toList().size());
		assertEquals(1291, model.listStatements().toList().size());
	}

	@Test
	public void parse_full() {
		final SystemTransformations systemTransformations = new SystemTransformations();
		systemTransformations.add(AssemblyLine.turnWithLoad());

		final PlanningOWLModel owlModel = new PlanningOWLModel();
		final SystemTransformationsOWLSchema owlSchema = new SystemTransformationsOWLSchema(owlModel);

		owlModel.createOntologyModel();
		Individual ind_systemTransformations = owlSchema.combine(systemTransformations);

		owlSchema.parse(ind_systemTransformations);
	}
}

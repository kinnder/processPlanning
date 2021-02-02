package application.storage.owl;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

	SystemTransformationOWLSchema systemTransformationOWLSchema_mock;

	@BeforeEach
	public void setup() {
		systemTransformationOWLSchema_mock = context.mock(SystemTransformationOWLSchema.class);

		testable = new SystemTransformationsOWLSchema(systemTransformationOWLSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new SystemTransformationsOWLSchema();
	}

	@Test
	public void connectOntologyModel() {
		final OntModel ontModel_mock = context.mock(OntModel.class);
		final OntClass systemTransformations_mock = context.mock(OntClass.class, "oc-1");
		final OntClass systemTransformation_mock = context.mock(OntClass.class, "oc-2");
		final ObjectProperty hasSystemTransformation_mock = context.mock(ObjectProperty.class, "op-1");
		final ObjectProperty isSystemTransformationOf_mock = context.mock(ObjectProperty.class, "op-2");

		// <connectOntologyModel>
		context.checking(new Expectations() {
			{
				oneOf(ontModel_mock).getOntClass(SystemTransformationsOWLModel.URI_SystemTransformations);
				will(returnValue(systemTransformations_mock));

				oneOf(ontModel_mock).getOntClass(SystemTransformationsOWLModel.URI_SystemTransformation);
				will(returnValue(systemTransformation_mock));

				oneOf(ontModel_mock).getObjectProperty(SystemTransformationsOWLModel.URI_hasSystemTransformation);
				will(returnValue(hasSystemTransformation_mock));

				oneOf(ontModel_mock).getObjectProperty(SystemTransformationsOWLModel.URI_isSystemTransformationOf);
				will(returnValue(isSystemTransformationOf_mock));

				oneOf(systemTransformationOWLSchema_mock).connectOntologyModel(ontModel_mock);
			}
		});
		// </connectOntologyModel>

		testable.connectOntologyModel(ontModel_mock);
	}

	@Test
	public void combine() {
		final OntModel ontModel_mock = context.mock(OntModel.class);
		final OntClass oc_systemTransformations_mock = context.mock(OntClass.class, "oc-1");
		final OntClass oc_systemTransformation_mock = context.mock(OntClass.class, "oc-2");
		final ObjectProperty op_hasSystemTransformation_mock = context.mock(ObjectProperty.class, "op-1");
		final ObjectProperty op_isSystemTransformationOf_mock = context.mock(ObjectProperty.class, "op-2");

		// <connectOntologyModel>
		context.checking(new Expectations() {
			{
				oneOf(ontModel_mock).getOntClass(SystemTransformationsOWLModel.URI_SystemTransformations);
				will(returnValue(oc_systemTransformations_mock));

				oneOf(ontModel_mock).getOntClass(SystemTransformationsOWLModel.URI_SystemTransformation);
				will(returnValue(oc_systemTransformation_mock));

				oneOf(ontModel_mock).getObjectProperty(SystemTransformationsOWLModel.URI_hasSystemTransformation);
				will(returnValue(op_hasSystemTransformation_mock));

				oneOf(ontModel_mock).getObjectProperty(SystemTransformationsOWLModel.URI_isSystemTransformationOf);
				will(returnValue(op_isSystemTransformationOf_mock));

				oneOf(systemTransformationOWLSchema_mock).connectOntologyModel(ontModel_mock);
			}
		});
		// </connectOntologyModel>

		testable.connectOntologyModel(ontModel_mock);

		final SystemTransformation systemTransformation_mock = context.mock(SystemTransformation.class);
		final SystemTransformations systemTransformations = new SystemTransformations();
		systemTransformations.add(systemTransformation_mock);
		final Individual i_systemTransformations_mock = context.mock(Individual.class, "i-1");
		final Individual i_systemTransformation_mock = context.mock(Individual.class, "i-2");

		// <combine>
		context.checking(new Expectations() {
			{
				oneOf(oc_systemTransformations_mock).createIndividual(with(any(String.class)));
				will(returnValue(i_systemTransformations_mock));

				oneOf(i_systemTransformations_mock).addLabel("System Transformations 1", "en");

				oneOf(i_systemTransformations_mock).addLabel("Трансформации системы 1", "ru");

				oneOf(systemTransformationOWLSchema_mock).combine(systemTransformation_mock);
				will(returnValue(i_systemTransformation_mock));

				oneOf(i_systemTransformations_mock).addProperty(op_hasSystemTransformation_mock,
						i_systemTransformation_mock);

				oneOf(i_systemTransformation_mock).addProperty(op_isSystemTransformationOf_mock,
						i_systemTransformations_mock);
			}
		});
		// </combine>

		assertEquals(i_systemTransformations_mock, testable.combine(systemTransformations));
	}

	@Test
	public void parse() {
		final OntModel ontModel_mock = context.mock(OntModel.class);
		final OntClass oc_systemTransformations_mock = context.mock(OntClass.class, "oc-1");
		final OntClass oc_systemTransformation_mock = context.mock(OntClass.class, "oc-2");
		final ObjectProperty op_hasSystemTransformation_mock = context.mock(ObjectProperty.class, "op-1");
		final ObjectProperty op_isSystemTransformationOf_mock = context.mock(ObjectProperty.class, "op-2");

		// <connectOntologyModel>
		context.checking(new Expectations() {
			{
				oneOf(ontModel_mock).getOntClass(SystemTransformationsOWLModel.URI_SystemTransformations);
				will(returnValue(oc_systemTransformations_mock));

				oneOf(ontModel_mock).getOntClass(SystemTransformationsOWLModel.URI_SystemTransformation);
				will(returnValue(oc_systemTransformation_mock));

				oneOf(ontModel_mock).getObjectProperty(SystemTransformationsOWLModel.URI_hasSystemTransformation);
				will(returnValue(op_hasSystemTransformation_mock));

				oneOf(ontModel_mock).getObjectProperty(SystemTransformationsOWLModel.URI_isSystemTransformationOf);
				will(returnValue(op_isSystemTransformationOf_mock));

				oneOf(systemTransformationOWLSchema_mock).connectOntologyModel(ontModel_mock);
			}
		});
		// </connectOntologyModel>

		testable.connectOntologyModel(ontModel_mock);

		final Individual i_systemTransformations_mock = context.mock(Individual.class, "i-1");
		ExtendedIterator<Individual> systemTransformationsIterator = new NiceIterator<Individual>()
				.andThen(Arrays.asList(i_systemTransformations_mock).iterator());

		final Individual i_systemTransformation1_mock = context.mock(Individual.class, "i-2");
		final Individual i_systemTransformation2_mock = context.mock(Individual.class, "i-3");
		ExtendedIterator<Individual> systemTransformationIterator = new NiceIterator<Individual>()
				.andThen(Arrays.asList(i_systemTransformation1_mock, i_systemTransformation2_mock).iterator());

		SystemTransformation systemTransformation_mock = context.mock(SystemTransformation.class);

		// <parse>
		context.checking(new Expectations() {
			{
				oneOf(oc_systemTransformations_mock).listInstances();
				will(returnValue(systemTransformationsIterator));

				oneOf(oc_systemTransformation_mock).listInstances();
				will(returnValue(systemTransformationIterator));

				oneOf(i_systemTransformations_mock).hasProperty(op_hasSystemTransformation_mock,
						i_systemTransformation1_mock);
				will(returnValue(true));

				oneOf(i_systemTransformation1_mock).asIndividual();
				will(returnValue(i_systemTransformation1_mock));

				oneOf(systemTransformationOWLSchema_mock).parse(i_systemTransformation1_mock);
				will(returnValue(systemTransformation_mock));

				oneOf(i_systemTransformations_mock).hasProperty(op_hasSystemTransformation_mock,
						i_systemTransformation2_mock);
				will(returnValue(false));
			}
		});
		// </parse>

		SystemTransformations result = testable.parse(null);
		assertEquals(1, result.size());
		assertEquals(systemTransformation_mock, result.get(0));
	}
}

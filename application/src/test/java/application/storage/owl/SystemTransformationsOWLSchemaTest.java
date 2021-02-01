package application.storage.owl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
	@Disabled
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

//		final Individual i_systemTransformations_mock = context.mock(Individual.class, "i-1");
//		final Individual i_systemTransformation_mock = context.mock(Individual.class, "i-2");

		// <parse>
		context.checking(new Expectations() {
			{
				oneOf(oc_systemTransformations_mock).listInstances();
			}
		});
		// </parse>

		testable.parse(null);
	}

	@Test
	public void combine_full() {
		testable = new SystemTransformationsOWLSchema();
		final SystemTransformations systemTransformations = new SystemTransformations();
		systemTransformations.add(AssemblyLine.turnWithLoad());

		OntModel model = new SystemTransformationsOWLModel().createOntologyModel();
		testable.connectOntologyModel(model);
		testable.combine(systemTransformations);
		assertNotNull(model);
		assertEquals(222, model.listObjects().toList().size());
		assertEquals(753, model.listStatements().toList().size());

		// TODO (2020-12-14 #31): удалить
		model.write(java.lang.System.out, "RDF/XML");
	}

	@Test
	public void parse_full() {
		testable = new SystemTransformationsOWLSchema();
		final SystemTransformations systemTransformations = new SystemTransformations();
		systemTransformations.add(AssemblyLine.turnWithLoad());

		OntModel model = new SystemTransformationsOWLModel().createOntologyModel();
		testable.connectOntologyModel(model);
		testable.combine(systemTransformations);

		testable.parse(null);
	}
}

package application.storage.owl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.jena.ontology.Individual;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class SystemProcessOWLModelTest {

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

	SystemProcessOWLModel testable;

	@BeforeEach
	public void setup() {
		testable = new SystemProcessOWLModel();
	}

	@Test
	public void getOWLSchema() {
		assertTrue(testable.getOWLSchema() instanceof SystemProcessOWLSchema);
	}

	@Test
	public void createOntologyModel() {
		testable.createOntologyModel();

		assertEquals(SystemProcessOWLModel.URI_ActionParameters, testable.getClass_ActionParameters().getURI());
		assertEquals(SystemProcessOWLModel.URI_Parameter, testable.getClass_Parameter().getURI());
		assertEquals(SystemProcessOWLModel.URI_Process, testable.getClass_Process().getURI());
		assertEquals(SystemProcessOWLModel.URI_SystemOperation, testable.getClass_SystemOperation().getURI());

		assertEquals(SystemProcessOWLModel.URI_key, testable.getDataProperty_key().getURI());
		assertEquals(SystemProcessOWLModel.URI_name, testable.getDataProperty_name().getURI());
		assertEquals(SystemProcessOWLModel.URI_value, testable.getDataProperty_value().getURI());

		assertEquals(SystemProcessOWLModel.URI_areActionParametersOf,
				testable.getObjectProperty_areActionParametersOf().getURI());
		assertEquals(SystemProcessOWLModel.URI_hasActionParameters,
				testable.getObjectProperty_hasActionParameters().getURI());
		assertEquals(SystemProcessOWLModel.URI_hasParameter, testable.getObjectProperty_hasParameter().getURI());
		assertEquals(SystemProcessOWLModel.URI_hasSystemOperation,
				testable.getObjectProperty_hasSystemOperation().getURI());
		assertEquals(SystemProcessOWLModel.URI_isParameterOf, testable.getObjectProperty_isParameterOf().getURI());
		assertEquals(SystemProcessOWLModel.URI_isSystemOperationOf,
				testable.getObjectProperty_isSystemOperationOf().getURI());
	}

	@Test
	public void connectOntologyModel() {
		final SystemProcessOWLModel newOwlModel = new SystemProcessOWLModel();
		newOwlModel.createOntologyModel();

		testable.connectOntologyModel(newOwlModel.getOntologyModel());

		assertEquals(SystemProcessOWLModel.URI_ActionParameters, testable.getClass_ActionParameters().getURI());
		assertEquals(SystemProcessOWLModel.URI_Parameter, testable.getClass_Parameter().getURI());
		assertEquals(SystemProcessOWLModel.URI_Process, testable.getClass_Process().getURI());
		assertEquals(SystemProcessOWLModel.URI_SystemOperation, testable.getClass_SystemOperation().getURI());

		assertEquals(SystemProcessOWLModel.URI_key, testable.getDataProperty_key().getURI());
		assertEquals(SystemProcessOWLModel.URI_name, testable.getDataProperty_name().getURI());
		assertEquals(SystemProcessOWLModel.URI_value, testable.getDataProperty_value().getURI());

		assertEquals(SystemProcessOWLModel.URI_areActionParametersOf,
				testable.getObjectProperty_areActionParametersOf().getURI());
		assertEquals(SystemProcessOWLModel.URI_hasActionParameters,
				testable.getObjectProperty_hasActionParameters().getURI());
		assertEquals(SystemProcessOWLModel.URI_hasParameter, testable.getObjectProperty_hasParameter().getURI());
		assertEquals(SystemProcessOWLModel.URI_hasSystemOperation,
				testable.getObjectProperty_hasSystemOperation().getURI());
		assertEquals(SystemProcessOWLModel.URI_isParameterOf, testable.getObjectProperty_isParameterOf().getURI());
		assertEquals(SystemProcessOWLModel.URI_isSystemOperationOf,
				testable.getObjectProperty_isSystemOperationOf().getURI());
	}

	@Test
	public void getUniqueURI() {
		assertNotEquals(testable.getUniqueURI(), testable.getUniqueURI());
	}

	@Test
	public void newIndividual() {
		testable.createOntologyModel();

		Individual actionParameters = testable.newIndividual_ActionParameters();
		assertEquals(actionParameters.getOntClass(), testable.getClass_ActionParameters());
		assertNotEquals(actionParameters.getURI(), testable.newIndividual_ActionParameters().getURI());

		Individual parameter = testable.newIndividual_Parameter();
		assertEquals(parameter.getOntClass(), testable.getClass_Parameter());
		assertNotEquals(parameter.getURI(), testable.newIndividual_Parameter().getURI());

		Individual process = testable.newIndividual_Process();
		assertEquals(process.getOntClass(), testable.getClass_Process());
		assertNotEquals(process.getURI(), testable.newIndividual_Process().getURI());

		Individual systemOperation = testable.newIndividual_SystemOperation();
		assertEquals(systemOperation.getOntClass(), testable.getClass_SystemOperation());
		assertNotEquals(systemOperation.getURI(), testable.newIndividual_SystemOperation().getURI());
	}
}

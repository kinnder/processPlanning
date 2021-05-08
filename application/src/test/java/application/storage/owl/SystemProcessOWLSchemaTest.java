package application.storage.owl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

import planning.model.Action;
import planning.model.SystemOperation;
import planning.model.SystemProcess;

public class SystemProcessOWLSchemaTest {

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

	SystemProcessOWLSchema testable;

	SystemProcessOWLModel owlModel_mock;

	SystemOperationOWLSchema systemOperationOWLSchema_mock;

	@BeforeEach
	public void setup() {
		owlModel_mock = context.mock(SystemProcessOWLModel.class);
		systemOperationOWLSchema_mock = context.mock(SystemOperationOWLSchema.class);

		testable = new SystemProcessOWLSchema(owlModel_mock, systemOperationOWLSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new SystemProcessOWLSchema(new SystemProcessOWLModel());
	}

	@Test
	public void combine() {
		final SystemOperation systemOperation_mock = context.mock(SystemOperation.class, "systemOperation");
		final SystemProcess systemProcess = new SystemProcess();
		systemProcess.add(systemOperation_mock);
		final Individual i_systemProcess_mock = context.mock(Individual.class, "i-systemProcess");
		final Individual i_systemOperation_mock = context.mock(Individual.class, "i-systemOperation");
		final ObjectProperty op_hasSystemOperation_mock = context.mock(ObjectProperty.class, "op-hasSystemOperation");
		final ObjectProperty op_isSystemOperationOf_mock = context.mock(ObjectProperty.class, "op-isSystemOperationOf");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_Process();
				will(returnValue(i_systemProcess_mock));

				oneOf(i_systemProcess_mock).addLabel("Process", "en");

				oneOf(i_systemProcess_mock).addLabel("Процесс", "ru");

				oneOf(systemOperationOWLSchema_mock).combine(systemOperation_mock);
				will(returnValue(i_systemOperation_mock));

				oneOf(owlModel_mock).getObjectProperty_hasSystemOperation();
				will(returnValue(op_hasSystemOperation_mock));

				oneOf(i_systemProcess_mock).addProperty(op_hasSystemOperation_mock, i_systemOperation_mock);

				oneOf(owlModel_mock).getObjectProperty_isSystemOperationOf();
				will(returnValue(op_isSystemOperationOf_mock));

				oneOf(i_systemOperation_mock).addProperty(op_isSystemOperationOf_mock, i_systemProcess_mock);
			}
		});

		assertEquals(i_systemProcess_mock, testable.combine(systemProcess));
	}

	@Test
	public void parse() {
		final SystemOperation systemOperation_mock = context.mock(SystemOperation.class, "systemOperation");
		final SystemProcess systemProcess = new SystemProcess();
		systemProcess.add(systemOperation_mock);
		final ObjectProperty op_hasSystemOperation_mock = context.mock(ObjectProperty.class, "op-hasSystemOperation");
		final OntClass oc_process_mock = context.mock(OntClass.class, "oc-process");
		final OntClass oc_systemOperation_mock = context.mock(OntClass.class, "oc-systemOperation");

		final Individual i_systemProcess_mock = context.mock(Individual.class, "i-systemProcess");
		final ExtendedIterator<Individual> systemProcessIterator = new NiceIterator<Individual>()
				.andThen(Arrays.asList(i_systemProcess_mock).iterator());

		final Individual i_systemOperation_mock = context.mock(Individual.class, "i-systemOperation");
		final ExtendedIterator<Individual> systemOperationIterator = new NiceIterator<Individual>()
				.andThen(Arrays.asList(i_systemOperation_mock).iterator());

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getClass_Process();
				will(returnValue(oc_process_mock));

				oneOf(oc_process_mock).listInstances();
				will(returnValue(systemProcessIterator));

				oneOf(owlModel_mock).getClass_SystemOperation();
				will(returnValue(oc_systemOperation_mock));

				oneOf(oc_systemOperation_mock).listInstances();
				will(returnValue(systemOperationIterator));

				oneOf(owlModel_mock).getObjectProperty_hasSystemOperation();
				will(returnValue(op_hasSystemOperation_mock));

				oneOf(i_systemProcess_mock).hasProperty(op_hasSystemOperation_mock, i_systemOperation_mock);
				will(returnValue(true));

				oneOf(i_systemOperation_mock).asIndividual();
				will(returnValue(i_systemOperation_mock));

				oneOf(systemOperationOWLSchema_mock).parse(i_systemOperation_mock);
				will(returnValue(systemOperation_mock));
			}
		});

		SystemProcess result = testable.parse(null);
		assertTrue(result.contains(systemOperation_mock));
	}

	@Test
	public void combine_full() {
		final SystemProcess systemProcess = new SystemProcess();
		final Map<String, String> parameters = new HashMap<>();
		parameters.put("test-parameter-name", "test-parameter-value");
		final Action action = new Action("test-action");
		systemProcess.add(new SystemOperation(action, parameters));

		SystemProcessOWLModel owlModel = new SystemProcessOWLModel();
		SystemProcessOWLSchema owlSchema = new SystemProcessOWLSchema(owlModel);

		owlModel.createOntologyModel();
		owlSchema.combine(systemProcess);
		OntModel model = owlModel.getOntologyModel();
		assertNotNull(model);
		assertEquals(66, model.listObjects().toList().size());
		assertEquals(234, model.listStatements().toList().size());

		// TODO (2020-11-09 #31): удалить
//		model.write(System.out, "RDF/XML");
	}

	@Test
	public void parse_full() {
		final SystemProcess systemProcess = new SystemProcess();
		final Map<String, String> parameters = new HashMap<>();
		parameters.put("test-parameter-name", "test-parameter-value");
		final Action action = new Action("test-action");
		systemProcess.add(new SystemOperation(action, parameters));

		SystemProcessOWLModel owlModel = new SystemProcessOWLModel();
		SystemProcessOWLSchema owlSchema = new SystemProcessOWLSchema(owlModel);

		owlModel.createOntologyModel();
		Individual ind_systemProcess = owlSchema.combine(systemProcess);

		owlSchema.parse(ind_systemProcess);
	}
}

package application.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;

import org.jdom2.JDOMException;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import application.storage.owl.NodeNetworkOWLFile;
import application.storage.owl.SystemProcessOWLFile;
import application.storage.owl.SystemTransformationsOWLFile;
import application.storage.owl.TaskDescriptionOWLFile;
import application.storage.xml.NodeNetworkXMLFile;
import application.storage.xml.SystemProcessXMLFile;
import application.storage.xml.SystemTransformationsXMLFile;
import application.storage.xml.TaskDescriptionXMLFile;
import planning.method.NodeNetwork;
import planning.method.SystemTransformations;
import planning.method.TaskDescription;
import planning.model.SystemProcess;

public class PersistanceStorageTest {

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

	PersistanceStorage testable;

	SystemTransformationsXMLFile systemTransformationsXMLFile_mock;

	TaskDescriptionXMLFile taskDescriptionXMLFile_mock;

	SystemProcessXMLFile systemProcessXMLFile_mock;

	NodeNetworkXMLFile nodeNetworkXMLFile_mock;

	SystemTransformationsOWLFile systemTransformationsOWLFile_mock;

	TaskDescriptionOWLFile taskDescriptionOWLFile_mock;

	SystemProcessOWLFile systemProcessOWLFile_mock;

	NodeNetworkOWLFile nodeNetworkOWLFile_mock;

	@BeforeEach
	public void setup() {
		systemTransformationsXMLFile_mock = context.mock(SystemTransformationsXMLFile.class);
		taskDescriptionXMLFile_mock = context.mock(TaskDescriptionXMLFile.class);
		systemProcessXMLFile_mock = context.mock(SystemProcessXMLFile.class);
		nodeNetworkXMLFile_mock = context.mock(NodeNetworkXMLFile.class);

		systemTransformationsOWLFile_mock = context.mock(SystemTransformationsOWLFile.class);
		taskDescriptionOWLFile_mock = context.mock(TaskDescriptionOWLFile.class);
		systemProcessOWLFile_mock = context.mock(SystemProcessOWLFile.class);
		nodeNetworkOWLFile_mock = context.mock(NodeNetworkOWLFile.class);

		testable = new PersistanceStorage(systemTransformationsXMLFile_mock, taskDescriptionXMLFile_mock,
				systemProcessXMLFile_mock, nodeNetworkXMLFile_mock, systemTransformationsOWLFile_mock,
				taskDescriptionOWLFile_mock, systemProcessOWLFile_mock, nodeNetworkOWLFile_mock);
	}

	@Test
	public void newInstance() {
		testable = new PersistanceStorage();
	}

	@Test
	public void saveSystemTransformations_xml() throws IOException {
		final SystemTransformations systemTransformations_mock = context.mock(SystemTransformations.class);
		final String path = "path-to-file";

		context.checking(new Expectations() {
			{
				oneOf(systemTransformationsXMLFile_mock).save(systemTransformations_mock, path);
			}
		});

		testable.saveSystemTransformations(systemTransformations_mock, path);
	}

	@Test
	public void saveSystemTransformations_owl() throws IOException {
		final SystemTransformations systemTransformations_mock = context.mock(SystemTransformations.class);
		final String path = "path-to-file.owl";

		context.checking(new Expectations() {
			{
				oneOf(systemTransformationsOWLFile_mock).save(systemTransformations_mock, path);
			}
		});

		testable.saveSystemTransformations(systemTransformations_mock, path);
	}

	@Test
	public void saveTaskDescription_xml() throws IOException {
		final TaskDescription taskDescription_mock = context.mock(TaskDescription.class);
		final String path = "path-to-file";

		context.checking(new Expectations() {
			{
				oneOf(taskDescriptionXMLFile_mock).save(taskDescription_mock, path);
			}
		});

		testable.saveTaskDescription(taskDescription_mock, path);
	}

	@Test
	public void saveTaskDescription_owl() throws IOException {
		final TaskDescription taskDescription_mock = context.mock(TaskDescription.class);
		final String path = "path-to-file.owl";

		context.checking(new Expectations() {
			{
				oneOf(taskDescriptionOWLFile_mock).save(taskDescription_mock, path);
			}
		});

		testable.saveTaskDescription(taskDescription_mock, path);
	}

	@Test
	public void loadSystemTransformations_xml() throws IOException, JDOMException {
		final SystemTransformations systemTransformations_mock = context.mock(SystemTransformations.class);
		final String path = "path-to-file";

		context.checking(new Expectations() {
			{
				oneOf(systemTransformationsXMLFile_mock).load(path);
				will(returnValue(systemTransformations_mock));
			}
		});

		assertEquals(systemTransformations_mock, testable.loadSystemTransformations(path));
	}

	@Test
	public void loadSystemTransformations_owl() throws IOException, JDOMException {
		final SystemTransformations systemTransformations_mock = context.mock(SystemTransformations.class);
		final String path = "path-to-file.owl";

		context.checking(new Expectations() {
			{
				oneOf(systemTransformationsOWLFile_mock).load(path);
				will(returnValue(systemTransformations_mock));
			}
		});

		assertEquals(systemTransformations_mock, testable.loadSystemTransformations(path));
	}

	@Test
	public void loadTaskDescription_xml() throws IOException, JDOMException {
		final TaskDescription taskDescription_mock = context.mock(TaskDescription.class);
		final String path = "path-to-file";

		context.checking(new Expectations() {
			{
				oneOf(taskDescriptionXMLFile_mock).load(path);
				will(returnValue(taskDescription_mock));
			}
		});

		assertEquals(taskDescription_mock, testable.loadTaskDescription(path));
	}

	@Test
	public void loadTaskDescription_owl() throws IOException, JDOMException {
		final TaskDescription taskDescription_mock = context.mock(TaskDescription.class);
		final String path = "path-to-file.owl";

		context.checking(new Expectations() {
			{
				oneOf(taskDescriptionOWLFile_mock).load(path);
				will(returnValue(taskDescription_mock));
			}
		});

		assertEquals(taskDescription_mock, testable.loadTaskDescription(path));
	}

	@Test
	public void saveSystemProcess_xml() throws IOException {
		final SystemProcess systemProcess_mock = context.mock(SystemProcess.class);
		final String path = "path-to-file";

		context.checking(new Expectations() {
			{
				oneOf(systemProcessXMLFile_mock).save(systemProcess_mock, path);
			}
		});

		testable.saveSystemProcess(systemProcess_mock, path);
	}

	@Test
	public void saveSystemProcess_owl() throws IOException {
		final SystemProcess systemProcess_mock = context.mock(SystemProcess.class);
		final String path = "path-to-file.owl";

		context.checking(new Expectations() {
			{
				oneOf(systemProcessOWLFile_mock).save(systemProcess_mock, path);
			}
		});

		testable.saveSystemProcess(systemProcess_mock, path);
	}

	@Test
	public void saveNodeNetwork_xml() throws IOException {
		final NodeNetwork nodeNetwork_mock = context.mock(NodeNetwork.class);
		final String path = "path-to-file";

		context.checking(new Expectations() {
			{
				oneOf(nodeNetworkXMLFile_mock).save(nodeNetwork_mock, path);
			}
		});

		testable.saveNodeNetwork(nodeNetwork_mock, path);
	}

	@Test
	public void saveNodeNetwork_owl() throws IOException {
		final NodeNetwork nodeNetwork_mock = context.mock(NodeNetwork.class);
		final String path = "path-to-file.owl";

		context.checking(new Expectations() {
			{
				oneOf(nodeNetworkOWLFile_mock).save(nodeNetwork_mock, path);
			}
		});

		testable.saveNodeNetwork(nodeNetwork_mock, path);
	}

	@Test
	public void getResourceAsStream_TaskDescriptionXSD() {
		assertTrue(testable.getResourceAsStream(PersistanceStorage.TASK_DESCRIPTION_XSD) instanceof InputStream);
	}

	@Test
	public void getResourceAsStream_SystemTransformationsXSD() {
		assertTrue(testable.getResourceAsStream(PersistanceStorage.SYSTEM_TRANSFORMATIONS_XSD) instanceof InputStream);
	}

	@Test
	public void getResourceAsStream_ProcessXSD() {
		assertTrue(testable.getResourceAsStream(PersistanceStorage.PROCESS_XSD) instanceof InputStream);
	}

	@Test
	public void getResourceAsStream_NodeNetworkXSD() {
		assertTrue(testable.getResourceAsStream(PersistanceStorage.NODE_NETWORK_XSD) instanceof InputStream);
	}
}

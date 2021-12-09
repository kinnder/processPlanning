package application.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import application.Application;
import planning.method.NodeNetwork;
import planning.method.SystemTransformations;
import planning.method.TaskDescription;
import planning.model.SystemProcess;

public class ConvertCommandTest {

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

	ConvertCommand testable;

	Application application_mock;

	@BeforeEach
	public void setup() {
		application_mock = context.mock(Application.class);

		testable = new ConvertCommand(application_mock);
	}

	// TODO (2021-11-17 #47): разделить на несколько отдельных тестов
	@Test
	public void execute() throws Exception {
		final ConvertCommandData data = new ConvertCommandData();
		data.taskDescriptionFile = "taskDescription.xml";
		data.systemTransformationsFile = "systemTransformations.xml";
		data.processFile = "processFile.xml";
		data.nodeNetworkFile = "nodeNetwork.xml";

		final TaskDescription taskDescription_mock = context.mock(TaskDescription.class);
		final SystemTransformations systemTransformations_mock = context.mock(SystemTransformations.class);
		final SystemProcess systemProcess_mock = context.mock(SystemProcess.class);
		final NodeNetwork nodeNetwork_mock = context.mock(NodeNetwork.class);

		context.checking(new Expectations() {
			{
				// >> taskDescription

				oneOf(application_mock).loadTaskDescription("taskDescription.xml");
				will(returnValue(taskDescription_mock));

				oneOf(application_mock).saveTaskDescription(taskDescription_mock, "taskDescription.owl");

				// << taskDescription

				// >> systemTransformations

				oneOf(application_mock).loadSystemTransformations("systemTransformations.xml");
				will(returnValue(systemTransformations_mock));

				oneOf(application_mock).saveSystemTransformations(systemTransformations_mock,
						"systemTransformations.owl");

				// << systemTransformations

				// >> nodeNetworkFile

				oneOf(application_mock).loadNodeNetwork("nodeNetwork.xml");
				will(returnValue(nodeNetwork_mock));

				oneOf(application_mock).saveNodeNetwork(nodeNetwork_mock, "nodeNetwork.owl");

				// << nodeNetworkFile

				// >> processFile

				oneOf(application_mock).loadSystemProcess("processFile.xml");
				will(returnValue(systemProcess_mock));

				oneOf(application_mock).saveSystemProcess(systemProcess_mock, "processFile.owl");

				// << processFile
			}
		});

		testable.execute(data);
	}

	@Test
	public void execute_no_files_specified() throws Exception {
		final ConvertCommandData data = new ConvertCommandData();

		context.checking(new Expectations() {
			{
				//
			}
		});

		testable.execute(data);
	}

	@Test
	public void changeFileExtension_xml_to_owl() {
		final String fileName = "pathToFile.xml";
		assertEquals("pathToFile.owl", testable.changeFileExtension(fileName));
	}

	@Test
	public void changeFileExtension_owl_to_xml() {
		final String fileName = "pathToFile.owl";
		assertEquals("pathToFile.xml", testable.changeFileExtension(fileName));
	}

	@Test
	public void getName() {
		assertEquals("convert", testable.getName());
	}
}

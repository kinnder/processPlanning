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
import planning.method.TaskDescription;

public class NewTaskDescriptionCommandTest {

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

	NewTaskDescriptionCommand testable;

	Application application_mock;

	@BeforeEach
	public void setup() {
		application_mock = context.mock(Application.class);

		testable = new NewTaskDescriptionCommand(application_mock);
	}

	@Test
	public void execute() throws Exception {
		final NewTaskDescriptionCommandData data_mock = context.mock(NewTaskDescriptionCommandData.class);
		data_mock.taskDescriptionFile = "taskDescription.xml";
		data_mock.domain = "unknown";

		context.checking(new Expectations() {
			{
				// TODO (2020-07-24 #29): добавить Matcher для сравнения TaskDescription
				oneOf(application_mock).saveTaskDescription(with(any(TaskDescription.class)), with("taskDescription.xml"));
			}
		});

		testable.execute(data_mock);
	}

	@Test
	public void execute_assemblyLine() throws Exception {
		final NewTaskDescriptionCommandData data_mock = context.mock(NewTaskDescriptionCommandData.class);
		data_mock.taskDescriptionFile = "taskDescription.xml";
		data_mock.domain = "assemblyLine";

		context.checking(new Expectations() {
			{
				// TODO (2020-07-24 #29): добавить Matcher для сравнения TaskDescription
				oneOf(application_mock).saveTaskDescription(with(any(TaskDescription.class)), with("taskDescription.xml"));
			}
		});

		testable.execute(data_mock);
	}

	@Test
	public void execute_cuttingProcess() throws Exception {
		final NewTaskDescriptionCommandData data_mock = context.mock(NewTaskDescriptionCommandData.class);
		data_mock.taskDescriptionFile = "taskDescription.xml";
		data_mock.domain = "cuttingProcess";

		context.checking(new Expectations() {
			{
				// TODO (2020-07-24 #29): добавить Matcher для сравнения TaskDescription
				oneOf(application_mock).saveTaskDescription(with(any(TaskDescription.class)), with("taskDescription.xml"));
			}
		});

		testable.execute(data_mock);
	}

	@Test
	public void execute_materialPoints() throws Exception {
		final NewTaskDescriptionCommandData data_mock = context.mock(NewTaskDescriptionCommandData.class);
		data_mock.taskDescriptionFile = "taskDescription.xml";
		data_mock.domain = "materialPoints";

		context.checking(new Expectations() {
			{
				// TODO (2020-07-24 #29): добавить Matcher для сравнения TaskDescription
				oneOf(application_mock).saveTaskDescription(with(any(TaskDescription.class)), with("taskDescription.xml"));
			}
		});

		testable.execute(data_mock);
	}

	@Test
	public void getName() {
		assertEquals("new_td", testable.getName());
	}
}

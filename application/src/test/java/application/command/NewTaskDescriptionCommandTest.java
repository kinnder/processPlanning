package application.command;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import application.Application;
import application.event.CommandStatusEventMatcher;
import application.storage.PersistanceStorage;
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
		final PersistanceStorage persistanceStorage_mock = context.mock(PersistanceStorage.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).notifyCommandStatus(with(new CommandStatusEventMatcher()
						.expectMessage("executing command: \"new task description\"...")));

				oneOf(application_mock).getPersistanceStorage();
				will(returnValue(persistanceStorage_mock));

				// TODO (2020-07-24 #29): добавить Matcher для сравнения TaskDescription
				oneOf(persistanceStorage_mock).saveTaskDescription(with(any(TaskDescription.class)), with("taskDescription.xml"));

				oneOf(application_mock).notifyCommandStatus(with(new CommandStatusEventMatcher().expectMessage("done")));
			}
		});

		testable.execute(data_mock);
	}

	@Test
	public void execute_assemblyLine() throws Exception {
		final NewTaskDescriptionCommandData data_mock = context.mock(NewTaskDescriptionCommandData.class);
		data_mock.taskDescriptionFile = "taskDescription.xml";
		data_mock.domain = "assemblyLine";
		final PersistanceStorage persistanceStorage_mock = context.mock(PersistanceStorage.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).notifyCommandStatus(with(new CommandStatusEventMatcher()
						.expectMessage("executing command: \"new task description\"...")));

				oneOf(application_mock).getPersistanceStorage();
				will(returnValue(persistanceStorage_mock));

				// TODO (2020-07-24 #29): добавить Matcher для сравнения TaskDescription
				oneOf(persistanceStorage_mock).saveTaskDescription(with(any(TaskDescription.class)), with("taskDescription.xml"));

				oneOf(application_mock).notifyCommandStatus(with(new CommandStatusEventMatcher().expectMessage("done")));
			}
		});

		testable.execute(data_mock);
	}

	@Test
	public void execute_cuttingProcess() throws Exception {
		final NewTaskDescriptionCommandData data_mock = context.mock(NewTaskDescriptionCommandData.class);
		data_mock.taskDescriptionFile = "taskDescription.xml";
		data_mock.domain = "cuttingProcess";
		final PersistanceStorage persistanceStorage_mock = context.mock(PersistanceStorage.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).notifyCommandStatus(with(new CommandStatusEventMatcher()
						.expectMessage("executing command: \"new task description\"...")));

				oneOf(application_mock).getPersistanceStorage();
				will(returnValue(persistanceStorage_mock));

				// TODO (2020-07-24 #29): добавить Matcher для сравнения TaskDescription
				oneOf(persistanceStorage_mock).saveTaskDescription(with(any(TaskDescription.class)), with("taskDescription.xml"));

				oneOf(application_mock).notifyCommandStatus(with(new CommandStatusEventMatcher().expectMessage("done")));
			}
		});

		testable.execute(data_mock);
	}

	@Test
	public void execute_materialPoints() throws Exception {
		final NewTaskDescriptionCommandData data_mock = context.mock(NewTaskDescriptionCommandData.class);
		data_mock.taskDescriptionFile = "taskDescription.xml";
		data_mock.domain = "materialPoints";
		final PersistanceStorage persistanceStorage_mock = context.mock(PersistanceStorage.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).notifyCommandStatus(with(new CommandStatusEventMatcher()
						.expectMessage("executing command: \"new task description\"...")));

				oneOf(application_mock).getPersistanceStorage();
				will(returnValue(persistanceStorage_mock));

				// TODO (2020-07-24 #29): добавить Matcher для сравнения TaskDescription
				oneOf(persistanceStorage_mock).saveTaskDescription(with(any(TaskDescription.class)), with("taskDescription.xml"));

				oneOf(application_mock).notifyCommandStatus(with(new CommandStatusEventMatcher().expectMessage("done")));
			}
		});

		testable.execute(data_mock);
	}
}

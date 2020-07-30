package application.command;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import application.UserInterface;
import application.event.CommandStatusEventMatcher;
import planning.storage.TaskDescriptionXMLFile;
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

	TaskDescriptionXMLFile taskXMLFile_mock;

	@BeforeEach
	public void setup() {
		taskXMLFile_mock = context.mock(TaskDescriptionXMLFile.class);

		testable = new NewTaskDescriptionCommand();
		// TODO (2020-07-24 #29): перенести в конструктор
		testable.taskXMLFile = taskXMLFile_mock;
	}

	@Test
	public void execute() throws Exception {
		final NewTaskDescriptionCommandData data_mock = context.mock(NewTaskDescriptionCommandData.class);
		data_mock.taskDescriptionFile = "taskDescription.xml";
		// TODO (2020-07-30 #30): добавить варианты теста с другими domain
		data_mock.domain = "unknown";
		final UserInterface ui_mock = context.mock(UserInterface.class);

		context.checking(new Expectations() {
			{
				oneOf(ui_mock).notifyCommandStatus(with(new CommandStatusEventMatcher()
						.expectMessage("executing command: \"new task description\"...")));

				// TODO (2020-07-24 #29): добавить Matcher для сравнения TaskDescription
				oneOf(taskXMLFile_mock).setObject(with(any(TaskDescription.class)));

				oneOf(taskXMLFile_mock).save("taskDescription.xml");

				oneOf(ui_mock).notifyCommandStatus(with(new CommandStatusEventMatcher().expectMessage("done")));
			}
		});
		testable.registerUserInterface(ui_mock);

		testable.execute(data_mock);
	}
}

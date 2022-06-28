package application.command;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import application.Application;

public class CommandManagerTest {

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

	CommandManager testable;

	Application application_mock;

	@BeforeEach
	public void setup() {
		application_mock = context.mock(Application.class);

		testable = new CommandManager(application_mock);
	}

	@Test
	public void registerCommand() {
		final Command command_mock = context.mock(Command.class);

		context.checking(new Expectations() {
			{
				oneOf(command_mock).getName();
				will(returnValue("command-name"));
			}
		});

		testable.registerCommand(command_mock);
	}

	@Test
	public void runCommand() {
		final Command command_mock = context.mock(Command.class);
		final CommandData commandData_mock = context.mock(CommandData.class);

		context.checking(new Expectations() {
			{
				oneOf(command_mock).getName();
				will(returnValue("command-name"));

				oneOf(command_mock).prepare(commandData_mock);

				oneOf(command_mock).run();
			}
		});
		testable.registerCommand(command_mock);

		testable.runCommand("command-name", commandData_mock);
	}

	@Test
	public void stop() {
		testable.stop();
	}
}

package application.command;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

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

	@BeforeEach
	public void setup() {
		testable = new CommandManager();
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

				oneOf(command_mock).run(commandData_mock);
			}
		});
		testable.registerCommand(command_mock);

		testable.runCommand("command-name", commandData_mock);
	}
}

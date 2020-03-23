package application.command;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import application.UserInterface;
import application.event.CommandStatusEvent;
import application.event.HelpMessageEvent;

public class CommandTest {

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

	Command testable;

	@BeforeEach
	public void setup() {
		testable = new Command() {
			@Override
			public void execute(CommandData data) throws Exception {
			}
		};
	}

	@Test
	public void registerUserInterface() {
		final UserInterface ui_mock = context.mock(UserInterface.class);

		testable.registerUserInterface(ui_mock);
	}

	@Test
	public void notifyHelpMessage() {
		final UserInterface ui_mock = context.mock(UserInterface.class);
		final HelpMessageEvent event_mock = context.mock(HelpMessageEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(ui_mock).notifyHelpMessage(event_mock);
			}
		});
		testable.registerUserInterface(ui_mock);

		testable.notifyHelpMessage(event_mock);
	}

	@Test
	public void notifyCommandStatus() {
		final UserInterface ui_mock = context.mock(UserInterface.class);
		final CommandStatusEvent event_mock = context.mock(CommandStatusEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(ui_mock).notifyCommandStatus(event_mock);
			}
		});
		testable.registerUserInterface(ui_mock);

		testable.notifyCommandStatus(event_mock);
	}

	@Test
	public void execute() throws Exception {
		final CommandData data_mock = context.mock(CommandData.class);

		testable.execute(data_mock);
	}
}

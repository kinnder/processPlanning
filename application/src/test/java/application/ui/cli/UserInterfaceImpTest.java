package application.ui.cli;

import java.io.PrintStream;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import application.event.CommandStatusEvent;
import application.event.HelpMessageEvent;
import application.ui.cli.UserInterfaceImp;

public class UserInterfaceImpTest {

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

	UserInterfaceImp testable;

	PrintStream printStream_mock;

	@BeforeEach
	public void setup() {
		printStream_mock = context.mock(PrintStream.class);

		testable = new UserInterfaceImp(printStream_mock);
	}

	@Test
	public void notifyHelpMessage() {
		final HelpMessageEvent event_mock = context.mock(HelpMessageEvent.class);
		event_mock.message = "message";

		context.checking(new Expectations() {
			{
				oneOf(printStream_mock).println("message");
			}
		});

		testable.notifyHelpMessage(event_mock);
	}

	@Test
	public void notifyCommandStatus() {
		final CommandStatusEvent event_mock = context.mock(CommandStatusEvent.class);
		event_mock.message = "message";

		context.checking(new Expectations() {
			{
				oneOf(printStream_mock).println("message");
			}
		});

		testable.notifyCommandStatus(event_mock);
	}
}

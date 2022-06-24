package application.ui.cli;

import java.io.PrintStream;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import application.Application;
import application.Arguments;
import application.event.CommandStatusEvent;
import application.event.UserMessageEvent;

public class MainViewShellTest {

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

	MainViewShell testable;

	PrintStream printStream_mock;

	Application application_mock;

	@BeforeEach
	public void setup() {
		printStream_mock = context.mock(PrintStream.class);
		application_mock = context.mock(Application.class);

		testable = new MainViewShell(application_mock, printStream_mock);
	}

	@Test
	public void notifyUserMessage() {
		final UserMessageEvent event_mock = context.mock(UserMessageEvent.class);
		event_mock.message = "message";

		context.checking(new Expectations() {
			{
				oneOf(printStream_mock).println("message");
			}
		});

		testable.notifyUserMessage(event_mock);
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

	@Test
	public void run_PlanCommand() throws Exception {
		final Arguments arguments_mock = context.mock(Arguments.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).getArguments();
				will(returnValue(arguments_mock));

				oneOf(arguments_mock).hasArgument_plan();
				will(returnValue(true));

				oneOf(application_mock).plan();
			}
		});

		testable.run();
	}

	@Test
	public void run_NewSystemTransformationsCommand() throws Exception {
		final Arguments arguments_mock = context.mock(Arguments.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).getArguments();
				will(returnValue(arguments_mock));

				oneOf(arguments_mock).hasArgument_plan();
				will(returnValue(false));

				oneOf(arguments_mock).hasArgument_new_st();
				will(returnValue(true));

				oneOf(application_mock).newSystemTransformations();
			}
		});

		testable.run();
	}

	@Test
	public void run_NewTaskDescriptionCommand() throws Exception {
		final Arguments arguments_mock = context.mock(Arguments.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).getArguments();
				will(returnValue(arguments_mock));

				oneOf(arguments_mock).hasArgument_plan();
				will(returnValue(false));

				oneOf(arguments_mock).hasArgument_new_st();
				will(returnValue(false));

				oneOf(arguments_mock).hasArgument_new_td();
				will(returnValue(true));

				oneOf(application_mock).newTaskDescription();
			}
		});

		testable.run();
	}

	@Test
	public void run_VerifyCommand() throws Exception {
		final Arguments arguments_mock = context.mock(Arguments.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).getArguments();
				will(returnValue(arguments_mock));

				oneOf(arguments_mock).hasArgument_plan();
				will(returnValue(false));

				oneOf(arguments_mock).hasArgument_new_st();
				will(returnValue(false));

				oneOf(arguments_mock).hasArgument_new_td();
				will(returnValue(false));

				oneOf(arguments_mock).hasArgument_verify();
				will(returnValue(true));

				oneOf(application_mock).verify();
			}
		});

		testable.run();
	}

	@Test
	public void run_ConvertCommand() throws Exception {
		final Arguments arguments_mock = context.mock(Arguments.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).getArguments();
				will(returnValue(arguments_mock));

				oneOf(arguments_mock).hasArgument_plan();
				will(returnValue(false));

				oneOf(arguments_mock).hasArgument_new_st();
				will(returnValue(false));

				oneOf(arguments_mock).hasArgument_new_td();
				will(returnValue(false));

				oneOf(arguments_mock).hasArgument_verify();
				will(returnValue(false));

				oneOf(arguments_mock).hasArgument_convert();
				will(returnValue(true));

				oneOf(application_mock).convert();
			}
		});

		testable.run();
	}

	@Test
	public void run_HelpCommand() throws Exception {
		final Arguments arguments_mock = context.mock(Arguments.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).getArguments();
				will(returnValue(arguments_mock));

				oneOf(arguments_mock).hasArgument_plan();
				will(returnValue(false));

				oneOf(arguments_mock).hasArgument_new_st();
				will(returnValue(false));

				oneOf(arguments_mock).hasArgument_new_td();
				will(returnValue(false));

				oneOf(arguments_mock).hasArgument_verify();
				will(returnValue(false));

				oneOf(arguments_mock).hasArgument_convert();
				will(returnValue(false));

				oneOf(application_mock).usageHelp();
			}
		});

		testable.run();
	}

	@Test
	public void stop() {
		testable.stop();
	}
}

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
import application.ApplicationArguments;
import application.command.ConvertCommand;
import application.command.ConvertCommandDataMatcher;
import application.command.NewSystemTransformationsCommand;
import application.command.NewSystemTransformationsCommandDataMatcher;
import application.command.NewTaskDescriptionCommand;
import application.command.NewTaskDescriptionCommandDataMatcher;
import application.command.PlanCommand;
import application.command.PlanCommandDataMatcher;
import application.command.VerifyCommand;
import application.command.VerifyCommandDataMatcher;
import application.event.CommandStatusEvent;
import application.event.HelpMessageEvent;

public class MainShellTest {

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

	MainShell testable;

	PrintStream printStream_mock;

	Application application_mock;

	@BeforeEach
	public void setup() {
		printStream_mock = context.mock(PrintStream.class);
		application_mock = context.mock(Application.class);

		testable = new MainShell(printStream_mock);
		testable.setApplication(application_mock);
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

	@Test
	public void run_PlanCommand() throws Exception {
		final ApplicationArguments applicationArguments_mock = context.mock(ApplicationArguments.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).getArguments();
				will(returnValue(applicationArguments_mock));

				oneOf(applicationArguments_mock).hasArgument_plan();
				will(returnValue(true));

				oneOf(applicationArguments_mock).getArgument_td("taskDescription.xml");
				will(returnValue("td_file.xml"));

				oneOf(applicationArguments_mock).getArgument_st("systemTransformations.xml");
				will(returnValue("st_file.xml"));

				oneOf(applicationArguments_mock).getArgument_p("process.xml");
				will(returnValue("p_file.xml"));

				oneOf(applicationArguments_mock).getArgument_nn("nodeNetwork.xml");
				will(returnValue("nn_file.xml"));

				oneOf(application_mock).runCommand(with(PlanCommand.NAME),
						with(new PlanCommandDataMatcher().expectSystemTransformationsFile("st_file.xml")
								.expectTaskDescriptionFile("td_file.xml").expectProcessFile("p_file.xml")
								.expectNodeNetworkFile("nn_file.xml")));
			}
		});

		testable.run();
	}

	@Test
	public void run_NewSystemTransformationsCommand() throws Exception {
		final ApplicationArguments applicationArguments_mock = context.mock(ApplicationArguments.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).getArguments();
				will(returnValue(applicationArguments_mock));

				oneOf(applicationArguments_mock).hasArgument_plan();
				will(returnValue(false));

				oneOf(applicationArguments_mock).hasArgument_new_st();
				will(returnValue(true));

				oneOf(applicationArguments_mock).getArgument_st("systemTransformations.xml");
				will(returnValue("st_file.xml"));

				oneOf(applicationArguments_mock).getArgument_d("unknown");
				will(returnValue("assemblyLine"));

				oneOf(application_mock).runCommand(with(NewSystemTransformationsCommand.NAME),
						with(new NewSystemTransformationsCommandDataMatcher()
								.expectSystemTransformationsFile("st_file.xml").expectDomain("assemblyLine")));
			}
		});

		testable.run();
	}

	@Test
	public void run_NewTaskDescriptionCommand() throws Exception {
		final ApplicationArguments applicationArguments_mock = context.mock(ApplicationArguments.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).getArguments();
				will(returnValue(applicationArguments_mock));

				oneOf(applicationArguments_mock).hasArgument_plan();
				will(returnValue(false));

				oneOf(applicationArguments_mock).hasArgument_new_st();
				will(returnValue(false));

				oneOf(applicationArguments_mock).hasArgument_new_td();
				will(returnValue(true));

				oneOf(applicationArguments_mock).getArgument_td("taskDescription.xml");
				will(returnValue("td_file.xml"));

				oneOf(applicationArguments_mock).getArgument_d("unknown");
				will(returnValue("materialPoints"));

				oneOf(application_mock).runCommand(with(NewTaskDescriptionCommand.NAME),
						with(new NewTaskDescriptionCommandDataMatcher().expectTaskDescriptionFile("td_file.xml")
								.expectDomain("materialPoints")));
			}
		});

		testable.run();
	}

	@Test
	public void run_VerifyCommand() throws Exception {
		final ApplicationArguments applicationArguments_mock = context.mock(ApplicationArguments.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).getArguments();
				will(returnValue(applicationArguments_mock));

				oneOf(applicationArguments_mock).hasArgument_plan();
				will(returnValue(false));

				oneOf(applicationArguments_mock).hasArgument_new_st();
				will(returnValue(false));

				oneOf(applicationArguments_mock).hasArgument_new_td();
				will(returnValue(false));

				oneOf(applicationArguments_mock).hasArgument_verify();
				will(returnValue(true));

				oneOf(applicationArguments_mock).getArgument_td(null);
				will(returnValue("td_file.xml"));

				oneOf(applicationArguments_mock).getArgument_st(null);
				will(returnValue("st_file.xml"));

				oneOf(applicationArguments_mock).getArgument_p(null);
				will(returnValue("p_file.xml"));

				oneOf(applicationArguments_mock).getArgument_nn(null);
				will(returnValue("nn_file.xml"));

				oneOf(application_mock).runCommand(with(VerifyCommand.NAME),
						with(new VerifyCommandDataMatcher().expectSystemTransformationsFile("st_file.xml")
								.expectTaskDescriptionFile("td_file.xml").expectProcessFile("p_file.xml")
								.expectNodeNetworkFile("nn_file.xml")));
			}
		});

		testable.run();
	}

	@Test
	public void run_ConvertCommand() throws Exception {
		final ApplicationArguments applicationArguments_mock = context.mock(ApplicationArguments.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).getArguments();
				will(returnValue(applicationArguments_mock));

				oneOf(applicationArguments_mock).hasArgument_plan();
				will(returnValue(false));

				oneOf(applicationArguments_mock).hasArgument_new_st();
				will(returnValue(false));

				oneOf(applicationArguments_mock).hasArgument_new_td();
				will(returnValue(false));

				oneOf(applicationArguments_mock).hasArgument_verify();
				will(returnValue(false));

				oneOf(applicationArguments_mock).hasArgument_convert();
				will(returnValue(true));

				oneOf(applicationArguments_mock).getArgument_td(null);
				will(returnValue("td_file.xml"));

				oneOf(applicationArguments_mock).getArgument_st(null);
				will(returnValue("st_file.xml"));

				oneOf(applicationArguments_mock).getArgument_p(null);
				will(returnValue("p_file.xml"));

				oneOf(applicationArguments_mock).getArgument_nn(null);
				will(returnValue("nn_file.xml"));

				oneOf(application_mock).runCommand(with(ConvertCommand.NAME),
						with(new ConvertCommandDataMatcher().expectSystemTransformationsFile("st_file.xml")
								.expectTaskDescriptionFile("td_file.xml").expectProcessFile("p_file.xml")
								.expectNodeNetworkFile("nn_file.xml")));
			}
		});

		testable.run();
	}

	@Test
	public void run_HelpCommand() throws Exception {
		final ApplicationArguments applicationArguments_mock = context.mock(ApplicationArguments.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).getArguments();
				will(returnValue(applicationArguments_mock));

				oneOf(applicationArguments_mock).hasArgument_plan();
				will(returnValue(false));

				oneOf(applicationArguments_mock).hasArgument_new_st();
				will(returnValue(false));

				oneOf(applicationArguments_mock).hasArgument_new_td();
				will(returnValue(false));

				oneOf(applicationArguments_mock).hasArgument_verify();
				will(returnValue(false));

				oneOf(applicationArguments_mock).hasArgument_convert();
				will(returnValue(false));

				oneOf(application_mock).showHelp();
			}
		});

		testable.run();
	}
}

package application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.UnrecognizedOptionException;
import org.jdom2.JDOMException;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import application.command.ConvertCommand;
import application.command.HelpCommand;
import application.command.HelpCommandDataMatcher;
import application.command.NewSystemTransformationsCommand;
import application.command.NewTaskDescriptionCommand;
import application.command.PlanCommand;
import application.command.PlanCommandData;
import application.command.VerifyCommand;
import application.event.CommandStatusEvent;
import application.event.CommandStatusEventMatcher;
import application.event.HelpMessageEvent;
import application.storage.PersistanceStorage;
import application.ui.UserInterface;
import application.ui.UserInterfaceBuilder;
import application.ui.UserInterfaceBuilder.UserInterfaceType;
import planning.method.NodeNetwork;
import planning.method.SystemTransformations;
import planning.method.TaskDescription;
import planning.model.SystemProcess;

public class ApplicationTest {

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

	Application testable;

	HelpCommand helpCommand_mock;

	PlanCommand planCommand_mock;

	NewSystemTransformationsCommand newSystemTransformationsCommand_mock;

	NewTaskDescriptionCommand newTaskDescriptionCommand_mock;

	VerifyCommand verifyCommand_mock;

	ConvertCommand convertCommand_mock;

	PersistanceStorage persistanceStorage_mock;

	ApplicationArguments applicationArguments_mock;

	UserInterfaceBuilder userInterfaceBuilder_mock;

	@BeforeEach
	public void setup() {
		helpCommand_mock = context.mock(HelpCommand.class);
		planCommand_mock = context.mock(PlanCommand.class);
		newSystemTransformationsCommand_mock = context.mock(NewSystemTransformationsCommand.class);
		newTaskDescriptionCommand_mock = context.mock(NewTaskDescriptionCommand.class);
		verifyCommand_mock = context.mock(VerifyCommand.class);
		convertCommand_mock = context.mock(ConvertCommand.class);
		persistanceStorage_mock = context.mock(PersistanceStorage.class);
		applicationArguments_mock = context.mock(ApplicationArguments.class);
		userInterfaceBuilder_mock = context.mock(UserInterfaceBuilder.class);

		context.checking(new Expectations() {
			{
				oneOf(helpCommand_mock).getName();
				will(returnValue(HelpCommand.NAME));

				oneOf(planCommand_mock).getName();
				will(returnValue(PlanCommand.NAME));

				oneOf(newSystemTransformationsCommand_mock).getName();
				will(returnValue(NewSystemTransformationsCommand.NAME));

				oneOf(newTaskDescriptionCommand_mock).getName();
				will(returnValue(NewTaskDescriptionCommand.NAME));

				oneOf(verifyCommand_mock).getName();
				will(returnValue(VerifyCommand.NAME));

				oneOf(convertCommand_mock).getName();
				will(returnValue(ConvertCommand.NAME));
			}
		});

		testable = new Application(helpCommand_mock, planCommand_mock, newSystemTransformationsCommand_mock,
				newTaskDescriptionCommand_mock, verifyCommand_mock, convertCommand_mock, persistanceStorage_mock,
				applicationArguments_mock, userInterfaceBuilder_mock);
	}

	@Test
	public void newInstance() {
		testable = new Application();
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
	public void run_gui() throws Exception {
		final String[] args = new String[] { "-gui" };
		final UserInterface ui_mock = context.mock(UserInterface.class);

		context.checking(new Expectations() {
			{
				oneOf(applicationArguments_mock).parseArguments(args);

				oneOf(applicationArguments_mock).hasArgument_gui();
				will(returnValue(true));

				oneOf(userInterfaceBuilder_mock).build(UserInterfaceType.gui);
				will(returnValue(ui_mock));

				oneOf(ui_mock).setApplication(testable);

				oneOf(ui_mock).run();
			}
		});

		testable.run(args);
	}

	@Test
	public void run_cli() throws Exception {
		final String[] args = new String[] { "-h" };
		final UserInterface ui_mock = context.mock(UserInterface.class);

		context.checking(new Expectations() {
			{
				oneOf(applicationArguments_mock).parseArguments(args);

				oneOf(applicationArguments_mock).hasArgument_gui();
				will(returnValue(false));

				oneOf(userInterfaceBuilder_mock).build(UserInterfaceType.cli);
				will(returnValue(ui_mock));

				oneOf(ui_mock).setApplication(testable);

				oneOf(ui_mock).run();
			}
		});

		testable.run(args);
	}

	@Test
	public void run_UnrecognizedOption() throws Exception {
		final String[] args = new String[] { "-?" };
		final Options options_mock = new Options();
		final UserInterface ui_mock = context.mock(UserInterface.class);

		context.checking(new Expectations() {
			{
				oneOf(applicationArguments_mock).parseArguments(args);
				will(throwException(new UnrecognizedOptionException("Unrecognized option: -?")));

				oneOf(userInterfaceBuilder_mock).build(UserInterfaceType.cli);
				will(returnValue(ui_mock));

				oneOf(ui_mock).setApplication(testable);

				oneOf(ui_mock).notifyCommandStatus(
						with(new CommandStatusEventMatcher().expectMessage("Unrecognized option: -?")));

				oneOf(applicationArguments_mock).getOptions();
				will(returnValue(options_mock));

				oneOf(helpCommand_mock).execute(with(new HelpCommandDataMatcher().expectOptions(options_mock)));
			}
		});

		testable.run(args);
	}

	@Test
	public void saveSystemTransformations() throws IOException {
		final SystemTransformations systemTransformations_mock = context.mock(SystemTransformations.class);
		final String path = "path-to-file";

		context.checking(new Expectations() {
			{
				oneOf(persistanceStorage_mock).saveSystemTransformations(systemTransformations_mock, path);
			}
		});

		testable.saveSystemTransformations(systemTransformations_mock, path);
	}

	@Test
	public void saveTaskDescription() throws IOException {
		final TaskDescription taskDescription_mock = context.mock(TaskDescription.class);
		final String path = "path-to-file";

		context.checking(new Expectations() {
			{
				oneOf(persistanceStorage_mock).saveTaskDescription(taskDescription_mock, path);
			}
		});

		testable.saveTaskDescription(taskDescription_mock, path);
	}

	@Test
	public void saveSystemProcess() throws IOException {
		final SystemProcess systemProcess_mock = context.mock(SystemProcess.class);
		final String path = "path-to-file";

		context.checking(new Expectations() {
			{
				oneOf(persistanceStorage_mock).saveSystemProcess(systemProcess_mock, path);
			}
		});

		testable.saveSystemProcess(systemProcess_mock, path);
	}

	@Test
	public void saveNodeNetwork() throws IOException {
		final NodeNetwork nodeNetwork_mock = context.mock(NodeNetwork.class);
		final String path = "path-to-file";

		context.checking(new Expectations() {
			{
				oneOf(persistanceStorage_mock).saveNodeNetwork(nodeNetwork_mock, path);
			}
		});

		testable.saveNodeNetwork(nodeNetwork_mock, path);
	}

	@Test
	public void loadSystemTransformations() throws IOException, JDOMException {
		final SystemTransformations systemTransformations_mock = context.mock(SystemTransformations.class);
		final String path = "path-to-file";

		context.checking(new Expectations() {
			{
				oneOf(persistanceStorage_mock).loadSystemTransformations(path);
				will(returnValue(systemTransformations_mock));
			}
		});

		assertEquals(systemTransformations_mock, testable.loadSystemTransformations(path));
	}

	@Test
	public void loadTaskDescription() throws IOException, JDOMException {
		final TaskDescription taskDescription_mock = context.mock(TaskDescription.class);
		final String path = "path-to-file";

		context.checking(new Expectations() {
			{
				oneOf(persistanceStorage_mock).loadTaskDescription(path);
				will(returnValue(taskDescription_mock));
			}
		});

		assertEquals(taskDescription_mock, testable.loadTaskDescription(path));
	}

	@Test
	public void loadNodeNetwork() throws IOException, JDOMException {
		final NodeNetwork nodeNetwork_mock = context.mock(NodeNetwork.class);
		final String path = "path-to-file";

		context.checking(new Expectations() {
			{
				oneOf(persistanceStorage_mock).loadNodeNetwork(path);
				will(returnValue(nodeNetwork_mock));
			}
		});

		assertEquals(nodeNetwork_mock, testable.loadNodeNetwork(path));
	}

	@Test
	public void loadSystemProcess() throws IOException, JDOMException {
		final SystemProcess systemProcess_mock = context.mock(SystemProcess.class);
		final String path = "path-to-file";

		context.checking(new Expectations() {
			{
				oneOf(persistanceStorage_mock).loadSystemProcess(path);
				will(returnValue(systemProcess_mock));
			}
		});

		assertEquals(systemProcess_mock, testable.loadSystemProcess(path));
	}

	@Test
	public void getResourceAsStream() {
		final InputStream resource_mock = context.mock(InputStream.class);
		final String path = "path-to-resource";

		context.checking(new Expectations() {
			{
				oneOf(persistanceStorage_mock).getResourceAsStream(path);
				will(returnValue(resource_mock));
			}
		});

		assertEquals(resource_mock, testable.getResourceAsStream(path));
	}

	@Test
	public void showHelp() throws Exception {
		final Options options_mock = new Options();

		context.checking(new Expectations() {
			{
				oneOf(applicationArguments_mock).getOptions();
				will(returnValue(options_mock));

				oneOf(helpCommand_mock).execute(with(new HelpCommandDataMatcher().expectOptions(options_mock)));
			}
		});

		testable.showHelp();
	}

	@Test
	public void getArguments() {
		assertEquals(applicationArguments_mock, testable.getArguments());
	}

	@Test
	public void runCommand() {
		final PlanCommandData planCommandData = new PlanCommandData();

		context.checking(new Expectations() {
			{
				oneOf(planCommand_mock).run(planCommandData);
			}
		});

		testable.runCommand(PlanCommand.NAME, planCommandData);
	}
}

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

import application.command.CommandManager;
import application.command.ConvertCommand;
import application.command.ConvertCommandDataMatcher;
import application.command.UsageHelpCommand;
import application.command.UsageHelpCommandDataMatcher;
import application.command.NewSystemTransformationsCommand;
import application.command.NewSystemTransformationsCommandDataMatcher;
import application.command.NewTaskDescriptionCommand;
import application.command.NewTaskDescriptionCommandDataMatcher;
import application.command.PlanCommand;
import application.command.PlanCommandDataMatcher;
import application.command.VerifyCommand;
import application.command.VerifyCommandDataMatcher;
import application.event.CommandStatusEvent;
import application.event.CommandStatusEventMatcher;
import application.event.UserMessageEvent;
import application.storage.PersistanceStorage;
import application.ui.UserInterfaceFactory.UserInterfaceType;
import application.ui.UserInterfaceManager;
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

	PersistanceStorage persistanceStorage_mock;

	Arguments arguments_mock;

	UserInterfaceManager userInterfaceManager_mock;

	CommandManager commandManager_mock;

	@BeforeEach
	public void setup() {
		persistanceStorage_mock = context.mock(PersistanceStorage.class);
		arguments_mock = context.mock(Arguments.class);
		userInterfaceManager_mock = context.mock(UserInterfaceManager.class);
		commandManager_mock = context.mock(CommandManager.class);

		testable = new Application(commandManager_mock, persistanceStorage_mock, arguments_mock, userInterfaceManager_mock);
	}

	@Test
	public void newInstance() {
		testable = new Application();
	}

	@Test
	public void notifyEvent_UserMessageEvent() {
		final UserMessageEvent event_mock = context.mock(UserMessageEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(userInterfaceManager_mock).notifyUserMessage(event_mock);
			}
		});

		testable.notifyEvent(event_mock);
	}

	@Test
	public void notifyEvent_CommandStatusEvent() {
		final CommandStatusEvent event_mock = context.mock(CommandStatusEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(userInterfaceManager_mock).notifyCommandStatus(event_mock);
			}
		});

		testable.notifyEvent(event_mock);
	}

	@Test
	public void start_gui() throws Exception {
		final String[] args = new String[] { "-gui" };

		context.checking(new Expectations() {
			{
				oneOf(arguments_mock).parseArguments(args);

				oneOf(arguments_mock).hasArgument_gui();
				will(returnValue(true));

				oneOf(userInterfaceManager_mock).createUserInterface(UserInterfaceType.gui);

				oneOf(userInterfaceManager_mock).runUserInterfaces();
			}
		});

		testable.start(args);
	}

	@Test
	public void start_cli() throws Exception {
		final String[] args = new String[] { "-h" };

		context.checking(new Expectations() {
			{
				oneOf(arguments_mock).parseArguments(args);

				oneOf(arguments_mock).hasArgument_gui();
				will(returnValue(false));

				oneOf(userInterfaceManager_mock).createUserInterface(UserInterfaceType.cli);

				oneOf(userInterfaceManager_mock).runUserInterfaces();
			}
		});

		testable.start(args);
	}

	@Test
	public void start_UnrecognizedOption() throws Exception {
		final String[] args = new String[] { "-?" };
		final Options options_mock = new Options();

		context.checking(new Expectations() {
			{
				oneOf(arguments_mock).parseArguments(args);
				will(throwException(new UnrecognizedOptionException("Unrecognized option: -?")));

				oneOf(userInterfaceManager_mock).createUserInterface(UserInterfaceType.cli);

				oneOf(userInterfaceManager_mock).notifyCommandStatus(with(new CommandStatusEventMatcher().expectMessage("Unrecognized option: -?")));

				oneOf(arguments_mock).getOptions();
				will(returnValue(options_mock));

				oneOf(commandManager_mock).runCommand(with(UsageHelpCommand.NAME), with(new UsageHelpCommandDataMatcher().expectOptions(options_mock)));

				oneOf(commandManager_mock).stop();

				oneOf(userInterfaceManager_mock).stop();
			}
		});

		testable.start(args);
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
	public void usageHelp() throws Exception {
		final Options options_mock = new Options();

		context.checking(new Expectations() {
			{
				oneOf(arguments_mock).getOptions();
				will(returnValue(options_mock));

				oneOf(commandManager_mock).runCommand(with(UsageHelpCommand.NAME), with(new UsageHelpCommandDataMatcher().expectOptions(options_mock)));
			}
		});

		testable.usageHelp();
	}

	@Test
	public void getArguments() {
		assertEquals(arguments_mock, testable.getArguments());
	}

	@Test
	public void plan() {
		context.checking(new Expectations() {
			{
				oneOf(arguments_mock).getArgument_td("taskDescription.xml");
				will(returnValue("td_file.xml"));

				oneOf(arguments_mock).getArgument_st("systemTransformations.xml");
				will(returnValue("st_file.xml"));

				oneOf(arguments_mock).getArgument_p("process.xml");
				will(returnValue("p_file.xml"));

				oneOf(arguments_mock).getArgument_nn("nodeNetwork.xml");
				will(returnValue("nn_file.xml"));

				oneOf(commandManager_mock).runCommand(with(PlanCommand.NAME),
						with(new PlanCommandDataMatcher().expectSystemTransformationsFile("st_file.xml")
								.expectTaskDescriptionFile("td_file.xml").expectProcessFile("p_file.xml")
								.expectNodeNetworkFile("nn_file.xml")));
			}
		});

		testable.plan();
	}

	@Test
	public void verify() {
		context.checking(new Expectations() {
			{
				oneOf(arguments_mock).getArgument_td(null);
				will(returnValue("td_file.xml"));

				oneOf(arguments_mock).getArgument_st(null);
				will(returnValue("st_file.xml"));

				oneOf(arguments_mock).getArgument_p(null);
				will(returnValue("p_file.xml"));

				oneOf(arguments_mock).getArgument_nn(null);
				will(returnValue("nn_file.xml"));

				oneOf(commandManager_mock).runCommand(with(VerifyCommand.NAME),
						with(new VerifyCommandDataMatcher().expectSystemTransformationsFile("st_file.xml")
								.expectTaskDescriptionFile("td_file.xml").expectProcessFile("p_file.xml")
								.expectNodeNetworkFile("nn_file.xml")));
			}
		});

		testable.verify();
	}

	@Test
	public void newTaskDescription() {
		context.checking(new Expectations() {
			{
				oneOf(arguments_mock).getArgument_td("taskDescription.xml");
				will(returnValue("td_file.xml"));

				oneOf(arguments_mock).getArgument_d("unknown");
				will(returnValue("materialPoints"));

				oneOf(commandManager_mock).runCommand(with(NewTaskDescriptionCommand.NAME),
						with(new NewTaskDescriptionCommandDataMatcher().expectTaskDescriptionFile("td_file.xml")
								.expectDomain("materialPoints")));
			}
		});

		testable.newTaskDescription();
	}

	@Test
	public void newSystemTransformations() {
		context.checking(new Expectations() {
			{
				oneOf(arguments_mock).getArgument_st("systemTransformations.xml");
				will(returnValue("st_file.xml"));

				oneOf(arguments_mock).getArgument_d("unknown");
				will(returnValue("assemblyLine"));

				oneOf(commandManager_mock).runCommand(with(NewSystemTransformationsCommand.NAME),
						with(new NewSystemTransformationsCommandDataMatcher()
								.expectSystemTransformationsFile("st_file.xml").expectDomain("assemblyLine")));
			}
		});

		testable.newSystemTransformations();
	}

	@Test
	public void convert() {
		context.checking(new Expectations() {
			{
				oneOf(arguments_mock).getArgument_td(null);
				will(returnValue("td_file.xml"));

				oneOf(arguments_mock).getArgument_st(null);
				will(returnValue("st_file.xml"));

				oneOf(arguments_mock).getArgument_p(null);
				will(returnValue("p_file.xml"));

				oneOf(arguments_mock).getArgument_nn(null);
				will(returnValue("nn_file.xml"));

				oneOf(commandManager_mock).runCommand(with(ConvertCommand.NAME),
						with(new ConvertCommandDataMatcher().expectSystemTransformationsFile("st_file.xml")
								.expectTaskDescriptionFile("td_file.xml").expectProcessFile("p_file.xml")
								.expectNodeNetworkFile("nn_file.xml")));
			}
		});

		testable.convert();
	}

	@Test
	public void stop() {
		context.checking(new Expectations() {
			{
				oneOf(commandManager_mock).stop();

				oneOf(userInterfaceManager_mock).stop();
			}
		});

		testable.stop();
	}
}

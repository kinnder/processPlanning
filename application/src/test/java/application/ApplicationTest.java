package application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;

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
import application.command.UsageHelpCommand;
import application.command.NewSystemTransformationsCommand;
import application.command.NewTaskDescriptionCommand;
import application.command.PlanCommand;
import application.command.StopApplicationCommand;
import application.command.VerifyCommand;
import application.event.CommandEvent;
import application.event.CommandEventMatcher;
import application.event.UserEvent;
import application.event.UserEventMatcher;
import application.event.UserEvent.Type;
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

	ExecutorService executorService_mock;

	@BeforeEach
	public void setup() {
		persistanceStorage_mock = context.mock(PersistanceStorage.class);
		arguments_mock = context.mock(Arguments.class);
		userInterfaceManager_mock = context.mock(UserInterfaceManager.class);
		commandManager_mock = context.mock(CommandManager.class);
		executorService_mock = context.mock(ExecutorService.class);

		testable = new Application(commandManager_mock, persistanceStorage_mock, arguments_mock,
				userInterfaceManager_mock, executorService_mock);
	}

	@Test
	public void newInstance() {
		testable = new Application();
	}

	@Test
	public void pushEvent_UserEvent() {
		final UserEvent event_mock = context.mock(UserEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(userInterfaceManager_mock).pushEvent(event_mock);

				oneOf(commandManager_mock).pushEvent(event_mock);
			}
		});

		testable.pushEvent(event_mock);
	}

	@Test
	public void pushEvent_CommandEvent() {
		final CommandEvent event_mock = context.mock(CommandEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(userInterfaceManager_mock).pushEvent(event_mock);

				oneOf(commandManager_mock).pushEvent(event_mock);
			}
		});

		testable.pushEvent(event_mock);
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

				oneOf(userInterfaceManager_mock).start();

				oneOf(executorService_mock).submit(userInterfaceManager_mock);

				oneOf(executorService_mock).submit(commandManager_mock);

				oneOf(arguments_mock).hasArgument_gui();
				will(returnValue(true));
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

				oneOf(userInterfaceManager_mock).start();

				oneOf(executorService_mock).submit(userInterfaceManager_mock);

				oneOf(executorService_mock).submit(commandManager_mock);

				oneOf(arguments_mock).hasArgument_gui();
				will(returnValue(false));

				oneOf(userInterfaceManager_mock).pushEvent(with(new CommandEventMatcher()
						.expectType(CommandEvent.Type.Start).expectCommandName(StopApplicationCommand.NAME)));

				oneOf(commandManager_mock).pushEvent(with(new CommandEventMatcher().expectType(CommandEvent.Type.Start)
						.expectCommandName(StopApplicationCommand.NAME)));
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

				oneOf(userInterfaceManager_mock).start();

				oneOf(executorService_mock).submit(userInterfaceManager_mock);

				oneOf(executorService_mock).submit(commandManager_mock);

				oneOf(userInterfaceManager_mock).pushEvent(
						with(new UserEventMatcher().expectType(Type.Error).expectMessage("Unrecognized option: -?")));

				oneOf(commandManager_mock).pushEvent(
						with(new UserEventMatcher().expectType(Type.Error).expectMessage("Unrecognized option: -?")));

				oneOf(arguments_mock).getOptions();
				will(returnValue(options_mock));

				oneOf(userInterfaceManager_mock).pushEvent(with(new CommandEventMatcher()
						.expectType(CommandEvent.Type.Start).expectCommandName(UsageHelpCommand.NAME)));

				oneOf(commandManager_mock).pushEvent(with(new CommandEventMatcher().expectType(CommandEvent.Type.Start)
						.expectCommandName(UsageHelpCommand.NAME)));

				oneOf(userInterfaceManager_mock).pushEvent(with(new CommandEventMatcher()
						.expectType(CommandEvent.Type.Start).expectCommandName(StopApplicationCommand.NAME)));

				oneOf(commandManager_mock).pushEvent(with(new CommandEventMatcher().expectType(CommandEvent.Type.Start)
						.expectCommandName(StopApplicationCommand.NAME)));
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
	public void saveSystemTransfromations_2() throws IOException {
		final SystemTransformations systemTransformations_mock = context.mock(SystemTransformations.class);

		context.checking(new Expectations() {
			{
				oneOf(arguments_mock).getArgument_st("systemTransformations.xml");
				will(returnValue("path-to-file"));

				oneOf(persistanceStorage_mock).saveSystemTransformations(systemTransformations_mock, "path-to-file");
			}
		});

		testable.saveSystemTransformations(systemTransformations_mock);
	}

	@Test
	public void saveSystemTransformations_2_throwException() throws IOException {
		final SystemTransformations systemTransformations_mock = context.mock(SystemTransformations.class);

		context.checking(new Expectations() {
			{
				oneOf(arguments_mock).getArgument_st("systemTransformations.xml");
				will(returnValue("path-to-file"));

				oneOf(persistanceStorage_mock).saveSystemTransformations(systemTransformations_mock, "path-to-file");
				will(throwException(new IOException()));

				oneOf(userInterfaceManager_mock)
						.pushEvent(with(new CommandEventMatcher().expectType(CommandEvent.Type.Errored)
								.expectCommandName("saveSystemTransformations").expectMessage("error")));

				oneOf(commandManager_mock)
						.pushEvent(with(new CommandEventMatcher().expectType(CommandEvent.Type.Errored)
								.expectCommandName("saveSystemTransformations").expectMessage("error")));
			}
		});

		testable.saveSystemTransformations(systemTransformations_mock);
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

				oneOf(userInterfaceManager_mock).pushEvent(with(new CommandEventMatcher()
						.expectType(CommandEvent.Type.Start).expectCommandName(UsageHelpCommand.NAME)));

				oneOf(commandManager_mock).pushEvent(with(new CommandEventMatcher().expectType(CommandEvent.Type.Start)
						.expectCommandName(UsageHelpCommand.NAME)));
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

				oneOf(userInterfaceManager_mock).pushEvent(with(new CommandEventMatcher()
						.expectType(CommandEvent.Type.Start).expectCommandName(PlanCommand.NAME)));

				oneOf(commandManager_mock).pushEvent(with(new CommandEventMatcher().expectType(CommandEvent.Type.Start)
						.expectCommandName(PlanCommand.NAME)));
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

				oneOf(userInterfaceManager_mock).pushEvent(with(new CommandEventMatcher()
						.expectType(CommandEvent.Type.Start).expectCommandName(VerifyCommand.NAME)));

				oneOf(commandManager_mock).pushEvent(with(new CommandEventMatcher().expectType(CommandEvent.Type.Start)
						.expectCommandName(VerifyCommand.NAME)));
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

				oneOf(userInterfaceManager_mock).pushEvent(with(new CommandEventMatcher()
						.expectType(CommandEvent.Type.Start).expectCommandName(NewTaskDescriptionCommand.NAME)));

				oneOf(commandManager_mock).pushEvent(with(new CommandEventMatcher().expectType(CommandEvent.Type.Start)
						.expectCommandName(NewTaskDescriptionCommand.NAME)));
			}
		});

		testable.newTaskDescription();
	}

	@Test
	public void newTaskDescription_v2() {
		assertTrue(testable.newTaskDescription_v2() instanceof TaskDescription);
	}

	@Test
	public void newSystemTransformations() {
		context.checking(new Expectations() {
			{
				oneOf(arguments_mock).getArgument_st("systemTransformations.xml");
				will(returnValue("st_file.xml"));

				oneOf(arguments_mock).getArgument_d("unknown");
				will(returnValue("assemblyLine"));

				oneOf(userInterfaceManager_mock).pushEvent(with(new CommandEventMatcher()
						.expectType(CommandEvent.Type.Start).expectCommandName(NewSystemTransformationsCommand.NAME)));

				oneOf(commandManager_mock).pushEvent(with(new CommandEventMatcher().expectType(CommandEvent.Type.Start)
						.expectCommandName(NewSystemTransformationsCommand.NAME)));
			}
		});

		testable.newSystemTransformations();
	}

	@Test
	public void newSystemTransformations_v2() {
		assertTrue(testable.newSystemTransformations_v2() instanceof SystemTransformations);
	}

	@Test
	public void newNodeNetwork_v2() {
		assertTrue(testable.newNodeNetwork_v2() instanceof NodeNetwork);
	}

	@Test
	public void newSystemProcess_v2() {
		assertTrue(testable.newSystemProcess_v2() instanceof SystemProcess);
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

				oneOf(userInterfaceManager_mock).pushEvent(with(new CommandEventMatcher()
						.expectType(CommandEvent.Type.Start).expectCommandName(ConvertCommand.NAME)));

				oneOf(commandManager_mock).pushEvent(with(new CommandEventMatcher().expectType(CommandEvent.Type.Start)
						.expectCommandName(ConvertCommand.NAME)));
			}
		});

		testable.convert();
	}

	@Test
	public void stopApplication() {
		context.checking(new Expectations() {
			{
				oneOf(userInterfaceManager_mock).pushEvent(with(new CommandEventMatcher()
						.expectType(CommandEvent.Type.Start).expectCommandName(StopApplicationCommand.NAME)));

				oneOf(commandManager_mock).pushEvent(with(new CommandEventMatcher().expectType(CommandEvent.Type.Start)
						.expectCommandName(StopApplicationCommand.NAME)));
			}
		});

		testable.stopApplication();
	}

	@Test
	public void stop() {
		context.checking(new Expectations() {
			{
				oneOf(commandManager_mock).stop();

				oneOf(userInterfaceManager_mock).stop();

				oneOf(executorService_mock).shutdown();
			}
		});

		testable.stop();
	}

	@Test
	public void loadSystemProcess_2() throws IOException, JDOMException {
		final SystemProcess systemProcess_mock = context.mock(SystemProcess.class);

		context.checking(new Expectations() {
			{
				oneOf(arguments_mock).getArgument_p("process.xml");
				will(returnValue("path-to-file"));

				oneOf(persistanceStorage_mock).loadSystemProcess("path-to-file");
				will(returnValue(systemProcess_mock));
			}
		});

		assertEquals(systemProcess_mock, testable.loadSystemProcess());
	}

	@Test
	public void loadSystemProcess_2_throwException() throws IOException, JDOMException {
		context.checking(new Expectations() {
			{
				oneOf(arguments_mock).getArgument_p("process.xml");
				will(returnValue("path-to-file"));

				oneOf(persistanceStorage_mock).loadSystemProcess("path-to-file");
				will(throwException(new JDOMException()));

				oneOf(userInterfaceManager_mock)
						.pushEvent(with(new CommandEventMatcher().expectType(CommandEvent.Type.Errored)
								.expectCommandName("loadSystemProcess").expectMessage("error")));

				oneOf(commandManager_mock)
						.pushEvent(with(new CommandEventMatcher().expectType(CommandEvent.Type.Errored)
								.expectCommandName("loadSystemProcess").expectMessage("error")));
			}
		});

		assertNull(testable.loadSystemProcess());
	}

	@Test
	public void loadNodeNetwork_2() throws IOException, JDOMException {
		final NodeNetwork nodeNetwork_mock = context.mock(NodeNetwork.class);

		context.checking(new Expectations() {
			{
				oneOf(arguments_mock).getArgument_nn("nodeNetwork.xml");
				will(returnValue("path-to-file"));

				oneOf(persistanceStorage_mock).loadNodeNetwork("path-to-file");
				will(returnValue(nodeNetwork_mock));
			}
		});

		assertEquals(nodeNetwork_mock, testable.loadNodeNetwork());
	}

	@Test
	public void loadNodeNetwork_2_throwException() throws IOException, JDOMException {
		context.checking(new Expectations() {
			{
				oneOf(arguments_mock).getArgument_nn("nodeNetwork.xml");
				will(returnValue("path-to-file"));

				oneOf(persistanceStorage_mock).loadNodeNetwork("path-to-file");
				will(throwException(new JDOMException()));

				oneOf(userInterfaceManager_mock)
						.pushEvent(with(new CommandEventMatcher().expectType(CommandEvent.Type.Errored)
								.expectCommandName("loadNodeNetwork").expectMessage("error")));

				oneOf(commandManager_mock)
						.pushEvent(with(new CommandEventMatcher().expectType(CommandEvent.Type.Errored)
								.expectCommandName("loadNodeNetwork").expectMessage("error")));
			}
		});

		assertNull(testable.loadNodeNetwork());
	}

	@Test
	public void loadTaskDescription_2() throws IOException, JDOMException {
		final TaskDescription taskDescription_mock = context.mock(TaskDescription.class);

		context.checking(new Expectations() {
			{
				oneOf(arguments_mock).getArgument_td("taskDescription.xml");
				will(returnValue("path-to-file"));

				oneOf(persistanceStorage_mock).loadTaskDescription("path-to-file");
				will(returnValue(taskDescription_mock));
			}
		});

		assertEquals(taskDescription_mock, testable.loadTaskDescription());
	}

	@Test
	public void loadTaskDescription_2_throwException() throws IOException, JDOMException {
		context.checking(new Expectations() {
			{
				oneOf(arguments_mock).getArgument_td("taskDescription.xml");
				will(returnValue("path-to-file"));

				oneOf(persistanceStorage_mock).loadTaskDescription("path-to-file");
				will(throwException(new JDOMException()));

				oneOf(userInterfaceManager_mock)
						.pushEvent(with(new CommandEventMatcher().expectType(CommandEvent.Type.Errored)
								.expectCommandName("loadTaskDescription").expectMessage("error")));

				oneOf(commandManager_mock)
						.pushEvent(with(new CommandEventMatcher().expectType(CommandEvent.Type.Errored)
								.expectCommandName("loadTaskDescription").expectMessage("error")));
			}
		});

		assertNull(testable.loadTaskDescription());
	}

	@Test
	public void loadSystemTransformations_2() throws IOException, JDOMException {
		final SystemTransformations systemTransformations_mock = context.mock(SystemTransformations.class);

		context.checking(new Expectations() {
			{
				oneOf(arguments_mock).getArgument_st("systemTransformations.xml");
				will(returnValue("path-to-file"));

				oneOf(persistanceStorage_mock).loadSystemTransformations("path-to-file");
				will(returnValue(systemTransformations_mock));
			}
		});

		assertEquals(systemTransformations_mock, testable.loadSystemTransformations());
	}

	@Test
	public void loadSystemTransformations_2_throwException() throws IOException, JDOMException {
		context.checking(new Expectations() {
			{
				oneOf(arguments_mock).getArgument_st("systemTransformations.xml");
				will(returnValue("path-to-file"));

				oneOf(persistanceStorage_mock).loadSystemTransformations("path-to-file");
				will(throwException(new JDOMException()));

				oneOf(userInterfaceManager_mock)
						.pushEvent(with(new CommandEventMatcher().expectType(CommandEvent.Type.Errored)
								.expectCommandName("loadSystemTransformations").expectMessage("error")));

				oneOf(commandManager_mock)
						.pushEvent(with(new CommandEventMatcher().expectType(CommandEvent.Type.Errored)
								.expectCommandName("loadSystemTransformations").expectMessage("error")));
			}
		});

		assertNull(testable.loadSystemTransformations());
	}

	@Test
	public void saveTaskDescription_2() throws IOException, JDOMException {
		final TaskDescription taskDescription_mock = context.mock(TaskDescription.class);

		context.checking(new Expectations() {
			{
				oneOf(arguments_mock).getArgument_td("taskDescription.xml");
				will(returnValue("path-to-file"));

				oneOf(persistanceStorage_mock).saveTaskDescription(taskDescription_mock, "path-to-file");
			}
		});

		testable.saveTaskDescription(taskDescription_mock);
	}

	@Test
	public void saveTaskDescription_2_throwException() throws IOException, JDOMException {
		final TaskDescription taskDescription_mock = context.mock(TaskDescription.class);

		context.checking(new Expectations() {
			{
				oneOf(arguments_mock).getArgument_td("taskDescription.xml");
				will(returnValue("path-to-file"));

				oneOf(persistanceStorage_mock).saveTaskDescription(taskDescription_mock, "path-to-file");
				will(throwException(new IOException()));

				oneOf(userInterfaceManager_mock)
						.pushEvent(with(new CommandEventMatcher().expectType(CommandEvent.Type.Errored)
								.expectCommandName("saveTaskDescription").expectMessage("error")));

				oneOf(commandManager_mock)
						.pushEvent(with(new CommandEventMatcher().expectType(CommandEvent.Type.Errored)
								.expectCommandName("saveTaskDescription").expectMessage("error")));
			}
		});

		testable.saveTaskDescription(taskDescription_mock);
	}
}

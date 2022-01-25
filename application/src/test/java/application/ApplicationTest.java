package application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.jdom2.JDOMException;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import application.command.ConvertCommand;
import application.command.ConvertCommandDataMatcher;
import application.command.HelpCommand;
import application.command.HelpCommandDataMatcher;
import application.command.NewSystemTransformationsCommand;
import application.command.NewSystemTransformationsCommandDataMatcher;
import application.command.NewTaskDescriptionCommand;
import application.command.NewTaskDescriptionCommandDataMatcher;
import application.command.PlanCommand;
import application.command.PlanCommandDataMatcher;
import application.command.VerifyCommand;
import application.command.VerifyCommandDataMatcher;
import application.domain.AssemblyLine;
import application.domain.CuttingProcess;
import application.domain.MaterialPoints;
import application.event.CommandStatusEvent;
import application.event.CommandStatusEventMatcher;
import application.event.HelpMessageEvent;
import application.storage.PersistanceStorage;
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

	@BeforeEach
	public void setup() {
		helpCommand_mock = context.mock(HelpCommand.class);
		planCommand_mock = context.mock(PlanCommand.class);
		newSystemTransformationsCommand_mock = context.mock(NewSystemTransformationsCommand.class);
		newTaskDescriptionCommand_mock = context.mock(NewTaskDescriptionCommand.class);
		verifyCommand_mock = context.mock(VerifyCommand.class);
		convertCommand_mock = context.mock(ConvertCommand.class);
		persistanceStorage_mock = context.mock(PersistanceStorage.class);

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
				newTaskDescriptionCommand_mock, verifyCommand_mock, convertCommand_mock, persistanceStorage_mock);
	}

	@Test
	public void newInstance() {
		testable = new Application();
	}

	@Test
	public void registerUserInterface() {
		final UserInterfaceImp ui_mock = context.mock(UserInterfaceImp.class);

		testable.registerUserInterface(ui_mock);
	}

	@Test
	public void notifyHelpMessage() {
		final UserInterfaceImp ui_mock = context.mock(UserInterfaceImp.class);
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
		final UserInterfaceImp ui_mock = context.mock(UserInterfaceImp.class);
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
	public void run_HelpCommand() throws Exception {
		Option h_option = new Option("h", "help", false, "prints usage");
		Option td_option = new Option("td", "task-description", true, "file with description of the task");
		Option st_option = new Option("st", "system-transformations", true, "file with description of the system transformations");
		Option p_option = new Option("p", "process", true, "output file with process");
		Option nn_option = new Option("nn", "node-network", true, "output file with node network");
		Option plan_option = new Option("plan", "plan process");
		Option new_st_option = new Option("new_st", "new-system-transformations", false, "create new file with predefined system transformations");
		Option new_td_option = new Option("new_td", "new-task-description", false, "create new file with predefined task description");
		Option verify_option = new Option("verify", "verify xml-files with according xml-schemas");
		Option convert_option = new Option("convert", "convert files between formats: xml to owl and owl to xml");
		Option gui_option = new Option("gui", "show graphical user interface");
		Option d_option = new Option("d", "domain", true, String.format("defines domain for predefined data: %s, %s or %s", MaterialPoints.DOMAIN_NAME, CuttingProcess.DOMAIN_NAME, AssemblyLine.DOMAIN_NAME));

		Options options = new Options();
		options.addOption(h_option);
		options.addOption(td_option);
		options.addOption(st_option);
		options.addOption(p_option);
		options.addOption(nn_option);
		options.addOption(plan_option);
		options.addOption(new_st_option);
		options.addOption(new_td_option);
		options.addOption(verify_option);
		options.addOption(convert_option);
		options.addOption(gui_option);
		options.addOption(d_option);

		context.checking(new Expectations() {
			{
				oneOf(helpCommand_mock).execute(with(new HelpCommandDataMatcher().expectOptions(options)));
			}
		});

		testable.run(new String[] { "-h" });
	}

	@Test
	public void run_PlanCommand() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(planCommand_mock).run(with(new PlanCommandDataMatcher()
						.expectSystemTransformationsFile("st_file.xml").expectTaskDescriptionFile("td_file.xml")
						.expectProcessFile("p_file.xml").expectNodeNetworkFile("nn_file.xml")));
			}
		});

		testable.run(new String[] { "-plan", "-task-description=td_file.xml", "-system-transformations=st_file.xml",
				"-process=p_file.xml", "-node-network=nn_file.xml" });
	}

	@Test
	public void run_NewSystemTransformationsCommand() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(newSystemTransformationsCommand_mock)
						.run(with(new NewSystemTransformationsCommandDataMatcher()
								.expectSystemTransformationsFile("st_file.xml").expectDomain("unknown")));
			}
		});

		testable.run(new String[] { "-new_st", "-system-transformations=st_file.xml" });
	}

	@Test
	public void run_NewTaskDescriptionCommand() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(newTaskDescriptionCommand_mock).run(with(new NewTaskDescriptionCommandDataMatcher()
						.expectTaskDescriptionFile("td_file.xml").expectDomain("materialPoints")));
			}
		});

		testable.run(new String[] { "-new_td", "-domain=materialPoints", "-task-description=td_file.xml" });
	}

	@Test
	public void run_VerifyCommand() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(verifyCommand_mock).run(with(new VerifyCommandDataMatcher()
						.expectSystemTransformationsFile("st_file.xml").expectTaskDescriptionFile("td_file.xml")
						.expectProcessFile("p_file.xml").expectNodeNetworkFile("nn_file.xml")));
			}
		});

		testable.run(new String[] { "-verify", "-task-description=td_file.xml", "-system-transformations=st_file.xml",
				"-process=p_file.xml", "-node-network=nn_file.xml" });
	}

	@Test
	public void run_ConvertCommand() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(convertCommand_mock).run(with(new ConvertCommandDataMatcher()
						.expectSystemTransformationsFile("st_file.xml").expectTaskDescriptionFile("td_file.xml")
						.expectProcessFile("p_file.xml").expectNodeNetworkFile("nn_file.xml")));
			}
		});

		testable.run(new String[] { "-convert", "-task-description=td_file.xml", "-system-transformations=st_file.xml",
				"-process=p_file.xml", "-node-network=nn_file.xml" });
	}

	@Test
	public void run_UnrecognizedOption() throws Exception {
		Option h_option = new Option("h", "help", false, "prints usage");
		Option td_option = new Option("td", "task-description", true, "file with description of the task");
		Option st_option = new Option("st", "system-transformations", true, "file with description of the system transformations");
		Option p_option = new Option("p", "process", true, "output file with process");
		Option nn_option = new Option("nn", "node-network", true, "output file with node network");
		Option plan_option = new Option("plan", "plan process");
		Option new_st_option = new Option("new_st", "new-system-transformations", false, "create new file with predefined system transformations");
		Option new_td_option = new Option("new_td", "new-task-description", false, "create new file with predefined task description");
		Option verify_option = new Option("verify", "verify xml-files with according xml-schemas");
		Option convert_option = new Option("convert", "convert files between formats: xml to owl and owl to xml");
		Option gui_option = new Option("gui", "show graphical user interface");
		Option d_option = new Option("d", "domain", true, String.format("defines domain for predefined data: %s, %s or %s", MaterialPoints.DOMAIN_NAME, CuttingProcess.DOMAIN_NAME, AssemblyLine.DOMAIN_NAME));

		Options options = new Options();
		options.addOption(h_option);
		options.addOption(td_option);
		options.addOption(st_option);
		options.addOption(p_option);
		options.addOption(nn_option);
		options.addOption(plan_option);
		options.addOption(new_st_option);
		options.addOption(new_td_option);
		options.addOption(verify_option);
		options.addOption(convert_option);
		options.addOption(gui_option);
		options.addOption(d_option);

		UserInterfaceImp ui_mock = context.mock(UserInterfaceImp.class);

		context.checking(new Expectations() {
			{
				oneOf(ui_mock).notifyCommandStatus(with(new CommandStatusEventMatcher().expectMessage("Unrecognized option: -?")));

				oneOf(helpCommand_mock).execute(with(new HelpCommandDataMatcher().expectOptions(options)));
			}
		});
		testable.registerUserInterface(ui_mock);

		testable.run(new String[] { "-?" });
	}

	@Test
	public void run_commandNotSpecified() throws Exception {
		Option h_option = new Option("h", "help", false, "prints usage");
		Option td_option = new Option("td", "task-description", true, "file with description of the task");
		Option st_option = new Option("st", "system-transformations", true, "file with description of the system transformations");
		Option p_option = new Option("p", "process", true, "output file with process");
		Option nn_option = new Option("nn", "node-network", true, "output file with node network");
		Option plan_option = new Option("plan", "plan process");
		Option new_st_option = new Option("new_st", "new-system-transformations", false, "create new file with predefined system transformations");
		Option new_td_option = new Option("new_td", "new-task-description", false, "create new file with predefined task description");
		Option verify_option = new Option("verify", "verify xml-files with according xml-schemas");
		Option convert_option = new Option("convert", "convert files between formats: xml to owl and owl to xml");
		Option gui_option = new Option("gui", "show graphical user interface");
		Option d_option = new Option("d", "domain", true, String.format("defines domain for predefined data: %s, %s or %s", MaterialPoints.DOMAIN_NAME, CuttingProcess.DOMAIN_NAME, AssemblyLine.DOMAIN_NAME));

		Options options = new Options();
		options.addOption(h_option);
		options.addOption(td_option);
		options.addOption(st_option);
		options.addOption(p_option);
		options.addOption(nn_option);
		options.addOption(plan_option);
		options.addOption(new_st_option);
		options.addOption(new_td_option);
		options.addOption(verify_option);
		options.addOption(convert_option);
		options.addOption(gui_option);
		options.addOption(d_option);

		UserInterfaceImp ui_mock = context.mock(UserInterfaceImp.class);

		context.checking(new Expectations() {
			{
				oneOf(helpCommand_mock).execute(with(new HelpCommandDataMatcher().expectOptions(options)));
			}
		});
		testable.registerUserInterface(ui_mock);

		testable.run(new String[] { "" });
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
}

package application;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.cli.UnrecognizedOptionException;
import org.jdom2.JDOMException;
import application.command.CommandData;
import application.command.CommandManager;
import application.command.ConvertCommand;
import application.command.ConvertCommandData;
import application.command.UsageHelpCommand;
import application.command.UsageHelpCommandData;
import application.command.NewSystemTransformationsCommand;
import application.command.NewSystemTransformationsCommandData;
import application.command.NewTaskDescriptionCommand;
import application.command.NewTaskDescriptionCommandData;
import application.command.PlanCommand;
import application.command.PlanCommandData;
import application.command.VerifyCommand;
import application.command.VerifyCommandData;
import application.event.CommandStatusEvent;
import application.event.UserMessageEvent;
import application.storage.PersistanceStorage;
import application.ui.UserInterfaceManager;
import application.ui.UserInterfaceFactory.UserInterfaceType;
import planning.method.NodeNetwork;
import planning.method.SystemTransformations;
import planning.method.TaskDescription;
import planning.model.SystemProcess;

public class Application {

	private CommandManager commandManager = new CommandManager();

	private UserInterfaceManager userInterfaceManager;

	public Application() {
		commandManager.registerCommand(new UsageHelpCommand(this));
		commandManager.registerCommand(new PlanCommand(this));
		commandManager.registerCommand(new NewSystemTransformationsCommand(this));
		commandManager.registerCommand(new NewTaskDescriptionCommand(this));
		commandManager.registerCommand(new VerifyCommand(this));
		commandManager.registerCommand(new ConvertCommand(this));

		persistanceStorage = new PersistanceStorage();
		arguments = new Arguments();

		userInterfaceManager = new UserInterfaceManager(this);
	}

	Application(UsageHelpCommand usageHelpCommand, PlanCommand planCommand,
			NewSystemTransformationsCommand newSystemTransformationsCommand,
			NewTaskDescriptionCommand newTaskDescriptionCommand, VerifyCommand verifyCommand,
			ConvertCommand convertCommand, PersistanceStorage persistanceStorage, Arguments arguments,
			UserInterfaceManager userInterfaceManager) {

		commandManager.registerCommand(usageHelpCommand);
		commandManager.registerCommand(planCommand);
		commandManager.registerCommand(newSystemTransformationsCommand);
		commandManager.registerCommand(newTaskDescriptionCommand);
		commandManager.registerCommand(verifyCommand);
		commandManager.registerCommand(convertCommand);

		this.persistanceStorage = persistanceStorage;
		this.arguments = arguments;

		this.userInterfaceManager = userInterfaceManager;
	}

	public void notifyUserMessage(UserMessageEvent event) {
		userInterfaceManager.notifyUserMessage(event);
	}

	public void notifyCommandStatus(CommandStatusEvent event) {
		userInterfaceManager.notifyCommandStatus(event);
	}

	private PersistanceStorage persistanceStorage;

	public void saveSystemTransformations(SystemTransformations systemTransformations, String path) throws IOException {
		persistanceStorage.saveSystemTransformations(systemTransformations, path);
	}

	public void saveTaskDescription(TaskDescription taskDescription, String path) throws IOException {
		persistanceStorage.saveTaskDescription(taskDescription, path);
	}

	public void saveSystemProcess(SystemProcess systemProcess, String path) throws IOException {
		persistanceStorage.saveSystemProcess(systemProcess, path);
	}

	public void saveNodeNetwork(NodeNetwork nodeNetwork, String path) throws IOException {
		persistanceStorage.saveNodeNetwork(nodeNetwork, path);
	}

	public SystemTransformations loadSystemTransformations(String path) throws IOException, JDOMException {
		return persistanceStorage.loadSystemTransformations(path);
	}

	public TaskDescription loadTaskDescription(String path) throws IOException, JDOMException {
		return persistanceStorage.loadTaskDescription(path);
	}

	public NodeNetwork loadNodeNetwork(String path) throws IOException, JDOMException {
		return persistanceStorage.loadNodeNetwork(path);
	}

	public SystemProcess loadSystemProcess(String path) throws IOException, JDOMException {
		return persistanceStorage.loadSystemProcess(path);
	}

	public InputStream getResourceAsStream(String resourcePath) {
		return persistanceStorage.getResourceAsStream(resourcePath);
	}

	private Arguments arguments;

	public Arguments getArguments() {
		return arguments;
	}

	public void run(String[] args) throws Exception {
		try {
			arguments.parseArguments(args);
			userInterfaceManager.createUserInterface(arguments.hasArgument_gui() ? UserInterfaceType.gui : UserInterfaceType.cli);
			userInterfaceManager.runUserInterfaces();
		} catch (UnrecognizedOptionException e) {
			userInterfaceManager.createUserInterface(UserInterfaceType.cli);
			notifyCommandStatus(new CommandStatusEvent(e.getMessage()));
			usageHelp();
		}
	}

	public void runCommand(String commandName, CommandData commandData) {
		commandManager.runCommand(commandName, commandData);
	}

	public void plan() {
		PlanCommandData data = new PlanCommandData();
		data.taskDescriptionFile = arguments.getArgument_td("taskDescription.xml");
		data.systemTransformationsFile = arguments.getArgument_st("systemTransformations.xml");
		data.processFile = arguments.getArgument_p("process.xml");
		data.nodeNetworkFile = arguments.getArgument_nn("nodeNetwork.xml");
		runCommand(PlanCommand.NAME, data);
	}

	public void verify() {
		VerifyCommandData data = new VerifyCommandData();
		data.taskDescriptionFile = arguments.getArgument_td(null);
		data.systemTransformationsFile = arguments.getArgument_st(null);
		data.processFile = arguments.getArgument_p(null);
		data.nodeNetworkFile = arguments.getArgument_nn(null);
		runCommand(VerifyCommand.NAME, data);
	}

	public void newTaskDescription() {
		NewTaskDescriptionCommandData data = new NewTaskDescriptionCommandData();
		data.taskDescriptionFile = arguments.getArgument_td("taskDescription.xml");
		data.domain = arguments.getArgument_d("unknown");
		runCommand(NewTaskDescriptionCommand.NAME, data);
	}

	public void newSystemTransformations() {
		NewSystemTransformationsCommandData data = new NewSystemTransformationsCommandData();
		data.systemTransformationsFile = arguments.getArgument_st("systemTransformations.xml");
		data.domain = arguments.getArgument_d("unknown");
		runCommand(NewSystemTransformationsCommand.NAME, data);
	}

	public void convert() {
		ConvertCommandData data = new ConvertCommandData();
		data.taskDescriptionFile = arguments.getArgument_td(null);
		data.systemTransformationsFile = arguments.getArgument_st(null);
		data.processFile = arguments.getArgument_p(null);
		data.nodeNetworkFile = arguments.getArgument_nn(null);
		runCommand(ConvertCommand.NAME, data);
	}

	public void usageHelp() {
		UsageHelpCommandData data = new UsageHelpCommandData();
		data.options = arguments.getOptions();
		runCommand(UsageHelpCommand.NAME, data);
	}
}

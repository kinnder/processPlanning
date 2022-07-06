package application;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.cli.UnrecognizedOptionException;
import org.jdom2.JDOMException;
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
import application.event.CommandEvent;
import application.event.Event;
import application.event.UserEvent;
import application.storage.PersistanceStorage;
import application.ui.UserInterfaceManager;
import application.ui.UserInterfaceFactory.UserInterfaceType;
import planning.method.NodeNetwork;
import planning.method.SystemTransformations;
import planning.method.TaskDescription;
import planning.model.SystemProcess;

public class Application {

	public Application() {
		persistanceStorage = new PersistanceStorage();
		arguments = new Arguments();

		userInterfaceManager = new UserInterfaceManager(this);
		commandManager = new CommandManager(this);
	}

	Application(CommandManager commandManager, PersistanceStorage persistanceStorage, Arguments arguments, UserInterfaceManager userInterfaceManager) {
		this.persistanceStorage = persistanceStorage;
		this.arguments = arguments;

		this.userInterfaceManager = userInterfaceManager;
		this.commandManager = commandManager;
	}

	public void start(String[] args) throws Exception {
		try {
			arguments.parseArguments(args);
			userInterfaceManager.createUserInterface(arguments.hasArgument_gui() ? UserInterfaceType.gui : UserInterfaceType.cli);
			userInterfaceManager.start();
		} catch (UnrecognizedOptionException e) {
			userInterfaceManager.createUserInterface(UserInterfaceType.cli);
			notifyEvent(UserEvent.error(e.getMessage()));
			usageHelp();
			stop();
		}
	}

	public void stop() {
		commandManager.stop();
		userInterfaceManager.stop();
	}

	private UserInterfaceManager userInterfaceManager;

	public void notifyEvent(Event event) {
		userInterfaceManager.notifyEvent(event);
		commandManager.notifyEvent(event);
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

	private CommandManager commandManager;

	public void plan() {
		PlanCommandData data = new PlanCommandData();
		data.taskDescriptionFile = arguments.getArgument_td("taskDescription.xml");
		data.systemTransformationsFile = arguments.getArgument_st("systemTransformations.xml");
		data.processFile = arguments.getArgument_p("process.xml");
		data.nodeNetworkFile = arguments.getArgument_nn("nodeNetwork.xml");
		notifyEvent(CommandEvent.start(PlanCommand.NAME, data));
	}

	public void verify() {
		VerifyCommandData data = new VerifyCommandData();
		data.taskDescriptionFile = arguments.getArgument_td(null);
		data.systemTransformationsFile = arguments.getArgument_st(null);
		data.processFile = arguments.getArgument_p(null);
		data.nodeNetworkFile = arguments.getArgument_nn(null);
		notifyEvent(CommandEvent.start(VerifyCommand.NAME, data));
	}

	public void newTaskDescription() {
		NewTaskDescriptionCommandData data = new NewTaskDescriptionCommandData();
		data.taskDescriptionFile = arguments.getArgument_td("taskDescription.xml");
		data.domain = arguments.getArgument_d("unknown");
		notifyEvent(CommandEvent.start(NewTaskDescriptionCommand.NAME, data));
	}

	public void newSystemTransformations() {
		NewSystemTransformationsCommandData data = new NewSystemTransformationsCommandData();
		data.systemTransformationsFile = arguments.getArgument_st("systemTransformations.xml");
		data.domain = arguments.getArgument_d("unknown");
		notifyEvent(CommandEvent.start(NewSystemTransformationsCommand.NAME, data));
	}

	public void convert() {
		ConvertCommandData data = new ConvertCommandData();
		data.taskDescriptionFile = arguments.getArgument_td(null);
		data.systemTransformationsFile = arguments.getArgument_st(null);
		data.processFile = arguments.getArgument_p(null);
		data.nodeNetworkFile = arguments.getArgument_nn(null);
		notifyEvent(CommandEvent.start(ConvertCommand.NAME, data));
	}

	public void usageHelp() {
		UsageHelpCommandData data = new UsageHelpCommandData();
		data.options = arguments.getOptions();
		notifyEvent(CommandEvent.start(UsageHelpCommand.NAME, data));
	}
}

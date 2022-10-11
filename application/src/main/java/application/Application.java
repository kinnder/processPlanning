package application;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
import application.command.StopApplicationCommand;
import application.command.StopApplicationCommandData;
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

		executor = Executors.newFixedThreadPool(2);
	}

	private ExecutorService executor;

	Application(CommandManager commandManager, PersistanceStorage persistanceStorage, Arguments arguments, UserInterfaceManager userInterfaceManager, ExecutorService executorService) {
		this.persistanceStorage = persistanceStorage;
		this.arguments = arguments;

		this.userInterfaceManager = userInterfaceManager;
		this.commandManager = commandManager;

		this.executor = executorService;
	}

	public void start(String[] args) throws Exception {
		try {
			arguments.parseArguments(args);
			userInterfaceManager.createUserInterface(arguments.hasArgument_gui() ? UserInterfaceType.gui : UserInterfaceType.cli);
			userInterfaceManager.start();
			executor.submit(userInterfaceManager);
			executor.submit(commandManager);
			if (arguments.hasArgument_gui() == false) {
				stopApplication();
			}
		} catch (UnrecognizedOptionException e) {
			userInterfaceManager.createUserInterface(UserInterfaceType.cli);
			userInterfaceManager.start();
			executor.submit(userInterfaceManager);
			executor.submit(commandManager);
			pushEvent(UserEvent.error(e.getMessage()));
			usageHelp();
			stopApplication();
		}
	}

	public void stop() {
		commandManager.stop();
		userInterfaceManager.stop();
		executor.shutdown();
	}

	private UserInterfaceManager userInterfaceManager;

	public void pushEvent(Event event) {
		userInterfaceManager.pushEvent(event);
		commandManager.pushEvent(event);
	}

	private PersistanceStorage persistanceStorage;

	public void saveSystemTransformations(SystemTransformations systemTransformations, String path) throws IOException {
		persistanceStorage.saveSystemTransformations(systemTransformations, path);
	}

	public void saveTaskDescription(TaskDescription taskDescription, String path) throws IOException {
		persistanceStorage.saveTaskDescription(taskDescription, path);
	}

	// TODO (2022-10-11 #72): переделать в команду
	public void saveTaskDescription(TaskDescription taskDescription) {
		String td_path = arguments.getArgument_td("taskDescription.xml");
		try {
			persistanceStorage.saveTaskDescription(taskDescription, td_path);
		} catch (IOException e) {
			pushEvent(CommandEvent.status("saveTaskDescription", e.getStackTrace().toString()));
		}
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

	// TODO (2022-09-18 #72): переделать в команду
	public SystemTransformations loadSystemTransformations() {
		String st_path = arguments.getArgument_st("systemTransformations.xml");
		SystemTransformations systemTransformations;
		try {
			systemTransformations = loadSystemTransformations(st_path);
		} catch (IOException | JDOMException e) {
			pushEvent(CommandEvent.status("loadSystemTransformations", e.getStackTrace().toString()));
			systemTransformations = new SystemTransformations();
		}
		return systemTransformations;
	}

	public TaskDescription loadTaskDescription(String path) throws IOException, JDOMException {
		return persistanceStorage.loadTaskDescription(path);
	}

	// TODO (2022-09-18 #72): переделать в команду
	public TaskDescription loadTaskDescription() {
		String td_path = arguments.getArgument_td("taskDescription.xml");
		TaskDescription taskDescription;
		try {
			taskDescription = loadTaskDescription(td_path);
		} catch (IOException | JDOMException e) {
			pushEvent(CommandEvent.status("loadTaskDescription", e.getStackTrace().toString()));
			taskDescription = new TaskDescription();
		}
		return taskDescription;
	}

	public NodeNetwork loadNodeNetwork(String path) throws IOException, JDOMException {
		return persistanceStorage.loadNodeNetwork(path);
	}

	// TODO (2022-09-18 #72): переделать в команду
	public NodeNetwork loadNodeNetwork() {
		String nn_path = arguments.getArgument_nn("nodeNetwork.xml");
		NodeNetwork nodeNetwork;
		try {
			nodeNetwork = loadNodeNetwork(nn_path);
		} catch (IOException | JDOMException e) {
			pushEvent(CommandEvent.status("loadNodeNetwork", e.getStackTrace().toString()));
			nodeNetwork = new NodeNetwork();
		}
		return nodeNetwork;
	}

	public SystemProcess loadSystemProcess(String path) throws IOException, JDOMException {
		return persistanceStorage.loadSystemProcess(path);
	}

	// TODO (2022-09-18 #72): переделать в команду
	public SystemProcess loadSystemProcess() {
		String pr_path = arguments.getArgument_p("process.xml");
		SystemProcess systemProcess;
		try {
			systemProcess = loadSystemProcess(pr_path);
		} catch (IOException | JDOMException e) {
			pushEvent(CommandEvent.status("loadNodeNetwork", e.getStackTrace().toString()));
			systemProcess = new SystemProcess();
		}
		return systemProcess;
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
		pushEvent(CommandEvent.start(PlanCommand.NAME, data));
	}

	public void verify() {
		VerifyCommandData data = new VerifyCommandData();
		data.taskDescriptionFile = arguments.getArgument_td(null);
		data.systemTransformationsFile = arguments.getArgument_st(null);
		data.processFile = arguments.getArgument_p(null);
		data.nodeNetworkFile = arguments.getArgument_nn(null);
		pushEvent(CommandEvent.start(VerifyCommand.NAME, data));
	}

	public void newTaskDescription() {
		NewTaskDescriptionCommandData data = new NewTaskDescriptionCommandData();
		data.taskDescriptionFile = arguments.getArgument_td("taskDescription.xml");
		data.domain = arguments.getArgument_d("unknown");
		pushEvent(CommandEvent.start(NewTaskDescriptionCommand.NAME, data));
	}

	public void newSystemTransformations() {
		NewSystemTransformationsCommandData data = new NewSystemTransformationsCommandData();
		data.systemTransformationsFile = arguments.getArgument_st("systemTransformations.xml");
		data.domain = arguments.getArgument_d("unknown");
		pushEvent(CommandEvent.start(NewSystemTransformationsCommand.NAME, data));
	}

	public void convert() {
		ConvertCommandData data = new ConvertCommandData();
		data.taskDescriptionFile = arguments.getArgument_td(null);
		data.systemTransformationsFile = arguments.getArgument_st(null);
		data.processFile = arguments.getArgument_p(null);
		data.nodeNetworkFile = arguments.getArgument_nn(null);
		pushEvent(CommandEvent.start(ConvertCommand.NAME, data));
	}

	public void usageHelp() {
		UsageHelpCommandData data = new UsageHelpCommandData();
		data.options = arguments.getOptions();
		pushEvent(CommandEvent.start(UsageHelpCommand.NAME, data));
	}

	public void stopApplication() {
		StopApplicationCommandData data = new StopApplicationCommandData();
		pushEvent(CommandEvent.start(StopApplicationCommand.NAME, data));
	}
}

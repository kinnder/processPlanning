package application;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.UnrecognizedOptionException;
import org.jdom2.JDOMException;

import application.command.Command;
import application.command.CommandData;
import application.command.ConvertCommand;
import application.command.ConvertCommandData;
import application.command.HelpCommand;
import application.command.HelpCommandData;
import application.command.NewSystemTransformationsCommand;
import application.command.NewSystemTransformationsCommandData;
import application.command.NewTaskDescriptionCommand;
import application.command.NewTaskDescriptionCommandData;
import application.command.PlanCommand;
import application.command.PlanCommandData;
import application.command.VerifyCommand;
import application.command.VerifyCommandData;
import application.event.CommandStatusEvent;
import application.event.HelpMessageEvent;
import application.storage.PersistanceStorage;
import application.ui.UserInterface;
import application.ui.UserInterfaceBuilder;
import application.ui.UserInterfaceBuilder.UserInterfaceType;
import planning.method.NodeNetwork;
import planning.method.SystemTransformations;
import planning.method.TaskDescription;
import planning.model.SystemProcess;

public class Application {

	private Map<String, Command> commands = new HashMap<>();

	private void registerCommand(Command command) {
		commands.put(command.getName(), command);
	}

	private UserInterfaceBuilder userInterfaceBuilder;

	public Application() {
		registerCommand(new HelpCommand(this));
		registerCommand(new PlanCommand(this));
		registerCommand(new NewSystemTransformationsCommand(this));
		registerCommand(new NewTaskDescriptionCommand(this));
		registerCommand(new VerifyCommand(this));
		registerCommand(new ConvertCommand(this));

		persistanceStorage = new PersistanceStorage();
		applicationArguments = new ApplicationArguments();
		userInterfaceBuilder = new UserInterfaceBuilder();
	}

	Application(HelpCommand helpCommand, PlanCommand planCommand,
			NewSystemTransformationsCommand newSystemTransformationsCommand,
			NewTaskDescriptionCommand newTaskDescriptionCommand, VerifyCommand verifyCommand,
			ConvertCommand convertCommand, PersistanceStorage persistanceStorage,
			ApplicationArguments applicationArguments, UserInterfaceBuilder userInterfaceBuilder) {

		registerCommand(helpCommand);
		registerCommand(planCommand);
		registerCommand(newSystemTransformationsCommand);
		registerCommand(newTaskDescriptionCommand);
		registerCommand(verifyCommand);
		registerCommand(convertCommand);

		this.persistanceStorage = persistanceStorage;
		this.applicationArguments = applicationArguments;
		this.userInterfaceBuilder = userInterfaceBuilder;
	}

	private List<UserInterface> uis = new ArrayList<UserInterface>();

	public void registerUserInterface(UserInterface ui) {
		uis.add(ui);
	}

	public void notifyHelpMessage(HelpMessageEvent event) {
		for (UserInterface ui : uis) {
			ui.notifyHelpMessage(event);
		}
	}

	public void notifyCommandStatus(CommandStatusEvent event) {
		for (UserInterface ui : uis) {
			ui.notifyCommandStatus(event);
		}
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

	private ApplicationArguments applicationArguments;

	public ApplicationArguments getArguments() {
		return applicationArguments;
	}

	private UserInterface createUserInterface(UserInterfaceType type) {
		UserInterface ui = userInterfaceBuilder.build(type);
		registerUserInterface(ui);
		ui.setApplication(this);
		return ui;
	}

	public void run(String[] args) throws Exception {
		try {
			applicationArguments.parseArguments(args);
			UserInterface ui = createUserInterface(
					applicationArguments.hasArgument_gui() ? UserInterfaceType.gui : UserInterfaceType.cli);
			ui.run();
		} catch (UnrecognizedOptionException e) {
			createUserInterface(UserInterfaceType.cli);
			notifyCommandStatus(new CommandStatusEvent(e.getMessage()));
			showHelp();
		}
	}

	public void runCommand(String commandName, CommandData commandData) {
		Command command = commands.get(commandName);
		command.run(commandData);
	}

	public void plan() {
		PlanCommandData data = new PlanCommandData();
		data.taskDescriptionFile = applicationArguments.getArgument_td("taskDescription.xml");
		data.systemTransformationsFile = applicationArguments.getArgument_st("systemTransformations.xml");
		data.processFile = applicationArguments.getArgument_p("process.xml");
		data.nodeNetworkFile = applicationArguments.getArgument_nn("nodeNetwork.xml");
		runCommand(PlanCommand.NAME, data);
	}

	public void verify() {
		VerifyCommandData data = new VerifyCommandData();
		data.taskDescriptionFile = applicationArguments.getArgument_td(null);
		data.systemTransformationsFile = applicationArguments.getArgument_st(null);
		data.processFile = applicationArguments.getArgument_p(null);
		data.nodeNetworkFile = applicationArguments.getArgument_nn(null);
		runCommand(VerifyCommand.NAME, data);
	}

	public void newTaskDescription() {
		NewTaskDescriptionCommandData data = new NewTaskDescriptionCommandData();
		data.taskDescriptionFile = applicationArguments.getArgument_td("taskDescription.xml");
		data.domain = applicationArguments.getArgument_d("unknown");
		runCommand(NewTaskDescriptionCommand.NAME, data);
	}

	public void newSystemTransformations() {
		NewSystemTransformationsCommandData data = new NewSystemTransformationsCommandData();
		data.systemTransformationsFile = applicationArguments.getArgument_st("systemTransformations.xml");
		data.domain = applicationArguments.getArgument_d("unknown");
		runCommand(NewSystemTransformationsCommand.NAME, data);
	}

	public void convert() {
		ConvertCommandData data = new ConvertCommandData();
		data.taskDescriptionFile = applicationArguments.getArgument_td(null);
		data.systemTransformationsFile = applicationArguments.getArgument_st(null);
		data.processFile = applicationArguments.getArgument_p(null);
		data.nodeNetworkFile = applicationArguments.getArgument_nn(null);
		runCommand(ConvertCommand.NAME, data);
	}

	public void showHelp() throws Exception {
		HelpCommandData data = new HelpCommandData();
		data.options = applicationArguments.getOptions();
		commands.get(HelpCommand.NAME).execute(data);
	}
}

package application;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.UnrecognizedOptionException;
import org.jdom2.JDOMException;

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
import application.ui.MainViewFrame;
import planning.method.NodeNetwork;
import planning.method.SystemTransformations;
import planning.method.TaskDescription;
import planning.model.SystemProcess;

public class Application {

	private HelpCommand helpCommand;

	private PlanCommand planCommand;

	private NewSystemTransformationsCommand newSystemTransformationsCommand;

	private NewTaskDescriptionCommand newTaskDescriptionCommand;

	private VerifyCommand verifyCommand;

	private ConvertCommand convertCommand;

	public Application() {
		helpCommand = new HelpCommand(this);
		planCommand = new PlanCommand(this);
		newSystemTransformationsCommand = new NewSystemTransformationsCommand(this);
		newTaskDescriptionCommand = new NewTaskDescriptionCommand(this);
		verifyCommand = new VerifyCommand(this);
		convertCommand = new ConvertCommand(this);

		persistanceStorage = new PersistanceStorage();
	}

	Application(HelpCommand helpCommand, PlanCommand planCommand, NewSystemTransformationsCommand newSystemTransformationsCommand,
			NewTaskDescriptionCommand newTaskDescriptionCommand,
			VerifyCommand verifyCommand, ConvertCommand convertCommand,
			PersistanceStorage persistanceStorage) {
		this.helpCommand = helpCommand;
		this.planCommand = planCommand;
		this.newSystemTransformationsCommand = newSystemTransformationsCommand;
		this.newTaskDescriptionCommand = newTaskDescriptionCommand;
		this.verifyCommand = verifyCommand;
		this.convertCommand = convertCommand;

		this.persistanceStorage = persistanceStorage;
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

	private ApplicationArguments applicationArguments = new ApplicationArguments();

	public ApplicationArguments getArguments() {
		return applicationArguments;
	}

	public void run(String[] args) throws Exception {
		try {
			applicationArguments.parseArguments(args);

			if(applicationArguments.hasArgument_gui()) {
				runGUIMode();
			} else {
				runCLIMode();
			}
		} catch (UnrecognizedOptionException e) {
			notifyCommandStatus(new CommandStatusEvent(e.getMessage()));
			showHelp();
		}
	}

	private void runGUIMode() throws Exception {
		for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
			if ("Nimbus".equals(info.getName())) {
				javax.swing.UIManager.setLookAndFeel(info.getClassName());
				break;
			}
		}

		MainViewFrame mainView = new MainViewFrame();
		mainView.setApplication(this);
		mainView.updateComponents();
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				mainView.setVisible(true);
			}
		});
	}

	private void runCLIMode() throws Exception {
		if (applicationArguments.hasArgument_plan()) {
			PlanCommandData data = new PlanCommandData();
			data.taskDescriptionFile = applicationArguments.getArgument_td("taskDescription.xml");
			data.systemTransformationsFile = applicationArguments.getArgument_st("systemTransformations.xml");
			data.processFile = applicationArguments.getArgument_p("process.xml");
			data.nodeNetworkFile = applicationArguments.getArgument_nn("nodeNetwork.xml");
			planCommand.run(data);
		}
		else if (applicationArguments.hasArgument_new_st()) {
			NewSystemTransformationsCommandData data = new NewSystemTransformationsCommandData();
			data.systemTransformationsFile = applicationArguments.getArgument_st("systemTransformations.xml");
			data.domain = applicationArguments.getArgument_new_st("unknown");
			newSystemTransformationsCommand.run(data);
		}
		else if (applicationArguments.hasArgument_new_td()) {
			NewTaskDescriptionCommandData data = new NewTaskDescriptionCommandData();
			data.taskDescriptionFile = applicationArguments.getArgument_td("taskDescription.xml");
			data.domain = applicationArguments.getArgument_new_td("unknown");
			newTaskDescriptionCommand.run(data);
		}
		else if (applicationArguments.hasArgument_verify()) {
			VerifyCommandData data = new VerifyCommandData();
			data.taskDescriptionFile = applicationArguments.getArgument_td(null);
			data.systemTransformationsFile = applicationArguments.getArgument_st(null);
			data.processFile = applicationArguments.getArgument_p(null);
			data.nodeNetworkFile = applicationArguments.getArgument_nn(null);
			verifyCommand.run(data);
		}
		else if (applicationArguments.hasArgument_convert()) {
			ConvertCommandData data = new ConvertCommandData();
			data.taskDescriptionFile = applicationArguments.getArgument_td(null);
			data.systemTransformationsFile = applicationArguments.getArgument_st(null);
			data.processFile = applicationArguments.getArgument_p(null);
			data.nodeNetworkFile = applicationArguments.getArgument_nn(null);
			convertCommand.run(data);
		}
		else {
			showHelp();
		}
	}

	private void showHelp() throws Exception {
		HelpCommandData data = new HelpCommandData();
		data.options = applicationArguments.getOptions();
		helpCommand.execute(data);
	}
}

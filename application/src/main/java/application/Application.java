package application;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
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
import planning.method.NodeNetwork;
import planning.method.SystemTransformations;
import planning.method.TaskDescription;
import planning.model.SystemProcess;

public class Application {

	// TODO : move to constructor
	Map<String, Command> commands = new HashMap<>();

	public Application() {
		commands.put(HelpCommand.NAME, new HelpCommand(this));
		commands.put(PlanCommand.NAME, new PlanCommand(this));
		commands.put(NewSystemTransformationsCommand.NAME, new NewSystemTransformationsCommand(this));
		commands.put(NewTaskDescriptionCommand.NAME, new NewTaskDescriptionCommand(this));
		commands.put(VerifyCommand.NAME, new VerifyCommand(this));
		commands.put(ConvertCommand.NAME, new ConvertCommand(this));
	}

	// TODO : move to constructor
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

	// TODO : move to constructor
	PersistanceStorage persistanceStorage = new PersistanceStorage();

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

	public void run(String[] args) throws Exception {
		// TODO (2020-07-29 #30): переделать работу с аргументами командной строки
		// https://commons.apache.org/proper/commons-cli/usage.html
		// executable [global options] <command> [command options] <arguments>
		Option h_option = new Option("h", "help", false, "prints usage");
		Option td_option = new Option("td", "taskDescription", true, "file with description of the task");
		Option st_option = new Option("st", "systemTransformations", true, "file with description of the system transformations");
		Option p_option = new Option("p", "process", true, "output file with process");
		Option nn_option = new Option("nn", "nodeNetwork", true, "output file with node network");
		Option plan_option = new Option("plan", "plan process");
		Option new_st_option = new Option("new_st", true, "create new file with predefined system transformations");
		new_st_option.setLongOpt("new-system-transformations");
		new_st_option.setArgName("domain");
		new_st_option.setOptionalArg(true);
		Option new_td_option = new Option("new_td", true, "create new file with predefined task description");
		new_td_option.setLongOpt("new-task-description");
		new_td_option.setArgName("domain");
		new_td_option.setOptionalArg(true);
		Option verify_option = new Option("verify", "verify xml-files with according xml-schemas");
		Option convert_option = new Option("convert", "convert files between formats: xml to owl and owl to xml");

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

		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(options, args);
			if (line.hasOption(h_option.getOpt())) {
				HelpCommandData data = new HelpCommandData();
				data.options = options;
				runCommand(HelpCommand.NAME, data);
			}
			if (line.hasOption(plan_option.getOpt())) {
				PlanCommandData data = new PlanCommandData();
				data.taskDescriptionFile = line.getOptionValue(td_option.getOpt(), "taskDescription.xml");
				data.systemTransformationsFile = line.getOptionValue(st_option.getOpt(), "systemTransformations.xml");
				data.processFile = line.getOptionValue(p_option.getOpt(), "process.xml");
				data.nodeNetworkFile = line.getOptionValue(nn_option.getOpt(), "nodeNetwork.xml");
				runCommand(PlanCommand.NAME, data);
			}
			if (line.hasOption(new_st_option.getOpt())) {
				NewSystemTransformationsCommandData data = new NewSystemTransformationsCommandData();
				data.systemTransformationsFile = line.getOptionValue(st_option.getOpt(), "systemTransformations.xml");
				data.domain = line.getOptionValue(new_st_option.getOpt(), "unknown");
				runCommand(NewSystemTransformationsCommand.NAME, data);
			}
			if (line.hasOption(new_td_option.getOpt())) {
				NewTaskDescriptionCommandData data = new NewTaskDescriptionCommandData();
				data.taskDescriptionFile = line.getOptionValue(td_option.getOpt(), "taskDescription.xml");
				data.domain = line.getOptionValue(new_td_option.getOpt(), "unknown");
				runCommand(NewTaskDescriptionCommand.NAME, data);
			}
			if (line.hasOption(verify_option.getOpt())) {
				VerifyCommandData data = new VerifyCommandData();
				data.taskDescriptionFile = line.getOptionValue(td_option.getOpt(), null);
				data.systemTransformationsFile = line.getOptionValue(st_option.getOpt(), null);
				data.processFile = line.getOptionValue(p_option.getOpt(), null);
				data.nodeNetworkFile = line.getOptionValue(nn_option.getOpt(), null);
				runCommand(VerifyCommand.NAME, data);
			}
			if (line.hasOption(convert_option.getOpt())) {
				ConvertCommandData data = new ConvertCommandData();
				data.taskDescriptionFile = line.getOptionValue(td_option.getOpt(), null);
				data.systemTransformationsFile = line.getOptionValue(st_option.getOpt(), null);
				data.processFile = line.getOptionValue(p_option.getOpt(), null);
				data.nodeNetworkFile = line.getOptionValue(nn_option.getOpt(), null);
				runCommand(ConvertCommand.NAME, data);
			}
		} catch (UnrecognizedOptionException e) {
			notifyCommandStatus(new CommandStatusEvent(e.getMessage()));
			HelpCommandData data = new HelpCommandData();
			data.options = options;
			runCommand(HelpCommand.NAME, data);
		}
	}

	public void runCommand(String name, CommandData data) throws Exception {
		commands.get(name).execute(data);
	}
}

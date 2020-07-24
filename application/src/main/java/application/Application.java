package application;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import application.command.Command;
import application.command.CommandData;
import application.command.HelpCommand;
import application.command.HelpCommandData;
import application.command.NewSystemTransformationsCommand;
import application.command.NewSystemTransformationsCommandData;
import application.command.NewTaskDescriptionCommand;
import application.command.NewTaskDescriptionCommandData;
import application.command.PlanCommand;
import application.command.PlanCommandData;

public class Application {

	// TODO : move to constructor
	Map<String, Command> commands = new HashMap<>();

	public Application() {
		commands.put(HelpCommand.NAME, new HelpCommand());
		commands.put(PlanCommand.NAME, new PlanCommand());
		commands.put(NewSystemTransformationsCommand.NAME, new NewSystemTransformationsCommand());
		commands.put(NewTaskDescriptionCommand.NAME, new NewTaskDescriptionCommand());
	}

	public void registerUserInterface(UserInterface ui) {
		for (Command command : commands.values()) {
			command.registerUserInterface(ui);
		}
	}

	public void run(String[] args) throws Exception {
		Option h_option = new Option("h", "help", false, "prints usage");
		Option td_option = new Option("td", "taskDescription", true, "file with description of the task");
		Option st_option = new Option("st", "systemTransformations", true,
				"file with description of the system transformations");
		Option p_option = new Option("p", "process", true, "output file with process");
		Option nn_option = new Option("nn", "nodeNetwork", true, "output file with node network");
		Option c_option = new Option("c", "command", true,
				"command to be executed \n\tplan, plan process\n\tnew_st, create new file with default system transformations\n\tnew_td, create new file with default task description");

		Options options = new Options();
		options.addOption(h_option);
		options.addOption(td_option);
		options.addOption(st_option);
		options.addOption(p_option);
		options.addOption(nn_option);
		options.addOption(c_option);

		CommandLineParser parser = new DefaultParser();
		CommandLine line = parser.parse(options, args);

		String command;
		if (line.hasOption(h_option.getOpt())) {
			command = HelpCommand.NAME;
		} else {
			command = line.getOptionValue(c_option.getOpt(), HelpCommand.NAME);
		}

		switch (command) {
		case PlanCommand.NAME: {
			PlanCommandData data = new PlanCommandData();
			data.taskDescriptionFile = line.getOptionValue(td_option.getOpt(), "taskDescription.xml");
			data.systemTransformationsFile = line.getOptionValue(st_option.getOpt(), "systemTransformations.xml");
			data.processFile = line.getOptionValue(p_option.getOpt(), "process.xml");
			data.nodeNetworkFile = line.getOptionValue(nn_option.getOpt(), "nodeNetwork.xml");
			runCommand(PlanCommand.NAME, data);
		}
			break;

		case NewSystemTransformationsCommand.NAME: {
			NewSystemTransformationsCommandData data = new NewSystemTransformationsCommandData();
			data.systemTransformationsFile = line.getOptionValue(st_option.getOpt(), "systemTransformations.xml");
			runCommand(NewSystemTransformationsCommand.NAME, data);
		}
			break;

		case NewTaskDescriptionCommand.NAME: {
			NewTaskDescriptionCommandData data = new NewTaskDescriptionCommandData();
			data.taskDescriptionFile = line.getOptionValue(td_option.getOpt(), "taskDescription.xml");
			runCommand(NewTaskDescriptionCommand.NAME, data);
		}
			break;

		default: {
			HelpCommandData data = new HelpCommandData();
			data.options = options;
			runCommand(HelpCommand.NAME, data);
		}
			break;
		}
	}

	public void runCommand(String name, CommandData data) throws Exception {
		commands.get(name).execute(data);
	}
}

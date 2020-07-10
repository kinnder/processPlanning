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
import application.command.PlanCommand;
import application.command.PlanCommandData;

public class Application {

	// TODO : move to constructor
	Map<String, Command> commands = new HashMap<>();

	public Application() {
		commands.put(HelpCommand.NAME, new HelpCommand());
		commands.put(PlanCommand.NAME, new PlanCommand());
	}

	public void registerUserInterface(UserInterface ui) {
		for (Command command : commands.values()) {
			command.registerUserInterface(ui);
		}
	}

	public void run(String[] args) throws Exception {
		Option h_option = new Option("h", "help", false, "prints usage");
		Option td_option = new Option("td", "taskDescription", true, "file with description of the task");
		Option st_option = new Option("st", "systemTransformations", true, "file with description of the system transformations");
		Option p_option = new Option("p", "process", true, "output file with process");
		Option nn_option = new Option("nn", "nodeNetwork", true, "output file with node network");

		Options options = new Options();
		options.addOption(h_option);
		options.addOption(td_option);
		options.addOption(st_option);
		options.addOption(p_option);
		options.addOption(nn_option);

		CommandLineParser parser = new DefaultParser();
		CommandLine line = parser.parse(options, args);

		if (line.hasOption(h_option.getOpt())) {
			HelpCommandData data = new HelpCommandData();
			data.options = options;

			runCommand(HelpCommand.NAME, data);
		} else {
			PlanCommandData data = new PlanCommandData();
			data.taskDescriptionFile = line.getOptionValue(td_option.getOpt(), "taskDescription.xml");
			data.systemTransformationsFile = line.getOptionValue(st_option.getOpt(), "systemTransformations.xml");
			data.processFile = line.getOptionValue(p_option.getOpt(), "process.xml");
			data.nodeNetworkFile = line.getOptionValue(nn_option.getOpt(), "nodeNetwork.xml");

			runCommand(PlanCommand.NAME, data);
		}
	}

	public void runCommand(String name, CommandData data) throws Exception {
		commands.get(name).execute(data);
	}
}

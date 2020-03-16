package application;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import application.command.Command;
import application.command.CommandData;
import application.command.HelpCommand;
import application.command.HelpCommandData;
import application.command.PlanCommand;
import application.command.PlanCommandData;

//TODO : remove this annotation
@SuppressWarnings("PMD")
public class Application {

	private Map<String, Command> commands = new HashMap<>();

	public Application() {
		commands.put(HelpCommand.NAME, new HelpCommand());
		commands.put(PlanCommand.NAME, new PlanCommand());
	}

	public void registerUserInterface(UserInterface ui) {
		for (Command command : commands.values()) {
			command.registerUserInterface(ui);
		}
	}

	public void run(String[] args) throws ParseException {
		Option helpOption = new Option("h", "help", false, "prints usage");

		Option taskDescriptionOption = new Option("td", "taskDescription", true, "file with description of the task");
		Option systemTransformationsOption = new Option("st", "systemTransformations", true, "file with description of the system transformations");
		Option processOption = new Option("p", "process", true, "output file");

		Options options = new Options();
		options.addOption(helpOption);
		options.addOption(taskDescriptionOption);
		options.addOption(systemTransformationsOption);
		options.addOption(processOption);

		CommandLineParser parser = new DefaultParser();
		CommandLine line = parser.parse(options, args);

		if (line.hasOption(helpOption.getOpt())) {
			StringBuilder sb = new StringBuilder();
			sb.append("application builds plan for [taskDescription] with [systemTransformations] and puts result in [process]\n");
			sb.append("usage:");
			for (Option option : options.getOptions()) {
				sb.append(String.format("%2s, %-21s %s\n", option.getOpt(), option.getLongOpt(), option.getDescription()));
			}

			HelpCommandData data = new HelpCommandData();
			data.usageText = sb.toString();

			runCommand(HelpCommand.NAME, data);

		} else {
			PlanCommandData data = new PlanCommandData();
			data.taskDescriptionFile = line.getOptionValue(taskDescriptionOption.getOpt(), "taskDescription.xml");
			data.systemTransformationsFile = line.getOptionValue(systemTransformationsOption.getOpt(), "systemTransformations.xml");
			data.processFile = line.getOptionValue(processOption.getOpt(), "process.xml");

			runCommand(PlanCommand.NAME, data);
		}
	}

	public void runCommand(String name, CommandData data) {
		try {
			commands.get(name).execute(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

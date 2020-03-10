package application;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

// TODO : remove this annotation
@SuppressWarnings("PMD")
public class Main {

	public static void main(String[] args) {
		CommandLineParser parser = new DefaultParser();

		Option help = new Option("h", "help", false, "prints usage");

		Option taskDescription = new Option("td", "taskDescription", true, "file with description of the task");
		Option systemTransformations = new Option("st", "systemTransformations", true,
				"file with description of the system transformations");
		Option process = new Option("p", "process", true, "output file");

		Options options = new Options();
		options.addOption(help);
		options.addOption(taskDescription);
		options.addOption(systemTransformations);
		options.addOption(process);

		args = new String[] { "-help" };

		try {
			CommandLine line = parser.parse(options, args);

			String taskDescriptionFile = line.getOptionValue(taskDescription.getOpt(), "taskDescription.xml");
			String systemTransformationsFile = line.getOptionValue(systemTransformations.getOpt(),
					"systemTransformations.xml");
			String processFile = line.getOptionValue(process.getOpt(), "process.xml");

			if (line.hasOption(help.getOpt())) {
				System.out.println(
						"application builds plan for [taskDescription] with [systemTransformations] and puts result in [process]");
				System.out.println("usage:");
				for (Option option : options.getOptions()) {
					StringBuilder sb = new StringBuilder();
					sb.append(String.format("%2s, %-21s", option.getOpt(), option.getLongOpt()));
					sb.append(" ");
					sb.append(option.getDescription());
					System.out.println(sb.toString());
				}
			} else {
				// TODO : call planning
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}

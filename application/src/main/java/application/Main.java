package application;

import java.io.IOException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.jdom2.JDOMException;

import planning.method.Planner;
import planning.method.SystemTransformations;
import planning.method.TaskDescription;
import planning.model.SystemProcess;
import planning.storage.SystemProcessXMLFile;
import planning.storage.SystemTransformationsXMLFile;
import planning.storage.TaskDescriptionXMLFile;

// TODO : remove this annotation
@SuppressWarnings("PMD")
public class Main {

	public static void main(String[] args) {
		CommandLineParser parser = new DefaultParser();

		Option helpOption = new Option("h", "help", false, "prints usage");

		Option taskDescriptionOption = new Option("td", "taskDescription", true, "file with description of the task");
		Option systemTransformationsOption = new Option("st", "systemTransformations", true,
				"file with description of the system transformations");
		Option processOption = new Option("p", "process", true, "output file");

		Options options = new Options();
		options.addOption(helpOption);
		options.addOption(taskDescriptionOption);
		options.addOption(systemTransformationsOption);
		options.addOption(processOption);

		// TODO : remove
//		args = new String[] { "-help" };

		try {
			CommandLine line = parser.parse(options, args);

			String taskDescriptionFile = line.getOptionValue(taskDescriptionOption.getOpt(), "taskDescription.xml");
			String systemTransformationsFile = line.getOptionValue(systemTransformationsOption.getOpt(),
					"systemTransformations.xml");
			String processFile = line.getOptionValue(processOption.getOpt(), "process.xml");

			if (line.hasOption(helpOption.getOpt())) {
				// TODO : resolve conflict with base class
				java.lang.System.out.println(
						"application builds plan for [taskDescription] with [systemTransformations] and puts result in [process]");
				java.lang.System.out.println("usage:");
				for (Option option : options.getOptions()) {
					StringBuilder sb = new StringBuilder();
					sb.append(String.format("%2s, %-21s", option.getOpt(), option.getLongOpt()));
					sb.append(" ");
					sb.append(option.getDescription());
					java.lang.System.out.println(sb.toString());
				}
			} else {
				java.lang.System.out.println("planning...");
				try {
					SystemTransformationsXMLFile transformationsXMLFile = new SystemTransformationsXMLFile();
					transformationsXMLFile.load(systemTransformationsFile);
					SystemTransformations systemTransformations = new SystemTransformations();
					systemTransformations.addAll(transformationsXMLFile.getObject());

					TaskDescriptionXMLFile taskXMLFile = new TaskDescriptionXMLFile();
					taskXMLFile.load(taskDescriptionFile);
					TaskDescription taskDescription = taskXMLFile.getObject();

					Planner planner = new Planner(taskDescription, systemTransformations);
					planner.plan();

					SystemProcess operations = planner.getShortestProcess();
					SystemProcessXMLFile xmlFile = new SystemProcessXMLFile();
					xmlFile.setObject(operations);
					xmlFile.save(processFile);
				} catch (JDOMException | CloneNotSupportedException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				java.lang.System.out.println("done");
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}

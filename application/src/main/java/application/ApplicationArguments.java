package application;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ApplicationArguments {

	private Options options;

	private Option h_option;

	private Option td_option;

	private Option st_option;

	private Option p_option;

	private Option nn_option;

	private Option plan_option;

	private Option new_st_option;

	private Option new_td_option;

	private Option verify_option;

	private Option convert_option;

	private Option gui_option;

	public ApplicationArguments() {
		// TODO (2020-07-29 #30): переделать работу с аргументами командной строки
		// https://commons.apache.org/proper/commons-cli/usage.html
		// executable [global options] <command> [command options] <arguments>
		h_option = new Option("h", "help", false, "prints usage");
		td_option = new Option("td", "taskDescription", true, "file with description of the task");
		st_option = new Option("st", "systemTransformations", true, "file with description of the system transformations");
		p_option = new Option("p", "process", true, "output file with process");
		nn_option = new Option("nn", "nodeNetwork", true, "output file with node network");
		plan_option = new Option("plan", "plan process");
		new_st_option = new Option("new_st", true, "create new file with predefined system transformations");
		new_st_option.setLongOpt("new-system-transformations");
		new_st_option.setArgName("domain");
		new_st_option.setOptionalArg(true);
		new_td_option = new Option("new_td", true, "create new file with predefined task description");
		new_td_option.setLongOpt("new-task-description");
		new_td_option.setArgName("domain");
		new_td_option.setOptionalArg(true);
		verify_option = new Option("verify", "verify xml-files with according xml-schemas");
		convert_option = new Option("convert", "convert files between formats: xml to owl and owl to xml");
		gui_option = new Option("gui", "show graphical user interface");

		options = new Options();
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
		options.addOption(gui_option);

		line = new CommandLine.Builder().build();
	}

	private CommandLine line;

	public void parseArguments(String[] arguments) throws ParseException {
		CommandLineParser parser = new DefaultParser();
		line = parser.parse(options, arguments);
	}

	public boolean hasArgument_h() {
		return line.hasOption(h_option.getOpt());
	}

	public boolean hasArgument_td() {
		return line.hasOption(td_option.getOpt());
	}

	public boolean hasArgument_gui() {
		return line.hasOption(gui_option.getOpt());
	}

	public boolean hasArgument_plan() {
		return line.hasOption(plan_option.getOpt());
	}

	public boolean hasArgument_new_st() {
		return line.hasOption(new_st_option.getOpt());
	}

	public boolean hasArgument_new_td() {
		return line.hasOption(new_td_option.getOpt());
	}

	public boolean hasArgument_verify() {
		return line.hasOption(verify_option.getOpt());
	}

	public boolean hasArgument_convert() {
		return line.hasOption(convert_option.getOpt());
	}

	public Options getOptions() {
		return options;
	}

	public String getArgument_td(String defaultValue) {
		return line.getOptionValue(td_option.getOpt(), defaultValue);
	}

	public String getArgument_st(String defaultValue) {
		return line.getOptionValue(st_option.getOpt(), defaultValue);
	}

	public String getArgument_p(String defaultValue) {
		return line.getOptionValue(p_option.getOpt(), defaultValue);
	}

	public String getArgument_nn(String defaultValue) {
		return line.getOptionValue(nn_option.getOpt(), defaultValue);
	}

	public String getArgument_new_st(String defaultValue) {
		// TODO (2022-01-11 #61): заменить на параметр domain
		return line.getOptionValue(new_st_option.getOpt(), defaultValue);
	}

	public String getArgument_new_td(String defaultValue) {
		// TODO (2022-01-11 #61): заменить на параметр domain
		return line.getOptionValue(new_td_option.getOpt(), defaultValue);
	}
	
	public String getArgument_domain(String defaultValue) {
		// TODO (2022-01-13 #61): добавить параметр domain
		return "unknown";
	}
}

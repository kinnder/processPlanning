package application.command;

import org.apache.commons.cli.Option;

import application.event.HelpMessageEvent;

public class HelpCommand extends Command {

	public final static String NAME = "help";

	@Override
	public void execute(CommandData data) throws Exception {
		execute((HelpCommandData) data);
	}

	private void execute(HelpCommandData data) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("application builds plan for [taskDescription] with [systemTransformations] and puts result in [process]\n");
		sb.append("usage:");
		for (Option option : data.options.getOptions()) {
			sb.append(String.format("%2s, %-21s %s\n", option.getOpt(), option.getLongOpt(), option.getDescription()));
		}

		HelpMessageEvent event = new HelpMessageEvent(sb.toString());
		notifyHelpMessage(event);
	}
}

package application.command;

import java.util.Optional;

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
		sb.append("usage:\n");
		for (Option option : data.options.getOptions()) {
			String shortName = Optional.ofNullable(option.getOpt()).orElse("");
			String longName = Optional.ofNullable(option.getLongOpt()).orElse("");
			String description = Optional.ofNullable(option.getDescription()).orElse("");
			sb.append(String.format("%6s, %-26s %s\n", shortName, longName, description));
		}

		HelpMessageEvent event = new HelpMessageEvent(sb.toString());
		notifyHelpMessage(event);
	}
}

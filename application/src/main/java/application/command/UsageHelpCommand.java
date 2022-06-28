package application.command;

import java.util.Optional;

import org.apache.commons.cli.Option;
import org.slf4j.LoggerFactory;

import application.Application;
import application.event.CommandStatusEvent;
import application.event.UserMessageEvent;

public class UsageHelpCommand extends Command {

	public static final String NAME = "usageHelp";

	public UsageHelpCommand (Application application) {
		super(application, NAME);
	}

	@Override
	public void run() {
		try {
			execute(data);
		} catch (Exception e) {
			application.notifyCommandStatus(new CommandStatusEvent("error"));
			LoggerFactory.getLogger(getClass()).error("", e);
		}
	}

	@Override
	public void execute(CommandData data) throws Exception {
		execute((UsageHelpCommandData) data);
	}

	private void execute(UsageHelpCommandData data) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("application builds plan for [taskDescription] with [systemTransformations] and puts result in [process]\n");
		sb.append("usage:\n");
		for (Option option : data.options.getOptions()) {
			String shortName = Optional.ofNullable(option.getOpt()).orElse("");
			String longName = Optional.ofNullable(option.getLongOpt()).orElse("");
			String description = Optional.ofNullable(option.getDescription()).orElse("");
			sb.append(String.format("%7s, %-26s %s\n", shortName, longName, description));
		}

		UserMessageEvent event = new UserMessageEvent(sb.toString());
		application.notifyUserMessage(event);
	}
}

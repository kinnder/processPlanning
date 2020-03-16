package application.command;

import application.event.HelpMessageEvent;

public class HelpCommand extends Command {

	public static String NAME = "help";

	@Override
	public void execute(CommandData data) throws Exception {
		execute((HelpCommandData) data);
	}

	public void execute(HelpCommandData data) throws Exception {
		HelpMessageEvent event = new HelpMessageEvent(data.usageText);
		notifyHelpMessage(event);
	}
}

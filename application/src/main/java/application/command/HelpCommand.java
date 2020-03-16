package application.command;

import application.Command;
import application.UserInterface;

public class HelpCommand implements Command<HelpCommandData> {

	private UserInterface ui;

	public HelpCommand(UserInterface ui) {
		this.ui = ui;
	}

	@Override
	public void execute(HelpCommandData data) {
		ui.printHelp(data.usageText);
	}
}

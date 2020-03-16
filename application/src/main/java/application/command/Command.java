package application.command;

import java.util.ArrayList;
import java.util.List;

import application.UserInterface;
import application.event.CommandStatusEvent;
import application.event.HelpMessageEvent;

public abstract class Command {

	private List<UserInterface> uis = new ArrayList<UserInterface>();

	public void registerUserInterface(UserInterface ui) {
		uis.add(ui);
	}

	public void notifyHelpMessage(HelpMessageEvent event) {
		for (UserInterface ui : uis) {
			ui.notifyHelpMessage(event);
		}
	}

	public void notifyCommandStatus(CommandStatusEvent event) {
		for (UserInterface ui : uis) {
			ui.notifyCommandStatus(event);
		}
	}

	public abstract void execute(CommandData data) throws Exception;
}

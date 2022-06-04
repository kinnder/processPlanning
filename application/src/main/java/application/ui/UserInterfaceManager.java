package application.ui;

import java.util.ArrayList;
import java.util.List;

import application.event.CommandStatusEvent;
import application.event.UserMessageEvent;

public class UserInterfaceManager {

	private List<UserInterface> uis = new ArrayList<UserInterface>();

	public void registerUserInterface(UserInterface ui) {
		uis.add(ui);
	}

	public void notifyUserMessage(UserMessageEvent event) {
		for (UserInterface ui : uis) {
			ui.notifyUserMessage(event);
		}
	}

	public void notifyCommandStatus(CommandStatusEvent event) {
		for (UserInterface ui : uis) {
			ui.notifyCommandStatus(event);
		}
	}
}

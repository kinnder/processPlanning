package application;

import application.event.CommandStatusEvent;
import application.event.HelpMessageEvent;

public interface UserInterface {

	void notifyHelpMessage(HelpMessageEvent event);

	void notifyCommandStatus(CommandStatusEvent event);

}

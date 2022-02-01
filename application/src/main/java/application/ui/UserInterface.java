package application.ui;

import application.Application;
import application.event.CommandStatusEvent;
import application.event.HelpMessageEvent;

public interface UserInterface {

	void notifyHelpMessage(HelpMessageEvent event);

	void notifyCommandStatus(CommandStatusEvent event);

	void run() throws Exception;

	void setApplication(Application application);
}

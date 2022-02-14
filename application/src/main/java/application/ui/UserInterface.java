package application.ui;

import application.Application;
import application.event.CommandStatusEvent;
import application.event.UsageHelpMessageEvent;

public interface UserInterface {

	void notifyUsageHelpMessage(UsageHelpMessageEvent event);

	void notifyCommandStatus(CommandStatusEvent event);

	void run() throws Exception;

	void setApplication(Application application);
}

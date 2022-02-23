package application.ui;

import application.event.CommandStatusEvent;
import application.event.UserMessageEvent;

public interface UserInterface {

	void notifyUserMessage(UserMessageEvent event);

	void notifyCommandStatus(CommandStatusEvent event);

	void run() throws Exception;
}

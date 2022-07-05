package application.ui;

import application.event.CommandEvent;
import application.event.UserEvent;

public interface UserInterface {

	void notifyUserEvent(UserEvent event);

	void notifyCommandEvent(CommandEvent event);

	void run() throws Exception;

	void stop();
}

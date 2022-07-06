package application.ui;

import java.util.ArrayList;
import java.util.List;

import application.Application;
import application.event.CommandEvent;
import application.event.Event;
import application.event.UserEvent;
import application.ui.UserInterfaceFactory.UserInterfaceType;

public class UserInterfaceManager {

	private List<UserInterface> uis = new ArrayList<UserInterface>();

	private UserInterfaceFactory userInterfaceFactory;

	private Application application;

	public UserInterfaceManager(Application application) {
		this.application = application;
		userInterfaceFactory = new UserInterfaceFactory();
	}

	UserInterfaceManager(Application application, UserInterfaceFactory userInterfaceFactory) {
		this.application = application;
		this.userInterfaceFactory = userInterfaceFactory;
	}

	public void registerUserInterface(UserInterface ui) {
		uis.add(ui);
	}

	public void notifyEvent(Event event) {
		if (event instanceof CommandEvent) {
			notifyCommandEvent((CommandEvent) event);
		}
		if (event instanceof UserEvent) {
			notifyUserEvent((UserEvent) event);
		}
	}

	public void notifyUserEvent(UserEvent event) {
		displayMessage(event.message);
	}

	public void notifyCommandEvent(CommandEvent event) {
		switch (event.type) {
		case Cancel:
			break;
		case Cancelled:
			displayMessage(event.message);
			break;
		case Errored:
			displayMessage(event.message);
			break;
		case Finished:
			displayMessage(event.message);
			break;
		case Start:
			break;
		case Started:
			displayMessage(event.message);
			break;
		case Status:
			displayMessage(event.message);
			break;
		default:
			break;
		}
	}

	private void displayMessage(String message) {
		for (UserInterface ui : uis) {
			ui.displayMessage(message);
		}
	}

	public void start() throws Exception {
		for (UserInterface ui : uis) {
			ui.start();
		}
	}

	public void createUserInterface(UserInterfaceType type) {
		UserInterface ui = userInterfaceFactory.createMainView(application, type);
		registerUserInterface(ui);
	}

	public void stop() {
		for (UserInterface ui : uis) {
			ui.stop();
		}
	}
}

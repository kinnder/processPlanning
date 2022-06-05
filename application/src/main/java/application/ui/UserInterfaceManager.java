package application.ui;

import java.util.ArrayList;
import java.util.List;

import application.Application;
import application.event.CommandStatusEvent;
import application.event.UserMessageEvent;
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

	public void runUserInterfaces() throws Exception {
		for (UserInterface ui : uis) {
			ui.run();
		}
	}

	public void createUserInterface(UserInterfaceType type) {
		UserInterface ui = userInterfaceFactory.createMainView(application, type);
		registerUserInterface(ui);
	}
}

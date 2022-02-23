package application.ui;

import application.ui.cli.MainShell;
import application.ui.gui.AboutFrame;
import application.ui.gui.MainViewFrame;
import application.ui.gui.OptionsFrame;

public class UserInterfaceFactory {

	public enum UserInterfaceType {
		cli, gui
	};

	public UserInterface createMainView(UserInterfaceType type) {
		switch (type) {
		case gui:
			return new MainViewFrame();
		default:
			return new MainShell(System.out);
		}
	}

	public OptionsFrame createOptionsView() {
		return new OptionsFrame();
	}

	public AboutFrame createAboutView() {
		return new AboutFrame();
	}
}

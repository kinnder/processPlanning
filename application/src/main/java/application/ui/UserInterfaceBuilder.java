package application.ui;

import application.ui.cli.MainShell;
import application.ui.gui.MainViewFrame;

public class UserInterfaceBuilder {

	public enum UserInterfaceType {
		cli, gui
	};

	public UserInterface build(UserInterfaceType type) {
		switch (type) {
		case gui:
			return new MainViewFrame();
		default:
			return new MainShell(System.out);
		}
	}
}

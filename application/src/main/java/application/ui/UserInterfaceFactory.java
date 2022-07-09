package application.ui;

import application.Application;
import application.ui.cli.MainViewShell;
import application.ui.gui.AboutFrame;
import application.ui.gui.MainViewFrame;
import application.ui.gui.NodeNetworkEditorFrame;
import application.ui.gui.OptionsFrame;
import application.ui.gui.SystemTransformationsEditorFrame;
import application.ui.gui.TaskDescriptionEditorFrame;

public class UserInterfaceFactory {

	public enum UserInterfaceType {
		cli, gui
	};

	public UserInterface createMainView(Application application, UserInterfaceType type) {
		switch (type) {
		case gui:
			return new MainViewFrame(application);
		default:
			return new MainViewShell(application, System.out);
		}
	}

	public OptionsFrame createOptionsView(Application application) {
		return new OptionsFrame(application);
	}

	public AboutFrame createAboutView() {
		return new AboutFrame();
	}

	public NodeNetworkEditorFrame createNodeNetworkEditorView() {
		return new NodeNetworkEditorFrame();
	}

	public SystemTransformationsEditorFrame createSystemTransformationsEditorView() {
		return new SystemTransformationsEditorFrame();
	}

	public TaskDescriptionEditorFrame createTaskDescriptionEditorView() {
		return new TaskDescriptionEditorFrame();
	}
}

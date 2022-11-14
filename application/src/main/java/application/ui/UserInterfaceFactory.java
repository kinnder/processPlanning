package application.ui;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import application.Application;
import application.ui.cli.MainViewShell;
import application.ui.gui.AboutFrame;
import application.ui.gui.EditorFrame;
import application.ui.gui.MainViewFrame;
import application.ui.gui.OptionsFrame;

public class UserInterfaceFactory {

	public enum UserInterfaceType {
		cli, gui
	};

	public UserInterface createMainView(Application application, UserInterfaceType type) {
		switch (type) {
		case gui:
			return new MainViewFrame(application);
		case cli:
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

	public EditorFrame createEditorView(Application application) {
		return new EditorFrame(application);
	}

	public static void initializeLookAndFeel() throws Exception {
		// TODO (2022-11-01 #72): посмотреть другие LookAndFeel
		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			if ("Nimbus".equals(info.getName())) {
				UIManager.setLookAndFeel(info.getClassName());
				break;
			}
		}
	}
}

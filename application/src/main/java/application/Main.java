package application;

public class Main {

	public static void main(String[] args) {
		ProcessEngineeringApplication application = new ProcessEngineeringApplication();
		ProcessEngineeringUserInterface ui = new ProcessEngineeringUserInterface();

		application.setUserInterface(ui);

		ui.parse(args);
	}
}

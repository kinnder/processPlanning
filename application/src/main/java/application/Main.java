package application;

public class Main {

	public static void main(String[] args) {
		UserInterface ui = new UserInterface();

		Application application = new Application();
		application.setUserInterface(ui);

		application.run(args);
		// TODO : remove
		// application.run(new String[] { "-help" });
	}
}

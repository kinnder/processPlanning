package application;

public class Main {

	public static void main(String[] args) {
		UserInterface ui = new UserInterface(System.out);

		Application application = new Application(ui);
		application.run(args);
		// TODO : remove
		// application.run(new String[] { "-help" });
	}
}

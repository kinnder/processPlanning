package application;

import java.io.PrintStream;

public class UserInterface {

	public UserInterface() {
		this(System.out);
	}

	private PrintStream printStream;

	UserInterface(PrintStream printStream) {
		this.printStream = printStream;
	}

	public void printHelp(String msg) {
		printStream.println(msg);
	}

	public void printCommandStatus(String msg) {
		printStream.println(msg);
	}
}

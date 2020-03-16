package application;

import java.io.PrintStream;

import application.event.CommandStatusEvent;
import application.event.HelpMessageEvent;

public class UserInterface {

	private PrintStream printStream;

	public UserInterface(PrintStream printStream) {
		this.printStream = printStream;
	}

	public void notifyHelpMessage(HelpMessageEvent event) {
		printStream.println(event.message);
	}

	public void notifyCommandStatus(CommandStatusEvent event) {
		printStream.println(event.message);
	}
}

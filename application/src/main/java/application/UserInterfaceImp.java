package application;

import java.io.PrintStream;

import application.event.CommandStatusEvent;
import application.event.HelpMessageEvent;

public class UserInterfaceImp implements UserInterface {

	private PrintStream printStream;

	public UserInterfaceImp(PrintStream printStream) {
		this.printStream = printStream;
	}

	@Override
	public void notifyHelpMessage(HelpMessageEvent event) {
		printStream.println(event.message);
	}

	@Override
	public void notifyCommandStatus(CommandStatusEvent event) {
		printStream.println(event.message);
	}
}

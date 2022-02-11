package application.ui.cli;

import java.io.PrintStream;

import application.Application;
import application.ApplicationArguments;
import application.event.CommandStatusEvent;
import application.event.HelpMessageEvent;
import application.ui.UserInterface;

public class MainShell implements UserInterface {

	private PrintStream printStream;

	private Application application;

	public MainShell(PrintStream printStream) {
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

	@Override
	public void run() throws Exception {
		ApplicationArguments applicationArguments = application.getArguments();

		if (applicationArguments.hasArgument_plan()) {
			application.plan();
		} else if (applicationArguments.hasArgument_new_st()) {
			application.newSystemTransformations();
		} else if (applicationArguments.hasArgument_new_td()) {
			application.newTaskDescription();
		} else if (applicationArguments.hasArgument_verify()) {
			application.verify();
		} else if (applicationArguments.hasArgument_convert()) {
			application.convert();
		} else {
			application.showHelp();
		}
	}

	@Override
	public void setApplication(Application application) {
		this.application = application;
	}
}

package application.ui.cli;

import java.io.PrintStream;

import application.Application;
import application.Arguments;
import application.ui.UserInterface;

public class MainViewShell implements UserInterface {

	private PrintStream printStream;

	private Application application;

	public MainViewShell(Application application, PrintStream printStream) {
		this.printStream = printStream;
		this.application = application;
	}

	@Override
	public void displayMessage(String message) {
		printStream.println(message);
	}

	@Override
	public void start() throws Exception {
		final Arguments arguments = application.getArguments();

		// TODO (2022-07-06 #69): перенести из start
		if (arguments.hasArgument_plan()) {
			application.plan();
		} else if (arguments.hasArgument_new_st()) {
			application.newSystemTransformations();
		} else if (arguments.hasArgument_new_td()) {
			application.newTaskDescription();
		} else if (arguments.hasArgument_verify()) {
			application.verify();
		} else if (arguments.hasArgument_convert()) {
			application.convert();
		} else {
			application.usageHelp();
		}
	}

	@Override
	public void stop() {
	}
}

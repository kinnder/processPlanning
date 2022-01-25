package application.ui.cli;

import java.io.PrintStream;

import application.Application;
import application.ApplicationArguments;
import application.command.ConvertCommand;
import application.command.ConvertCommandData;
import application.command.NewSystemTransformationsCommand;
import application.command.NewSystemTransformationsCommandData;
import application.command.NewTaskDescriptionCommand;
import application.command.NewTaskDescriptionCommandData;
import application.command.PlanCommand;
import application.command.PlanCommandData;
import application.command.VerifyCommand;
import application.command.VerifyCommandData;
import application.event.CommandStatusEvent;
import application.event.HelpMessageEvent;
import application.ui.UserInterface;

public class MainShell implements UserInterface {

	private PrintStream printStream;

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

	private Application application = new Application();

	public void setApplication(Application application) {
		this.application = application;
	}

	public void run() throws Exception {
		ApplicationArguments applicationArguments = application.getArguments();

		if (applicationArguments.hasArgument_plan()) {
			PlanCommandData data = new PlanCommandData();
			data.taskDescriptionFile = applicationArguments.getArgument_td("taskDescription.xml");
			data.systemTransformationsFile = applicationArguments.getArgument_st("systemTransformations.xml");
			data.processFile = applicationArguments.getArgument_p("process.xml");
			data.nodeNetworkFile = applicationArguments.getArgument_nn("nodeNetwork.xml");
			application.runCommand(PlanCommand.NAME, data);
		}
		else if (applicationArguments.hasArgument_new_st()) {
			NewSystemTransformationsCommandData data = new NewSystemTransformationsCommandData();
			data.systemTransformationsFile = applicationArguments.getArgument_st("systemTransformations.xml");
			data.domain = applicationArguments.getArgument_d("unknown");
			application.runCommand(NewSystemTransformationsCommand.NAME, data);
		}
		else if (applicationArguments.hasArgument_new_td()) {
			NewTaskDescriptionCommandData data = new NewTaskDescriptionCommandData();
			data.taskDescriptionFile = applicationArguments.getArgument_td("taskDescription.xml");
			data.domain = applicationArguments.getArgument_d("unknown");
			application.runCommand(NewTaskDescriptionCommand.NAME, data);
		}
		else if (applicationArguments.hasArgument_verify()) {
			VerifyCommandData data = new VerifyCommandData();
			data.taskDescriptionFile = applicationArguments.getArgument_td(null);
			data.systemTransformationsFile = applicationArguments.getArgument_st(null);
			data.processFile = applicationArguments.getArgument_p(null);
			data.nodeNetworkFile = applicationArguments.getArgument_nn(null);
			application.runCommand(VerifyCommand.NAME, data);
		}
		else if (applicationArguments.hasArgument_convert()) {
			ConvertCommandData data = new ConvertCommandData();
			data.taskDescriptionFile = applicationArguments.getArgument_td(null);
			data.systemTransformationsFile = applicationArguments.getArgument_st(null);
			data.processFile = applicationArguments.getArgument_p(null);
			data.nodeNetworkFile = applicationArguments.getArgument_nn(null);
			application.runCommand(ConvertCommand.NAME, data);
		}
		else {
			application.showHelp();
		}
	}
}

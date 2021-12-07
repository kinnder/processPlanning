package application.command;

import application.Application;
import application.event.CommandStatusEvent;
import planning.method.NodeNetwork;
import planning.method.Planner;
import planning.method.SystemTransformations;
import planning.method.TaskDescription;
import planning.model.SystemProcess;

public class PlanCommand extends Command {

	// TODO (2021-12-07 #49): сделать абстрактный метод getName()
	public final static String NAME = "plan";

	public PlanCommand(Application application) {
		super(application);
	}

	@Override
	public void execute(CommandData data) throws Exception {
		execute((PlanCommandData) data);
	}

	private void execute(PlanCommandData data) throws Exception {
		// TODO (2021-12-07 #49): перенести в Command
		application.notifyCommandStatus(new CommandStatusEvent("executing command: \"plan\"..."));

		SystemTransformations systemTransformations = application.loadSystemTransformations(data.systemTransformationsFile);
		TaskDescription taskDescription = application.loadTaskDescription(data.taskDescriptionFile);

		NodeNetwork nodeNetwork = new NodeNetwork();
		// TODO : move to initialization
		Planner planner = new Planner(taskDescription, systemTransformations, nodeNetwork);
		planner.plan();

		SystemProcess process = planner.getShortestProcess();
		application.saveSystemProcess(process, data.processFile);

		application.saveNodeNetwork(nodeNetwork, data.nodeNetworkFile);

		application.notifyCommandStatus(new CommandStatusEvent("done"));
	}
}

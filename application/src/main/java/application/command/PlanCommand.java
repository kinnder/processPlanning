package application.command;

import application.Application;
import planning.method.NodeNetwork;
import planning.method.Planner;
import planning.method.SystemTransformations;
import planning.method.TaskDescription;
import planning.model.SystemProcess;

public class PlanCommand extends Command {

	public static final String NAME = "plan";

	public PlanCommand(Application application) {
		super(application, NAME);
	}

	@Override
	public void execute(CommandData data) throws Exception {
		execute((PlanCommandData) data);
	}

	private void execute(PlanCommandData data) throws Exception {
		final SystemTransformations systemTransformations = application.loadSystemTransformations(data.systemTransformationsFile);
		final TaskDescription taskDescription = application.loadTaskDescription(data.taskDescriptionFile);

		final NodeNetwork nodeNetwork = new NodeNetwork();
		// TODO : move to initialization
		final Planner planner = new Planner(taskDescription, systemTransformations, nodeNetwork);
		planner.plan();

		final SystemProcess process = planner.getShortestProcess();
		application.saveSystemProcess(process, data.processFile);

		application.saveNodeNetwork(nodeNetwork, data.nodeNetworkFile);
	}
}

package application.command;

import application.event.CommandStatusEvent;
import application.storage.PersistanceStorage;
import planning.method.NodeNetwork;
import planning.method.Planner;
import planning.method.SystemTransformations;
import planning.method.TaskDescription;
import planning.model.SystemProcess;

public class PlanCommand extends Command {

	public final static String NAME = "plan";

	@Override
	public void execute(CommandData data) throws Exception {
		execute((PlanCommandData) data);
	}

	PersistanceStorage persistanceStorage = new PersistanceStorage();

	private void execute(PlanCommandData data) throws Exception {
		notifyCommandStatus(new CommandStatusEvent("executing command: \"plan\"..."));

		SystemTransformations systemTransformations = persistanceStorage.loadSystemTransformations(data.systemTransformationsFile);

		TaskDescription taskDescription = persistanceStorage.loadTaskDescription(data.taskDescriptionFile);

		NodeNetwork nodeNetwork = new NodeNetwork();
		// TODO : move to initialization
		Planner planner = new Planner(taskDescription, systemTransformations, nodeNetwork);
		planner.plan();

		SystemProcess process = planner.getShortestProcess();
		persistanceStorage.saveSystemProcess(process, data.processFile);

		persistanceStorage.saveNodeNetwork(nodeNetwork, data.nodeNetworkFile);

		notifyCommandStatus(new CommandStatusEvent("done"));
	}
}

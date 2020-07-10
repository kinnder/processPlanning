package application.command;

import application.event.CommandStatusEvent;
import planning.method.NodeNetwork;
import planning.method.Planner;
import planning.method.SystemTransformations;
import planning.method.TaskDescription;
import planning.model.SystemProcess;
import planning.storage.NodeNetworkXMLFile;
import planning.storage.SystemProcessXMLFile;
import planning.storage.SystemTransformationsXMLFile;
import planning.storage.TaskDescriptionXMLFile;

public class PlanCommand extends Command {

	public final static String NAME = "plan";

	@Override
	public void execute(CommandData data) throws Exception {
		execute((PlanCommandData) data);
	}

	SystemTransformationsXMLFile transformationsXMLFile = new SystemTransformationsXMLFile();

	TaskDescriptionXMLFile taskXMLFile = new TaskDescriptionXMLFile();

	SystemProcessXMLFile processXMLFile = new SystemProcessXMLFile();

	NodeNetworkXMLFile nodeNetworkXMLFile = new NodeNetworkXMLFile();

	private void execute(PlanCommandData data) throws Exception {
		notifyCommandStatus(new CommandStatusEvent("planning..."));

		transformationsXMLFile.load(data.systemTransformationsFile);
		SystemTransformations systemTransformations = transformationsXMLFile.getObject();

		taskXMLFile.load(data.taskDescriptionFile);
		TaskDescription taskDescription = taskXMLFile.getObject();

		NodeNetwork nodeNetwork = new NodeNetwork();
		// TODO : move to initialization
		Planner planner = new Planner(taskDescription, systemTransformations, nodeNetwork);
		planner.plan();

		SystemProcess operations = planner.getShortestProcess();
		processXMLFile.setObject(operations);
		processXMLFile.save(data.processFile);

		nodeNetworkXMLFile.setObject(nodeNetwork);
		nodeNetworkXMLFile.save(data.nodeNetworkFile);

		notifyCommandStatus(new CommandStatusEvent("done"));
	}
}

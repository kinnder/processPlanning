package application.command;

import application.event.CommandStatusEvent;
import application.storage.xml.NodeNetworkXMLFile;
import application.storage.xml.SystemProcessXMLFile;
import application.storage.xml.SystemTransformationsXMLFile;
import application.storage.xml.TaskDescriptionXMLFile;
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

	SystemTransformationsXMLFile transformationsXMLFile = new SystemTransformationsXMLFile();

	TaskDescriptionXMLFile taskXMLFile = new TaskDescriptionXMLFile();

	SystemProcessXMLFile processXMLFile = new SystemProcessXMLFile();

	NodeNetworkXMLFile nodeNetworkXMLFile = new NodeNetworkXMLFile();

	private void execute(PlanCommandData data) throws Exception {
		notifyCommandStatus(new CommandStatusEvent("executing command: \"plan\"..."));

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

package application.command;

import application.Command;
import application.CommandData;
import application.event.CommandStatusEvent;
import planning.method.Planner;
import planning.method.SystemTransformations;
import planning.method.TaskDescription;
import planning.model.SystemProcess;
import planning.storage.SystemProcessXMLFile;
import planning.storage.SystemTransformationsXMLFile;
import planning.storage.TaskDescriptionXMLFile;

public class PlanCommand extends Command {

	public static String NAME = "plan";

	@Override
	public void execute(CommandData data) throws Exception {
		execute((PlanCommandData) data);
	}

	public void execute(PlanCommandData data) throws Exception {
		notifyCommandStatus(new CommandStatusEvent("planning..."));

		SystemTransformationsXMLFile transformationsXMLFile = new SystemTransformationsXMLFile();
		transformationsXMLFile.load(data.systemTransformationsFile);
		SystemTransformations systemTransformations = new SystemTransformations();
		systemTransformations.addAll(transformationsXMLFile.getObject());

		TaskDescriptionXMLFile taskXMLFile = new TaskDescriptionXMLFile();
		taskXMLFile.load(data.taskDescriptionFile);
		TaskDescription taskDescription = taskXMLFile.getObject();

		Planner planner = new Planner(taskDescription, systemTransformations);
		planner.plan();

		SystemProcess operations = planner.getShortestProcess();
		SystemProcessXMLFile xmlFile = new SystemProcessXMLFile();
		xmlFile.setObject(operations);
		xmlFile.save(data.processFile);

		notifyCommandStatus(new CommandStatusEvent("done"));
	}
}

package application.command;

import java.io.IOException;

import org.jdom2.JDOMException;

import application.Command;
import application.UserInterface;
import planning.method.Planner;
import planning.method.SystemTransformations;
import planning.method.TaskDescription;
import planning.model.SystemProcess;
import planning.storage.SystemProcessXMLFile;
import planning.storage.SystemTransformationsXMLFile;
import planning.storage.TaskDescriptionXMLFile;

//TODO : remove this annotation
@SuppressWarnings("PMD")
public class PlanCommand implements Command<PlanCommandData> {

	private UserInterface ui;

	public PlanCommand(UserInterface ui) {
		this.ui = ui;
	}

	@Override
	public void execute(PlanCommandData data) {
		ui.printCommandStatus("planning...");
		try {
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
		} catch (JDOMException | CloneNotSupportedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ui.printCommandStatus("done");
	}
}

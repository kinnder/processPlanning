package application.command;

import java.io.IOException;

import application.Application;
import application.domain.AssemblyLine;
import application.domain.CuttingProcess;
import application.domain.MaterialPoints;
import application.event.CommandStatusEvent;
import application.storage.PersistanceStorage;
import planning.method.TaskDescription;
import planning.model.System;

public class NewTaskDescriptionCommand extends Command {

	public static final String NAME = "new_td";

	public NewTaskDescriptionCommand(Application application) {
		super(application);
	}

	@Override
	public void execute(CommandData data) throws Exception {
		execute((NewTaskDescriptionCommandData) data);
	}

	private void execute(NewTaskDescriptionCommandData data) throws IOException {
		application.notifyCommandStatus(new CommandStatusEvent("executing command: \"new task description\"..."));

		TaskDescription taskDescription;
		switch (data.domain) {
		case MaterialPoints.DOMAIN_NAME:
			taskDescription = MaterialPoints.getTaskDescription();
			break;
		case CuttingProcess.DOMAIN_NAME:
			taskDescription = CuttingProcess.getTaskDescription();
			break;
		case AssemblyLine.DOMAIN_NAME:
			taskDescription = AssemblyLine.getTaskDescription();
			break;
		default:
			taskDescription = new TaskDescription();
			// TODO (2020-07-30 #30): посмотреть конструктор класса TaskDescription
			taskDescription.setInitialSystem(new System());
			taskDescription.setFinalSystem(new System());
			break;
		}

		PersistanceStorage persistanceStorage = application.getPersistanceStorage();
		persistanceStorage.saveTaskDescription(taskDescription, data.taskDescriptionFile);

		application.notifyCommandStatus(new CommandStatusEvent("done"));
	}
}

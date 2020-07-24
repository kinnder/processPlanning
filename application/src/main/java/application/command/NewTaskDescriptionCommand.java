package application.command;

import java.io.IOException;

import application.event.CommandStatusEvent;
import planning.method.TaskDescription;
import planning.storage.TaskDescriptionXMLFile;
import planning.model.System;

public class NewTaskDescriptionCommand extends Command {

	public static final String NAME = "new_td";

	TaskDescriptionXMLFile taskXMLFile = new TaskDescriptionXMLFile();

	@Override
	public void execute(CommandData data) throws Exception {
		execute((NewTaskDescriptionCommandData) data);
	}

	private void execute(NewTaskDescriptionCommandData data) throws IOException {
		notifyCommandStatus(new CommandStatusEvent("executing command: \"new task description\"..."));

		TaskDescription taskDescription = alphaAndBeta();

		taskXMLFile.setObject(taskDescription);
		taskXMLFile.save(data.taskDescriptionFile);

		notifyCommandStatus(new CommandStatusEvent("done"));
	}

	// TODO (2020-07-24 #29): использовать в качестве примеров сценарии из domain
	public static TaskDescription alphaAndBeta() {
		TaskDescription taskDescription = new TaskDescription();
		taskDescription.setInitialSystem(initialSystem());
		taskDescription.setFinalSystem(finalSystem());
		return taskDescription;
	}

	public static System initialSystem() {
		final System system = new System();
		return system;
	}

	public static System finalSystem() {
		final System system = new System();
		return system;
	}
}

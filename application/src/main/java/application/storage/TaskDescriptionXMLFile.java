package application.storage;

import planning.method.TaskDescription;

public class TaskDescriptionXMLFile extends XMLFile<TaskDescription> {

	public TaskDescriptionXMLFile() {
		super(new TaskDescriptionXMLSchema());
	}
}

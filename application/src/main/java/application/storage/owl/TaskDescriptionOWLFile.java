package application.storage.owl;

import planning.method.TaskDescription;

public class TaskDescriptionOWLFile extends OWLFile<TaskDescription> {

	public TaskDescriptionOWLFile() {
		super(new TaskDescriptionOWLModel(), new TaskDescriptionOWLSchema());
	}
}

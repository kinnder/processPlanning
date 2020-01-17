package planning.storage;

import planning.method.TaskDescription;

public class TaskDescriptionXMLFile extends XMLFile {

	public TaskDescriptionXMLFile() {
		super(new TaskDescriptionXMLSchema());
	}

	@Override
	public TaskDescriptionXMLSchema getXMLSchema() {
		return (TaskDescriptionXMLSchema) super.getXMLSchema();
	}

	public TaskDescription getTaskDescription() {
		return getXMLSchema().getTaskDescription();
	}

	public void setTaskDescription(TaskDescription taskDescription) {
		getXMLSchema().setTaskDescription(taskDescription);
	}
}

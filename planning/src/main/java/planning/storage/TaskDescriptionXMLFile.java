package planning.storage;

import planning.method.TaskDescription;

public class TaskDescriptionXMLFile extends XMLFile {

	public TaskDescriptionXMLFile() {
		super(new TaskDescriptionXMLModel());
	}

	@Override
	public TaskDescriptionXMLModel getXMLModel() {
		return (TaskDescriptionXMLModel) super.getXMLModel();
	}

	public TaskDescription getTaskDescription() {
		return getXMLModel().getTaskDescription();
	}

	public void setTaskDescription(TaskDescription taskDescription) {
		getXMLModel().setTaskDescription(taskDescription);
	}
}

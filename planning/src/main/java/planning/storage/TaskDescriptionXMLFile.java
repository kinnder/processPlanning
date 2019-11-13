package planning.storage;

import planning.model.System;

public class TaskDescriptionXMLFile extends XMLFile {

	public TaskDescriptionXMLFile() {
		super(new TaskDescriptionXMLModel());
	}

	@Override
	public TaskDescriptionXMLModel getXMLModel() {
		return (TaskDescriptionXMLModel) super.getXMLModel();
	}

	public System getInitialSystem() {
		return getXMLModel().getInitialSystem();
	}

	public void setInitialSystem(System system) {
		getXMLModel().setInitialSystem(system);
	}

	public System getFinalSystem() {
		return getXMLModel().getFinalSystem();
	}

	public void setFinalSystem(System system) {
		getXMLModel().setFinalSystem(system);
	}
}

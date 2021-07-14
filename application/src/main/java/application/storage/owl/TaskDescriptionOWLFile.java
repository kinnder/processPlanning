package application.storage.owl;

import planning.method.TaskDescription;

public class TaskDescriptionOWLFile extends OWLFile<TaskDescription> {

	public TaskDescriptionOWLFile() {
		owlModel = new PlanningOWLModel();
		owlSchema = new TaskDescriptionOWLSchema((PlanningOWLModel) owlModel);
	}
}

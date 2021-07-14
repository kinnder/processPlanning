package application.storage.owl;

import planning.model.SystemProcess;

public class SystemProcessOWLFile extends OWLFile<SystemProcess> {

	public SystemProcessOWLFile() {
		owlModel = new PlanningOWLModel();
		owlSchema = new SystemProcessOWLSchema((PlanningOWLModel) owlModel);
	}
}

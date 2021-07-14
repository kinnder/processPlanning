package application.storage.owl;

import planning.method.SystemTransformations;

public class SystemTransformationsOWLFile extends OWLFile<SystemTransformations> {

	public SystemTransformationsOWLFile() {
		owlModel = new PlanningOWLModel();
		owlSchema = new SystemTransformationsOWLSchema((PlanningOWLModel) owlModel);
	}
}

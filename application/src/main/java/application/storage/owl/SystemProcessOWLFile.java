package application.storage.owl;

import planning.model.SystemProcess;

public class SystemProcessOWLFile extends OWLFile<SystemProcess> {

	public SystemProcessOWLFile() {
		super(new SystemProcessOWLModel());
	}
}

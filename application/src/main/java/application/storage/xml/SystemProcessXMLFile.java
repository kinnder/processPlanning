package application.storage.xml;

import planning.model.SystemProcess;

public class SystemProcessXMLFile extends XMLFile<SystemProcess> {

	public SystemProcessXMLFile() {
		super(new SystemProcessXMLSchema());
	}
}

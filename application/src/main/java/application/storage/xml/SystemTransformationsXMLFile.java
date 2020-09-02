package application.storage.xml;

import planning.method.SystemTransformations;

public class SystemTransformationsXMLFile extends XMLFile<SystemTransformations> {

	public SystemTransformationsXMLFile() {
		super(new SystemTransformationsXMLSchema());
	}
}

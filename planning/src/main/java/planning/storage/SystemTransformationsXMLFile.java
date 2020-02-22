package planning.storage;

import planning.method.SystemTransformations;

public class SystemTransformationsXMLFile extends XMLFile {

	public SystemTransformationsXMLFile() {
		super(new SystemTransformationsXMLSchema());
	}

	@Override
	public SystemTransformationsXMLSchema getXMLSchema() {
		return (SystemTransformationsXMLSchema) super.getXMLSchema();
	}

	public SystemTransformations getSystemTransformations() {
		return getXMLSchema().getSystemTransformations();
	}

	public void setSystemTransformations(SystemTransformations systemTransformations) {
		getXMLSchema().setSystemTransformations(systemTransformations);
	}
}

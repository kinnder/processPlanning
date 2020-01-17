package planning.storage;

import planning.model.SystemTransformation;

public class SystemTransformationsXMLFile extends XMLFile {

	public SystemTransformationsXMLFile() {
		super(new SystemTransformationsXMLSchema());
	}

	@Override
	public SystemTransformationsXMLSchema getXMLSchema() {
		return (SystemTransformationsXMLSchema) super.getXMLSchema();
	}

	public SystemTransformation[] getSystemTransformations() {
		return getXMLSchema().getSystemTransformations();
	}

	public void setSystemTransformations(SystemTransformation[] systemTransformations) {
		getXMLSchema().setSystemTransformations(systemTransformations);
	}
}

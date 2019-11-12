package planning.storage;

import planning.model.SystemTransformation;

public class SystemTransformationsXMLFile extends XMLFile {

	public SystemTransformationsXMLFile() {
		super(new SystemTransformationsXMLModel());
	}

	@Override
	public SystemTransformationsXMLModel getXMLModel() {
		return (SystemTransformationsXMLModel) super.getXMLModel();
	}

	public SystemTransformation[] getSystemTransformations() {
		return getXMLModel().getSystemTransformations();
	}

	public void setSystemTransformations(SystemTransformation[] systemTransformations) {
		getXMLModel().setSystemTransformations(systemTransformations);
	}
}

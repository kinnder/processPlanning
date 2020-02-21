package planning.storage;

import planning.model.SystemProcess;

public class SystemProcessXMLFile extends XMLFile {

	public SystemProcessXMLFile() {
		super(new SystemProcessXMLSchema());
	}

	@Override
	public SystemProcessXMLSchema getXMLSchema() {
		return (SystemProcessXMLSchema) super.getXMLSchema();
	}

	public void setSystemProcess(SystemProcess process) {
		getXMLSchema().setSystemProcess(process);
	}

	public SystemProcess getSystemProcess() {
		return getXMLSchema().getSystemProcess();
	}
}

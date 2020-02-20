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

	public void setProcess(SystemProcess process) {
		getXMLSchema().setProcess(process);
	}

	public SystemProcess getProcess() {
		return getXMLSchema().getProcess();
	}
}

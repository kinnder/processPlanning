package planning.model;

public class SystemVariant {

	private System system;

	private IdsMatching idsMatching;

	public SystemVariant(System system, IdsMatching idsMatching) {
		this.system = system;
		this.idsMatching = idsMatching;
	}

	public String getObjectIdByIdMatch(String templateId) {
		return idsMatching.get(templateId);
	}

	public SystemObject getObjectByIdMatch(String templateId) {
		String objectId = idsMatching.get(templateId);
		return system.getObjectById(objectId);
	}

	public System getSystem() {
		return system;
	}
}

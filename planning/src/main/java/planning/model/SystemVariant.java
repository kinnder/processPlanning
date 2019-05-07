package planning.model;

import java.util.Map;

public class SystemVariant {

	private System system;

	private Map<String, String> idsMatching;

	public SystemVariant(System system, Map<String, String> idsMatching) {
		this.system = system;
		this.idsMatching = idsMatching;
	}

	public String getObjectIdByIdMatch(String objectId) {
		return idsMatching.get(objectId);
	}

	public SystemObject getObjectByIdMatch(String objectId) {
		String objectIdMatch = idsMatching.get(objectId);
		return system.getObjectById(objectIdMatch);
	}
}

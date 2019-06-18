package planning.model;

public class SystemVariant {

	private System system;

	private IdsMatching idsMatching;

	private Action action;

	public SystemVariant(System system, IdsMatching idsMatching, Action action) {
		this.system = system;
		this.idsMatching = idsMatching;
		this.action = action;
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

	public IdsMatching getIdsMatching() {
		return idsMatching;
	}

	public void updateActionParameters() {
		action.updateParameters(system, idsMatching);
	}

	public Action getAction() {
		return action;
	}
}

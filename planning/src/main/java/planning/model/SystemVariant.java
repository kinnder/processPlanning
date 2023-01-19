package planning.model;

import java.util.HashMap;
import java.util.Map;

public class SystemVariant {

	public SystemVariant(System system, IdsMatching idsMatching) {
		this.system = system;
		this.idsMatching = idsMatching;
		this.actionParameters = new HashMap<>();
	}

	public String getObjectIdByIdMatch(String templateId) {
		return idsMatching.get(templateId);
	}

	public SystemObject getObjectByIdMatch(String templateId) {
		final String objectId = idsMatching.get(templateId);
		return system.getObjectById(objectId);
	}

	private System system;

	public System getSystem() {
		return system;
	}

	private IdsMatching idsMatching;

	public IdsMatching getIdsMatching() {
		return idsMatching;
	}

	private Map<String, String> actionParameters;

	public Map<String, String> getActionParameters() {
		return actionParameters;
	}

	public String getActionParameter(String parameterName) {
		return actionParameters.get(parameterName);
	}

	public void setActionParameter(String parameterName, String parameterValue) {
		actionParameters.put(parameterName, parameterValue);
	}
}

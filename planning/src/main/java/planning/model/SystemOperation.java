package planning.model;

import java.util.Map;

public class SystemOperation {

	public SystemOperation(Action action, Map<String, String> actionParameters) {
		this.action = action;
		this.actionParameters = actionParameters;
	}

	private Action action;

	public Action getAction() {
		return action;
	}

	private Map<String, String> actionParameters;

	public Map<String, String> getActionParameters() {
		return actionParameters;
	}

	public String getName() {
		return action.getName();
	}

	public String getParameter(String parameterName) {
		return actionParameters.get(parameterName);
	}
}

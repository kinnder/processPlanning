package planning.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Action implements Cloneable {

	private String name;

	public Action(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public Action clone() throws CloneNotSupportedException {
		Action clone = (Action) super.clone();
		clone.name = name;
		clone.parameterUpdaters = new ArrayList<>(parameterUpdaters);
		clone.conditionCheckers = new ArrayList<>(conditionCheckers);
		clone.parameters = new HashMap<>();
		clone.parameters.putAll(parameters);
		return clone;
	}

	public void updateParameters(SystemVariant systemVariant) {
		for (ParameterUpdater parameterUpdater : parameterUpdaters) {
			parameterUpdater.invoke(systemVariant, parameters);
		}
	}

	private List<ParameterUpdater> parameterUpdaters = new ArrayList<>();

	public void registerParameterUpdater(ParameterUpdater parameterUpdater) {
		parameterUpdaters.add(parameterUpdater);
	}

	private Map<String, String> parameters = new HashMap<>();

	public String getParameter(String parameterName) {
		return parameters.get(parameterName);
	}

	private List<ConditionChecker> conditionCheckers = new ArrayList<>();

	public void registerConditionChecker(ConditionChecker conditionChecker) {
		conditionCheckers.add(conditionChecker);
	}

	public boolean allConditionsPasses(SystemVariant systemVariant) {
		for (ConditionChecker conditionChecker : conditionCheckers) {
			boolean conditionPasses = conditionChecker.invoke(systemVariant, parameters);
			if (!conditionPasses) {
				return false;
			}
		}
		return true;
	}
}

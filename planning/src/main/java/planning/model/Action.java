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
	public Action clone() {
		Action clone = new Action(name);
		clone.parameterUpdaters.addAll(parameterUpdaters);
		clone.conditionCheckers.addAll(conditionCheckers);
		clone.parameters.putAll(parameters);
		return clone;
	}

	public void updateParameters(System system, IdsMatching idsMatching) {
		for (ParameterUpdater parameterUpdater : parameterUpdaters) {
			parameterUpdater.invoke(system, idsMatching, parameters);
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

	public boolean allConditionsPasses(System system, IdsMatching idsMatching) {
		for (ConditionChecker conditionChecker : conditionCheckers) {
			boolean conditionPasses = conditionChecker.invoke(system, idsMatching, parameters);
			if (!conditionPasses) {
				return false;
			}
		}
		return true;
	}
}

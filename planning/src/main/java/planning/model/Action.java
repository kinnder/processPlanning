package planning.model;

import java.util.ArrayList;
import java.util.List;

public class Action {

	private String name;

	public Action(String name) {
		this.name = name;
		this.parameterUpdaters = new ArrayList<>();
		this.conditionCheckers = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void updateActionParameters(SystemVariant systemVariant) {
		for (ParameterUpdater parameterUpdater : parameterUpdaters) {
			parameterUpdater.invoke(systemVariant);
		}
	}

	private List<ParameterUpdater> parameterUpdaters;

	public void registerParameterUpdater(ParameterUpdater parameterUpdater) {
		parameterUpdaters.add(parameterUpdater);
	}

	private List<ConditionChecker> conditionCheckers;

	public void registerConditionChecker(ConditionChecker conditionChecker) {
		conditionCheckers.add(conditionChecker);
	}

	public boolean haveAllConditionsPassed(SystemVariant systemVariant) {
		for (ConditionChecker conditionChecker : conditionCheckers) {
			boolean conditionPasses = conditionChecker.invoke(systemVariant);
			if (!conditionPasses) {
				return false;
			}
		}
		return true;
	}
}

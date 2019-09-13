package planning.model;

import java.util.ArrayList;
import java.util.List;

public class Action {

	private String name;

	public Action(String name) {
		this.name = name;
		this.parameterUpdaters = new ArrayList<>();
		this.preConditionCheckers = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void updateActionParameters(SystemVariant systemVariant) {
		for (ActionParameterUpdater parameterUpdater : parameterUpdaters) {
			parameterUpdater.invoke(systemVariant);
		}
	}

	private List<ActionParameterUpdater> parameterUpdaters;

	public void registerParameterUpdater(ActionParameterUpdater parameterUpdater) {
		parameterUpdaters.add(parameterUpdater);
	}

	private List<ActionPreConditionChecker> preConditionCheckers;

	public void registerPreConditionChecker(ActionPreConditionChecker preConditionChecker) {
		preConditionCheckers.add(preConditionChecker);
	}

	public boolean haveAllPreConditionsPassed(SystemVariant systemVariant) {
		for (ActionPreConditionChecker conditionChecker : preConditionCheckers) {
			boolean conditionPasses = conditionChecker.invoke(systemVariant);
			if (!conditionPasses) {
				return false;
			}
		}
		return true;
	}
}

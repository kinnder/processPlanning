package planning.model;

import java.util.ArrayList;
import java.util.List;

public class Action {

	private String name;

	public Action(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void updateActionParameters(SystemVariant systemVariant) {
		for (ActionParameterUpdater parameterUpdater : parameterUpdaters) {
			parameterUpdater.invoke(systemVariant);
		}
	}

	private List<ActionParameterUpdater> parameterUpdaters = new ArrayList<>();

	public List<ActionParameterUpdater> getParameterUpdaters() {
		return parameterUpdaters;
	}

	public void registerActionParameterUpdater(ActionParameterUpdater parameterUpdater) {
		parameterUpdaters.add(parameterUpdater);
	}

	private List<ActionPreConditionChecker> preConditionCheckers = new ArrayList<>();

	public List<ActionPreConditionChecker> getPreConditionCheckers() {
		return preConditionCheckers;
	}

	public void registerActionPreConditionChecker(ActionPreConditionChecker preConditionChecker) {
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

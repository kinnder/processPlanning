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

	public void updateParameters(SystemVariant systemVariant) {
		for (ActionFunction parameterUpdater : parameterUpdaters) {
			parameterUpdater.accept(systemVariant);
		}
	}

	private List<ActionFunction> parameterUpdaters = new ArrayList<>();

	public List<ActionFunction> getParameterUpdaters() {
		return parameterUpdaters;
	}

	public void registerParameterUpdater(ActionFunction parameterUpdater) {
		parameterUpdaters.add(parameterUpdater);
	}

	private List<ActionFunction> preConditionCheckers = new ArrayList<>();

	public List<ActionFunction> getPreConditionCheckers() {
		return preConditionCheckers;
	}

	public void registerPreConditionChecker(ActionFunction preConditionChecker) {
		preConditionCheckers.add(preConditionChecker);
	}

	public boolean haveAllPreConditionsPassed(SystemVariant systemVariant) {
		for (ActionFunction preConditionChecker : preConditionCheckers) {
			boolean conditionPasses = preConditionChecker.test(systemVariant);
			if (!conditionPasses) {
				return false;
			}
		}
		return true;
	}
}

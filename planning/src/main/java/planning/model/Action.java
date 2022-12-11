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

	public void setName(String actionName) {
		this.name = actionName;
	}

	@Override
	public String toString() {
		return "Action " + name;
	}

	private List<ActionFunction> actionFunctions = new ArrayList<>();

	public List<ActionFunction> getActionFunctions() {
		return actionFunctions;
	}

	public void updateParameters(SystemVariant systemVariant) {
		for (ActionFunction actionFunction : actionFunctions) {
			if (actionFunction.getType() == ActionFunction.TYPE_PARAMETER_UPDATER) {
				actionFunction.accept(systemVariant);
			}
		}
	}

	public List<ActionFunction> getParameterUpdaters() {
		List<ActionFunction> parameterUpdaters = new ArrayList<>();
		for (ActionFunction actionFunction : actionFunctions) {
			if (actionFunction.getType() == ActionFunction.TYPE_PARAMETER_UPDATER) {
				parameterUpdaters.add(actionFunction);
			}
		}
		return parameterUpdaters;
	}

	public void registerParameterUpdater(ActionFunction actionFunction) {
		actionFunction.setType(ActionFunction.TYPE_PARAMETER_UPDATER);
		actionFunctions.add(actionFunction);
	}

	public List<ActionFunction> getPreConditionCheckers() {
		List<ActionFunction> preConditionCheckers = new ArrayList<>();
		for (ActionFunction actionFunction : actionFunctions) {
			if (actionFunction.getType() == ActionFunction.TYPE_PRECONDITION_CHECKER) {
				preConditionCheckers.add(actionFunction);
			}
		}
		return preConditionCheckers;
	}

	public void registerPreConditionChecker(ActionFunction actionFunction) {
		actionFunction.setType(ActionFunction.TYPE_PRECONDITION_CHECKER);
		actionFunctions.add(actionFunction);
	}

	public boolean haveAllPreConditionsPassed(SystemVariant systemVariant) {
		for (ActionFunction actionFunction : actionFunctions) {
			if (actionFunction.getType() == ActionFunction.TYPE_PRECONDITION_CHECKER) {
				boolean conditionPasses = actionFunction.test(systemVariant);
				if (!conditionPasses) {
					return false;
				}
			}
		}
		return true;
	}

	public void removeActionFunction(ActionFunction actionFunction) {
		actionFunctions.remove(actionFunction);
	}

	public void addActionFunction(ActionFunction actionFunction) {
		actionFunctions.add(actionFunction);
	}
}

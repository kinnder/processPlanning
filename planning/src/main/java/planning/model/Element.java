package planning.model;

import java.util.ArrayList;
import java.util.List;

public class Element {

	private Action action;

	private SystemTemplate template;

	private Transformation[] transformations;

	public Element(Action action, SystemTemplate template, Transformation[] transformations) {
		this.action = action;
		this.template = template;
		this.transformations = transformations;
	}

	public Action getAction() {
		return action;
	}

	public SystemVariant[] applyTo(System system) throws CloneNotSupportedException {
		IdsMatching[] idsMatchings = template.matchIds(system);

		List<SystemVariant> systemVariants = new ArrayList<>();
		for (IdsMatching idsMatching : idsMatchings) {
			SystemVariant systemVariant = new SystemVariant(system.clone(), idsMatching);
			if (action.haveAllConditionsPassed(systemVariant)) {
				for (Transformation transformation : transformations) {
					transformation.applyTo(systemVariant);
				}
				action.updateActionParameters(systemVariant);
				systemVariants.add(systemVariant);
			}
		}

		return systemVariants.toArray(new SystemVariant[] {});
	}

}

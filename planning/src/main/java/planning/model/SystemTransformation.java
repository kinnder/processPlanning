package planning.model;

import java.util.ArrayList;
import java.util.List;

public class SystemTransformation {

	private String name;

	private Action action;

	private SystemTemplate template;

	private Transformation[] transformations;

	public SystemTransformation(String name, Action action, SystemTemplate template, Transformation[] transformations) {
		this.name = name;
		this.action = action;
		this.template = template;
		this.transformations = transformations;
	}

	public SystemTransformation(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public SystemTemplate getSystemTemplate() {
		return template;
	}

	public void setSystemTemplate(SystemTemplate systemTemplate) {
		this.template = systemTemplate;
	}

	public Transformation[] getTransformations() {
		return transformations;
	}

	public void setTransformations(Transformation[] transformations) {
		this.transformations = transformations;
	}

	public SystemVariant[] applyTo(System system) throws CloneNotSupportedException {
		IdsMatching[] idsMatchings = template.matchIds(system);

		List<SystemVariant> systemVariants = new ArrayList<>();
		for (IdsMatching idsMatching : idsMatchings) {
			SystemVariant systemVariant = new SystemVariant(system.clone(), idsMatching);
			if (action.haveAllPreConditionsPassed(systemVariant)) {
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

package planning.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SystemTransformation {

	private String name;

	private Action action;

	private SystemTemplate template;

	private Transformations transformations;

	public SystemTransformation(String name, Action action, SystemTemplate template, Transformations transformations) {
		this.name = name;
		this.action = action;
		this.template = template;
		this.transformations = transformations;
	}

	public SystemTransformation() {
		this.name = "systemTransformation-" + UUID.randomUUID().toString();
		// TODO (2023-01-28 #37): перенести в Action
		this.action = new Action("new-action");
		this.template = new SystemTemplate();
		this.transformations = new Transformations();
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

	public Transformations getTransformations() {
		return transformations;
	}

	public void setTransformations(Transformations transformations) {
		this.transformations = transformations;
	}

	public SystemVariant[] applyTo(System system) throws CloneNotSupportedException {
		final IdsMatching[] idsMatchings = template.matchIds(system);

		final List<SystemVariant> systemVariants = new ArrayList<>();
		for (IdsMatching idsMatching : idsMatchings) {
			final SystemVariant systemVariant = new SystemVariant(system.clone(), idsMatching);
			if (action.haveAllPreConditionsPassed(systemVariant)) {
				for (Transformation transformation : transformations) {
					transformation.applyTo(systemVariant);
				}
				action.updateParameters(systemVariant);
				systemVariants.add(systemVariant);
			}
		}

		return systemVariants.toArray(new SystemVariant[] {});
	}

	@Override
	public String toString() {
		return name;
	}

	public void setName(String aValue) {
		this.name = aValue;
	}
}

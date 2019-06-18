package planning.model;

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

	public SystemVariant[] applyTo(System system) {
		IdsMatching[] idsMatchings = template.matchIds(system);

		int amount = idsMatchings.length;
		SystemVariant[] systemVariants = new SystemVariant[amount];
		for (int i = 0; i < amount; i++) {
			SystemVariant systemVariant = new SystemVariant(system.clone(), idsMatchings[i]);
			for (Transformation transformation : transformations) {
				transformation.applyTo(systemVariant);
			}
			systemVariants[i] = systemVariant;
		}

		return systemVariants;
	}

}

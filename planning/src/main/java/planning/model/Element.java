package planning.model;

public class Element {

	private String operation;

	private System template;

	private Transformation[] transformations;

	public Element(String operation, System template, Transformation[] transformations) {
		this.operation = operation;
		this.template = template;
		this.transformations = transformations;
	}

	public String getOperation() {
		return operation;
	}

	public SystemVariant[] applyTo(System system) {
		IdsMatching[] idsMatchings = system.matchIds(template);

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

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

	public void applyTo(SystemVariant systemVariant) {
		for (Transformation transformation : transformations) {
			transformation.applyTo(systemVariant);
		}
	}

	public String getOperation() {
		return operation;
	}

	public SystemVariant[] prepareSystemVariants(System system) {
		IdsMatching[] idsMatchings = system.matchIds(template);

		int amount = idsMatchings.length;
		SystemVariant[] systemVariants = new SystemVariant[amount];
		for (int i = 0; i < amount; i++) {
			systemVariants[i] = new SystemVariant(system.clone(), idsMatchings[i]);
		}

		return systemVariants;
	}

}

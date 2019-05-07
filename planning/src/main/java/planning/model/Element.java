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

	public System getTemplate() {
		return template;
	}

	public void applyTo(SystemVariant systemVariant) {
		for (Transformation transformation : transformations) {
			transformation.applyTo(systemVariant);
		}
	}
}

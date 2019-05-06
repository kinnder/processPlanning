package planning.model;

import java.util.Map;

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

	public void applyTo(System system, Map<String, String> idsMatching) {
		for (Transformation transformation : transformations) {
			transformation.applyTo(system, idsMatching);
		}
	}
}

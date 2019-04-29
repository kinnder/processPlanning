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

	public System applyTo(System system) {
		System transformedSystem = system.clone();

		Map<String, String> idsMatching = transformedSystem.matchIds(template);
		for (Transformation transformation : transformations) {
			transformation.applyTo(transformedSystem, idsMatching);
		}

		return transformedSystem;
	}
}

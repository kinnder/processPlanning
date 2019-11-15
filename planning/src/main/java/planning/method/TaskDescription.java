package planning.method;

import planning.model.System;

public class TaskDescription {

	private System initialSystem;

	public System getInitialSystem() {
		return this.initialSystem;
	}

	public void setInitialSystem(System initialSystem) {
		this.initialSystem = initialSystem;
	}

	private System finalSystem;

	public System getFinalSystem() {
		return this.finalSystem;
	}

	public void setFinalSystem(System finalSystem) {
		this.finalSystem = finalSystem;
	}
}

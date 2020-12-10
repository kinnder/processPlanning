package planning.method;

import planning.model.System;

public class TaskDescription {
	// TODO (2020-12-10 #31): удалить link из элемента SystemObject в схеме taskDescription.tsd 

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

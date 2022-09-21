package planning.method;

import planning.model.System;

public class TaskDescription {

	private System initialSystem = new System();

	public System getInitialSystem() {
		return this.initialSystem;
	}

	public void setInitialSystem(System initialSystem) {
		this.initialSystem = initialSystem;
	}

	private System finalSystem = new System();

	public System getFinalSystem() {
		return this.finalSystem;
	}

	public void setFinalSystem(System finalSystem) {
		this.finalSystem = finalSystem;
	}

	// TODO (2022-09-21 #72): добавить чтение / запись поля
	public String name = "TaskDescription";

	@Override
	public String toString() {
		return name;
	}
}

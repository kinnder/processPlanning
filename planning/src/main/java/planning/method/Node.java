package planning.method;

import java.util.UUID;

import planning.model.System;

public class Node {

	private String id;

	private boolean checked;

	public String getId() {
		return this.id;
	}

	public Node(System system) {
		this(UUID.randomUUID().toString(), system);
	}

	Node(String id, System system) {
		this(id, system, false);
	}

	public Node(String id, System system, boolean checked) {
		this.id = id;
		this.system = system;
		this.checked = checked;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj instanceof Node) {
			Node node = (Node) obj;
			return id.equals(node.id);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	private System system;

	public System getSystem() {
		return this.system;
	}

	public boolean isUnchecked() {
		return !checked;
	}

	public void setChecked(boolean b) {
		this.checked = b;
	}

	public boolean getChecked() {
		return this.checked;
	}
}

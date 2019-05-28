package planning.method;

import java.util.UUID;

import planning.model.System;

public class Node {

	private String id;

	public String getId() {
		return this.id;
	}

	public Node(System system) {
		this(UUID.randomUUID().toString(), system);
	}

	Node(String id, System system) {
		this.id = id;
		this.system = system;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Node node = (Node) obj;
		return id.equals(node.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	private System system;

	public System getSystem() {
		return this.system;
	}
}

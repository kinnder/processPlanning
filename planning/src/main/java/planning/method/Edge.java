package planning.method;

import java.util.UUID;

import planning.model.Action;

public class Edge {

	private String id;

	public String getId() {
		return this.id;
	}

	public Edge(Action action) {
		this(UUID.randomUUID().toString(), action);
	}

	Edge(String id, Action action) {
		this.id = id;
		this.action = action;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Edge edge = (Edge) obj;
		return id.equals(edge.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	private Action action;

	public Action getAction() {
		return action;
	}
}

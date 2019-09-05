package planning.method;

import java.util.UUID;

import planning.model.SystemOperation;

public class Edge {

	private String id;

	public String getId() {
		return this.id;
	}

	public Edge(SystemOperation systemOperation) {
		this(UUID.randomUUID().toString(), systemOperation);
	}

	Edge(String id, SystemOperation systemOperation) {
		this.id = id;
		this.systemOperation = systemOperation;
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

	private SystemOperation systemOperation;

	public SystemOperation getSystemOperation() {
		return systemOperation;
	}
}

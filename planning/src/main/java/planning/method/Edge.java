package planning.method;

import java.util.UUID;

import planning.model.SystemOperation;

public class Edge {

	private String id;

	private String beginNodeId;

	private String endNodeId;

	public String getId() {
		return this.id;
	}

	public Edge(String beginNodeId, String endNodeId, SystemOperation systemOperation) {
		this(UUID.randomUUID().toString(), beginNodeId, endNodeId, systemOperation);
	}

	public Edge(String id, String beginNodeId, String endNodeId, SystemOperation systemOperation) {
		this.id = id;
		this.beginNodeId = beginNodeId;
		this.endNodeId = endNodeId;
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

	public String getBeginNodeId() {
		return beginNodeId;
	}

	public String getEndNodeId() {
		return endNodeId;
	}
}

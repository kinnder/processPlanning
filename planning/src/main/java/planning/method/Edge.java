package planning.method;

import java.util.UUID;

import planning.model.Element;
import planning.model.SystemVariant;

public class Edge {

	private String id;

	public String getId() {
		return this.id;
	}

	public Edge(Element element, SystemVariant systemVariant) {
		this(UUID.randomUUID().toString(), element, systemVariant);
	}

	Edge(String id, Element element, SystemVariant systemVariant) {
		this.id = id;
		this.element = element;
		this.systemVariant = systemVariant;
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

	private Element element;

	public Element getElement() {
		return this.element;
	}

	private SystemVariant systemVariant;

	public SystemVariant getSystemVariant() {
		return this.systemVariant;
	}
}

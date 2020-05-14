package planning.model;

import java.util.Objects;

public class Link implements Cloneable {

	private String objectId;

	private String name;

	public Link(String name, String objectId) {
		this.name = name;
		this.objectId = objectId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Link link = (Link) obj;

		return name.equals(link.name) && Objects.equals(objectId, link.objectId);
	}

	@Override
	public Link clone() throws CloneNotSupportedException {
		Link clone = (Link) super.clone();
		clone.name = name;
		clone.objectId = objectId;
		return clone;
	}

	public String getName() {
		return name;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public LinkTemplate createTemplate() {
		return new LinkTemplate(name, objectId);
	}
}

package planning.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Link implements Cloneable {

	private String objectId1;

	private String objectId2;

	private String name;

	public Link(String name, String objectId1, String objectId2) {
		this.name = name;
		this.objectId1 = objectId1;
		this.objectId2 = objectId2;
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

		return name.equals(link.name) && Objects.equals(objectId1, link.objectId1)
				&& Objects.equals(objectId2, link.objectId2);
	}

	@Override
	public Link clone() throws CloneNotSupportedException {
		Link clone = (Link) super.clone();
		clone.name = name;
		clone.objectId1 = objectId1;
		clone.objectId2 = objectId2;
		return clone;
	}

	public String getName() {
		return name;
	}

	public String getObjectId1() {
		return objectId1;
	}

	public void setObjectId1(String objectId1) {
		this.objectId1 = objectId1;
	}

	public String getObjectId2() {
		return objectId2;
	}

	public void setObjectId2(String objectId2) {
		this.objectId2 = objectId2;
	}

	public LinkTemplate createTemplate() {
		return new LinkTemplate(name, objectId1, objectId2);
	}

	public Set<String> getIds() {
		Set<String> linkIds = new HashSet<>();
		if (objectId1 != null) {
			linkIds.add(objectId1);
		}
		if (objectId2 != null) {
			linkIds.add(objectId2);
		}
		return linkIds;
	}
}

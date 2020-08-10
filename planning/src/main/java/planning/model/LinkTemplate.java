package planning.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class LinkTemplate implements Cloneable {

	private String objectId1;

	private String objectId2;

	private String name;

	public LinkTemplate(String name, String objectId1, String objectId2) {
		this.name = name;
		this.objectId1 = objectId1;
		this.objectId2 = objectId2;
	}

	public String getObjectId1() {
		return objectId1;
	}

	public String getObjectId2() {
		return objectId2;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj instanceof LinkTemplate) {
			LinkTemplate linkTemplate = (LinkTemplate) obj;
			return name.equals(linkTemplate.name) && Objects.equals(objectId1, linkTemplate.objectId1)
					&& Objects.equals(objectId2, linkTemplate.objectId2);
		}
		return false;
	}

	public boolean matches(Link link, IdsMatching matching) {
		return link.getName().equals(name) && Objects.equals(matching.get(objectId1), link.getObjectId1())
				&& Objects.equals(matching.get(objectId2), link.getObjectId2());
	}

	@Override
	public LinkTemplate clone() throws CloneNotSupportedException {
		LinkTemplate clone = (LinkTemplate) super.clone();
		clone.name = name;
		clone.objectId1 = objectId1;
		clone.objectId2 = objectId2;
		return clone;
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

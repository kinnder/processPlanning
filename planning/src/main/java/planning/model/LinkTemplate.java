package planning.model;

import java.util.Objects;

public class LinkTemplate {

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

	public boolean matches(Link link, IdsMatching matching) {
		return link.getName().equals(name) && Objects.equals(matching.get(objectId1), link.getObjectId1())
				&& Objects.equals(matching.get(objectId2), link.getObjectId2());
	}
}

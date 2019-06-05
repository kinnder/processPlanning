package planning.model;

public class Link {

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
		if (name.equals(link.name)) {
			if (objectId == null) {
				return link.objectId == null;
			}
			return objectId.equals(link.objectId);
		}
		return false;
	}

	@Override
	public Object clone() {
		return new Link(name, objectId);
	}

	public String getName() {
		return name;
	}

	public String getObjectId() {
		return objectId;
	}

	public boolean matches(Link template, IdsMatching matching) {
		if (name.equals(template.name)) {
			if (objectId == null) {
				return template.objectId == null;
			}
			return objectId.equals(matching.get(template.objectId));
		}
		return false;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
}

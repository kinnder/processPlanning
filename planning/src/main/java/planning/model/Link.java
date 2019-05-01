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
		return name.equals(link.name) && objectId.equals(link.objectId);
	}

	@Override
	public Object clone() {
		return new Link(name, objectId);
	}

	public String getName() {
		return name;
	}

	public boolean matches(Link template) {
		return name.equals(template.name);
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
}

package planning.model;

public class Link {

	private String objectId;

	private String name;

	private System system;

	public Link(String name, System system, String objectId) {
		this.name = name;
		this.objectId = objectId;
		this.system = system;
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
		return name.equals(link.name) && objectId.equals(link.objectId)
				&& system.getObjectById(objectId).equals(link.system.getObjectById(objectId));
	}

	@Override
	public Object clone() {
		return new Link(name, system, objectId);
	}

	public String getName() {
		return name;
	}

	public void setSystem(System system) {
		this.system = system;
	}

	public boolean matches(Link template) {
		return name.equals(template.name);
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
}

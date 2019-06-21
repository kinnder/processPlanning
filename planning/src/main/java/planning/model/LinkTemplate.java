package planning.model;

public class LinkTemplate {

	private String objectId;

	private String name;

	public LinkTemplate(String name, String objectId) {
		this.name = name;
		this.objectId = objectId;
	}

	public String getObjectId() {
		return objectId;
	}

	public String getName() {
		return name;
	}

	public boolean matches(Link link, IdsMatching matching) {
		if (link.getName().equals(name)) {
			if (link.getObjectId() == null) {
				return objectId == null;
			}
			return link.getObjectId().equals(matching.get(objectId));
		}
		return false;
	}
}

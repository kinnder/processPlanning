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

	public boolean matches(Link template, IdsMatching matching) {
		if (template.getName().equals(name)) {
			if (template.getObjectId() == null) {
				return objectId == null;
			}
			return template.getObjectId().equals(matching.get(objectId));
		}
		return false;
	}
}

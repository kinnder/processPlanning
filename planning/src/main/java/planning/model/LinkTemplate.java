package planning.model;

public class LinkTemplate {

	private String objectId;

	private String objectId_2;

	private String name;

	// TODO : добавить поддержку второго поля
	public LinkTemplate(String name, String objectId, String objectId_2) {
		this.name = name;
		this.objectId = objectId;
		this.objectId_2 = objectId_2;
	}

	public String getObjectId() {
		return objectId;
	}

	public String getObjectId_2() {
		return objectId_2;
	}

	public String getName() {
		return name;
	}

	public boolean matches(Link link, IdsMatching matching) {
		if (link.getName().equals(name)) {
			if (link.getObjectId1() == null) {
				return objectId == null;
			}
			return link.getObjectId1().equals(matching.get(objectId));
		}
		return false;
	}
}

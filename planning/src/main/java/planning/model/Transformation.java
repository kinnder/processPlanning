package planning.model;

public class Transformation {

	private String objectId;

	public Transformation(String objectId) {
		this.objectId = objectId;
	}

	public void applyTo(SystemVariant systemVariant) {
	}

	public String getObjectId() {
		return objectId;
	}
}

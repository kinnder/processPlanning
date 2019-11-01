package planning.model;

public class Transformation {

	private String objectId;

	public Transformation(String objectId) {
		this.objectId = objectId;
	}

	public void applyTo(SystemVariant systemVariant) {
		applyTo(systemVariant.getObjectByIdMatch(objectId), systemVariant);
	}

	protected void applyTo(SystemObject object, SystemVariant systemVariant) {
	}

	public String getObjectId() {
		return objectId;
	}
}

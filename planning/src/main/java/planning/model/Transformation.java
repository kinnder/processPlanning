package planning.model;

public class Transformation {

	private String id;

	public Transformation(String id) {
		this.id = id;
	}

	public void applyTo(SystemVariant systemVariant) {
	}

	public String getId() {
		return id;
	}
}

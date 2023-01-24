package planning.model;

public class Transformation {

	private String id;

	public Transformation() {
		this.id = "object-id";
	}

	public Transformation(String id) {
		this.id = id;
	}

	public void applyTo(SystemVariant systemVariant) {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "transformation";
	}
}

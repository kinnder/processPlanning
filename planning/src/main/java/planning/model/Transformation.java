package planning.model;

import java.util.Map;

public class Transformation {

	protected String objectId;

	public Transformation(String objectId) {
		this.objectId = objectId;
	}

	public void applyTo(System system, Map<String, String> idsMatching) {
	}
}

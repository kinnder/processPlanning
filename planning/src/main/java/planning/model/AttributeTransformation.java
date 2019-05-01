package planning.model;

import java.util.Map;

public class AttributeTransformation extends Transformation {

	private String attributeName;

	private Object attributeValue;

	public AttributeTransformation(String objectId, String attributeName, Object attributeValue) {
		super(objectId);
		this.attributeName = attributeName;
		this.attributeValue = attributeValue;
	}

	@Override
	public void applyTo(System system, Map<String, String> idsMatching) {
		String concreateObjectId = idsMatching.get(objectId);
		SystemObject object = system.getObjectById(concreateObjectId);

		Attribute attribute = object.getAttribute(attributeName);
		attribute.setValue(attributeValue);
	}
}

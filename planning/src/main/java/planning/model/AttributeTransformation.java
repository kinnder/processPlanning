package planning.model;

public class AttributeTransformation extends Transformation {

	private String attributeName;

	private Object attributeValue;

	public AttributeTransformation(String objectId, String attributeName, Object attributeValue) {
		super(objectId);
		this.attributeName = attributeName;
		this.attributeValue = attributeValue;
	}

	@Override
	public void applyTo(SystemVariant systemVariant) {
		SystemObject object = systemVariant.getObjectByIdMatch(objectId);

		Attribute attribute = object.getAttribute(attributeName);
		attribute.setValue(attributeValue);
	}
}

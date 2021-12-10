package planning.model;

public class AttributeTransformation extends Transformation {

	private String attributeName;

	private Object attributeValue;

	public AttributeTransformation(String id, String attributeName, Object attributeValue) {
		super(id);
		this.attributeName = attributeName;
		this.attributeValue = attributeValue;
	}

	@Override
	public void applyTo(SystemVariant systemVariant) {
		SystemObject object = systemVariant.getObjectByIdMatch(getId());
		Attribute attribute = object.getAttribute(attributeName);
		attribute.setValue(attributeValue);
	}

	public String getAttributeName() {
		return attributeName;
	}

	public Object getAttributeValue() {
		return attributeValue;
	}
}

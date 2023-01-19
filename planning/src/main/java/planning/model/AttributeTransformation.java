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
		final SystemObject object = systemVariant.getObjectByIdMatch(getId());
		final Attribute attribute = object.getAttribute(attributeName);
		attribute.setValue(attributeValue);
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public Object getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	@Override
	public String toString() {
		return "attribute-transformation";
	}
}

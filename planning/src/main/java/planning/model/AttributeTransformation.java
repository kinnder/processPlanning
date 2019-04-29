package planning.model;

public class AttributeTransformation extends Transformation {

	private String attributeName;

	private Object attributeValue;

	public AttributeTransformation(String objectId, String attributeName, Object attributeValue) {
		super(objectId);
		this.attributeName = attributeName;
		this.attributeValue = attributeValue;
	}
}

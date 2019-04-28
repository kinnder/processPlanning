package planning.model;

public class AttributeTransformation extends Transformation {

	private String attributeName;

	private boolean attributeValue;

	public AttributeTransformation(String thingId, String attributeName, boolean attributeValue) {
		super(thingId);
		this.attributeName = attributeName;
		this.attributeValue = attributeValue;
	}

}

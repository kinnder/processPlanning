package planning.model;

public class LinkTransformation extends Transformation {

	private String linkName;

	private String linkValue;

	public LinkTransformation(String thingId, String linkName, String linkValue) {
		super(thingId);
		this.linkName = linkName;
		this.linkValue = linkValue;
	}
}

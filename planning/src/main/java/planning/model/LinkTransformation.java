package planning.model;

public class LinkTransformation extends Transformation {

	private String linkName;

	private String linkValue;

	public LinkTransformation(String objectId, String linkName, String linkValue) {
		super(objectId);
		this.linkName = linkName;
		this.linkValue = linkValue;
	}
}

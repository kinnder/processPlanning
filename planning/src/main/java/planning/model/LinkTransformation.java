package planning.model;

public class LinkTransformation extends Transformation {

	private String linkName;

	private String linkNewValue;

	private String linkOldValue;

	public LinkTransformation(String objectId, String linkName, String linkOldValue, String linkNewValue) {
		super(objectId);
		this.linkName = linkName;
		this.linkOldValue = linkOldValue;
		this.linkNewValue = linkNewValue;
	}

	@Override
	protected void applyTo(SystemObject object, SystemVariant systemVariant) {
		String linkNewValueActual = systemVariant.getObjectIdByIdMatch(linkNewValue);
		String linkOldValueActual = systemVariant.getObjectIdByIdMatch(linkOldValue);

		Link link = object.getLink(linkName, linkOldValueActual);
		link.setObjectId(linkNewValueActual);
	}
}

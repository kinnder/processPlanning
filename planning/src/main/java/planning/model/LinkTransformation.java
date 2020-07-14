package planning.model;

public class LinkTransformation extends Transformation {

	private String linkName;

	private String linkObjectId2New;

	private String linkObjectId2Old;

	public String getLinkName() {
		return linkName;
	}

	public String getLinkObjectId2New() {
		return linkObjectId2New;
	}

	public String getLinkObjectId2Old() {
		return linkObjectId2Old;
	}

	public LinkTransformation(String objectId, String linkName, String linkObjectId2Old, String linkObjectId2New) {
		super(objectId);
		this.linkName = linkName;
		this.linkObjectId2Old = linkObjectId2Old;
		this.linkObjectId2New = linkObjectId2New;
	}

	@Override
	public void applyTo(SystemVariant systemVariant) {
		SystemObject object = systemVariant.getObjectByIdMatch(getObjectId());
		String objectIdActual = object.getId();
		String linkObjectId2NewActual = systemVariant.getObjectIdByIdMatch(linkObjectId2New);
		String linkObjectId2OldActual = systemVariant.getObjectIdByIdMatch(linkObjectId2Old);

		Link link = systemVariant.getSystem().getLink(linkName, objectIdActual, linkObjectId2OldActual);
		link.setObjectId2(linkObjectId2NewActual);
	}
}

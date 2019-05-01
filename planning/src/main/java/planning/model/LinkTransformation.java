package planning.model;

import java.util.Map;

public class LinkTransformation extends Transformation {

	private String linkName;

	private String linkValue;

	public LinkTransformation(String objectId, String linkName, String linkValue) {
		super(objectId);
		this.linkName = linkName;
		this.linkValue = linkValue;
	}

	@Override
	public void applyTo(System system, Map<String, String> idsMatching) {
		String concreateObjectId = idsMatching.get(objectId);
		String linkObjectId = idsMatching.get(linkValue);
		SystemObject object = system.getObjectById(concreateObjectId);

		Link link = object.getLink(linkName);
		link.setObjectId(linkObjectId);
	}
}

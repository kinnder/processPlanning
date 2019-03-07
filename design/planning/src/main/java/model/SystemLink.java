package model;

import utility.CollectionItem;

/** Связь */
public class SystemLink implements CollectionItem {

	/** Объект, участвующий в связи */
	public SystemObject object_1;

	/** Объект, участвующий в связи */
	public SystemObject object_2;

	/** Название связи */
	public String linkName;

	public SystemLink(SystemObject object_1, SystemObject object_2) {
		this.object_1 = object_1;
		this.object_2 = object_2;
	}

	public SystemLink(SystemObject object_1, SystemObject object_2, String linkName) {
		this(object_1, object_2);
		this.linkName = linkName;
	}

	public SystemLinkInformation getLinkInformation() {
		SystemLinkInformation linkInformation = new SystemLinkInformation();
		linkInformation.object_1_id = object_1.id;
		linkInformation.object_2_id = object_2.id;
		linkInformation.name = linkName;
		return linkInformation;
	}

	@Override
	public Object clone() {
		return new SystemLink(object_1, object_2, linkName);
	}

	@Override
	public boolean matches(Object pattern) {
		SystemLink patternObject = (SystemLink) pattern;
		if (this == patternObject) {
			return true;
		}
		if (object_1 == null && patternObject.object_1 != null) {
			return false;
		}
		if (object_2 == null && patternObject.object_2 != null) {
			return false;
		}
		return ((object_1 != null) ? object_1.matches(patternObject.object_1) : true)
				&& ((object_2 != null) ? object_2.matches(patternObject.object_2) : true)
				&& (linkName == patternObject.linkName);
	}

	@Override
	public int compareTo(CollectionItem o) {
		// TODO Auto-generated method stub
		return 0;
	}
}

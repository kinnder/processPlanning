package model;

public class SystemModificationLinkChange extends SystemModification {

	public static enum LinkChangeType {
		connect, disconnect
	}

	public LinkChangeType linkChangeType;

	public SystemObject object_1;

	public SystemObject object_2;

	public String linkName;
}

package planning.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SystemObject {

	private Map<String, Attribute> attributes = new HashMap<String, Attribute>();

	private Map<String, Link> links = new HashMap<String, Link>();

	private String objectId;

	private String name;

	public SystemObject(String name) {
		this(name, UUID.randomUUID().toString());
	}

	public SystemObject(String name, String objectId) {
		this.name = name;
		this.objectId = objectId;
	}

	public String getObjectId() {
		return objectId;
	}

	public void addAttribute(Attribute attribute) {
		attributes.put(attribute.getName(), attribute);
	}

	public boolean matches(SystemObject template, System system) {
		// TODO Auto-generated method stub
		return false;
	}

	public void addLink(Link link) {
		links.put(link.getName(), link);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		SystemObject systemObject = (SystemObject) obj;
		if (!name.equals(systemObject.name)) {
			return false;
		}
		if (attributes.size() != systemObject.attributes.size()) {
			return false;
		}
		if (links.size() != systemObject.links.size()) {
			return false;
		}
		for (Attribute thisAttribute : attributes.values()) {
			Attribute thatAttribute = systemObject.attributes.get(thisAttribute.getName());
			if (!thisAttribute.equals(thatAttribute)) {
				return false;
			}
		}
		for (Link thisLink : links.values()) {
			Link thatLink = systemObject.links.get(thisLink.getName());
			if (!thisLink.equals(thatLink)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public Object clone() {
		SystemObject cloned = new SystemObject(name, objectId);
		for (Attribute attribute : attributes.values()) {
			cloned.addAttribute((Attribute) attribute.clone());
		}
		for (Link link : links.values()) {
			cloned.addLink((Link) link.clone());
		}
		return cloned;
	}

	public void updateLinks(System system) {
		for (Link link : links.values()) {
			link.setSystem(system);
		}
	}
}

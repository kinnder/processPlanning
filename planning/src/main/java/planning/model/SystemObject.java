package planning.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class SystemObject implements Cloneable {

	private Map<String, Attribute> attributes = new HashMap<>();

	private Map<String, Link> links = new HashMap<>();

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

	public boolean matchesAttributes(SystemObject templateObject) {
		List<Attribute> notMatchedAttributeTemplates = new ArrayList<>(templateObject.attributes.values());
		for (Attribute attribute : attributes.values()) {
			for (Attribute attributeTemplate : notMatchedAttributeTemplates) {
				if (attribute.matches(attributeTemplate)) {
					notMatchedAttributeTemplates.remove(attributeTemplate);
					break;
				}
			}
		}
		return notMatchedAttributeTemplates.isEmpty();
	}

	public boolean matchesLinks(SystemObject template, IdsMatching matching) {
		List<Link> notMatchedLinkTemplates = new ArrayList<>(template.links.values());
		for (Link link : links.values()) {
			for (Link linkTemplate : notMatchedLinkTemplates) {
				if (link.matches(linkTemplate, matching)) {
					notMatchedLinkTemplates.remove(linkTemplate);
					break;
				}
			}
		}
		return notMatchedLinkTemplates.isEmpty();
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
	public SystemObject clone() {
		SystemObject cloned = new SystemObject(name, objectId);
		for (Attribute attribute : attributes.values()) {
			cloned.addAttribute((Attribute) attribute.clone());
		}
		for (Link link : links.values()) {
			cloned.addLink((Link) link.clone());
		}
		return cloned;
	}

	public Attribute getAttribute(String attributeName) {
		return attributes.get(attributeName);
	}

	public Link getLink(String linkName) {
		return links.get(linkName);
	}

	public void removeLink(String linkName) {
		links.remove(linkName);
	}

	public Set<String> getObjectIds() {
		Set<String> objectIds = new HashSet<>();
		objectIds.add(objectId);
		for (Link link : links.values()) {
			String linkValue = link.getObjectId();
			if (linkValue != null) {
				objectIds.add(linkValue);
			}
		}
		return objectIds;
	}
}

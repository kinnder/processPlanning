package planning.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
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

	public void addLink(Link link) {
		links.put(link.getName(), link);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof SystemObject)) {
			return false;
		}
		SystemObject systemObject = (SystemObject) obj;
		return Objects.equals(name, systemObject.name) && equalsAttributes(systemObject) && equalsLinks(systemObject);
	}

	private boolean equalsAttributes(SystemObject systemObject) {
		return attributes.entrySet().equals(systemObject.attributes.entrySet());
	}

	private boolean equalsLinks(SystemObject systemObject) {
		return links.entrySet().equals(systemObject.links.entrySet());
	}

	@Override
	public SystemObject clone() {
		SystemObject cloned = new SystemObject(name, objectId);
		for (Attribute attribute : attributes.values()) {
			cloned.addAttribute(attribute.clone());
		}
		for (Link link : links.values()) {
			cloned.addLink(link.clone());
		}
		return cloned;
	}

	public Attribute getAttribute(String attributeName) {
		return attributes.get(attributeName);
	}

	public Link getLink(String linkName) {
		return links.get(linkName);
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

	public SystemObjectTemplate createTemplate() {
		SystemObjectTemplate template = new SystemObjectTemplate(objectId);
		for (Link link : links.values()) {
			template.addLink(link.createTemplate());
		}
		for (Attribute attribute : attributes.values()) {
			template.addAttribute(attribute.createTemplate());
		}
		return template;
	}

	public Collection<Attribute> getAttributes() {
		return Collections.unmodifiableCollection(attributes.values());
	}

	public Collection<Link> getLinks() {
		return Collections.unmodifiableCollection(links.values());
	}
}

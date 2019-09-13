package planning.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class SystemObject implements Cloneable {

	private Map<String, Attribute> attributes;

	private List<Link> links;

	private String id;

	private String name;

	public SystemObject(String name) {
		this(name, UUID.randomUUID().toString());
	}

	public SystemObject(String name, String id) {
		this.name = name;
		this.id = id;
		this.attributes = new HashMap<>();
		this.links = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public void addAttribute(Attribute attribute) {
		attributes.put(attribute.getName(), attribute);
	}

	public void addLink(Link link) {
		links.add(link);
	}

	public void addAttribute(String attributeName, Object attributeValue) {
		addAttribute(new Attribute(attributeName, attributeValue));
	}

	public void addLink(String linkName, String linkValue) {
		addLink(new Link(linkName, linkValue));
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
		return links.equals(systemObject.links);
	}

	@Override
	public SystemObject clone() throws CloneNotSupportedException {
		SystemObject clone = (SystemObject) super.clone();
		clone.name = name;
		clone.id = id;
		clone.attributes = new HashMap<>();
		for (Attribute attribute : attributes.values()) {
			clone.addAttribute(attribute.clone());
		}
		clone.links = new ArrayList<>();
		for (Link link : links) {
			clone.addLink(link.clone());
		}
		return clone;
	}

	public Attribute getAttribute(String attributeName) {
		return attributes.get(attributeName);
	}

	public Link getLink(String linkName, String linkValue) {
		Link template = new Link(linkName, linkValue);
		for (Link link : links) {
			if (link.equals(template)) {
				return link;
			}
		}
		return null;
	}

	public Set<String> getIds() {
		Set<String> objectIds = new HashSet<>();
		objectIds.add(id);
		for (Link link : links) {
			String linkValue = link.getObjectId();
			if (linkValue != null) {
				objectIds.add(linkValue);
			}
		}
		return objectIds;
	}

	public SystemObjectTemplate createTemplate() {
		SystemObjectTemplate template = new SystemObjectTemplate(id);
		for (Link link : links) {
			template.addLinkTemplate(link.createTemplate());
		}
		for (Attribute attribute : attributes.values()) {
			template.addAttributeTemplate(attribute.createTemplate());
		}
		return template;
	}

	public Collection<Attribute> getAttributes() {
		return Collections.unmodifiableCollection(attributes.values());
	}

	public Collection<Link> getLinks() {
		return Collections.unmodifiableCollection(links);
	}

	public String getName() {
		return this.name;
	}
}

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

	private String id;

	private String name;

	public SystemObject() {
		this.id = UUID.randomUUID().toString();
		this.name = "object-" + this.id;
	}

	public SystemObject(String name) {
		this(name, UUID.randomUUID().toString());
	}

	public SystemObject(String name, String id) {
		this.name = name;
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String objectId) {
		this.id = objectId;
	}

	public void addAttribute(Attribute attribute) {
		attributes.put(attribute.getName(), attribute);
	}

	public void addAttribute(String attributeName, Object attributeValue) {
		addAttribute(new Attribute(attributeName, attributeValue));
	}

	public void removeAttribute(Attribute attribute) {
		attributes.remove(attribute.getName());
	}

	public void setAttribute(String attributeName, Object attributeValue) {
		attributes.get(attributeName).setValue(attributeValue);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj instanceof SystemObject) {
			final SystemObject systemObject = (SystemObject) obj;
			return Objects.equals(name, systemObject.name) && equalsAttributes(systemObject);
		}
		return false;
	}

	private boolean equalsAttributes(SystemObject systemObject) {
		return attributes.entrySet().equals(systemObject.attributes.entrySet());
	}

	@Override
	public SystemObject clone() throws CloneNotSupportedException {
		final SystemObject clone = (SystemObject) super.clone();
		clone.name = name;
		clone.id = id;
		clone.attributes = new HashMap<>();
		for (Attribute attribute : attributes.values()) {
			clone.addAttribute(attribute.clone());
		}
		return clone;
	}

	public Attribute getAttribute(String attributeName) {
		return attributes.get(attributeName);
	}

	public Set<String> getIds() {
		final Set<String> objectIds = new HashSet<>();
		objectIds.add(id);
		return objectIds;
	}

	public SystemObjectTemplate createTemplate() {
		final SystemObjectTemplate template = new SystemObjectTemplate(id);
		for (Attribute attribute : attributes.values()) {
			template.addAttributeTemplate(attribute.createTemplate());
		}
		return template;
	}

	public Collection<Attribute> getAttributes() {
		return Collections.unmodifiableCollection(attributes.values());
	}

	public String getName() {
		return this.name;
	}

	public void setName(String objectName) {
		this.name = objectName;
	}

	@Override
	public String toString() {
		return name;
	}
}

package planning.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SystemObjectTemplate {

	private Map<String, AttributeTemplate> attributes = new HashMap<>();

	private Map<String, LinkTemplate> links = new HashMap<>();

	private String objectId;

	public String getId() {
		return objectId;
	}

	public Set<String> getIds() {
		Set<String> objectIds = new HashSet<>();
		objectIds.add(objectId);
		for (LinkTemplate link : links.values()) {
			String linkValue = link.getObjectId1();
			if (linkValue != null) {
				objectIds.add(linkValue);
			}
		}
		return objectIds;
	}

	public SystemObjectTemplate(String objectId) {
		this.objectId = objectId;
	}

	public void addAttributeTemplate(AttributeTemplate attribute) {
		attributes.put(attribute.getName(), attribute);
	}

	@Deprecated
	public void addLinkTemplate(LinkTemplate link) {
		links.put(link.getName(), link);
	}

	@Deprecated
	public void addLinkTemplate(String linkTemplateName, String linkTemplateValue) {
		addLinkTemplate(new LinkTemplate(linkTemplateName, linkTemplateValue, null));
	}

	public boolean matchesAttributes(SystemObject object) {
		List<AttributeTemplate> notMatchedAttributeTemplates = new ArrayList<>(attributes.values());
		for (Attribute attribute : object.getAttributes()) {
			for (AttributeTemplate attributeTemplate : notMatchedAttributeTemplates) {
				if (attributeTemplate.matches(attribute)) {
					notMatchedAttributeTemplates.remove(attributeTemplate);
					break;
				}
			}
		}
		return notMatchedAttributeTemplates.isEmpty();
	}

	public boolean matchesLinks(SystemObject object, IdsMatching matching) {
		List<LinkTemplate> notMatchedLinkTemplates = new ArrayList<>(links.values());
		for (Link link : object.getLinks()) {
			for (LinkTemplate linkTemplate : notMatchedLinkTemplates) {
				if (linkTemplate.matches(link, matching)) {
					notMatchedLinkTemplates.remove(linkTemplate);
					break;
				}
			}
		}
		return notMatchedLinkTemplates.isEmpty();
	}

	public Collection<AttributeTemplate> getAttributeTemplates() {
		return Collections.unmodifiableCollection(attributes.values());
	}

	public Collection<LinkTemplate> getLinkTemplates() {
		return Collections.unmodifiableCollection(links.values());
	}
}

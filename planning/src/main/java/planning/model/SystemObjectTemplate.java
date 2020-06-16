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

	private Map<String, AttributeTemplate> attributeTemplates = new HashMap<>();

	private String objectId;

	public String getId() {
		return objectId;
	}

	public Set<String> getIds() {
		Set<String> objectIds = new HashSet<>();
		objectIds.add(objectId);
		return objectIds;
	}

	public SystemObjectTemplate(String objectId) {
		this.objectId = objectId;
	}

	public void addAttributeTemplate(AttributeTemplate attributeTemplate) {
		attributeTemplates.put(attributeTemplate.getName(), attributeTemplate);
	}

	public boolean matchesAttributes(SystemObject object) {
		List<AttributeTemplate> notMatchedAttributeTemplates = new ArrayList<>(attributeTemplates.values());
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

	public Collection<AttributeTemplate> getAttributeTemplates() {
		return Collections.unmodifiableCollection(attributeTemplates.values());
	}
}

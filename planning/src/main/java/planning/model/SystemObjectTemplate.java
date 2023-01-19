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

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Set<String> getIds() {
		final Set<String> ids = new HashSet<>();
		ids.add(id);
		return ids;
	}

	public SystemObjectTemplate(String id) {
		this.id = id;
	}

	public void addAttributeTemplate(AttributeTemplate attributeTemplate) {
		attributeTemplates.put(attributeTemplate.getName(), attributeTemplate);
	}

	public boolean matchesAttributes(SystemObject object) {
		final List<AttributeTemplate> notMatchedAttributeTemplates = new ArrayList<>(attributeTemplates.values());
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

	@Override
	public String toString() {
		return "Object Template";
	}

	public void removeAttributeTemplate(AttributeTemplate attributeTemplate) {
		attributeTemplates.remove(attributeTemplate.getName());
	}
}

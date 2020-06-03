package planning.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class System implements Cloneable {

	private List<SystemObject> objects = new ArrayList<>();

	public Collection<SystemObject> getObjects() {
		return Collections.unmodifiableCollection(objects);
	}

	public void addObject(SystemObject object) {
		objects.add(object);
	}

	public SystemObject addNewObject(String name) {
		SystemObject object = new SystemObject(name);
		addObject(object);
		return object;

	}

	public Set<String> getIds() {
		Set<String> systemIds = new HashSet<>();
		for (SystemObject object : objects) {
			Set<String> objectIds = object.getIds();
			systemIds.addAll(objectIds);
		}
		return Collections.unmodifiableSet(systemIds);
	}

	@Override
	public System clone() throws CloneNotSupportedException {
		System clone = (System) super.clone();
		clone.objects = new ArrayList<>();
		for (SystemObject object : objects) {
			clone.addObject(object.clone());
		}
		return clone;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof System)) {
			return false;
		}
		System system = (System) obj;
		if (objects.size() != system.objects.size()) {
			return false;
		}

		List<SystemObject> notEqualObjects = new ArrayList<>(objects);
		for (SystemObject object : system.objects) {
			for (SystemObject notEqualObject : notEqualObjects) {
				if (object.equals(notEqualObject)) {
					notEqualObjects.remove(notEqualObject);
					break;
				}
			}
		}

		return notEqualObjects.isEmpty();
	}

	public SystemObject getObjectById(String id) {
		for (SystemObject object : objects) {
			if (object.getId().equals(id)) {
				return object;
			}
		}
		return null;
	}

	public boolean contains(System system) {
		IdsMatching[] idsMatching = system.createTemplate().matchIds(this);
		for (IdsMatching variant : idsMatching) {
			if (!variant.areKeysAndValuesTheSame()) {
				return false;
			}
		}
		return idsMatching.length > 0;
	}

	public SystemTemplate createTemplate() {
		SystemTemplate template = new SystemTemplate();
		for (SystemObject object : objects) {
			template.addObjectTemplate(object.createTemplate());
		}
		return template;
	}

	public SystemObject getObjectByName(String name) {
		for (SystemObject object : objects) {
			if (object.getName().equals(name)) {
				return object;
			}
		}
		return null;
	}

	private List<Link> links = new ArrayList<>();

	public void addLink(SystemObject object1, String linkName, SystemObject object2) {
		addLink(object1, linkName, linkName, object2);
	}

	public void addLink(SystemObject object1, String linkName_o1_o2, String linkName_o2_o1, SystemObject object2) {
		String object1Id = (object1 == null) ? null : object1.getId();
		String object2Id = (object2 == null) ? null : object2.getId();

		if (object1Id != null) {
			object1.addLink(linkName_o1_o2, object2Id);
			links.add(new Link(linkName_o1_o2, object1Id, object2Id));
		}
		if (object2Id != null) {
			object2.addLink(linkName_o2_o1, object1Id);
			links.add(new Link(linkName_o2_o1, object2Id, object1Id));
		}
	}

	public Collection<Link> getLinks() {
		return Collections.unmodifiableCollection(links);
	}

	public void addLink(Link link) {
		links.add(link);
	}
}

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

	public Set<String> getSystemIds() {
		Set<String> systemIds = new HashSet<>();
		for (SystemObject object : objects) {
			Set<String> objectIds = object.getObjectIds();
			systemIds.addAll(objectIds);
		}
		return Collections.unmodifiableSet(systemIds);
	}

	@Override
	public System clone() {
		System system = new System();
		for (SystemObject object : objects) {
			SystemObject cloned = object.clone();
			system.addObject(cloned);
		}
		return system;
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

		List<SystemObject> notEqualThings = new ArrayList<>(objects);
		for (SystemObject thing : system.objects) {
			for (SystemObject notEqualThing : notEqualThings) {
				if (thing.equals(notEqualThing)) {
					notEqualThings.remove(notEqualThing);
					break;
				}
			}
		}

		return notEqualThings.isEmpty();
	}

	public SystemObject getObjectById(String objectId) {
		for (SystemObject object : objects) {
			if (object.getObjectId().equals(objectId)) {
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
			template.addObject(object.createTemplate());
		}
		return template;
	}
}

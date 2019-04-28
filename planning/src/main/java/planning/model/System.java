package planning.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class System {

	private List<SystemObject> objects = new ArrayList<SystemObject>();

	public void addObject(SystemObject object) {
		objects.add(object);
	}

	public Map<String, String> matchIds(System template) {
		/*
		 * Map<String, String> matchings = new HashMap<String, String>();
		 *
		 * List<Thing> notMatchedThings = new ArrayList<Thing>(things); for (Thing
		 * templateThing : template.things) { for (Thing notMatchedThing :
		 * notMatchedThings) { if (notMatchedThing.matches(templateThing, this)) { } } }
		 *
		 * return matchings;
		 */
		return null;
	}

	public System clone() {
		System system = new System();
		for (SystemObject object : objects) {
			SystemObject cloned = (SystemObject) object.clone();
			cloned.updateLinks(system);
			system.addObject(cloned);
		}
		return system;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		System system = (System) obj;
		if (objects.size() != system.objects.size()) {
			return false;
		}

		List<SystemObject> notEqualThings = new ArrayList<SystemObject>(objects);
		for (SystemObject thing : system.objects) {
			for (SystemObject notEqualThing : notEqualThings) {
				if (thing.equals(notEqualThing)) {
					notEqualThings.remove(notEqualThing);
					break;
				}
			}
		}

		if (notEqualThings.size() != 0) {
			return false;
		}

		return true;
	}

	public SystemObject getObjectById(String objectId) {
		for (SystemObject object : objects) {
			if (object.getObjectId().equals(objectId)) {
				return object;
			}
		}
		return null;
	}
}

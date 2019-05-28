package planning.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class System {

	// TODO : replace List with Set
	private List<SystemObject> objects = new ArrayList<SystemObject>();

	public void addObject(SystemObject object) {
		objects.add(object);
	}

	public System() {
	}

	System(IdsMatchingManager idsMatchingManager) {
		this.idsMatchingsManager = idsMatchingManager;
	}

	private IdsMatchingManager idsMatchingsManager = new IdsMatchingManager();

	public SystemVariant[] matchIds(System template) {
		idsMatchingsManager.prepareMatchingsCandidates(template.getSystemIds(), getSystemIds());

		for (SystemObject object : objects) {
			for (SystemObject templateObject : template.objects) {
				if (!object.matchesAttributes(templateObject)) {
					idsMatchingsManager.removeMatchingsCandidate(templateObject.getObjectId(), object.getObjectId());
				}
			}
		}

		idsMatchingsManager.generateMatchingsFromCandidates();

		while (idsMatchingsManager.haveUncheckedMatching()) {
			IdsMatching matching = idsMatchingsManager.getUncheckedMatching();
			int templateMatchings = 0;
			for (SystemObject object : objects) {
				for (SystemObject templateObject : template.objects) {
					if (object.matchesAttributes(templateObject) && object.matchesLinks(templateObject, matching)) {
						templateMatchings++;
					}
				}
			}
			if (templateMatchings != template.objects.size()) {
				idsMatchingsManager.removeMatching(matching);
			}
		}

		int amount = idsMatchingsManager.getMatchingsAmount();
		SystemVariant[] systemVariants = new SystemVariant[amount];
		for (int i = 0; i < amount; i++) {
			// TODO : clone should be removed()
			systemVariants[i] = new SystemVariant(clone(), idsMatchingsManager.getMatching(i));
		}

		return systemVariants;
	}

	public Set<String> getSystemIds() {
		Set<String> systemIds = new HashSet<String>();
		for (SystemObject object : objects) {
			Set<String> objectIds = object.getObjectIds();
			systemIds.addAll(objectIds);
		}
		return systemIds;
	}

	// TODO : override super-method
	public System clone() {
		System system = new System();
		for (SystemObject object : objects) {
			SystemObject cloned = (SystemObject) object.clone();
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

	public boolean partially_equals(System system) {
		SystemVariant[] variants = matchIds(system);
		if (variants.length > 0) {
			return true;
		}
		return false;
	}
}

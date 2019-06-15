package planning.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SystemTemplate {

	private List<SystemObjectTemplate> objects = new ArrayList<>();

	public Set<String> getIds() {
		Set<String> systemIds = new HashSet<>();
		for (SystemObjectTemplate object : objects) {
			Set<String> objectIds = object.getIds();
			systemIds.addAll(objectIds);
		}
		return systemIds;
	}

	public void addObjectTemplate(SystemObjectTemplate object) {
		objects.add(object);
	}

	public SystemTemplate() {
	}

	SystemTemplate(IdsMatchingManager idsMatchingManager) {
		this.idsMatchingsManager = idsMatchingManager;
	}

	private IdsMatchingManager idsMatchingsManager = new IdsMatchingManager();

	public IdsMatching[] matchIds(System system) {
		idsMatchingsManager.prepareMatchingsCandidates(getIds(), system.getIds());

		for (SystemObject object : system.getObjects()) {
			for (SystemObjectTemplate templateObject : objects) {
				if (!templateObject.matchesAttributes(object)) {
					idsMatchingsManager.removeMatchingsCandidate(templateObject.getId(), object.getId());
				}
			}
		}

		idsMatchingsManager.generateMatchingsFromCandidates();

		while (idsMatchingsManager.haveUncheckedMatching()) {
			IdsMatching matching = idsMatchingsManager.getUncheckedMatching();
			int templateMatchings = 0;
			for (SystemObject object : system.getObjects()) {
				for (SystemObjectTemplate templateObject : objects) {
					if (templateObject.matchesAttributes(object) && templateObject.matchesLinks(object, matching)) {
						templateMatchings++;
					}
				}
			}
			if (templateMatchings != objects.size()) {
				idsMatchingsManager.removeMatching(matching);
			}
		}

		return idsMatchingsManager.getIdsMatchings();
	}

	public Collection<SystemObjectTemplate> getObjects() {
		return Collections.unmodifiableCollection(objects);
	}
}

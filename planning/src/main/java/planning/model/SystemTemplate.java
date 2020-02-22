package planning.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SystemTemplate {

	private List<SystemObjectTemplate> objects;

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
		this(new IdsMatchingManager());
	}

	SystemTemplate(IdsMatchingManager idsMatchingManager) {
		this.idsMatchingsManager = idsMatchingManager;
		this.objects = new ArrayList<>();
	}

	private IdsMatchingManager idsMatchingsManager;

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
			List<SystemObjectTemplate> objectTemplatesToMatch = new ArrayList<>(objects);

			for (SystemObject object : system.getObjects()) {
				for (SystemObjectTemplate objectTemplate : objectTemplatesToMatch) {
					if (objectTemplate.matchesAttributes(object) && objectTemplate.matchesLinks(object, matching)) {
						objectTemplatesToMatch.remove(objectTemplate);
						break;
					}
				}
			}
			if (!objectTemplatesToMatch.isEmpty()) {
				idsMatchingsManager.removeMatching(matching);
			}
		}

		return idsMatchingsManager.getIdsMatchings();
	}

	public Collection<SystemObjectTemplate> getObjectTemplates() {
		return Collections.unmodifiableCollection(objects);
	}
}

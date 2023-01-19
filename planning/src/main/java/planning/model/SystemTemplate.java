package planning.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SystemTemplate {

	private List<SystemObjectTemplate> objectTemplates;

	public Set<String> getIds() {
		final Set<String> systemIds = new HashSet<>();
		for (SystemObjectTemplate objectTemplate : objectTemplates) {
			final Set<String> objectIds = objectTemplate.getIds();
			systemIds.addAll(objectIds);
		}
		for (LinkTemplate linkTemplate : linkTemplates) {
			final Set<String> linkTemplateIds = linkTemplate.getIds();
			systemIds.addAll(linkTemplateIds);
		}
		return systemIds;
	}

	public void addObjectTemplate(SystemObjectTemplate object) {
		objectTemplates.add(object);
	}

	public SystemTemplate() {
		this(new IdsMatchingManager());
	}

	SystemTemplate(IdsMatchingManager idsMatchingManager) {
		this.idsMatchingsManager = idsMatchingManager;
		this.objectTemplates = new ArrayList<>();
	}

	private IdsMatchingManager idsMatchingsManager;

	public IdsMatching[] matchIds(System system) {
		idsMatchingsManager.prepareMatchingsCandidates(getIds(), system.getIds());

		for (SystemObject object : system.getObjects()) {
			for (SystemObjectTemplate templateObject : objectTemplates) {
				if (!templateObject.matchesAttributes(object)) {
					idsMatchingsManager.removeMatchingsCandidate(templateObject.getId(), object.getId());
				}
			}
		}

		idsMatchingsManager.generateMatchingsFromCandidates();

		while (idsMatchingsManager.haveUncheckedMatching()) {
			final IdsMatching matching = idsMatchingsManager.getUncheckedMatching();

			if (!matchesLinks(system, matching)) {
				idsMatchingsManager.removeMatching(matching);
				continue;
			}

			final List<SystemObjectTemplate> objectTemplatesToMatch = new ArrayList<>(objectTemplates);

			for (SystemObject object : system.getObjects()) {
				for (SystemObjectTemplate objectTemplate : objectTemplatesToMatch) {
					if (objectTemplate.matchesAttributes(object)) {
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

	public boolean matchesLinks(System system, IdsMatching matching) {
		final List<LinkTemplate> notMatchedLinkTemplates = new ArrayList<>(linkTemplates);
		for (Link link : system.getLinks()) {
			for (LinkTemplate linkTemplate : notMatchedLinkTemplates) {
				if (linkTemplate.matches(link, matching)) {
					notMatchedLinkTemplates.remove(linkTemplate);
					break;
				}
			}
		}
		return notMatchedLinkTemplates.isEmpty();
	}

	public Collection<SystemObjectTemplate> getObjectTemplates() {
		return Collections.unmodifiableCollection(objectTemplates);
	}

	private List<LinkTemplate> linkTemplates = new ArrayList<>();

	public void addLinkTemplate(String linkTemplateName, String objectTemplate1Id, String objectTemplate2Id) {
		addLinkTemplate(new LinkTemplate(linkTemplateName, objectTemplate1Id, objectTemplate2Id));
	}

	public void addLinkTemplate(SystemObjectTemplate object1, String linkName, SystemObjectTemplate object2) {
		addLinkTemplate(object1, linkName, linkName, object2);
	}

	public void addLinkTemplate(SystemObjectTemplate objectTemplate1, String linkName_o1_o2, String linkName_o2_o1,
			SystemObjectTemplate objectTempate2) {
		final String objectTemplate1Id = (objectTemplate1 == null) ? null : objectTemplate1.getId();
		final String objectTemplate2Id = (objectTempate2 == null) ? null : objectTempate2.getId();

		if (objectTemplate1Id != null) {
			linkTemplates.add(new LinkTemplate(linkName_o1_o2, objectTemplate1Id, objectTemplate2Id));
		}
		if (objectTemplate2Id != null) {
			linkTemplates.add(new LinkTemplate(linkName_o2_o1, objectTemplate2Id, objectTemplate1Id));
		}
	}

	public Collection<LinkTemplate> getLinkTemplates() {
		return Collections.unmodifiableCollection(linkTemplates);
	}

	public void addLinkTemplate(LinkTemplate linkTemplate) {
		linkTemplates.add(linkTemplate);
	}

	@Override
	public String toString() {
		return "System Template";
	}

	public void removeObjectTemplate(SystemObjectTemplate objectTemplate) {
		objectTemplates.remove(objectTemplate);
	}

	public void removeLinkTemplate(LinkTemplate linkTemplate) {
		linkTemplates.remove(linkTemplate);
	}
}

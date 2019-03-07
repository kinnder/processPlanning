package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import utility.Collection;
import utility.CollectionItem;

/** Объект системы */
public class SystemObject implements CollectionItem {

	/** Идентификатор объекта */
	public UUID id;

	/** Атрибуты объекта */
	public Collection attributes;

	/** Множество связей */
	public List<SystemLinkInformation> linkInformations;

	/** Конструктор */
	public SystemObject() {
		id = UUID.randomUUID();
		attributes = new Collection();
		linkInformations = new ArrayList<SystemLinkInformation>();
	}

	@Override
	public boolean matches(Object pattern) {
		SystemObject patternObject = (SystemObject) pattern;
		if (this == patternObject) {
			return true;
		}
		if (attributes == null && patternObject.attributes != null) {
			return false;
		}
		return (attributes != null) ? attributes.matches(patternObject.attributes) : false;
	}

	@Override
	public Object clone() {
		SystemObject cloned = new SystemObject();
		cloned.attributes = (Collection) attributes.clone();
		return cloned;
	}

	public void registerLink(SystemLink link) {
		linkInformations.add(link.getLinkInformation());
		SystemAttribute attribute = getAttributeByName(link.linkName);
		if (attribute != null) {
			attribute.setInteger(attribute.getInteger() - 1);
		}
	}

	@Override
	public int compareTo(CollectionItem obj) {
		if (obj == this) {
			return 0;
		}
		if (obj == null) {
			return 1;
		}
		SystemObject systemObject = (SystemObject) obj;
		int compareToResultForAttributes = attributes.compareTo(systemObject.attributes);
		return compareToResultForAttributes;
	}

	public SystemAttribute getAttributeByName(String name) {
		for (CollectionItem ci : attributes) {
			SystemAttribute attribute = (SystemAttribute) ci;
			if (attribute.name.compareTo(name) == 0) {
				return attribute;
			}
		}
		return null;
	}

	public boolean hasAvailableLink(String linkName) {
		if (linkName == null) {
			return true;
		}
		SystemAttribute attribute = getAttributeByName(linkName);
		if (attribute != null) {
			if (attribute.getInteger() > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Получить модифицированную копию объекта
	 *
	 * @param modifications - модификатор
	 * @return модифицированную копию объекта
	 */
	public SystemObject getModifiedObject(Collection modifications) {
		SystemObject modifiedObject = (SystemObject) clone();
		modifiedObject.modify(modifications);
		return modifiedObject;
	}

	/**
	 * Модифицировать
	 *
	 * @param modifications - модификатор
	 */
	public void modify(Collection modifications) {
		for (CollectionItem cim : modifications) {
			SystemAttribute modification = (SystemAttribute) cim;
			for (CollectionItem cia : attributes) {
				SystemAttribute attribute = (SystemAttribute) cia;
				if (attribute.matches(modification)) {
					attribute.modify(modification);
				}
			}
		}
	}

	public void addAttribute(SystemAttribute attribute) {
		attributes.add(attribute);
	}
}

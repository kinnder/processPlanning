package cppbuilder;

import cppbuilder.utility.Collection;

/** Коллекция атрибутов */
public class AttributeCollection extends Collection<Attribute> {

	/** Конструктор */
	public AttributeCollection() {
		xmlName = "attributes";
	}

	/** Поиск атрибута */
	public Attribute find(String name) {
		return super.find(attribute -> attribute.name.equals(name));
	}
}

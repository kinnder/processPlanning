package asm.objects;

import model.SystemAttribute;
import model.SystemObject;

/** Станция комплектации */
public class ConfigurationStation extends SystemObject {

	/** Значение атрибута "class" */
	public final static String className = "Станция комплектации";

	/** Конструктор */
	public ConfigurationStation() {
		super();

		attributes.add(new SystemAttribute("class", className));
	}
}

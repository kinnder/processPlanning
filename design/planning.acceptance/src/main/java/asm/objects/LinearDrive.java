package asm.objects;

import model.SystemAttribute;
import model.SystemObject;

/** Линейный привод */
public class LinearDrive extends SystemObject {

	/** Значение атрибута "class" */
	public final static String className = "Линейный привод";

	/** Конструктор */
	public LinearDrive() {
		super();
		attributes.add(new SystemAttribute("class", className));
	}
}

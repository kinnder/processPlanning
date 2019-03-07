package asm.objects;

import model.SystemAttribute;
import model.SystemObject;

/** Поворотный привод */
public class RotaryDrive extends SystemObject {

	/** Значение атрибута "class" */
	public final static String className = "Поворотный привод";

	/** Конструктор */
	public RotaryDrive() {
		super();
		attributes.add(new SystemAttribute("class", className));
	}
}

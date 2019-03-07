package asm.objects;

import model.SystemAttribute;
import model.SystemObject;

/** Схват */
public class Grab extends SystemObject {

	/** Значение атрибута "class" */
	public final static String className = "Захват";

	/** Конструктор */
	public Grab() {
		super();
		attributes.add(new SystemAttribute("class", className));
	}
}

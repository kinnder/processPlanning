package asm.objects;

import model.SystemAttribute;
import model.SystemObject;

/** Вертикальный привод */
public class VerticalDrive extends SystemObject {

	/** Значение атрибута "class" */
	public final static String className = "Вертикальный привод";

	/** Конструктор */
	public VerticalDrive() {
		super();
		attributes.add(new SystemAttribute("class", className));
	}
}

package asm.objects;

import model.SystemAttribute;
import model.SystemObject;

/** Робот-перекладчик */
public class PickAndPlaceRobot extends SystemObject {

	/** Значение атрибута "class" */
	public final static String className = "Робот-перекладчик";

	/** Конструктор */
	public PickAndPlaceRobot() {
		super();
		attributes.add(new SystemAttribute("class", className));
	}
}

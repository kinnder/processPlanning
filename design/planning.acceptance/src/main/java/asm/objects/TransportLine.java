package asm.objects;

import model.SystemAttribute;
import model.SystemObject;

/** Транспортная линия зоны измерений */
public class TransportLine extends SystemObject {

	/** Значение атрибута "class" */
	public final static String className = "Транспортная линия зоны измерений";

	/** Конструктор */
	public TransportLine() {
		super();
		attributes.add(new SystemAttribute("class", className));
	}
}

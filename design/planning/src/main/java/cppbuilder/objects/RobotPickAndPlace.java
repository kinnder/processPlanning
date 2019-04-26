package cppbuilder.objects;

import cppbuilder.Attribute;
import cppbuilder.Element;
import cppbuilder.SystemObject;

/** Робот перекладчик */
public class RobotPickAndPlace extends SystemObject {

	public final static String DEFAULT_NAME = "Робот-перекладчик";

	/** Конструктор */
	public RobotPickAndPlace() {
		super(DEFAULT_NAME);
		attributes.add(new Attribute(Attribute.ATTRIBUTE_POSITION, Attribute.VALUE_SHUTTLE));
		attributes.shuffle();
	}

	/** Типовой элемент - перемещение тары с шатла на предменый столик 1 */
	public static Element moveBoxFromShuttleToTable1() {
		// TODO: подготовка типового элемента
		return new Element();
	}
}

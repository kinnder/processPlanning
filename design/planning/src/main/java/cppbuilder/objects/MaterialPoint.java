package cppbuilder.objects;

import cppbuilder.Attribute;
import cppbuilder.Element;
import cppbuilder.SystemObject;
import cppbuilder.SystemState;
import cppbuilder.Transition;
import cppbuilder.TransitionType;

/** Материальная точка */
public class MaterialPoint extends SystemObject {

	public final static String MOVE_LEFT = "влево";

	public final static String MOVE_RIGHT = "вправо";

	public final static String MOVE_UP = "вверх";

	public final static String MOVE_DOWN = "вниз";

	public final static String DEFAULT_NAME = "Точка";

	/** Конструктор */
	public MaterialPoint() {
		this(new Point(0, 0), Attribute.ATTRIBUTE_MOVEABLE);
	}

	/** Конструктор с параметрами */
	public MaterialPoint(Point position) {
		this(position, Attribute.ATTRIBUTE_MOVEABLE);
	}

	/** Конструктор с параметрами */
	public MaterialPoint(Point position, String moveableValue) {
		super(DEFAULT_NAME);
		attributes.add(new Attribute(Attribute.ATTRIBUTE_X, position.x));
		attributes.add(new Attribute(Attribute.ATTRIBUTE_Y, position.y));
		attributes.add(new Attribute(moveableValue));
		attributes.shuffle();
	}

	/** Типовой элемент - перемещение вверх */
	public static Element moveUp() {
		return movement(MOVE_UP, Attribute.ATTRIBUTE_Y, 1);
	}

	/** Типовой элемент - перемещение вниз */
	public static Element moveDown() {
		return movement(MOVE_DOWN, Attribute.ATTRIBUTE_Y, -1);
	}

	/** Типовой элемент - перемещение влево */
	public static Element moveLeft() {
		return movement(MOVE_LEFT, Attribute.ATTRIBUTE_X, -1);
	}

	/** Типовой элемент - перемещение вправо */
	public static Element moveRight() {
		return movement(MOVE_RIGHT, Attribute.ATTRIBUTE_X, 1);
	}

	/** Типовой элемент - перемещение */
	private static Element movement(String transition, String attribute, int value) {
		Element element = new Element();
		element.systemStateBeforeTransition = create_Object_MovementCondition(attribute);
		element.systemStateAfterTransition = create_Object_Delta(attribute, value);
		element.transition = new Transition(transition, TransitionType.DeltaAttributes);
		return element;
	}

	/** Создать объект - условие перемещения */
	private static SystemState create_Object_MovementCondition(String attribute) {
		SystemState state = new SystemState();
		SystemObject object = new SystemObject(MaterialPoint.DEFAULT_NAME);
		object.attributes.add(new Attribute(attribute));
		object.attributes.add(new Attribute(Attribute.ATTRIBUTE_MOVEABLE));
		object.attributes.shuffle();
		state.systemObjects.add(object);
		return state;
	}

	/** Создать объект - разница координат */
	private static SystemState create_Object_Delta(String attribute, int value) {
		SystemState state = new SystemState();
		SystemObject object = new SystemObject(MaterialPoint.DEFAULT_NAME);
		object.attributes.add(new Attribute(attribute, value));
		state.systemObjects.add(object);
		return state;
	}
}

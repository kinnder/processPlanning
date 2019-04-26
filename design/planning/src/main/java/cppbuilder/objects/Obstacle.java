package cppbuilder.objects;

import cppbuilder.Attribute;
import cppbuilder.Element;
import cppbuilder.SystemObject;
import cppbuilder.Transition;
import cppbuilder.TransitionType;

/** Препятствие */
public class Obstacle extends SystemObject {

	/** Название по умолчанию */
	public final static String DEFAULT_NAME = "Препятствие";

	/** Конструктор */
	public Obstacle() {
		this(0, 0, 0, 0);
	}

	/** Конструктор с параметрами */
	public Obstacle(Rect obstacle) {
		this(obstacle.left, obstacle.top, obstacle.right, obstacle.bottom);
	}

	/** Конструктор с параметрами */
	public Obstacle(int left, int top, int right, int bottom) {
		super(DEFAULT_NAME);
		attributes.add(new Attribute(Attribute.ATTRIBUTE_IS_AN_OBSTACLE));
		attributes.add(new Attribute(Attribute.ATTRIBUTE_X, left, right));
		attributes.add(new Attribute(Attribute.ATTRIBUTE_Y, bottom, top));
		attributes.shuffle();
	}

	/** Типовой элемент - столкновение */
	public static Element collision() {
		Element element = new Element();
		element.systemStateBeforeTransition.systemObjects
				.add(new MaterialPoint(new Point(0, 0), Attribute.ATTRIBUTE_MOVEABLE));
		element.systemStateBeforeTransition.systemObjects.add(new Obstacle());
		element.systemStateAfterTransition.systemObjects
				.add(new MaterialPoint(new Point(0, 0), Attribute.ATTRIBUTE_NOT_MOVEABLE));
		element.systemStateAfterTransition.systemObjects.add(new Obstacle());
		element.transition = new Transition("Столкновение", TransitionType.ChangeAttributes);
		return element;
	}
}

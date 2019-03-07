package points;

import model.Element;
import model.ElementsDataBase;
import model.SystemAttribute;
import model.SystemModification;
import model.SystemObject;
import model.SystemState;
import model.SystemTransition;
import points.objects.Point;

// TODO: перенести типовые элементы в отдельные файлы
/**
 * База типовых элементов "Материальные точки
 * <p>
 * База типовых элементов для работы с точками
 * </p>
 */
public class PointElementsDataBase extends ElementsDataBase {

	/**
	 * Конструктор
	 * <p>
	 * Выполняет заполнение базы типовыми элементами для работы с точками
	 * </p>
	 */
	public PointElementsDataBase() {
		super();
		elements.add(pointElement_noMovement());
		elements.add(pointElement_movementRight());
		elements.add(pointElement_movementLeft());
		elements.add(pointElement_movementUp());
		elements.add(pointElement_movementDown());
		elements.add(pointElement_movementForward());
		elements.add(pointElement_movementBack());
	}

	/**
	 * Формирование типового элемента для работы с точками
	 *
	 * @return типовой элемент "нет движения"
	 */
	public static Element pointElement_noMovement() {
		SystemState pattern = new SystemState();
		pattern.objects.add(Point.getPattern());

		SystemModification modification = new SystemModification();
		modification.target = (SystemObject) pattern.objects.get(0);

		SystemTransition transition = new SystemTransition();
		transition.modifications.add(modification);
		transition.name = "нет движения";

		return new Element(pattern, transition);
	}

	/**
	 * Формирование типового элемента для работы с точками
	 *
	 * @return типовой элемент "движение вправо"
	 */
	public static Element pointElement_movementRight() {
		SystemState pattern = new SystemState();
		pattern.objects.add(Point.getPattern());

		SystemModification modification = new SystemModification();
		modification.target = (SystemObject) pattern.objects.get(0);
		modification.modifications.add(new SystemAttribute("x", 1));

		SystemTransition transition = new SystemTransition();
		transition.modifications.add(modification);
		transition.name = "движение вправо";

		return new Element(pattern, transition);
	}

	/**
	 * Формирование типового элемента для работы с точками
	 *
	 * @return типовой элемент "движение влево"
	 */
	public static Element pointElement_movementLeft() {
		SystemState pattern = new SystemState();
		pattern.objects.add(Point.getPattern());

		SystemModification modification = new SystemModification();
		modification.target = (SystemObject) pattern.objects.get(0);
		modification.modifications.add(new SystemAttribute("x", -1));

		SystemTransition transition = new SystemTransition();
		transition.modifications.add(modification);
		transition.name = "движение влево";

		return new Element(pattern, transition);
	}

	/**
	 * Формирование типового элемента для работы с точками
	 *
	 * @return типовой элемент "движение вверх"
	 */
	public static Element pointElement_movementUp() {
		SystemState pattern = new SystemState();
		pattern.objects.add(Point.getPattern());

		SystemModification modification = new SystemModification();
		modification.target = (SystemObject) pattern.objects.get(0);
		modification.modifications.add(new SystemAttribute("z", 1));

		SystemTransition transition = new SystemTransition();
		transition.modifications.add(modification);
		transition.name = "движение вверх";

		return new Element(pattern, transition);
	}

	/**
	 * Формирование типового элемента для работы с точками
	 *
	 * @return типовой элемент "движение вниз"
	 */
	public static Element pointElement_movementDown() {
		SystemState pattern = new SystemState();
		pattern.objects.add(Point.getPattern());

		SystemModification modification = new SystemModification();
		modification.target = (SystemObject) pattern.objects.get(0);
		modification.modifications.add(new SystemAttribute("z", -1));

		SystemTransition transition = new SystemTransition();
		transition.modifications.add(modification);
		transition.name = "движение вниз";

		return new Element(pattern, transition);
	}

	/**
	 * Формирование типового элемента для работы с точками
	 *
	 * @return типовой элемент "движение назад"
	 */
	public static Element pointElement_movementBack() {
		SystemState pattern = new SystemState();
		pattern.objects.add(Point.getPattern());

		SystemModification modification = new SystemModification();
		modification.target = (SystemObject) pattern.objects.get(0);
		modification.modifications.add(new SystemAttribute("y", -1));

		SystemTransition transition = new SystemTransition();
		transition.modifications.add(modification);
		transition.name = "движение назад";

		return new Element(pattern, transition);
	}

	/**
	 * Формирование типового элемента для работы с точками
	 *
	 * @return типовой элемент "движение вперёд"
	 */
	public static Element pointElement_movementForward() {
		SystemState pattern = new SystemState();
		pattern.objects.add(Point.getPattern());

		SystemModification modification = new SystemModification();
		modification.target = (SystemObject) pattern.objects.get(0);
		modification.modifications.add(new SystemAttribute("y", 1));

		SystemTransition transition = new SystemTransition();
		transition.modifications.add(modification);
		transition.name = "движение вперед";

		return new Element(pattern, transition);
	}
}

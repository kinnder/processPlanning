package points.objects;

import model.SystemAttribute;
import model.SystemObject;

/** Материальная точка */
public class Point extends SystemObject {

	/** Значение атрибута "class" */
	public final static String className = "Материальная точка";

	/**
	 * Конструктор
	 *
	 * @param name - название материальной точки
	 * @param x    - значение атрибута "x"
	 * @param y    - значение атрибута "y"
	 * @param z    - значение атрибута "z"
	 */
	public Point(String name, int x, int y, int z) {
		super();
		attributes.add(new SystemAttribute("name", name));
		attributes.add(new SystemAttribute("class", className));
		attributes.add(new SystemAttribute("x", x));
		attributes.add(new SystemAttribute("y", y));
		attributes.add(new SystemAttribute("z", z));
	}

	/**
	 * Получить шаблон объекта
	 *
	 * @return шаблон объекта материальная точка
	 */
	public static SystemObject getPattern() {
		SystemObject pattern = new SystemObject();
		pattern.attributes.add(new SystemAttribute("class", Point.className));
		return pattern;
	}
}

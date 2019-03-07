package utility;

import model.SystemAttribute;
import model.SystemObject;

/** Построитель объектов */
public class SystemObjectBuilder {

	/** Результат построения */
	private SystemObject construction;

	/**
	 * Построить
	 *
	 * @return сконструированный объект
	 */
	public SystemObject build() {
		return construction;
	}

	/** Конструктор */
	public SystemObjectBuilder() {
		construction = new SystemObject();
	}

	/**
	 * Конструктор
	 *
	 * @param construction - создаваемый объект
	 */
	public SystemObjectBuilder(SystemObject construction) {
		this.construction = construction;
	}

	/**
	 * Добавить атрибут
	 *
	 * @param name  - название атрибута
	 * @param value - значение атрибута
	 * @return строитель объектов
	 */
	public SystemObjectBuilder addAttribute(String name, String value) {
		construction.addAttribute(new SystemAttribute(name, value));
		return this;
	}
}

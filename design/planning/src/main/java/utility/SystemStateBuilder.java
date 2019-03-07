package utility;

import model.SystemObject;
import model.SystemState;

/** Строитель состояний системы */
public class SystemStateBuilder {

	/** Результат построения */
	private SystemState construction;

	/** Конструктор */
	public SystemStateBuilder() {
		construction = new SystemState();
	}

	/**
	 * Конструктор
	 *
	 * @param construction - создаваемое состояние
	 */
	public SystemStateBuilder(SystemState construction) {
		this.construction = construction;
	}

	/**
	 * Добавить объект
	 *
	 * @param object_n - объект
	 * @return строитель состояний
	 */
	public SystemStateBuilder addObject(SystemObject object_n) {
		construction.addObject(object_n);
		return this;
	}

	/**
	 * Добавить связь
	 *
	 * @param object_1 - объект 1
	 * @param object_2 - объект 2
	 * @param linkName - название связи
	 * @return строитель состояний
	 */
	public SystemStateBuilder linkObjects(SystemObject object_1, SystemObject object_2, String linkName) {
		try {
			construction.createLink(object_1, object_2, linkName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	public SystemStateBuilder linkObjects(SystemObject object_1, SystemObject object_2) {
		return linkObjects(object_1, object_2, null);
	}

	/**
	 * Построить
	 *
	 * @return сконструированное состояние
	 */
	public SystemState build() {
		return construction;
	}
}

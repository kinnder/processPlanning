package model;

import utility.Collection;
import utility.CollectionItem;

/** Переходное состояние */
public class TransitionalState implements CollectionItem {

	/** Изменяемые объекты */
	public Collection modifiingObjects;

	/** Постоянные объекты */
	public Collection permanentObjects;

	/** Конструктор */
	public TransitionalState() {
		modifiingObjects = new Collection();
		permanentObjects = new Collection();
	}

	/**
	 * Вычисление состояния после перехода
	 *
	 * @param transition - переход
	 * @return состояние системы, после выполнения перехода изменяемыми объектами
	 */
	public SystemState getStateAfterTransition(SystemTransition transition) {
		SystemState state = new SystemState();
		Collection modifiedObjects = transition.getModifiedObjects(modifiingObjects);
		state.objects.addRange(modifiedObjects);
		state.objects.addRange((Collection) permanentObjects.clone());
		return state;
	}

	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int compareTo(CollectionItem o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean matches(Object pattern) {
		// TODO Auto-generated method stub
		return false;
	}
}

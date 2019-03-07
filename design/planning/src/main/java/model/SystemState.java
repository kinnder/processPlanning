package model;

import java.util.UUID;

import utility.Collection;
import utility.CollectionItem;

/** Состояние системы */
public class SystemState implements CollectionItem {

	/** Объекты */
	public Collection objects;

	/** Множество связей */
	public Collection links;

	/** Конструктор */
	public SystemState() {
		objects = new Collection();
		links = new Collection();
	}

	@Override
	public Object clone() {
		SystemState cloned = new SystemState();
		cloned.objects = (Collection) objects.clone();
		cloned.links = (Collection) links.clone();
		for (CollectionItem ci : cloned.links) {
			SystemLink link = (SystemLink) ci;
			link.object_1 = cloned.getObjectById(link.object_1.id);
			link.object_2 = cloned.getObjectById(link.object_2.id);
		}
		return cloned;
	}

	public SystemObject getObjectById(UUID id) {
		return (SystemObject) objects.find((obj) -> ((SystemObject) obj).id == id);
	}

	@Override
	public int compareTo(CollectionItem obj) {
		if (obj == this) {
			return 0;
		}
		if (obj == null) {
			return 1;
		}
		SystemState systemState = (SystemState) obj;
		int compareToResultForObjects = objects.compareTo(systemState.objects);
		return compareToResultForObjects;
	}

	public void createLink(SystemObject object_1, SystemObject object_2, String linkName) throws Exception {
		if (objects.contains(object_1) && objects.contains(object_2)) {
			if (object_1.hasAvailableLink(linkName) && object_2.hasAvailableLink(linkName)) {
				SystemLink link = new SystemLink(object_1, object_2, linkName);
				links.add(link);
				object_1.registerLink(link);
				object_2.registerLink(link);
			} else {
				// TODO : специальный тип исключений
				throw new Exception("objects doesn't have links");
			}
		} else {
			// TODO : специальный тип исключений
			throw new Exception("objects belongs to different systems");
		}
	}

	@Override
	public boolean matches(Object pattern) {
		SystemState patternState = (SystemState) pattern;
		if ((this == patternState) || (objects.matches(patternState.objects) && links.matches(patternState.links))) {
			// TODO : проверка связей
			return true;
		}
		return false;
	}

	/**
	 * Подготовить переходные состояния
	 *
	 * @param statePattern - шаблон
	 * @return коллекцию переходных состояний с изменяющейся частью, соответствующей
	 *         шаблону
	 */
	public Collection prepareTransitionalStates(SystemState statePattern) {
		Collection objectsPossibleToChange = objects.intersect(statePattern.objects);
		Collection objectsNotPossibleToChange = objects.complement(statePattern.objects);

		Collection[] changeCombinations = objectsPossibleToChange.subsets(statePattern.objects);

		Collection results = new Collection();

		for (Collection changeCombination : changeCombinations) {
			TransitionalState transitionalState = new TransitionalState();

			transitionalState.modifiingObjects = (Collection) changeCombination.clone();
			Collection notChangedObjects = objectsPossibleToChange.complement(changeCombination);
			transitionalState.permanentObjects = (Collection) notChangedObjects.clone();
			transitionalState.permanentObjects.addRange((Collection) objectsNotPossibleToChange.clone());

			results.add(transitionalState);
		}

		return results;
	}

	public void addObject(SystemObject object_n) {
		objects.add(object_n);
	}
}

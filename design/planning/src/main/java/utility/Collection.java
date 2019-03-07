package utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/** Коллекция */
public class Collection implements CollectionItem, Iterable<CollectionItem> {

	/** Элементы */
	private List<CollectionItem> items;

	public int size() {
		return items.size();
	}

	public CollectionItem get(int index) {
		return items.get(index);
	}

	public void addRange(Collection c) {
		items.addAll(c.items);
	}

	/** Конструктор */
	public Collection() {
		items = new ArrayList<CollectionItem>();
	}

	@Override
	public Iterator<CollectionItem> iterator() {
		return items.iterator();
	}

	public boolean contains(CollectionItem item) {
		return items.contains(item);
	}

	public void add(CollectionItem item) {
		items.add(item);
	}

	public void sort() {
		items.sort(null);
	}

	public boolean remove(CollectionItem item) {
		return items.remove(item);
	}

	@Override
	public Object clone() {
		Collection clonedCollection = new Collection();
		for (CollectionItem item : items) {
			CollectionItem clonedItem = (CollectionItem) item.clone();
			clonedCollection.add(clonedItem);
		}
		return clonedCollection;
	}

	@Override
	public int compareTo(CollectionItem o) {
		if (o == this) {
			return 0;
		}
		if (o == null) {
			return 1;
		}
		Collection collection = (Collection) o;
		sort();
		collection.sort();
		for (int i = 0; i < size() && i < collection.size(); i++) {
			int compareToResultForItem = items.get(i).compareTo(collection.get(i));
			if (compareToResultForItem != 0) {
				return compareToResultForItem;
			}
		}
		if (size() < collection.size()) {
			return -1;
		}
		if (size() > collection.size()) {
			return 1;
		}
		return 0;
	}

	@Override
	public boolean matches(Object pattern) {
		Collection patternCollection = (Collection) pattern;
		if (this == patternCollection) {
			return true;
		}
		if (patternCollection.size() > size()) {
			return false;
		}
		Collection objectsInPattern = (Collection) patternCollection.clone();
		for (CollectionItem objectInThis : this) {
			for (CollectionItem objectInPattern : objectsInPattern) {
				if (objectInThis.matches(objectInPattern)) {
					objectsInPattern.remove(objectInPattern);
					break;
				}
			}
		}
		if (objectsInPattern.size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Пересечение
	 *
	 * @param pattern - шаблон
	 * @return коллекцию всех объектов, соответствующих шаблону
	 */
	public Collection intersect(Object pattern) {
		Collection patternCollection = (Collection) pattern;

		Collection intersection = new Collection();
		for (CollectionItem objectInThis : this) {
			if (hasMatchWithPattern(objectInThis, patternCollection)) {
				intersection.add(objectInThis);
			}
		}
		return intersection;
	}

	/**
	 * Разность
	 *
	 * @param pattern - шаблон
	 * @return коллекция всех объектов, не соответствующих шаблону
	 */
	public Collection complement(Object pattern) {
		Collection patternCollection = (Collection) pattern;

		Collection complemention = new Collection();
		for (CollectionItem objectInThis : this) {
			if (!hasMatchWithPattern(objectInThis, patternCollection)) {
				complemention.add(objectInThis);
			}
		}
		return complemention;
	}

	/**
	 * Предикат - объект соответствует одному из объектов шаблона
	 *
	 * @param objectToMatch - объект
	 * @param pattern       - шаблон
	 * @return true - объект соответствует одному объекту в шаблоне<br>
	 *         false - объект не соответствует ни одному из объектов в шаблоне
	 */
	private boolean hasMatchWithPattern(CollectionItem objectToMatch, Collection pattern) {
		for (CollectionItem objectInPattern : pattern) {
			if (objectToMatch.matches(objectInPattern)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Подмножества
	 *
	 * @param pattern - шаблон
	 * @return коллекцию множеств объектов, входящих в исходную коллекцию и
	 *         соответствующих шаблону
	 */
	public Collection[] subsets(Object pattern) {
		Collection patternCollection = (Collection) pattern;

		List<Collection> subsets = new ArrayList<Collection>();

		int[] indexes = new int[patternCollection.size()];

		// TODO: сделать отдельный класс для работы с массивами индексов, добавить в
		// него метод для получения уникальных комбинаций
		while (indexes[0] < size()) {
			boolean indexesCombinationShouldBeAdded = true;
			for (int indexId = 0; indexId < indexes.length; indexId++) {
				if (!this.get(indexes[indexId]).matches(patternCollection.get(indexId))) {
					indexesCombinationShouldBeAdded = false;
					break;
				}
			}
			for (int i = 0; indexesCombinationShouldBeAdded && (i < indexes.length); i++) {
				for (int j = i + 1; j < indexes.length; j++) {
					if (indexes[i] == indexes[j]) {
						indexesCombinationShouldBeAdded = false;
						break;
					}
				}
			}
			if (indexesCombinationShouldBeAdded) {
				Collection subset = new Collection();
				for (int index : indexes) {
					subset.add(this.get(index));
				}
				subsets.add(subset);
			}
			indexes[indexes.length - 1]++;
			for (int id = indexes.length - 1; id > 0; id--) {
				if (indexes[id] == size()) {
					indexes[id] = 0;
					indexes[id - 1]++;
				}
			}
		}

		return subsets.toArray(new Collection[0]);
	}

	public CollectionItem find(Predicate<CollectionItem> match) {
		for (CollectionItem item : items) {
			if (match.test(item)) {
				return item;
			}
		}
		return null;
	}
}

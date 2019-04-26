package cppbuilder.utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/** Коллекция */
public class Collection<Type extends LogicOperators<Type>> extends LogicOperators<Collection<Type>>
		implements Iterable<Type> {

	/** Данные */
	protected List<Type> items;

	/** XML тэг коллекции */
	protected String xmlName;

	/** Конструктор */
	public Collection() {
		items = new ArrayList<Type>();
	}

	/** Добавить элемент */
	public void add(Type item) {
		items.add(item);
	}

	/** Размер */
	public int size() {
		return items.size();
	}

	/** Получить элемент */
	public Type get(int index) {
		return items.get(index);
	}

	/** Получить последний элемент */
	public Type back() {
		return items.get(items.size() - 1);
	}

	@Override
	public String toXMLString() {
		XMLTagBuilder collection = new XMLTagBuilder("collection");
		String result = collection.startTag();
		for (Type item : items) {
			result += item.toXMLString();
		}
		result += collection.endTag();
		return result;
	}

	@Override
	public Iterator<Type> iterator() {
		return items.iterator();
	}

	/** Очистить */
	public void clear() {
		items.clear();
	}

	/** Перемешать */
	public void shuffle() {
		Collections.shuffle(items);
	}

	/** Поиск */
	public Type find(Predicate<Type> template) {
		return items.stream().filter(template).findAny().orElse(null);
	}

	@Override
	protected boolean allFieldsAreEqual(Collection<Type> collection) {
		if (items.size() != collection.items.size()) {
			return false;
		}
		for (Type item : items) {
			if (collection.find(collectionItem -> collectionItem.equal(item)) == null) {
				return false;
			}
		}
		return true;
	}

	@Override
	protected boolean haveMatchingFields(Collection<Type> collection) {
		if (items.size() > collection.items.size()) {
			return false;
		}
		for (Type item : items) {
			if (collection.find(collectionItem -> collectionItem.matches(item)) == null) {
				return false;
			}
		}
		return true;
	}

	@Override
	protected boolean haveSubset(Collection<Type> collection) {
		for (Type collectionItem : collection.items) {
			if (find(item -> item.includes(collectionItem)) == null) {
				return false;
			}
		}
		return true;
	}

	/** Удалить */
	public void remove(Type item) {
		items.remove(item);
	}

	/** Предикат - элемент существует */
	public boolean exists(Type item) {
		return items.indexOf(item) != -1;
	}
}

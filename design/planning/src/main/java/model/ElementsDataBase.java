package model;

import java.util.ArrayList;
import java.util.List;

/** База типовых элементов */
public class ElementsDataBase {

	/** Элементы */
	public List<Element> elements;

	/** Конструктор */
	public ElementsDataBase() {
		elements = new ArrayList<Element>();
	}

	/**
	 * Предикат - есть элемент с исходным состоянием, совпадающим с указанным
	 * состоянием
	 *
	 * @param state - состояние
	 * @return
	 */
	public boolean haveElementMatchingState(SystemState state) {
		for (Element element : elements) {
			if (state.matches(element.getStatePattern())) {
				return true;
			}
		}
		return false;
	}
}

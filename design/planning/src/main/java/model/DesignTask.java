package model;

/**
 * «адание
 * <p>
 * «адание на проектировани€
 * </p>
 */
public class DesignTask {

	/** Ќачальное состо€ние системы */
	SystemState initialState;

	/**  онечное состо€ние системы */
	SystemState targetState;

	/** Ѕаза элементов */
	ElementsDataBase elements;

	/**
	 *  онструктор
	 *
	 * @param initialState - начальное состо€ние системы
	 * @param targetState  - конечное состо€ние системы
	 * @param elements     - база элементов
	 */
	public DesignTask(SystemState initialState, SystemState targetState, ElementsDataBase elements) {
		this.initialState = initialState;
		this.targetState = targetState;
		this.elements = elements;
	}

	/**
	 * ѕолучить конечное состо€ние
	 *
	 * @return конечное состо€ние системы дл€ текущего задани€
	 */
	public SystemState getTargetState() {
		return targetState;
	}

	/**
	 * —оздать вспомогательное задание
	 *
	 * @param systemState - начальное состо€ние системы дл€ вспомогательного задани€
	 * @return задание на проектирование
	 */
	public DesignTask createSubTask(SystemState systemState) {
		return new DesignTask(systemState, targetState, elements);
	}

	/**
	 * ѕолучить базу элементов
	 *
	 * @return базу элементов дл€ текущего задани€
	 */
	public ElementsDataBase getElements() {
		return elements;
	}

	/**
	 * ѕолучить начальное состо€ние
	 *
	 * @return начальное состо€ние системы дл€ текущего задани€
	 */
	public SystemState getInitialState() {
		return initialState;
	}

	/**
	 * ѕредикат - €вл€етс€ правильным
	 *
	 * @return true - если задание €вл€етс€ правильным;<br>
	 *         false - если задание €вл€етс€ не правильным
	 */
	public boolean isValid() {
		return (initialState != null) && (targetState != null) && (elements != null)
				&& elements.haveElementMatchingState(initialState);
	}
}

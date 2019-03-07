package model;

import utility.Collection;
import utility.CollectionItem;

/**
 * Типовой элемент
 * <p>
 * Типовой элемент, используемый при проектировании процессов
 * </p>
 */
public class Element {

	/**
	 * Шаблон состояния
	 * <p>
	 * Шаблон состояния системы, определяющий возможность выполнения перехода
	 * </p>
	 */
	SystemState statePattern;

	/**
	 * Переход
	 * <p>
	 * Изменение состояния системы в результате выполнения перехода
	 * </p>
	 */
	SystemTransition transition;

	/**
	 * Конструктор
	 *
	 * @param statePattern - шаблон состояния
	 * @param transition   - переход
	 */
	public Element(SystemState statePattern, SystemTransition transition) {
		this.statePattern = statePattern;
		this.transition = transition;
	}

	/**
	 * Получить шаблон состояния
	 *
	 * @return шаблон состояния системы для текущего элемента
	 */
	public SystemState getStatePattern() {
		return statePattern;
	}

	/**
	 * Получить переход
	 *
	 * @return переход для текущего элемента
	 */
	public SystemTransition getTransition() {
		return transition;
	}

	/**
	 * Вычисление состояний системы в результате перехода
	 * <p>
	 * Вычисление состояний системы в результате переходов из исходного состояния
	 * </p>
	 *
	 * @param state - исходное состояние
	 * @return состояния системы
	 */
	public Collection getStatesAfterTransitionFromState(SystemState state) {
		Collection transitions = new Collection();
		Collection transitionalStates = state.prepareTransitionalStates(statePattern);
		for (CollectionItem ci : transitionalStates) {
			TransitionalState transitionalState = (TransitionalState) ci;
			SystemState transitionResult = transitionalState.getStateAfterTransition(transition);
			transitions.add(transitionResult);
		}
		return transitions;
	}
}

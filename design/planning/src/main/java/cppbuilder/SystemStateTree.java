package cppbuilder;

import cppbuilder.objects.Obstacle;

/** Дерево состояний системы */
public class SystemStateTree {

	/** Начальное состояние */
	public SystemState startState = null;

	/** Конечное состояние */
	public SystemState finalState = null;

	/** Узлы */
	public SystemStateCollection states = new SystemStateCollection();

	/** Пути */
	public RouteCollection routes = new RouteCollection();

	/** База элементов */
	private ElementBase elements = null;

	/** Анализ состояния системы */
	private void analyzeSystemState(SystemState systemState, Route route) {
		if (!finalState.isMatchingTo(systemState)) {
			if (!systemState.isDeadEndState()) {
				for (int i = 0; i < systemState.links.size(); i++) {
					route.nodes.add(new Node(systemState.links.get(i)));
					analyzeSystemState(systemState.links.get(i).end, route);
				}
			}
		} else {
			routes.add(route);
		}
	}

	/** Предикат - есть состояние совпадающиее с конечным */
	private boolean haveStateMatchingFinalState() {
		if (finalState != null) {
			for (int i = 0; i < states.size(); i++) {
				if (finalState.isMatchingTo(states.get(i))) {
					return true;
				}
			}
		}
		return false;
	}

	/** Построение следующих узлов из элементов */
	private void buildNextStates(SystemState state) {
		ElementCollection l_elements = elements.find(state);
		for (Element element : l_elements) {
			addNewStateToTree(state, (element));
		}
	}

	/** Поиск путей */
	public void prepareRoutes() {
		routes.clear();
		if (haveStateMatchingFinalState()) {
			if (startState != null) {
				Route route = new Route();
				analyzeSystemState(startState, route);
			}
		}
	}

	/** Построение дерева */
	public void buildTree(SystemState begin, SystemState end, ElementBase elements) {
		this.elements = elements;
		states.clear();
		states.add(begin);
		int stateId = 0;
		int l_limit = 1000;
		while ((stateId < states.size()) && (l_limit > 0)) {
			SystemState l_state = states.get(stateId);
			if (l_state.includes(end)) {
				startState = states.get(0);
				finalState = (l_state);
				break;
			}
			buildNextStates((l_state));
			stateId++;
			l_limit--;

		}
	}

	/** Добавить новый узел */
	public void addNewStateToTree(SystemState state, Element element) {
		SystemStateCollection l_newStates = element.calculateSystemState(state);
		for (SystemState l_newState : l_newStates) {
			// !\todo refactor
			SystemStateCollection l_result = Obstacle.collision().calculateSystemState(l_newState);
			if (l_result.size() == 0)
//			if (p_element->statePassesRestriction(*l_newState))
			{
				if (!states.exists(l_newState)) {
					states.add(l_newState);
					state.link(l_newState, element.transition);
				}
			}
		}
	}
}

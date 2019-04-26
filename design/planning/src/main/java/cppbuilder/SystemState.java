package cppbuilder;

import cppbuilder.utility.LogicOperators;
import cppbuilder.utility.XMLTagBuilder;

/** Состояние системы */
public class SystemState extends LogicOperators<SystemState> {

	/** Объекты */
	public SystemObjectCollection systemObjects;

	/** Связи */
	public LinkCollection links;

	/** Флаг -должно быть добавлено */
	public boolean shouldBeAdded;

	/** Связать */
	public void link(SystemState nextSystemState, Transition transition) {
		links.add(new Link(this, nextSystemState, transition));
	}

	/** Предикат - является тупиковым состоянием */
	public boolean isDeadEndState() {
		return links.size() > 0 ? false : true;
	}

	/** Предикат - совпадает с состоянием */
	// TODO : rename includes
	public boolean isMatchingTo(SystemState systemState) {
		return operator_equality(systemState);
	}

	@Override
	public String toXMLString() {
		XMLTagBuilder element = new XMLTagBuilder("state");
		return element.startTag() + systemObjects.toXMLString() + element.endTag();
	}

	/** Поиск подмножеств */
	public SystemStateCollection findSubset(SystemState searchTemplate) {
		SystemStateCollection result = new SystemStateCollection();
		for (int first_id = 0; first_id < systemObjects.size(); first_id++) {
			if (!searchTemplate.objectIsPresentInState(systemObjects.get(first_id))) {
				continue;
			}
			if (searchTemplate.systemObjects.size() > 1) {
				int second_id = first_id;
				for (second_id++; second_id < systemObjects.size(); second_id++) {
					SystemState state = new SystemState();
					state.systemObjects.add(systemObjects.get(first_id));
					for (int object_id = second_id; object_id < systemObjects.size(); object_id++) {
						if (state.systemObjects.size() == searchTemplate.systemObjects.size()) {
							break;
						}
						state.systemObjects.add(systemObjects.get(object_id));
					}
					if (searchTemplate.matches(state)) {
						result.add(state);
					}
				}
			} else {
				SystemState state = new SystemState();
				state.systemObjects.add(systemObjects.get(first_id));
				if (searchTemplate.matches(state)) {
					result.add(state);
				}
			}
		}
		return result;
	}

	/** Конструктор */
	public SystemState() {
		shouldBeAdded = false;
		systemObjects = new SystemObjectCollection();
		links = new LinkCollection();
	}

	/** Применение элементов изменяющих состояние объектов */
	public void applyAllStateChangingElements(ElementBase elementBase) {
		ElementCollection elements = elementBase.findStateChangingElements(this);
		for (Element element : elements) {
			element.applyStateChangeToState(this);
		}
	}

	/** Предикат - объект присутствует в состоянии */
	private boolean objectIsPresentInState(SystemObject systemObject) {
		for (SystemObject so : systemObjects) {
			if (so.matches(systemObject)) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected boolean allFieldsAreEqual(SystemState systemState) {
		return systemObjects.operator_equality(systemState.systemObjects);
	}

	@Override
	protected boolean haveMatchingFields(SystemState systemState) {
		return systemObjects.matches(systemState.systemObjects);
	}

	@Override
	protected boolean haveSubset(SystemState systemState) {
		return systemObjects.includes(systemState.systemObjects);
	}
}

package cppbuilder;

/** База типовых элементов */
public class ElementBase {

	/** Типовые элементы */
	public ElementCollection elements = new ElementCollection();

	/** Поиск элементов */
	public ElementCollection find(SystemState systemState) {
		ElementCollection result = new ElementCollection();
		for (Element element : elements) {
			if (element.systemStateBeforeTransition.matches(systemState)) {
				result.add(element);
			}
		}
		return result;
	}

	/** Поиск элеметов, изменяющих состояние */
	public ElementCollection findStateChangingElements(SystemState systemState) {
		ElementCollection result = new ElementCollection();
		for (Element element : elements) {
			if (element.systemStateBeforeTransition.matches(systemState)) {
				if (element.transition.type == TransitionType.ChangeAttributes) {
					result.add(element);
				}
			}
		}
		return result;
	}

}

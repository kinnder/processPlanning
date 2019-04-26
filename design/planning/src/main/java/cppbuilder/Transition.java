package cppbuilder;

import cppbuilder.utility.LogicOperators;
import cppbuilder.utility.XMLTagBuilder;

/** Переход */
public class Transition extends LogicOperators<Transition> {

	/** Название */
	public String name;

	/** Тип */
	public TransitionType type;

	/** Конструктор */
	public Transition() {
		this("unknown", TransitionType.Unknown);
	}

	/** Конструктор с параметрами */
	public Transition(String name) {
		this(name, TransitionType.Unknown);
	}

	/** Конструктор с параметрами */
	public Transition(String name, TransitionType type) {
		this.name = name;
		this.type = type;
	}

	@Override
	protected boolean allFieldsAreEqual(Transition transition) {
		return (name.equals(transition.name)) && (type == transition.type);
	}

	@Override
	public String toXMLString() {
		XMLTagBuilder transitionTag = new XMLTagBuilder("transition");
		XMLTagBuilder nameTag = new XMLTagBuilder("name");
		XMLTagBuilder typeTag = new XMLTagBuilder("type");

		return transitionTag.startTag() + nameTag.print(name) + typeTag.print(type.toString()) + transitionTag.endTag();
	}
}

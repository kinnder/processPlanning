package cppbuilder;

import cppbuilder.utility.LogicOperators;
import cppbuilder.utility.XMLTagBuilder;

/** Связь */
public class Link extends LogicOperators<Link> {

	/** Начальное состояние */
	public SystemState begin;

	/** Конечное состояние */
	public SystemState end;

	/** Переход */
	public Transition transition;

	@Override
	public String toXMLString() {
		XMLTagBuilder element = new XMLTagBuilder("link");
		String result = element.startTag();
		if (begin != null) {
			result += begin.toXMLString();
		}
		if (end != null) {
			result += end.toXMLString();
		}
		result += transition.toXMLString();
		result += element.endTag();
		return result;
	}

	/** Конструктор */
	public Link() {
		this.begin = null;
		this.end = null;
		this.transition = new Transition();
	}

	/** Конструктор с параметрами */
	public Link(SystemState begin, SystemState end, Transition transition) {
		this.begin = begin;
		this.end = end;
		this.transition = transition;
	}

	@Override
	protected boolean allFieldsAreEqual(Link link) {
		return transition.operator_equality(link.transition) && begin.operator_equality(link.begin)
				&& end.operator_equality(link.end);
	}
}

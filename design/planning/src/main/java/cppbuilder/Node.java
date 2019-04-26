package cppbuilder;

import cppbuilder.utility.LogicOperators;
import cppbuilder.utility.XMLTagBuilder;

/** Узел */
public class Node extends LogicOperators<Node> {

	/** Связь */
	public Link link;

	@Override
	public String toXMLString() {
		XMLTagBuilder element = new XMLTagBuilder("node");
		String result = element.startTag();
		if (link != null) {
			result += link.toXMLString();
		}
		result += element.endTag();
		return result;
	}

	/** Конструктор */
	public Node() {
		this.link = null;
	}

	/** Конструктор с параметрами */
	public Node(Link link) {
		this.link = link;
	}

	@Override
	protected boolean allFieldsAreEqual(Node node) {
		// TODO : NULL-object
		if ((link != null) && (node.link == null)) {
			return false;
		}
		if ((link == null) && (node.link != null)) {
			return false;
		}
		if ((link != null) && (node.link != null)) {
			if (link.operator_equality(node.link) == false) {
				return false;
			}
		}
		return true;
	}
}

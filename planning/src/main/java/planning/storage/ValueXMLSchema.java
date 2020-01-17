package planning.storage;

import org.jdom2.Element;

public class ValueXMLSchema {

	public Object parseValue(Element root) {
		if (root == null) {
			return null;
		}
		String type = root.getAttributeValue("type", "string");
		String value = root.getText();
		if ("boolean".equals(type)) {
			return Boolean.valueOf(value);
		}
		if ("integer".equals(type)) {
			return Integer.valueOf(value);
		}
		return value;
	}

	public Element combineValue(Object value) {
		Element root = new Element("value");
		if (value instanceof Boolean) {
			root.setAttribute("type", "boolean");
		} else if (value instanceof Integer) {
			root.setAttribute("type", "integer");
		}
		root.setText(value.toString());
		return root;
	}
}

package planning.storage;

import org.jdom2.Element;

public class ValueXMLSchema implements XMLSchema<Object> {

	@Override
	public Object parse(Element element) {
		if (element == null) {
			return null;
		}
		String type = element.getAttributeValue("type", "string");
		String value = element.getText();
		if ("boolean".equals(type)) {
			return Boolean.valueOf(value);
		}
		if ("integer".equals(type)) {
			return Integer.valueOf(value);
		}
		return value;
	}

	@Override
	public Element combine(Object value) {
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

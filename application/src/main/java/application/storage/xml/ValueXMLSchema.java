package application.storage.xml;

import org.jdom2.Element;

public class ValueXMLSchema implements XMLSchema<Object> {

	final private static String TAG_value = "value";

	final private static String TAG_type = "type";

	final private static String TAG_string = "string";

	final private static String TAG_boolean = "boolean";

	final private static String TAG_integer = "integer";

	@Override
	public String getSchemaName() {
		return TAG_value;
	}

	@Override
	public Object parse(Element element) {
		// TODO (2020-06-18 #22): убрать работу с null элементами
		if (element == null) {
			return null;
		}
		String type = element.getAttributeValue(TAG_type, TAG_string);
		String value = element.getText();
		if (TAG_boolean.equals(type)) {
			return Boolean.valueOf(value);
		}
		if (TAG_integer.equals(type)) {
			return Integer.valueOf(value);
		}
		return value;
	}

	@Override
	public Element combine(Object value) {
		Element root = new Element(TAG_value);
		if (value instanceof Boolean) {
			root.setAttribute(TAG_type, TAG_boolean);
		} else if (value instanceof Integer) {
			root.setAttribute(TAG_type, TAG_integer);
		}
		root.setText(value.toString());
		return root;
	}
}

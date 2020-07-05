package planning.storage;

import java.util.HashMap;
import java.util.Map;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

public class ActionParametersXMLSchema implements XMLSchema<Map<String, String>> {

	final private static String TAG_parameters = "parameters";

	final private static String TAG_parameter = "parameter";

	final private static String TAG_name = "name";

	public ActionParametersXMLSchema() {
		this(new ValueXMLSchema());
	}

	ActionParametersXMLSchema(ValueXMLSchema valueXMLSchema) {
		this.valueXMLSchema = valueXMLSchema;
	}

	private ValueXMLSchema valueXMLSchema;

	@Override
	public String getSchemaName() {
		return TAG_parameters;
	}

	@Override
	public Map<String, String> parse(Element root) throws DataConversionException {
		Map<String, String> actionParameters = new HashMap<>();

		for (Element element : root.getChildren(TAG_parameter)) {
			String name = element.getChildText(TAG_name);
			Object value = valueXMLSchema.parse(element.getChild(TAG_parameter));
			actionParameters.put(name, value.toString());
		}

		return actionParameters;
	}

	@Override
	public Element combine(Map<String, String> actionParameters) {
		Element root = new Element(TAG_parameters);
		for (String key : actionParameters.keySet()) {
			Element name = new Element(TAG_name);
			name.setText(key);
			Element value = valueXMLSchema.combine(actionParameters.get(key));
			Element parameter = new Element(TAG_parameter);
			parameter.addContent(name);
			parameter.addContent(value);
			root.addContent(parameter);
		}
		return root;
	}

}

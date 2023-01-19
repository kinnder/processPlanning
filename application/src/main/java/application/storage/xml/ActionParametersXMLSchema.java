package application.storage.xml;

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
		final Map<String, String> actionParameters = new HashMap<>();

		for (Element parameter : root.getChildren(TAG_parameter)) {
			final String name = parameter.getChildText(TAG_name);
			Object value;
			{
				final Element element = parameter.getChild(valueXMLSchema.getSchemaName());
				value = valueXMLSchema.parse(element);
			}
			actionParameters.put(name, value.toString());
		}

		return actionParameters;
	}

	@Override
	public Element combine(Map<String, String> actionParameters) {
		final Element root = new Element(TAG_parameters);
		for (String key : actionParameters.keySet()) {
			final Element parameter = new Element(TAG_parameter);
			{
				final Element name = new Element(TAG_name);
				name.setText(key);
				parameter.addContent(name);
			}
			{
				final Element value = valueXMLSchema.combine(actionParameters.get(key));
				parameter.addContent(value);
			}
			root.addContent(parameter);
		}
		return root;
	}

}

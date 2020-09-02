package application.storage.xml;

import java.util.Map;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.Action;
import planning.model.SystemOperation;

public class SystemOperationXMLSchema implements XMLSchema<SystemOperation> {

	final private static String TAG_operation = "operation";

	final private static String TAG_name = "name";

	@Override
	public String getSchemaName() {
		return TAG_operation;
	}

	private ActionParametersXMLSchema actionParametersXMLSchema;

	public SystemOperationXMLSchema() {
		this(new ActionParametersXMLSchema());
	}

	SystemOperationXMLSchema(ActionParametersXMLSchema actionParametersXMLSchema) {
		this.actionParametersXMLSchema = actionParametersXMLSchema;
	}

	@Override
	public SystemOperation parse(Element root) throws DataConversionException {
		String name = root.getChildText(TAG_name);
		Map<String, String> actionParameters;
		{
			Element element = root.getChild(actionParametersXMLSchema.getSchemaName());
			actionParameters = actionParametersXMLSchema.parse(element);
		}

		return new SystemOperation(new Action(name), actionParameters);
	}

	@Override
	public Element combine(SystemOperation systemOperation) {
		Element root = new Element(TAG_operation);
		{
			Element element = new Element(TAG_name);
			element.setText(systemOperation.getName());
			root.addContent(element);
		}
		{
			Element element = actionParametersXMLSchema.combine(systemOperation.getActionParameters());
			root.addContent(element);
		}

		return root;
	}

}

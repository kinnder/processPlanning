package application.storage.xml;

import java.util.List;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.Action;
import planning.model.ActionFunction;

public class ActionXMLSchema implements XMLSchema<Action> {

	final private static String TAG_action = "action";

	final private static String TAG_name = "name";

	final private static String TAG_functionType = "functionType";

	final private static String VALUE_preConditionChecker = "preConditionChecker";

	final private static String VALUE_parameterUpdater = "parameterUpdater";

	@Override
	public String getSchemaName() {
		return TAG_action;
	}

	public ActionXMLSchema() {
		this(new ActionFunctionXMLSchema());
	}

	ActionXMLSchema(ActionFunctionXMLSchema actionFunctionXMLSchema) {
		this.actionFunctionXMLSchema = actionFunctionXMLSchema;
	}

	private ActionFunctionXMLSchema actionFunctionXMLSchema;

	@Override
	public Action parse(Element root) throws DataConversionException {
		String name = root.getChildText(TAG_name);
		Action action = new Action(name);
		List<Element> elements = root.getChildren(actionFunctionXMLSchema.getSchemaName());
		for (Element element : elements) {
			// TODO (2021-12-15 #50): добавить значения functionType в xsd-схему
			ActionFunction actionFunction = actionFunctionXMLSchema.parse(element);
			String functionType = element.getAttributeValue(TAG_functionType);
			if (VALUE_preConditionChecker.equals(functionType)) {
				action.registerPreConditionChecker(actionFunction);
				continue;
			}
			if (VALUE_parameterUpdater.equals(functionType)) {
				action.registerParameterUpdater(actionFunction);
				continue;
			}
		}
		return action;
	}

	@Override
	public Element combine(Action action) {
		Element root = new Element(TAG_action);

		Element name = new Element(TAG_name);
		name.setText(action.getName());
		root.addContent(name);

		for (ActionFunction preConditionChecker : action.getPreConditionCheckers()) {
			Element element = actionFunctionXMLSchema.combine(preConditionChecker);
			element.setAttribute(TAG_functionType, VALUE_preConditionChecker);
			root.addContent(element);
		}
		for (ActionFunction parameterUpdater : action.getParameterUpdaters()) {
			Element element = actionFunctionXMLSchema.combine(parameterUpdater);
			element.setAttribute(TAG_functionType, VALUE_parameterUpdater);
			root.addContent(element);
		}
		return root;
	}
}

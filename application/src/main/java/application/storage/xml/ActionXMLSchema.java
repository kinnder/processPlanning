package application.storage.xml;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.Action;
import planning.model.ActionFunction;
import planning.model.LuaScriptActionFunction;

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
		this(new LuaScriptActionFunctionXMLSchema());
	}

	ActionXMLSchema(LuaScriptActionFunctionXMLSchema luaSciptActionFunctionXMLSchema) {
		this.luaSciptActionFunctionXMLSchema = luaSciptActionFunctionXMLSchema;
	}

	private LuaScriptActionFunctionXMLSchema luaSciptActionFunctionXMLSchema;

	@Override
	public Action parse(Element root) throws DataConversionException {
		String name = root.getChildText(TAG_name);
		Action action = new Action(name);
		List<Element> elements = root.getChildren(luaSciptActionFunctionXMLSchema.getSchemaName());
		for (Element element : elements) {
			// TODO (2021-05-25 #39): заполнять атрибуты внутри ActionFunctionXMLSchema
			LuaScriptActionFunction luaScriptActionFunction = luaSciptActionFunctionXMLSchema.parse(element);
			String functionType = element.getAttributeValue(TAG_functionType);
			if (VALUE_preConditionChecker.equals(functionType)) {
				action.registerPreConditionChecker(luaScriptActionFunction);
				continue;
			}
			if (VALUE_parameterUpdater.equals(functionType)) {
				action.registerParameterUpdater(luaScriptActionFunction);
				continue;
			}
		}
		return action;
	}

	@Override
	public Element combine(Action action) {
		Element name = new Element(TAG_name);
		name.setText(action.getName());
		List<Element> elements = new ArrayList<>();
		for (ActionFunction preConditionChecker : action.getPreConditionCheckers()) {
			Element element = luaSciptActionFunctionXMLSchema.combine((LuaScriptActionFunction) preConditionChecker);
			element.setAttribute(TAG_functionType, VALUE_preConditionChecker);
			elements.add(element);
		}
		for (ActionFunction parameterUpdater : action.getParameterUpdaters()) {
			Element element = luaSciptActionFunctionXMLSchema.combine((LuaScriptActionFunction) parameterUpdater);
			element.setAttribute(TAG_functionType, VALUE_parameterUpdater);
			elements.add(element);
		}
		Element root = new Element(TAG_action);
		root.addContent(name);
		root.addContent(elements);
		return root;
	}
}

package application.storage.xml;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.Action;
import planning.model.ActionParameterUpdater;
import planning.model.ActionPreConditionChecker;

public class ActionXMLSchema implements XMLSchema<Action> {

	final private static String TAG_action = "action";

	final private static String TAG_name = "name";

	@Override
	public String getSchemaName() {
		return TAG_action;
	}

	public ActionXMLSchema() {
		this(new ParameterUpdaterXMLSchema(), new PreConditionCheckerXMLSchema());
	}

	ActionXMLSchema(ParameterUpdaterXMLSchema parameterUpdaterXMLSchema,
			PreConditionCheckerXMLSchema preConditionCheckerXMLSchema) {
		this.parameterUpdaterXMLSchema = parameterUpdaterXMLSchema;
		this.preConditionCheckerXMLSchema = preConditionCheckerXMLSchema;
	}

	private ParameterUpdaterXMLSchema parameterUpdaterXMLSchema;

	private PreConditionCheckerXMLSchema preConditionCheckerXMLSchema;

	@Override
	public Action parse(Element root) throws DataConversionException {
		String name = root.getChildText(TAG_name);
		Action action = new Action(name);
		List<Element> elements = root.getChildren(preConditionCheckerXMLSchema.getSchemaName());
		for (Element element : elements) {
			ActionPreConditionChecker preConditionChecher = preConditionCheckerXMLSchema.parse(element);
			action.registerActionPreConditionChecker(preConditionChecher);
		}
		elements = root.getChildren(parameterUpdaterXMLSchema.getSchemaName());
		for (Element element : elements) {
			ActionParameterUpdater parameterUpdater = parameterUpdaterXMLSchema.parse(element);
			action.registerActionParameterUpdater(parameterUpdater);
		}
		return action;
	}

	@Override
	public Element combine(Action action) {
		Element name = new Element(TAG_name);
		name.setText(action.getName());
		List<Element> elements = new ArrayList<>();
		for (ActionPreConditionChecker preConditionChecker : action.getPreConditionCheckers()) {
			Element element = preConditionCheckerXMLSchema.combine(preConditionChecker);
			elements.add(element);
		}
		for (ActionParameterUpdater parameterUpdater : action.getParameterUpdaters()) {
			Element element = parameterUpdaterXMLSchema.combine(parameterUpdater);
			elements.add(element);
		}
		Element root = new Element(TAG_action);
		root.addContent(name);
		root.addContent(elements);
		return root;
	}
}

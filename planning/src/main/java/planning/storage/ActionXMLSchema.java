package planning.storage;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.Action;
import planning.model.ActionParameterUpdater;
import planning.model.ActionPreConditionChecker;

public class ActionXMLSchema implements XMLSchema<Action> {

	final private static String TAG_schema = "action";

	final private static String TAG_name = "name";

	@Override
	public String getSchemaName() {
		return TAG_schema;
	}

	public ActionXMLSchema() {
		this(new ParameterUpdaterXMLSchema(), new PreConditionCheckerXMLSchema());
	}

	ActionXMLSchema(ParameterUpdaterXMLSchema parameterUpdaterXMLSchema,
			PreConditionCheckerXMLSchema preConditionCheckerXMLSchema) {
		this.parameterUpdaterSchema = parameterUpdaterXMLSchema;
		this.preConditionCheckerSchema = preConditionCheckerXMLSchema;
	}

	private ParameterUpdaterXMLSchema parameterUpdaterSchema;

	private PreConditionCheckerXMLSchema preConditionCheckerSchema;

	@Override
	public Action parse(Element root) throws DataConversionException {
		String name = root.getChildText(TAG_name);
		Action action = new Action(name);
		List<Element> elements = root.getChildren(preConditionCheckerSchema.getSchemaName());
		for (Element element : elements) {
			ActionPreConditionChecker preConditionChecher = preConditionCheckerSchema.parse(element);
			action.registerActionPreConditionChecker(preConditionChecher);
		}
		elements = root.getChildren(parameterUpdaterSchema.getSchemaName());
		for (Element element : elements) {
			ActionParameterUpdater parameterUpdater = parameterUpdaterSchema.parse(element);
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
			Element element = preConditionCheckerSchema.combine(preConditionChecker);
			elements.add(element);
		}
		for (ActionParameterUpdater parameterUpdater : action.getParameterUpdaters()) {
			Element element = parameterUpdaterSchema.combine(parameterUpdater);
			elements.add(element);
		}
		Element root = new Element(TAG_schema);
		root.addContent(name);
		root.addContent(elements);
		return root;
	}
}

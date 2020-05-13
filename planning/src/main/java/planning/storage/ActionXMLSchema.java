package planning.storage;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.Action;
import planning.model.ActionParameterUpdater;
import planning.model.ActionPreConditionChecker;

public class ActionXMLSchema implements XMLSchema<Action> {

	private ParameterUpdaterXMLSchema parameterUpdaterSchema = new ParameterUpdaterXMLSchema();

	private PreConditionCheckerXMLSchema preConditionCheckerSchema = new PreConditionCheckerXMLSchema();

	@Override
	public Action parse(Element root) throws DataConversionException {
		String name = root.getChildText("name");
		Action action = new Action(name);
		List<Element> elements = root.getChildren("preConditionChecker");
		for (Element element : elements) {
			ActionPreConditionChecker preConditionChecher = preConditionCheckerSchema.parse(element);
			action.registerActionPreConditionChecker(preConditionChecher);
		}
		elements = root.getChildren("parameterUpdater");
		for (Element element : elements) {
			ActionParameterUpdater parameterUpdater = parameterUpdaterSchema.parse(element);
			action.registerActionParameterUpdater(parameterUpdater);
		}
		return action;
	}

	@Override
	public Element combine(Action action) {
		Element name = new Element("name");
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
		Element root = new Element("action");
		root.addContent(name);
		root.addContent(elements);
		return root;
	}

}

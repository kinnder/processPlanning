package planning.storage;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.jdom2.Namespace;
import planning.method.SystemTransformations;
import planning.model.Action;
import planning.model.ActionParameterUpdater;
import planning.model.ActionPreConditionChecker;
import planning.model.SystemTemplate;
import planning.model.SystemTransformation;
import planning.model.Transformation;

public class SystemTransformationsXMLSchema implements XMLSchema<SystemTransformations> {

	@Override
	public SystemTransformations parse(Element element) throws DataConversionException {
		SystemTransformations systemTransformations = new SystemTransformations();
		List<Element> elements = element.getChildren("systemTransformation");
		for (Element e : elements) {
			SystemTransformation systemTransformation = parseSystemTransformation(e);
			systemTransformations.add(systemTransformation);
		}
		return systemTransformations;
	}

	@Override
	public Element combine(SystemTransformations systemTransformations) {
		List<Element> elements = new ArrayList<>();
		for (SystemTransformation systemTransformation : systemTransformations) {
			Element element = combineSystemTransformation(systemTransformation);
			elements.add(element);
		}
		Element root = new Element("systemTransformations");
		Namespace xsiNamespace = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		root.setAttribute("noNamespaceSchemaLocation", "../systemTransformations.xsd", xsiNamespace);
		root.addContent(elements);
		return root;
	}

	public SystemTransformation parseSystemTransformation(Element root) throws DataConversionException {
		String name = root.getChildText("name");
		Action action = parseAction(root.getChild("action"));
		SystemTemplate systemTemplate = systemTemplateSchema.parse(root.getChild("systemTemplate"));
		Transformation[] transformations = transformationsSchema.parse(root.getChild("transformations"));
		return new SystemTransformation(name, action, systemTemplate, transformations);
	}

	public Element combineSystemTransformation(SystemTransformation systemTransformation) {
		Element name = new Element("name");
		name.setText(systemTransformation.getName());
		Element action = combineAction(systemTransformation.getAction());
		Element systemTemplate = systemTemplateSchema.combine(systemTransformation.getSystemTemplate());
		Element transformations = transformationsSchema.combine(systemTransformation.getTransformations());
		Element root = new Element("systemTransformation");
		root.addContent(name);
		root.addContent(systemTemplate);
		root.addContent(transformations);
		root.addContent(action);
		return root;
	}

	public Action parseAction(Element root) throws DataConversionException {
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

	public Element combineAction(Action action) {
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

	private ParameterUpdaterXMLSchema parameterUpdaterSchema = new ParameterUpdaterXMLSchema();

	private PreConditionCheckerXMLSchema preConditionCheckerSchema = new PreConditionCheckerXMLSchema();

	private TransformationsXMLSchema transformationsSchema = new TransformationsXMLSchema();

	private SystemTemplateXMLSchema systemTemplateSchema = new SystemTemplateXMLSchema();
}

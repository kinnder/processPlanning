package planning.storage;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

import planning.method.SystemTransformations;
import planning.model.Action;
import planning.model.ActionParameterUpdater;
import planning.model.ActionPreConditionChecker;
import planning.model.AttributeTransformation;
import planning.model.LinkTransformation;
import planning.model.LuaScriptActionParameterUpdater;
import planning.model.LuaScriptActionPreConditionChecker;
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
		Transformation[] transformations = parseTransformations(root.getChild("transformations"));
		return new SystemTransformation(name, action, systemTemplate, transformations);
	}

	public Element combineSystemTransformation(SystemTransformation systemTransformation) {
		Element name = new Element("name");
		name.setText(systemTransformation.getName());
		Element action = combineAction(systemTransformation.getAction());
		Element systemTemplate = systemTemplateSchema.combine(systemTransformation.getSystemTemplate());
		Element transformations = combineTransformations(systemTransformation.getTransformations());
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
			ActionPreConditionChecker preConditionChecher = parsePreConditionChecker(element);
			action.registerActionPreConditionChecker(preConditionChecher);
		}
		elements = root.getChildren("parameterUpdater");
		for (Element element : elements) {
			ActionParameterUpdater parameterUpdater = parseParameterUpdater(element);
			action.registerActionParameterUpdater(parameterUpdater);
		}
		return action;
	}

	public Element combineAction(Action action) {
		Element name = new Element("name");
		name.setText(action.getName());
		List<Element> elements = new ArrayList<>();
		for (ActionPreConditionChecker preConditionChecker : action.getPreConditionCheckers()) {
			Element element = combinePreConditionChecker(preConditionChecker);
			elements.add(element);
		}
		for (ActionParameterUpdater parameterUpdater : action.getParameterUpdaters()) {
			Element element = combineParameterUpdater(parameterUpdater);
			elements.add(element);
		}
		Element root = new Element("action");
		root.addContent(name);
		root.addContent(elements);
		return root;
	}

	// TODO : пересмотреть положение globals
	private static Globals globals = JsePlatform.standardGlobals();

	public ActionParameterUpdater parseParameterUpdater(Element root) throws DataConversionException {
		List<Element> elements = root.getChildren("line");
		String[] lines = new String[elements.size()];
		for (Element element : elements) {
			int id = element.getAttribute("n").getIntValue() - 1;
			lines[id] = element.getText();
		}
		StringBuilder script = new StringBuilder();
		for (String line : lines) {
			script.append(line).append("\n");
		}
		return new LuaScriptActionParameterUpdater(globals, script.toString());
	}

	public Element combineParameterUpdater(ActionParameterUpdater parameterUpdater) {
		LuaScriptActionParameterUpdater luaParameterUpdater = (LuaScriptActionParameterUpdater) parameterUpdater;
		String lines[] = luaParameterUpdater.getScript().split("\n");
		Element root = new Element("parameterUpdater");
		for (int i = 0; i < lines.length; i++) {
			Element element = new Element("line");
			element.setText(lines[i]);
			element.setAttribute("n", Integer.toString(i + 1));
			root.addContent(element);
		}
		return root;
	}

	public ActionPreConditionChecker parsePreConditionChecker(Element root) throws DataConversionException {
		List<Element> elements = root.getChildren("line");
		String[] lines = new String[elements.size()];
		for (Element element : elements) {
			int id = element.getAttribute("n").getIntValue() - 1;
			lines[id] = element.getText();
		}
		StringBuilder script = new StringBuilder();
		for (String line : lines) {
			script.append(line).append("\n");
		}
		return new LuaScriptActionPreConditionChecker(globals, script.toString());
	}

	public Element combinePreConditionChecker(ActionPreConditionChecker preConditionChecker) {
		LuaScriptActionPreConditionChecker luaPreConditionChecker = (LuaScriptActionPreConditionChecker) preConditionChecker;
		String lines[] = luaPreConditionChecker.getScript().split("\n");
		Element root = new Element("preConditionChecker");
		for (int i = 0; i < lines.length; i++) {
			Element element = new Element("line");
			element.setText(lines[i]);
			element.setAttribute("n", Integer.toString(i + 1));
			root.addContent(element);
		}
		return root;
	}

	public Transformation[] parseTransformations(Element root) throws DataConversionException {
		List<Transformation> transformations = new ArrayList<>();
		List<Element> elements = root.getChildren("linkTransformation");
		for (Element element : elements) {
			transformations.add(linkTransformationSchema.parse(element));
		}
		elements = root.getChildren("attributeTransformation");
		for (Element element : elements) {
			transformations.add(attributeTransformationSchema.parse(element));
		}
		return transformations.toArray(new Transformation[0]);
	}

	public Element combineTransformations(Transformation[] transformations) {
		Element root = new Element("transformations");
		for (Transformation transformation : transformations) {
			Element element;
			if (transformation instanceof AttributeTransformation) {
				element = attributeTransformationSchema.combine((AttributeTransformation) transformation);
			} else if (transformation instanceof LinkTransformation) {
				element = linkTransformationSchema.combine((LinkTransformation) transformation);
			} else {
				element = combineTransformation(transformation);
			}
			root.addContent(element);
		}
		return root;
	}

	public Element combineTransformation(Transformation transformation) {
		Element objectId = new Element("objectId");
		objectId.setText(transformation.getObjectId());
		Element root = new Element("transformation");
		root.addContent(objectId);
		return root;
	}

	private AttributeTransformationXMLSchema attributeTransformationSchema = new AttributeTransformationXMLSchema();

	private LinkTransformationXMLSchema linkTransformationSchema = new LinkTransformationXMLSchema();

	private SystemTemplateXMLSchema systemTemplateSchema = new SystemTemplateXMLSchema();
}

package planning.storage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

import planning.model.Action;
import planning.model.ActionParameterUpdater;
import planning.model.ActionPreConditionChecker;
import planning.model.AttributeTemplate;
import planning.model.AttributeTransformation;
import planning.model.LinkTemplate;
import planning.model.LinkTransformation;
import planning.model.LuaScriptActionParameterUpdater;
import planning.model.LuaScriptActionPreConditionChecker;
import planning.model.SystemObjectTemplate;
import planning.model.SystemTemplate;
import planning.model.SystemTransformation;
import planning.model.Transformation;

public class SystemTransformationsXMLFile {

	private SystemTransformation[] systemTransformations = new SystemTransformation[0];

	public SystemTransformation[] getSystemTransformations() {
		return systemTransformations;
	}

	private SAXBuilder builder;

	public SystemTransformationsXMLFile() {
		this(new SAXBuilder());
	}

	SystemTransformationsXMLFile(SAXBuilder builder) {
		this.builder = builder;
	}

	public void load(URL resource) throws JDOMException, IOException {
		Element root = builder.build(resource).getRootElement();
		systemTransformations = parseSystemTransformations(root);
	}

	public SystemTransformation[] parseSystemTransformations(Element root) throws DataConversionException {
		List<SystemTransformation> systemTransformations = new ArrayList<>();
		List<Element> elements = root.getChildren("systemTransformation");
		for (Element element : elements) {
			SystemTransformation systemTransformation = parseSystemTransformation(element);
			systemTransformations.add(systemTransformation);
		}
		return systemTransformations.toArray(new SystemTransformation[0]);
	}

	public SystemTransformation parseSystemTransformation(Element root) throws DataConversionException {
		String name = root.getChildText("name");
		Action action = parseAction(root.getChild("action"));
		SystemTemplate systemTemplate = parseSystemTemplate(root.getChild("systemTemplate"));
		Transformation[] transformations = parseTransformations(root.getChild("transformations"));
		return new SystemTransformation(name, action, systemTemplate, transformations);
	}

	public Action parseAction(Element root) throws DataConversionException {
		String name = root.getChildText("name");
		Action action = new Action(name);
		List<Element> elements = root.getChildren("preConditionChecker");
		for (Element element : elements) {
			ActionPreConditionChecker preConditionChecher = parsePreConditionChecker(element);
			action.registerPreConditionChecker(preConditionChecher);
		}
		elements = root.getChildren("parameterUpdater");
		for (Element element : elements) {
			ActionParameterUpdater parameterUpdater = parseParameterUpdater(element);
			action.registerParameterUpdater(parameterUpdater);
		}
		return action;
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

	public Transformation[] parseTransformations(Element root) {
		List<Transformation> transformations = new ArrayList<>();
		List<Element> elements = root.getChildren("linkTransformation");
		for (Element element : elements) {
			transformations.add(parseLinkTransformation(element));
		}
		elements = root.getChildren("attributeTransformation");
		for (Element element : elements) {
			transformations.add(parseAttributeTransformation(element));
		}
		return transformations.toArray(new Transformation[0]);
	}

	public AttributeTransformation parseAttributeTransformation(Element root) {
		String objectId = root.getChildText("objectId");
		String name = root.getChildText("name");
		String value = root.getChildText("value");
		return new AttributeTransformation(objectId, name, value);
	}

	public LinkTransformation parseLinkTransformation(Element root) {
		String objectId = root.getChildText("objectId");
		String name = root.getChildText("name");
		String oldValue = root.getChildText("oldValue");
		String newValue = root.getChildText("newValue");
		return new LinkTransformation(objectId, name, oldValue, newValue);
	}

	public SystemTemplate parseSystemTemplate(Element root) {
		List<Element> elements = root.getChildren("objectTemplate");
		SystemTemplate systemTemplate = new SystemTemplate();
		for (Element element : elements) {
			SystemObjectTemplate systemObjectTemplate = parseSystemObjectTemplate(element);
			systemTemplate.addObjectTemplate(systemObjectTemplate);
		}
		return systemTemplate;
	}

	public SystemObjectTemplate parseSystemObjectTemplate(Element root) {
		String objectId = root.getChildText("objectId");
		SystemObjectTemplate objectTemplate = new SystemObjectTemplate(objectId);
		List<Element> elements = root.getChildren("attributeTemplate");
		for (Element element : elements) {
			AttributeTemplate attributeTemplate = parseAtttributeTemplate(element);
			objectTemplate.addAttributeTemplate(attributeTemplate);
		}
		elements = root.getChildren("linkTemplate");
		for (Element element : elements) {
			LinkTemplate linkTemplate = parseLinkTemplate(element);
			objectTemplate.addLinkTemplate(linkTemplate);
		}
		return objectTemplate;
	}

	public LinkTemplate parseLinkTemplate(Element root) {
		String name = root.getChildText("name");
		String value = root.getChildText("value");
		return new LinkTemplate(name, value);
	}

	public AttributeTemplate parseAtttributeTemplate(Element root) {
		String name = root.getChildText("name");
		String value = root.getChildText("value");
		return new AttributeTemplate(name, value);
	}
}

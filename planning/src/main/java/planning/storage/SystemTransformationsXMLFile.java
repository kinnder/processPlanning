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

	public void load(URL resource) throws JDOMException, IOException {
		Element root = new SAXBuilder().build(resource).getRootElement();
		parse(root);
	}

	public void parse(Element root) throws DataConversionException {
		List<Element> systemTransformations = root.getChildren("systemTransformation");
		for (Element systemTransformation : systemTransformations) {
			SystemTransformation element = parseSystemTransformation(systemTransformation);
			elements.add(element);
		}
	}

	private SystemTransformation parseSystemTransformation(Element root) throws DataConversionException {
		Element name = root.getChild("name");
		Element action = root.getChild("action");
		Element systemTemplate = root.getChild("systemTemplate");
		Element transformations = root.getChild("transformations");

		return new SystemTransformation(parseName(name), parseAction(action), parseSystemTemplate(systemTemplate),
				parseTransformations(transformations));
	}

	private String parseName(Element root) {
		return root.getText();
	}

	private Action parseAction(Element root) throws DataConversionException {
		Element name = root.getChild("name");
		List<Element> preConditionCheckers = root.getChildren("preConditionChecker");
		List<Element> parameterUpdaters = root.getChildren("parameterUpdater");

		Action action = new Action(parseName(name));
		for (Element preConditionChecher : preConditionCheckers) {
			ActionPreConditionChecker checker = parsePreConditionChecker(preConditionChecher);
			action.registerPreConditionChecker(checker);
		}
		for (Element parameterUpdater : parameterUpdaters) {
			ActionParameterUpdater updater = parseParameterUpdater(parameterUpdater);
			action.registerParameterUpdater(updater);
		}

		return action;
	}

	// TODO : пересмотреть положение globals
	private static Globals globals = JsePlatform.standardGlobals();

	private ActionParameterUpdater parseParameterUpdater(Element root) throws DataConversionException {
		List<Element> lines = root.getChildren("line");
		String[] codes = new String[lines.size()];
		for (Element line : lines) {
			int id = line.getAttribute("n").getIntValue() - 1;
			codes[id] = line.getText();
		}
		StringBuilder script = new StringBuilder();
		for (String code : codes) {
			script.append(code).append("\n");
		}
		return new LuaScriptActionParameterUpdater(globals, script.toString());
	}

	private ActionPreConditionChecker parsePreConditionChecker(Element root) throws DataConversionException {
		List<Element> lines = root.getChildren("line");
		String[] codes = new String[lines.size()];
		for (Element line : lines) {
			int id = line.getAttribute("n").getIntValue();
			codes[id] = line.getText();
		}
		StringBuilder script = new StringBuilder();
		for (String code : codes) {
			script.append(code).append("\n");
		}
		return new LuaScriptActionPreConditionChecker(globals, script.toString());
	}

	private Transformation[] parseTransformations(Element root) {
		List<Element> linkTransformations = root.getChildren("linkTransformation");
		List<Element> attributeTransformations = root.getChildren("attributeTransformation");
		List<Transformation> transformations = new ArrayList<>();
		for (Element linkTransformation : linkTransformations) {
			transformations.add(parseLinkTransformation(linkTransformation));
		}
		for (Element attributeTransformation : attributeTransformations) {
			transformations.add(parseAttributeTransformation(attributeTransformation));
		}
		return transformations.toArray(new Transformation[0]);
	}

	private AttributeTransformation parseAttributeTransformation(Element root) {
		String objectId = root.getChild("objectId").getText();
		String name = root.getChild("name").getText();
		String value = root.getChild("value").getText();
		return new AttributeTransformation(objectId, name, value);
	}

	private LinkTransformation parseLinkTransformation(Element root) {
		String objectId = root.getChild("objectId").getText();
		String name = root.getChild("name").getText();
		String oldValue = root.getChild("oldValue").getText();
		String newValue = root.getChild("newValue").getText();
		return new LinkTransformation(objectId, name, oldValue, newValue);
	}

	private SystemTemplate parseSystemTemplate(Element root) {
		List<Element> objectTemplates = root.getChildren("objectTemplate");
		SystemTemplate systemTemplate = new SystemTemplate();
		for (Element objectTemplate : objectTemplates) {
			SystemObjectTemplate object = parseSystemObjectTemplate(objectTemplate);
			systemTemplate.addObjectTemplate(object);
		}
		return systemTemplate;
	}

	private SystemObjectTemplate parseSystemObjectTemplate(Element root) {
		String objectId = root.getChild("objectId").getText();
		List<Element> attributeTemplates = root.getChildren("attributeTemplate");
		List<Element> linkTemplates = root.getChildren("linkTemplate");
		SystemObjectTemplate objectTemplate = new SystemObjectTemplate(objectId);
		for (Element attributeTemplate : attributeTemplates) {
			AttributeTemplate attribute = parseAtttributeTemplate(attributeTemplate);
			objectTemplate.addAttributeTemplate(attribute);
		}
		for (Element linkTemplate : linkTemplates) {
			LinkTemplate link = parseLinkTemplate(linkTemplate);
			objectTemplate.addLinkTemplate(link);
		}
		return objectTemplate;
	}

	private LinkTemplate parseLinkTemplate(Element root) {
		String name = root.getChild("name").getText();
		String value = root.getChild("value").getText();
		return new LinkTemplate(name, value);
	}

	private AttributeTemplate parseAtttributeTemplate(Element root) {
		String name = root.getChild("name").getText();
		String value = root.getChild("value").getText();
		return new AttributeTemplate(name, value);
	}

	public SystemTransformationsXMLFile() {
		elements = new ArrayList<>();
	}

	private List<SystemTransformation> elements;

	public SystemTransformation[] getElements() {
		return elements.toArray(new SystemTransformation[0]);
	}
}

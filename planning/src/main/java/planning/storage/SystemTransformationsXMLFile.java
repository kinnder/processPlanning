package planning.storage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import planning.model.Action;
import planning.model.ActionParameterUpdater;
import planning.model.ActionPreConditionChecker;
import planning.model.SystemTemplate;
import planning.model.SystemTransformation;
import planning.model.Transformation;

// TODO : remove
@SuppressWarnings("PMD")
public class SystemTransformationsXMLFile {

	public void load(URL resource) throws JDOMException, IOException {
		Element root = new SAXBuilder().build(resource).getRootElement();
		parse(root);
	}

	public void parse(Element root) {
		List<Element> systemTransformations = root.getChildren("systemTransformation");
		for (Element systemTransformation : systemTransformations) {
			SystemTransformation element = parseSystemTransformation(systemTransformation);
			elements.add(element);
		}
	}

	private SystemTransformation parseSystemTransformation(Element root) {
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

	private Action parseAction(Element root) {
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

	private ActionParameterUpdater parseParameterUpdater(Element root) {
		// TODO Auto-generated method stub
		return null;
	}

	private ActionPreConditionChecker parsePreConditionChecker(Element root) {
		// TODO Auto-generated method stub
		return null;
	}

	private Transformation[] parseTransformations(Element root) {
		// TODO Auto-generated method stub
		return null;
	}

	private SystemTemplate parseSystemTemplate(Element root) {
		// TODO Auto-generated method stub
		return null;
	}

	public SystemTransformationsXMLFile() {
		elements = new ArrayList<>();
	}

	private List<SystemTransformation> elements;

	public SystemTransformation[] getElements() {
		return elements.toArray(new SystemTransformation[0]);
	}
}

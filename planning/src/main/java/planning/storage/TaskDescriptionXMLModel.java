package planning.storage;

import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.jdom2.Namespace;

import planning.method.TaskDescription;
import planning.model.Attribute;
import planning.model.Link;
import planning.model.System;
import planning.model.SystemObject;

public class TaskDescriptionXMLModel extends ValueXMLModel implements XMLModel {

	@Override
	public void parse(Element element) throws DataConversionException {
		taskDescription = parseTaskDescription(element);
	}

	@Override
	public Element combine() {
		return combineTaskDescription(taskDescription);
	}

	private TaskDescription taskDescription = new TaskDescription();

	public TaskDescription getTaskDescription() {
		return this.taskDescription;
	}

	public void setTaskDescription(TaskDescription taskDescription) {
		this.taskDescription = taskDescription;
	}

	public TaskDescription parseTaskDescription(Element root) {
		TaskDescription taskDescription = new TaskDescription();

		System initialSystem = parseSystem(root.getChild("initialSystem"));
		taskDescription.setInitialSystem(initialSystem);

		System finalSystem = parseSystem(root.getChild("finalSystem"));
		taskDescription.setFinalSystem(finalSystem);

		return taskDescription;
	}

	public Element combineTaskDescription(TaskDescription taskDescription) {
		Element root = new Element("taskDescription");
		Namespace xsiNamespace = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		root.setAttribute("noNamespaceSchemaLocation", "../taskDescription.xsd", xsiNamespace);

		Element initialSystem = combineSystem(taskDescription.getInitialSystem());
		initialSystem.setName("initialSystem");
		root.addContent(initialSystem);

		Element finalSystem = combineSystem(taskDescription.getFinalSystem());
		finalSystem.setName("finalSystem");
		root.addContent(finalSystem);

		return root;
	}

	private System parseSystem(Element root) {
		System system = new System();

		for (Element element : root.getChildren("systemObject")) {
			SystemObject object = parseSystemObject(element);
			system.addObject(object);
		}

		return system;
	}

	private Element combineSystem(System system) {
		Element root = new Element("system");
		for (SystemObject systemObject : system.getObjects()) {
			Element element = combineSystemObject(systemObject);
			root.addContent(element);
		}

		return root;
	}

	private SystemObject parseSystemObject(Element root) {
		String name = root.getChildText("name");
		String id = root.getChildText("id");

		SystemObject object = new SystemObject(name, id);

		for (Element element : root.getChildren("attribute")) {
			Attribute attribute = parseAttribute(element);
			object.addAttribute(attribute);
		}

		for (Element element : root.getChildren("link")) {
			Link link = parseLink(element);
			object.addLink(link);
		}

		return object;
	}

	private Element combineSystemObject(SystemObject systemObject) {
		Element root = new Element("systemObject");

		Element name = new Element("name");
		name.setText(systemObject.getName());
		root.addContent(name);

		Element id = new Element("id");
		id.setText(systemObject.getId());
		root.addContent(id);

		for (Attribute attribute : systemObject.getAttributes()) {
			Element element = combineAttribute(attribute);
			root.addContent(element);
		}

		for (Link link : systemObject.getLinks()) {
			Element element = combineLink(link);
			root.addContent(element);
		}

		return root;
	}

	private Link parseLink(Element root) {
		String name = root.getChildText("name");
		String objectId = root.getChildText("objectId");
		return new Link(name, objectId);
	}

	private Element combineLink(Link link) {
		Element root = new Element("link");

		Element name = new Element("name");
		name.setText(link.getName());
		root.addContent(name);

		String linkObjectId = link.getObjectId();
		if (linkObjectId != null) {
			Element objectId = new Element("objectId");
			objectId.setText(linkObjectId);
			root.addContent(objectId);
		}
		return root;
	}

	private Attribute parseAttribute(Element root) {
		String name = root.getChildText("name");
		Object value = parseValue(root.getChild("value"));
		return new Attribute(name, value);
	}

	private Element combineAttribute(Attribute attribute) {
		Element root = new Element("attribute");

		Element name = new Element("name");
		name.setText(attribute.getName());
		root.addContent(name);

		Object attributeValue = attribute.getValue();
		if (attributeValue != null) {
			Element value = combineValue(attributeValue);
			root.addContent(value);
		}
		return root;
	}
}

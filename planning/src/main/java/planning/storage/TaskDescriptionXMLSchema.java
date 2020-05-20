package planning.storage;

import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.jdom2.Namespace;

import planning.method.TaskDescription;
import planning.model.System;
import planning.model.SystemObject;

public class TaskDescriptionXMLSchema implements XMLSchema<TaskDescription> {

	@Override
	public TaskDescription parse(Element element) throws DataConversionException {
		TaskDescription taskDescription = new TaskDescription();

		System initialSystem = parseSystem(element.getChild("initialSystem"));
		taskDescription.setInitialSystem(initialSystem);

		System finalSystem = parseSystem(element.getChild("finalSystem"));
		taskDescription.setFinalSystem(finalSystem);

		return taskDescription;
	}

	@Override
	public Element combine(TaskDescription taskDescription) {
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

	public System parseSystem(Element root) throws DataConversionException {
		System system = new System();

		for (Element element : root.getChildren("systemObject")) {
			SystemObject object = systemObjectSchema.parse(element);
			system.addObject(object);
		}

		return system;
	}

	public Element combineSystem(System system) {
		Element root = new Element("system");
		for (SystemObject systemObject : system.getObjects()) {
			Element element = systemObjectSchema.combine(systemObject);
			root.addContent(element);
		}

		return root;
	}

	private SystemObjectXMLSchema systemObjectSchema = new SystemObjectXMLSchema();
}

package planning.storage;

import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.jdom2.Namespace;

import planning.method.TaskDescription;
import planning.model.System;

public class TaskDescriptionXMLSchema implements XMLSchema<TaskDescription> {

	final private static String TAG_schema = "taskDescription";

	@Override
	public String getSchemaName() {
		return TAG_schema;
	}

	private SystemXMLSchema systemSchema = new SystemXMLSchema();

	@Override
	public TaskDescription parse(Element element) throws DataConversionException {
		TaskDescription taskDescription = new TaskDescription();

		System initialSystem = systemSchema.parse(element.getChild("initialSystem"));
		taskDescription.setInitialSystem(initialSystem);

		System finalSystem = systemSchema.parse(element.getChild("finalSystem"));
		taskDescription.setFinalSystem(finalSystem);

		return taskDescription;
	}

	@Override
	public Element combine(TaskDescription taskDescription) {
		Element root = new Element("taskDescription");
		Namespace xsiNamespace = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		root.setAttribute("noNamespaceSchemaLocation", "../taskDescription.xsd", xsiNamespace);

		Element initialSystem = systemSchema.combine(taskDescription.getInitialSystem());
		initialSystem.setName("initialSystem");
		root.addContent(initialSystem);

		Element finalSystem = systemSchema.combine(taskDescription.getFinalSystem());
		finalSystem.setName("finalSystem");
		root.addContent(finalSystem);

		return root;
	}
}

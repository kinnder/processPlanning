package planning.storage;

import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.jdom2.Namespace;

import planning.method.TaskDescription;
import planning.model.System;

public class TaskDescriptionXMLSchema implements XMLSchema<TaskDescription> {

	final private static String TAG_taskDescription = "taskDescription";

	final private static String TAG_finalSystem = "finalSystem";

	final private static String TAG_initialSystem = "initialSystem";

	@Override
	public String getSchemaName() {
		return TAG_taskDescription;
	}

	public TaskDescriptionXMLSchema() {
		this(new SystemXMLSchema());
	}

	TaskDescriptionXMLSchema(SystemXMLSchema systemXMLSchema) {
		this.systemSchema = systemXMLSchema;
	}

	private SystemXMLSchema systemSchema;

	@Override
	public TaskDescription parse(Element element) throws DataConversionException {
		TaskDescription taskDescription = new TaskDescription();

		System initialSystem = systemSchema.parse(element.getChild(TAG_initialSystem));
		taskDescription.setInitialSystem(initialSystem);

		System finalSystem = systemSchema.parse(element.getChild(TAG_finalSystem));
		taskDescription.setFinalSystem(finalSystem);

		return taskDescription;
	}

	@Override
	public Element combine(TaskDescription taskDescription) {
		Element root = new Element(TAG_taskDescription);
		Namespace xsiNamespace = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		root.setAttribute("noNamespaceSchemaLocation", "../taskDescription.xsd", xsiNamespace);

		Element initialSystem = systemSchema.combine(taskDescription.getInitialSystem());
		initialSystem.setName(TAG_initialSystem);
		root.addContent(initialSystem);

		Element finalSystem = systemSchema.combine(taskDescription.getFinalSystem());
		finalSystem.setName(TAG_finalSystem);
		root.addContent(finalSystem);

		return root;
	}
}

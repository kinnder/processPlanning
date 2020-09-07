package application.storage;

import java.io.IOException;

import org.jdom2.JDOMException;

import application.storage.xml.SystemTransformationsXMLFile;
import application.storage.xml.TaskDescriptionXMLFile;
import planning.method.SystemTransformations;
import planning.method.TaskDescription;

public class PersistanceStorage {

	SystemTransformationsXMLFile transformationsXMLFile;

	TaskDescriptionXMLFile taskXMLFile;

	public PersistanceStorage() {
		this(new SystemTransformationsXMLFile(), new TaskDescriptionXMLFile());
	}

	PersistanceStorage(SystemTransformationsXMLFile transformationsXMLFile, TaskDescriptionXMLFile taskXMLFile) {
		this.transformationsXMLFile = transformationsXMLFile;
		this.taskXMLFile = taskXMLFile;
	}

	public void saveSystemTransformations(SystemTransformations systemTransformations, String path) throws IOException {
		transformationsXMLFile.save(systemTransformations, path);
	}

	public void saveTaskDescription(TaskDescription taskDescription, String path) throws IOException {
		taskXMLFile.save(taskDescription, path);
	}

	public SystemTransformations loadSystemTransformations(String path) throws IOException, JDOMException {
		return transformationsXMLFile.load(path);
	}

	public TaskDescription loadTaskDescription(String path) throws IOException, JDOMException {
		return taskXMLFile.load(path);
	}
}

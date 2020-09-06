package application.storage;

import java.io.IOException;

import org.jdom2.JDOMException;

import application.storage.xml.SystemTransformationsXMLFile;
import application.storage.xml.TaskDescriptionXMLFile;
import planning.method.SystemTransformations;
import planning.method.TaskDescription;

public class PersistanceStorage {

	private StorageType storageType;

	SystemTransformationsXMLFile transformationsXMLFile = new SystemTransformationsXMLFile();

	TaskDescriptionXMLFile taskXMLFile = new TaskDescriptionXMLFile();

	public PersistanceStorage(StorageType storageType) {
		this.storageType = storageType;
	}

	public void save(SystemTransformations systemTransformations, String path) throws IOException {
		switch (storageType) {
		case xml:
//			transformationsXMLFile.setObject(systemTransformations);
//			transformationsXMLFile.save(path);
			break;
		default:
			break;
		}
	}

	public void save(TaskDescription taskDescription, String path) throws IOException {
		switch (storageType) {
		case xml:
//			taskXMLFile.setObject(taskDescription);
//			taskXMLFile.save(path);
			break;
		default:
			break;
		}
	}

	public void load(SystemTransformations systemTransformations, String path) throws IOException, JDOMException {
		switch (storageType) {
		case xml:
//			transformationsXMLFile.load(path);
//			systemTransformations = transformationsXMLFile.getObject();
			// v2
			// transformationsXMLFile.setObject(systemTransformations);
			// transformations.load(path);
			break;
		default:
			break;
		}
	}

	public void load(TaskDescription taskDescription, String path) throws IOException, JDOMException {
		switch (storageType) {
		case xml:
//			taskXMLFile.load(path);
//			taskDescription = taskXMLFile.getObject();
			break;
		default:
			break;
		}
	}
}

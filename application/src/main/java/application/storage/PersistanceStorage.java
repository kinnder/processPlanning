package application.storage;

import java.io.IOException;

import org.jdom2.JDOMException;

import application.storage.xml.NodeNetworkXMLFile;
import application.storage.xml.SystemProcessXMLFile;
import application.storage.xml.SystemTransformationsXMLFile;
import application.storage.xml.TaskDescriptionXMLFile;
import planning.method.NodeNetwork;
import planning.method.SystemTransformations;
import planning.method.TaskDescription;
import planning.model.SystemProcess;

public class PersistanceStorage {

	SystemTransformationsXMLFile transformationsXMLFile = new SystemTransformationsXMLFile();

	TaskDescriptionXMLFile taskXMLFile = new TaskDescriptionXMLFile();

	SystemProcessXMLFile processXMLFile = new SystemProcessXMLFile();

	NodeNetworkXMLFile nodeNetworkXMLFile = new NodeNetworkXMLFile();

	public PersistanceStorage() {
	}

	PersistanceStorage(SystemTransformationsXMLFile transformationsXMLFile, TaskDescriptionXMLFile taskXMLFile,
			SystemProcessXMLFile processXMLFile, NodeNetworkXMLFile nodeNetworkXMLFile) {
		this.transformationsXMLFile = transformationsXMLFile;
		this.taskXMLFile = taskXMLFile;
		this.processXMLFile = processXMLFile;
		this.nodeNetworkXMLFile = nodeNetworkXMLFile;
	}

	public void saveSystemTransformations(SystemTransformations systemTransformations, String path) throws IOException {
		transformationsXMLFile.save(systemTransformations, path);
	}

	public void saveTaskDescription(TaskDescription taskDescription, String path) throws IOException {
		taskXMLFile.save(taskDescription, path);
	}

	public void saveSystemProcess(SystemProcess systemProcess, String path) throws IOException {
		processXMLFile.save(systemProcess, path);
	}

	public void saveNodeNetwork(NodeNetwork nodeNetwork, String path) throws IOException {
		nodeNetworkXMLFile.save(nodeNetwork, path);
	}

	public SystemTransformations loadSystemTransformations(String path) throws IOException, JDOMException {
		return transformationsXMLFile.load(path);
	}

	public TaskDescription loadTaskDescription(String path) throws IOException, JDOMException {
		return taskXMLFile.load(path);
	}
}

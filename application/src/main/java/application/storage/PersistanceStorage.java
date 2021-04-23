package application.storage;

import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.jdom2.JDOMException;

import application.storage.owl.NodeNetworkOWLFile;
import application.storage.owl.SystemProcessOWLFile;
import application.storage.owl.SystemTransformationsOWLFile;
import application.storage.owl.TaskDescriptionOWLFile;
import application.storage.xml.NodeNetworkXMLFile;
import application.storage.xml.SystemProcessXMLFile;
import application.storage.xml.SystemTransformationsXMLFile;
import application.storage.xml.TaskDescriptionXMLFile;
import planning.method.NodeNetwork;
import planning.method.SystemTransformations;
import planning.method.TaskDescription;
import planning.model.SystemProcess;

public class PersistanceStorage {

	private SystemTransformationsXMLFile transformationsXMLFile;

	private SystemTransformationsOWLFile transformationsOWLFile;

	private TaskDescriptionXMLFile taskXMLFile;

	private TaskDescriptionOWLFile taskOWLFile;

	private SystemProcessXMLFile processXMLFile;

	private SystemProcessOWLFile processOWLFile;

	private NodeNetworkXMLFile nodeNetworkXMLFile;

	private NodeNetworkOWLFile nodeNetworkOWLFile;

	public PersistanceStorage() {
		this(new SystemTransformationsXMLFile(), new TaskDescriptionXMLFile(), new SystemProcessXMLFile(),
				new NodeNetworkXMLFile(), new SystemTransformationsOWLFile(), new TaskDescriptionOWLFile(),
				new SystemProcessOWLFile(), new NodeNetworkOWLFile());
	}

	PersistanceStorage(SystemTransformationsXMLFile transformationsXMLFile, TaskDescriptionXMLFile taskXMLFile,
			SystemProcessXMLFile processXMLFile, NodeNetworkXMLFile nodeNetworkXMLFile,
			SystemTransformationsOWLFile transformationsOWLFile, TaskDescriptionOWLFile taskOWLFile,
			SystemProcessOWLFile processOWLFile, NodeNetworkOWLFile nodeNetworkOWLFile) {
		this.transformationsXMLFile = transformationsXMLFile;
		this.taskXMLFile = taskXMLFile;
		this.processXMLFile = processXMLFile;
		this.nodeNetworkXMLFile = nodeNetworkXMLFile;
		this.transformationsOWLFile = transformationsOWLFile;
		this.taskOWLFile = taskOWLFile;
		this.processOWLFile = processOWLFile;
		this.nodeNetworkOWLFile = nodeNetworkOWLFile;
	}

	public void saveSystemTransformations(SystemTransformations systemTransformations, String path) throws IOException {
		if ("owl".equals(FilenameUtils.getExtension(path))) {
			transformationsOWLFile.save(systemTransformations, path);
		} else {
			transformationsXMLFile.save(systemTransformations, path);
		}
	}

	public void saveTaskDescription(TaskDescription taskDescription, String path) throws IOException {
		if ("owl".equals(FilenameUtils.getExtension(path))) {
			taskOWLFile.save(taskDescription, path);
		} else {
			taskXMLFile.save(taskDescription, path);
		}
	}

	public void saveSystemProcess(SystemProcess systemProcess, String path) throws IOException {
		if ("owl".equals(FilenameUtils.getExtension(path))) {
			processOWLFile.save(systemProcess, path);
		} else {
			processXMLFile.save(systemProcess, path);
		}
	}

	public void saveNodeNetwork(NodeNetwork nodeNetwork, String path) throws IOException {
		if ("owl".equals(FilenameUtils.getExtension(path))) {
			nodeNetworkOWLFile.save(nodeNetwork, path);
		} else {
			nodeNetworkXMLFile.save(nodeNetwork, path);
		}
	}

	public SystemTransformations loadSystemTransformations(String path) throws IOException, JDOMException {
		if ("owl".equals(FilenameUtils.getExtension(path))) {
			return transformationsOWLFile.load(path);
		}
		return transformationsXMLFile.load(path);
	}

	public TaskDescription loadTaskDescription(String path) throws IOException, JDOMException {
		if ("owl".equals(FilenameUtils.getExtension(path))) {
			return taskOWLFile.load(path);
		}
		return taskXMLFile.load(path);
	}
}

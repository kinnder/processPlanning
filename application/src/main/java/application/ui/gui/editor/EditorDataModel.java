package application.ui.gui.editor;

import java.util.Collection;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import planning.method.NodeNetwork;
import planning.method.SystemTransformations;
import planning.method.TaskDescription;
import planning.model.System;
import planning.model.SystemObject;
import planning.model.SystemProcess;

public class EditorDataModel extends DefaultTreeModel {

	private static final long serialVersionUID = 2748742512319035267L;

	private DefaultMutableTreeNode projectNode;

	private DefaultMutableTreeNode taskDescriptionNode;

	private DefaultMutableTreeNode systemTransformationsNode;

	private DefaultMutableTreeNode nodeNetworkNode;

	private DefaultMutableTreeNode systemProcessNode;

	public EditorDataModel() {
		super(null);

		projectNode = new DefaultMutableTreeNode("Project");
		taskDescriptionNode = new DefaultMutableTreeNode("Task Description");
		systemTransformationsNode = new DefaultMutableTreeNode("System Transformations");
		nodeNetworkNode = new DefaultMutableTreeNode("Node Network");
		systemProcessNode = new DefaultMutableTreeNode("Process");

		projectNode.add(taskDescriptionNode);
		projectNode.add(systemTransformationsNode);
		projectNode.add(nodeNetworkNode);
		projectNode.add(systemProcessNode);

		this.root = projectNode;
	}

	public void loadTaskDescription(TaskDescription taskDescription) {
		System system;
		DefaultMutableTreeNode systemNode;

		// TODO (2022-09-22 #72): удалить
		// taskDescription = AssemblyLine.getTaskDescription();

		taskDescriptionNode.removeAllChildren();
		taskDescriptionNode.setUserObject(taskDescription);

		system = taskDescription.getInitialSystem();
		system.name = "initialSystem";
		systemNode = createSystemNode(system);
		taskDescriptionNode.add(systemNode);

		system = taskDescription.getFinalSystem();
		system.name = "finalSystem";
		systemNode = createSystemNode(system);
		taskDescriptionNode.add(systemNode);

		reload();
	}

	public TaskDescription saveTaskDescription() {
		return (TaskDescription) taskDescriptionNode.getUserObject();
	}

	private DefaultMutableTreeNode createSystemNode(System system) {
		DefaultMutableTreeNode systemNode = new DefaultMutableTreeNode(system);
		Collection<SystemObject> objects = system.getObjects();
		for (SystemObject object : objects) {
			DefaultMutableTreeNode objectNode = new DefaultMutableTreeNode(object);
			systemNode.add(objectNode);
		}
		return systemNode;
	}

	public void loadSystemTransformations(SystemTransformations systemTransformations) {
		DefaultMutableTreeNode systemTransformationNode;
		DefaultMutableTreeNode systemNode;
		DefaultMutableTreeNode objectNode;
		DefaultMutableTreeNode transformationsNode;
		DefaultMutableTreeNode transformationNode;
		DefaultMutableTreeNode actionNode;
		DefaultMutableTreeNode functionNode;
		systemTransformationsNode.removeAllChildren();

		systemTransformationNode = new DefaultMutableTreeNode("2D-Pocket");
		systemTransformationsNode.add(systemTransformationNode);
		systemNode = new DefaultMutableTreeNode("System Template");
		systemTransformationNode.add(systemNode);
		objectNode = new DefaultMutableTreeNode("template-object");
		systemNode.add(objectNode);
		transformationsNode = new DefaultMutableTreeNode("Transformations");
		systemTransformationNode.add(transformationsNode);
		transformationNode = new DefaultMutableTreeNode("attribute-transformation");
		transformationsNode.add(transformationNode);
		actionNode = new DefaultMutableTreeNode("Action");
		systemTransformationNode.add(actionNode);
		functionNode = new DefaultMutableTreeNode("action-function");
		actionNode.add(functionNode);

		reload();
	}

	public void loadNodeNetwork(NodeNetwork nodeNetwork) {
		DefaultMutableTreeNode nodesNode;
		DefaultMutableTreeNode nodeNode;
		DefaultMutableTreeNode systemNode;
		DefaultMutableTreeNode objectNode;
		DefaultMutableTreeNode edgesNode;
		DefaultMutableTreeNode edgeNode;
		nodeNetworkNode.removeAllChildren();

		nodesNode = new DefaultMutableTreeNode("Nodes");
		nodeNetworkNode.add(nodesNode);
		nodeNode = new DefaultMutableTreeNode("node-id-0000");
		nodesNode.add(nodeNode);
		systemNode = new DefaultMutableTreeNode("system-id");
		nodeNode.add(systemNode);
		objectNode = new DefaultMutableTreeNode("object-id");
		systemNode.add(objectNode);
		edgesNode = new DefaultMutableTreeNode("Edges");
		nodeNetworkNode.add(edgesNode);
		edgeNode = new DefaultMutableTreeNode("edge-id-0000");
		edgesNode.add(edgeNode);

		reload();
	}

	public void loadSystemProcess(SystemProcess systemProcess) {
		DefaultMutableTreeNode processNode;
		systemProcessNode.removeAllChildren();

		processNode = new DefaultMutableTreeNode("process-id-0000");
		systemProcessNode.add(processNode);

		reload();
	}
}

package application.ui.gui.editor;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import planning.method.NodeNetwork;
import planning.method.SystemTransformations;
import planning.method.TaskDescription;
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
		DefaultMutableTreeNode systemNode, objectNode;
		taskDescriptionNode.removeAllChildren();

		systemNode = new DefaultMutableTreeNode("initialSystem");
		taskDescriptionNode.add(systemNode);
		objectNode = new DefaultMutableTreeNode("object-workpiece");
		systemNode.add(objectNode);
		objectNode = new DefaultMutableTreeNode("object-workpiece-pad");
		systemNode.add(objectNode);
		objectNode = new DefaultMutableTreeNode("object-workpiece-pad-sketch");
		systemNode.add(objectNode);
		objectNode = new DefaultMutableTreeNode("object-workpiece-plane");
		systemNode.add(objectNode);
		objectNode = new DefaultMutableTreeNode("object-part");
		systemNode.add(objectNode);
		objectNode = new DefaultMutableTreeNode("object-part-pad");
		systemNode.add(objectNode);
		objectNode = new DefaultMutableTreeNode("object-part-pad-sketch");
		systemNode.add(objectNode);
		objectNode = new DefaultMutableTreeNode("object-part-plane");
		systemNode.add(objectNode);
		objectNode = new DefaultMutableTreeNode("object-part-pocket");
		systemNode.add(objectNode);
		objectNode = new DefaultMutableTreeNode("object-part-pocket-sketch");
		systemNode.add(objectNode);

		systemNode = new DefaultMutableTreeNode("finalSystem");
		taskDescriptionNode.add(systemNode);
		objectNode = new DefaultMutableTreeNode("object-part-pocket");
		systemNode.add(objectNode);
	}

	public void loadSystemTransformations(SystemTransformations systemTransformations) {
		DefaultMutableTreeNode systemTransformationNode, systemNode, objectNode, transformationsNode,
				transformationNode, actionNode, functionNode;
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
	}

	public void loadNodeNetwork(NodeNetwork nodeNetwork) {
		DefaultMutableTreeNode nodesNode, nodeNode, systemNode, objectNode, edgesNode, edgeNode;
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
	}

	public void loadSystemProcess(SystemProcess systemProcess) {
		DefaultMutableTreeNode processNode;
		systemProcessNode.removeAllChildren();

		processNode = new DefaultMutableTreeNode("process-id-0000");
		systemProcessNode.add(processNode);
	}
}

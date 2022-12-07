package application.ui.gui.editor;

import java.util.Collection;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import planning.method.NodeNetwork;
import planning.method.SystemTransformations;
import planning.method.TaskDescription;
import planning.model.Action;
import planning.model.ActionFunction;
import planning.model.System;
import planning.model.SystemObject;
import planning.model.SystemObjectTemplate;
import planning.model.SystemProcess;
import planning.model.SystemTemplate;
import planning.model.SystemTransformation;
import planning.model.Transformation;

// TODO (2022-11-16 #72): saveXXX и loadXXX методы переименовать в getXXX и setXXX
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
		system.setName("initialSystem");
		systemNode = createSystemNode(system);
		taskDescriptionNode.add(systemNode);

		system = taskDescription.getFinalSystem();
		system.setName("finalSystem");
		systemNode = createSystemNode(system);
		taskDescriptionNode.add(systemNode);

		reload();
	}

	public TaskDescription saveTaskDescription() {
		return (TaskDescription) taskDescriptionNode.getUserObject();
	}

	public DefaultMutableTreeNode createSystemNode(System system) {
		DefaultMutableTreeNode systemNode = new DefaultMutableTreeNode(system);
		Collection<SystemObject> objects = system.getObjects();
		for (SystemObject object : objects) {
			DefaultMutableTreeNode objectNode = createObjectNode(object);
			systemNode.add(objectNode);
		}
		return systemNode;
	}

	public DefaultMutableTreeNode createObjectNode(SystemObject object) {
		return new DefaultMutableTreeNode(object);
	}

	public DefaultMutableTreeNode createSystemTransformationNode(SystemTransformation systemTransformation) {
		DefaultMutableTreeNode systemTransformationNode;
		DefaultMutableTreeNode systemTemplateNode;
		DefaultMutableTreeNode transformationsNode;
		DefaultMutableTreeNode actionNode;

		systemTransformationNode = new DefaultMutableTreeNode(systemTransformation);

		systemTemplateNode = createSystemTemplateNode(systemTransformation.getSystemTemplate());
		systemTransformationNode.add(systemTemplateNode);

		transformationsNode = createTransformationsNode(systemTransformation.getTransformations());
		systemTransformationNode.add(transformationsNode);

		actionNode = createActionNode(systemTransformation.getAction());
		systemTransformationNode.add(actionNode);

		return systemTransformationNode;
	}

	public DefaultMutableTreeNode createActionNode(Action action) {
		DefaultMutableTreeNode actionNode = new DefaultMutableTreeNode(action);
		// TODO (2022-12-07 #73): все функции должны обрабатываться в одной коллекции
		Collection<ActionFunction> parameterUpdaters = action.getParameterUpdaters();
		for (ActionFunction actionFunction : parameterUpdaters) {
			DefaultMutableTreeNode functionNode = createActionFunctionNode(actionFunction);
			actionNode.add(functionNode);
		}
		Collection<ActionFunction> preConditionCheckers = action.getPreConditionCheckers();
		for (ActionFunction actionFunction : preConditionCheckers) {
			DefaultMutableTreeNode functionNode = createActionFunctionNode(actionFunction);
			actionNode.add(functionNode);
		}
		return actionNode;
	}

	public DefaultMutableTreeNode createActionFunctionNode(ActionFunction actionFunction) {
		return new DefaultMutableTreeNode(actionFunction);
	}

	@SuppressWarnings("PMD.UnusedFormalParameter")
	private DefaultMutableTreeNode createTransformationsNode(Transformation[] transformations) {
		DefaultMutableTreeNode transformationsNode;
		DefaultMutableTreeNode transformationNode;
		transformationsNode = new DefaultMutableTreeNode("Transformations");
		transformationNode = new DefaultMutableTreeNode("attribute-transformation");
		transformationsNode.add(transformationNode);
		return transformationsNode;
	}

	public DefaultMutableTreeNode createSystemTemplateNode(SystemTemplate systemTemplate) {
		DefaultMutableTreeNode systemTemplateNode = new DefaultMutableTreeNode(systemTemplate);
		Collection<SystemObjectTemplate> objectTemplates = systemTemplate.getObjectTemplates();
		for (SystemObjectTemplate objectTemplate : objectTemplates) {
			DefaultMutableTreeNode objectTemplateNode = createObjectTemplateNode(objectTemplate);
			systemTemplateNode.add(objectTemplateNode);
		}
		return systemTemplateNode;
	}

	public DefaultMutableTreeNode createObjectTemplateNode(SystemObjectTemplate objectTemplate) {
		return new DefaultMutableTreeNode(objectTemplate);
	}

	public void loadSystemTransformations(SystemTransformations systemTransformations) {
		DefaultMutableTreeNode systemTransformationNode;

		// TODO (2022-11-18 #73): удалить
		// systemTransformations = AssemblyLine.getSystemTransformations();

		systemTransformationsNode.removeAllChildren();
		systemTransformationsNode.setUserObject(systemTransformations);

		for (SystemTransformation systemTransformation : systemTransformations) {
			systemTransformationNode = createSystemTransformationNode(systemTransformation);
			systemTransformationsNode.add(systemTransformationNode);
		}

		reload();
	}

	public SystemTransformations saveSystemTransformations() {
		return (SystemTransformations) systemTransformationsNode.getUserObject();
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

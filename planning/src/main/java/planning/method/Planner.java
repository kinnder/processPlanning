package planning.method;

import org.jgrapht.GraphPath;

import planning.model.SystemTransformation;
import planning.model.SystemProcess;
import planning.model.System;
import planning.model.SystemOperation;
import planning.model.SystemVariant;

public class Planner {

	private Node initialNode;

	private Node finalNode;

	private System initialSystem;

	private System finalSystem;

	private SystemTransformations systemTransformations;

	private NodeNetwork nodeNetwork;

	public Planner(TaskDescription taskDescription, SystemTransformations systemTransformations,
			NodeNetwork nodeNetwork) {
		// TODO (2020-06-28 #22): передавать InitialSystem и FinalSystem в качестве параметров конструктора
		this.initialSystem = taskDescription.getInitialSystem();
		this.finalSystem = taskDescription.getFinalSystem();
		this.systemTransformations = systemTransformations;
		this.nodeNetwork = nodeNetwork;
	}

	public void plan() throws CloneNotSupportedException {
		initialNode = nodeNetwork.createNode(initialSystem);

		while (nodeNetwork.hasNextUncheckedNode()) {
			Node sourceNode = nodeNetwork.nextUncheckedNode();
			System sourceSystem = sourceNode.getSystem();
			if (sourceSystem.contains(finalSystem)) {
				finalNode = sourceNode;
				break;
			}
			for (SystemTransformation systemTransformation : systemTransformations) {
				SystemVariant systemVariants[] = systemTransformation.applyTo(sourceSystem);
				for (SystemVariant systemVariant : systemVariants) {
					System targetSystem = systemVariant.getSystem();
					Node targetNode = nodeNetwork.findNode(targetSystem);
					if (targetNode == null) {
						targetNode = nodeNetwork.createNode(targetSystem);
					}

					SystemOperation operation = new SystemOperation(systemTransformation.getAction(),
							systemVariant.getActionParameters());
					nodeNetwork.createEdge(sourceNode, targetNode, operation);
				}
			}
		}
	}

	public SystemProcess getShortestProcess() {
		GraphPath<Node, Edge> path = nodeNetwork.getShortestPath(initialNode, finalNode);
		SystemProcess process = new SystemProcess();
		for (Edge edge : path.getEdgeList()) {
			process.add(edge.getSystemOperation());
		}
		return process;
	}
}

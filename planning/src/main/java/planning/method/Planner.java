package planning.method;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;

import planning.model.SystemTransformation;
import planning.model.SystemProcess;
import planning.model.System;
import planning.model.SystemOperation;
import planning.model.SystemVariant;

public class Planner {

	private List<Node> checkedNodes = new ArrayList<>();

	private List<Node> uncheckedNodes = new ArrayList<>();

	private Node initialNode;

	private Node finalNode;

	private List<Edge> edges = new ArrayList<>();

	private System initialSystem;

	private System finalSystem;

	private SystemTransformations systemTransformations;

	private DefaultDirectedGraph<Node, Edge> network = new DefaultDirectedGraph<>(Edge.class);

	public Planner(TaskDescription taskDescription, SystemTransformations systemTransformations) {
		this.initialSystem = taskDescription.getInitialSystem();
		this.finalSystem = taskDescription.getFinalSystem();
		this.systemTransformations = systemTransformations;
	}

	public void plan() throws CloneNotSupportedException {
		initialNode = new Node(initialSystem);
		uncheckedNodes.add(initialNode);
		network.addVertex(initialNode);

		while (true) {
			iterate();
			if (finalNode != null) {
				break;
			}
			if (uncheckedNodes.isEmpty()) {
				break;
			}
		}
	}

	private void iterate() throws CloneNotSupportedException {
		Node sourceNode = uncheckedNodes.get(0);
		System sourceSystem = sourceNode.getSystem();
		if (sourceSystem.contains(finalSystem)) {
			finalNode = sourceNode;
		}
		for (SystemTransformation systemTransformation : systemTransformations) {
			SystemVariant systemVariants[] = systemTransformation.applyTo(sourceSystem);
			for (SystemVariant systemVariant : systemVariants) {
				System targetSystem = systemVariant.getSystem();

				Node targetNode = null;
				for (Node checkedNode : checkedNodes) {
					if (checkedNode.getSystem().equals(targetSystem)) {
						targetNode = checkedNode;
						break;
					}
				}
				if (targetNode == null) {
					targetNode = new Node(targetSystem);
					uncheckedNodes.add(targetNode);
				}

				Edge edge = new Edge(
						new SystemOperation(systemTransformation.getAction(), systemVariant.getActionParameters()));
				edges.add(edge);

				network.addVertex(targetNode);
				network.addEdge(sourceNode, targetNode, edge);
			}
		}

		uncheckedNodes.remove(0);
		checkedNodes.add(sourceNode);
	}

	public SystemProcess getShortestProcess() {
		DijkstraShortestPath<Node, Edge> alg = new DijkstraShortestPath<>(network);
		GraphPath<Node, Edge> path = alg.getPath(initialNode, finalNode);
		SystemProcess process = new SystemProcess();
		for (Edge edge : path.getEdgeList()) {
			process.add(edge.getSystemOperation());
		}
		return process;
	}
}

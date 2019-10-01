package planning.method;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;

import planning.model.SystemTransformation;
import planning.model.System;
import planning.model.SystemOperation;
import planning.model.SystemVariant;

public class Planner {

	private List<Node> checkedNodes;

	private List<Node> uncheckedNodes;

	private Node initialNode;

	private Node finalNode;

	private List<Edge> edges;

	private System initialSystem;

	private System finalSystem;

	private SystemTransformation[] systemTransformations;

	private DefaultDirectedGraph<Node, Edge> network;

	public Planner(System initialSystem, System finalSystem, SystemTransformation[] systemTransformations) {
		this.initialSystem = initialSystem;
		this.finalSystem = finalSystem;
		this.systemTransformations = systemTransformations;

		this.checkedNodes = new ArrayList<>();
		this.uncheckedNodes = new ArrayList<>();

		this.edges = new ArrayList<>();

		this.network = new DefaultDirectedGraph<>(Edge.class);
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

	public List<SystemOperation> getShortestPlan() {
		DijkstraShortestPath<Node, Edge> alg = new DijkstraShortestPath<>(network);
		GraphPath<Node, Edge> path = alg.getPath(initialNode, finalNode);
		List<SystemOperation> systemOperations = new ArrayList<>();
		for (Edge edge : path.getEdgeList()) {
			systemOperations.add(edge.getSystemOperation());
		}
		return systemOperations;
	}
}

package planning.method;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;

import planning.model.Element;
import planning.model.System;
import planning.model.SystemVariant;

public class Planner {

	private List<Node> checkedNodes;

	private List<Node> uncheckedNodes;

	private Node initialNode;

	private Node finalNode;

	private List<Edge> edges;

	private System initial_system;

	private System final_system;

	private Element[] elements;

	private DefaultDirectedGraph<Node, Edge> network;

	public Planner(System initial_system, System final_system, Element[] elements) {
		this.initial_system = initial_system;
		this.final_system = final_system;
		this.elements = elements;

		this.checkedNodes = new ArrayList<>();
		this.uncheckedNodes = new ArrayList<>();

		this.edges = new ArrayList<>();

		this.network = new DefaultDirectedGraph<>(Edge.class);
	}

	public void plan() {
		initialNode = new Node(initial_system);
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

	private void iterate() {
		Node sourceNode = uncheckedNodes.get(0);
		System sourceSystem = sourceNode.getSystem();
		if (sourceSystem.partially_equals(final_system)) {
			finalNode = sourceNode;
		}
		for (Element element : elements) {
			SystemVariant systemVariants[] = sourceSystem.prepareSystemVariants(element.getTemplate());
			for (SystemVariant systemVariant : systemVariants) {
				element.applyTo(systemVariant);

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

				Edge edge = new Edge(element, systemVariant);
				edges.add(edge);

				network.addVertex(targetNode);
				network.addEdge(sourceNode, targetNode, edge);
			}
		}

		uncheckedNodes.remove(0);
		checkedNodes.add(sourceNode);
	}

	public List<String> getShortestPlan() {
		DijkstraShortestPath<Node, Edge> alg = new DijkstraShortestPath<>(network);
		GraphPath<Node, Edge> path = alg.getPath(initialNode, finalNode);
		List<String> operations = new ArrayList<>();
		for (Edge edge : path.getEdgeList()) {
			operations.add(edge.getElement().getOperation());
		}
		return operations;
	}
}

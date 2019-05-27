package planning.method;

import java.util.ArrayList;
import java.util.List;

import com.google.common.graph.MutableNetwork;
import com.google.common.graph.NetworkBuilder;

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

	private MutableNetwork<Node, Edge> network;

	public Planner(System initial_system, System final_system, Element[] elements) {
		this.initial_system = initial_system;
		this.final_system = final_system;
		this.elements = elements;

		this.checkedNodes = new ArrayList<Node>();
		this.uncheckedNodes = new ArrayList<Node>();

		this.edges = new ArrayList<Edge>();

		this.network = NetworkBuilder.directed().allowsParallelEdges(true).build();
	}

	public void plan() {
		initialNode = new Node(initial_system);
		uncheckedNodes.add(initialNode);
		network.addNode(initialNode);

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
		if (sourceSystem.matches(final_system)) {
			finalNode = sourceNode;
		}
		for (Element element : elements) {
			SystemVariant systemVariants[] = sourceSystem.matchIds(element.getTemplate());
			for (SystemVariant systemVariant : systemVariants) {
				element.applyTo(systemVariant);

				Node targetNode = new Node(systemVariant.getSystem());
				uncheckedNodes.add(targetNode);

				Edge edge = new Edge(element, systemVariant);
				edges.add(edge);

				network.addNode(targetNode);
				network.addEdge(sourceNode, targetNode, edge);
			}
		}

		uncheckedNodes.remove(0);
		checkedNodes.add(sourceNode);
	}

	public Plan getShortestPlan() {
		// TODO Auto-generated method stub
		return null;
	}
}

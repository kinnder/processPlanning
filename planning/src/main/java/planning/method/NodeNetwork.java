package planning.method;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;

import planning.model.System;
import planning.model.SystemOperation;

public class NodeNetwork {

	private List<Node> uncheckedNodes = new ArrayList<>();

	private DefaultDirectedGraph<Node, Edge> network = new DefaultDirectedGraph<>(Edge.class);

	public Node findNode(System system) {
		for (Node node : network.vertexSet()) {
			if (node.getSystem().equals(system)) {
				return node;
			}
		}
		return null;
	}

	public Node findNodeById(String id) {
		for (Node node : network.vertexSet()) {
			if (node.getId().equals(id)) {
				return node;
			}
		}
		return null;
	}

	public Collection<Node> getNodes() {
		return Collections.unmodifiableCollection(network.vertexSet());
	}

	public Collection<Edge> getEdges() {
		return Collections.unmodifiableCollection(network.edgeSet());
	}

	public Node createNode(System system) {
		Node node = new Node(system);
		uncheckedNodes.add(node);
		network.addVertex(node);
		return node;
	}

	public void createEdge(Node begin, Node end, SystemOperation operation) {
		Edge edge = new Edge(begin.getId(), end.getId(), operation);
		network.addEdge(begin, end, edge);
	}

	public boolean hasNextUncheckedNode() {
		return !(uncheckedNodes.isEmpty());
	}

	public Node nextUncheckedNode() {
		Node node = uncheckedNodes.get(0);
		node.setChecked(true);
		uncheckedNodes.remove(0);
		return node;
	}

	public GraphPath<Node, Edge> getShortestPath(Node begin, Node end) {
		DijkstraShortestPath<Node, Edge> alg = new DijkstraShortestPath<>(network);
		return alg.getPath(begin, end);
	}

	public void addNode(Node node) {
		network.addVertex(node);
		if (node.isUnchecked()) {
			uncheckedNodes.add(node);
		}
	}

	public void addEdge(Edge edge) {
		Node begin = findNodeById(edge.getBeginNodeId());
		Node end = findNodeById(edge.getEndNodeId());

		network.addEdge(begin, end, edge);
	}

	@Override
	public String toString() {
		return "NodeNetwork";
	}
}

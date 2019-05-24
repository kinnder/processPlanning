package algorithms.graph;

import structures.graph.Graph;

public class Reachability {

	static public <V, E> void compute(Graph<V, E> graph, V vertexLabel) {
		graph.visit(vertexLabel);

		for (V neighbor : graph.neighbors(vertexLabel)) {
			if (!graph.isVisited(neighbor)) {
				compute(graph, neighbor);
			}
		}
	}
}

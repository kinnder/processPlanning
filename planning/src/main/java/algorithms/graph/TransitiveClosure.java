package algorithms.graph;

import structures.graph.Edge;
import structures.graph.Graph;

public class TransitiveClosure {

	public static <V> void compute(Graph<V, Integer> graph) {
		for (V w : graph.vertices()) {
			for (V u : graph.vertices()) {
				for (V v : graph.vertices()) {
					if (graph.containsEdge(u, w) && graph.containsEdge(w, v)) {
						Edge<V, Integer> edge1 = graph.getEdge(u, w);
						Edge<V, Integer> edge2 = graph.getEdge(w, v);
						int distance = edge1.label() + edge2.label();
						if (graph.containsEdge(u, v)) {
							Edge<V, Integer> edge3 = graph.getEdge(u, v);
							if (distance < edge3.label()) {
								edge3.setLabel(distance);
							}
						} else {
							graph.addEdge(u, v, distance);
						}
					}
				}
			}
		}
	}
}

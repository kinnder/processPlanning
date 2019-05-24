package algorithms.graph;

import java.util.ArrayList;
import java.util.List;

import structures.graph.Graph;

public class TopologicalSort {

	public static <V, E> List<V> compute(Graph<V, E> graph) {
		List<V> result = new ArrayList<V>();

		for (V vLabel : graph.vertices()) {
			if (!graph.isVisited(vLabel)) {
				DFS(graph, vLabel, result);
			}
		}

		return result;
	}

	private static <V, E> void DFS(Graph<V, E> graph, V vLabel, List<V> list) {
		graph.visit(vLabel);
		for (V nLabel : graph.neighbors(vLabel)) {
			if (!graph.isVisited(nLabel)) {
				DFS(graph, nLabel, list);
			}
		}
		list.add(vLabel);
	}
}

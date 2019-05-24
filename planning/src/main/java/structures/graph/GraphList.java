package structures.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Implementation of graph using adjacency lists. Edges are stored in lists of
 * vertex structure.<br>
 * Class is abstract: you must use GraphListDirected or GraphListUndirected to
 * construct particular instances of graphs.
 *
 * @see https://github.com/sliuu/word_gen/blob/master/structure5/GraphList.java
 */
abstract public class GraphList<V, E> implements Graph<V, E> {

	/**
	 * Map associating vertex labels with vertex structures.
	 */
	protected Map<V, GraphListVertex<V, E>> dict;

	/**
	 * Whether of not graph is directed.
	 */
	protected boolean directed;

	/**
	 * Constructor of directed/undirected GraphList.
	 *
	 * @param directed true if graph is to be directed
	 */
	protected GraphList(boolean directed) {
		dict = new Hashtable<V, GraphListVertex<V, E>>();
		this.directed = directed;
	}

	@Override
	public void add(V vLabel) {
		if (dict.containsKey(vLabel)) {
			return;
		}
		GraphListVertex<V, E> vertex = new GraphListVertex<V, E>(vLabel);
		dict.put(vLabel, vertex);
	}

	@Override
	public V remove(V vLabel) {
		GraphListVertex<V, E> v = dict.get(vLabel);

		for (V v2 : dict.keySet()) {
			if (!vLabel.equals(v2)) {
				removeEdge(v2, vLabel);
			}
		}

		dict.remove(vLabel);
		return v.label();
	}

	@Override
	public V get(V vLabel) {
		return dict.get(vLabel).label;
	}

	@Override
	public Edge<V, E> getEdge(V vLabel1, V vLabel2) {
		Edge<V, E> edge = new Edge<V, E>(get(vLabel1), get(vLabel2), null, directed);
		return dict.get(vLabel1).getEdge(edge);
	}

	@Override
	public boolean contains(V vLabel) {
		return dict.containsKey(vLabel);
	}

	@Override
	public boolean containsEdge(V vLabel1, V vLabel2) {
		Edge<V, E> edge = new Edge<V, E>(vLabel1, vLabel2, null, directed);
		return dict.get(vLabel1).containsEdge(edge);
	}

	@Override
	public boolean visit(V vLabel) {
		return dict.get(vLabel).visit();
	}

	@Override
	public boolean visitEdge(Edge<V, E> edge) {
		return edge.visit();
	}

	@Override
	public boolean isVisited(V vLabel) {
		return dict.get(vLabel).isVisited();
	}

	@Override
	public boolean isVisitedEdge(Edge<V, E> edge) {
		return edge.isVisited();
	}

	@Override
	public void reset() {
		for (GraphListVertex<V, E> vertex : dict.values()) {
			vertex.reset();
		}
		for (Edge<V, E> edge : edges()) {
			edge.reset();
		}
	}

	@Override
	public int size() {
		return dict.size();
	}

	@Override
	public int degree(V vLabel) {
		return dict.get(vLabel).degree();
	}

	@Override
	public List<V> vertices() {
		return Collections.unmodifiableList(new ArrayList<V>(dict.keySet()));
	}

	@Override
	public List<V> neighbors(V vLabel) {
		return dict.get(vLabel).adjacentVertices();
	}

	@Override
	public List<Edge<V, E>> edges() {
		List<Edge<V, E>> result = new ArrayList<Edge<V, E>>();
		for (GraphListVertex<V, E> vertex : dict.values()) {
			for (Edge<V, E> edge : vertex.adjacentEdges()) {
				if (vertex.label().equals(edge.here())) {
					result.add(edge);
				}
			}
		}
		return Collections.unmodifiableList(result);
	}

	@Override
	public void clear() {
		dict.clear();
	}

	@Override
	public boolean isEmpty() {
		return dict.isEmpty();
	}

	@Override
	public boolean isDirected() {
		return directed;
	}
}

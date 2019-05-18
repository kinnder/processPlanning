package algorithms.graph;

import java.util.Hashtable;
import java.util.Iterator;
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
	public void add(V label) {
		if (dict.containsKey(label)) {
			return;
		}
		GraphListVertex<V, E> v = new GraphListVertex<V, E>(label);
		dict.put(label, v);
	}

	@Override
	public V get(V label) {
		return dict.get(label).label;
	}

	@Override
	public Edge<V, E> getEdge(V label1, V label2) {
		Edge<V, E> e = new Edge<V, E>(get(label1), get(label2), null, directed);
		return dict.get(label1).getEdge(e);
	}

	@Override
	public boolean contains(V label) {
		return dict.containsKey(label);
	}

	@Override
	public boolean containsEdge(V vLabel1, V vLabel2) {
		Edge<V, E> e = new Edge<V, E>(vLabel1, vLabel2, null, directed);
		return dict.get(vLabel1).containsEdge(e);
	}

	@Override
	public boolean visit(V label) {
		return dict.get(label).visit();
	}

	@Override
	public boolean visitEdge(Edge<V, E> e) {
		return e.visit();
	}

	@Override
	public boolean isVisited(V label) {
		return dict.get(label).isVisited();
	}

	@Override
	public boolean isVisitedEdge(Edge<V, E> e) {
		return e.isVisited();
	}

	@Override
	public void reset() {
		// reset the vertices
		Iterator<GraphListVertex<V, E>> vi = dict.values().iterator();
		while (vi.hasNext()) {
			GraphListVertex<V, E> vtx = vi.next();
			vtx.reset();
		}
		// reset the edges
		Iterator<Edge<V, E>> ei = edges();
		while (ei.hasNext()) {
			Edge<V, E> e = ei.next();
			e.reset();
		}
	}

	@Override
	public int size() {
		return dict.size();
	}

	@Override
	public int degree(V label) {
		return dict.get(label).degree();
	}

	@Override
	public Iterator<V> iterator() {
		return dict.keySet().iterator();
	}

	@Override
	public Iterator<V> neighbors(V label) {
		return dict.get(label).adjacentVertices();
	}

	@Override
	public Iterator<Edge<V, E>> edges() {
		return new GraphListEIterator<V, E>(dict);
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

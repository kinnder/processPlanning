package structures.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A private implementation of a vertex for use in graphs that are internally
 * represented as a list. A vertex is capable of holding a label and has a flag
 * that can be set to mark it as visited.
 *
 * @see https://github.com/sliuu/word_gen/blob/master/structure5/GraphListVertex.java
 */
class GraphListVertex<V, E> extends Vertex<V> {

	/**
	 * Adjacent edges;
	 */
	protected List<Edge<V, E>> adjacencies;

	/**
	 * Constructs a new vertex, not incident to any edge
	 *
	 * @param label
	 */
	public GraphListVertex(V label) {
		super(label);
		adjacencies = new ArrayList<Edge<V, E>>();
	}

	/**
	 * Adds edge to this vertex's adjacency list
	 *
	 * @param e
	 */
	public void addEdge(Edge<V, E> e) {
		if (!containsEdge(e)) {
			adjacencies.add(e);
		}
	}

	/**
	 * @param e
	 *
	 * @return true if e appears on adjacency list
	 */
	public boolean containsEdge(Edge<V, E> e) {
		return adjacencies.contains(e);
	}

	/**
	 * Remove adjacent edge "equal" to e
	 *
	 * @param e
	 */
	public Edge<V, E> removeEdge(Edge<V, E> e) {
		adjacencies.remove(e);
		return e;
	}

	/**
	 * @param e
	 *
	 * @return edge that "equals e, or null
	 */
	public Edge<V, E> getEdge(Edge<V, E> e) {
		Iterator<Edge<V, E>> edges = adjacencies.iterator();
		while (edges.hasNext()) {
			Edge<V, E> adjE = edges.next();
			if (e.equals(adjE)) {
				return adjE;
			}
		}
		return null;
	}

	/**
	 * @return degree of this node
	 */
	public int degree() {
		return adjacencies.size();
	}

	/**
	 * @return iterator over adjacent vertices
	 */
	public Iterator<V> adjacentVertices() {
		return new GraphListAIterator<V, E>(adjacentEdges(), label());
	}

	/**
	 * @return iterator over adjacent edges
	 */
	public Iterator<Edge<V, E>> adjacentEdges() {
		return adjacencies.iterator();
	}

	@Override
	public String toString() {
		return "<GraphListVertex: " + label() + ">";
	}
}

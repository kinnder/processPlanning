package structures.graph;

import java.util.ArrayList;
import java.util.Collections;
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
	protected List<Edge<V, E>> adjacentEdges;

	/**
	 * Constructs a new vertex, not incident to any edge
	 *
	 * @param label
	 */
	public GraphListVertex(V label) {
		super(label);
		adjacentEdges = new ArrayList<Edge<V, E>>();
	}

	/**
	 * Adds edge to this vertex's adjacency list
	 *
	 * @param e
	 */
	public void addEdge(Edge<V, E> e) {
		if (!containsEdge(e)) {
			adjacentEdges.add(e);
		}
	}

	/**
	 * @param e
	 *
	 * @return true if e appears on adjacency list
	 */
	public boolean containsEdge(Edge<V, E> e) {
		return adjacentEdges.contains(e);
	}

	/**
	 * Remove adjacent edge "equal" to e
	 *
	 * @param e
	 */
	public Edge<V, E> removeEdge(Edge<V, E> e) {
		Edge<V, E> edge = getEdge(e);
		adjacentEdges.remove(edge);
		return edge;
	}

	/**
	 * @param e
	 *
	 * @return edge that "equals e, or null
	 */
	public Edge<V, E> getEdge(Edge<V, E> e) {
		for (Edge<V, E> edge : adjacentEdges) {
			if (e.equals(edge)) {
				return edge;
			}
		}
		return null;
	}

	/**
	 * @return degree of this node
	 */
	public int degree() {
		return adjacentEdges.size();
	}

	/**
	 * @return list of labels of adjacent vertices
	 */
	public List<V> adjacentVertices() {
		List<V> result = new ArrayList<V>();
		for (Edge<V, E> edge : adjacentEdges) {
			if (label.equals(edge.here())) {
				result.add(edge.there());
			} else {
				result.add(edge.here());
			}
		}
		return Collections.unmodifiableList(result);
	}

	/**
	 * @return list of adjacent edges
	 */
	public List<Edge<V, E>> adjacentEdges() {
		return Collections.unmodifiableList(adjacentEdges);
	}
}

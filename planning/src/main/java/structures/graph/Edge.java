package structures.graph;

/**
 * A class implementing common edge type among graphs. This class supports both
 * directed and undirected edge. Edge have visited flag to support algorithms.
 *
 * @param <V> - label type for vertices
 * @param <E> - label type for edges
 *
 * @see https://github.com/sliuu/word_gen/blob/master/structure5/Edge.java
 */
public class Edge<V, E> {

	/**
	 * Two element array of vertex labels. When necessary, first element is source.
	 */
	protected V here, there;

	/**
	 * Label associated with edge. May be null.
	 */
	protected E label;

	/**
	 * Whether or not this edge has been visited.
	 */
	protected boolean visited;

	/**
	 * Whether or not this edge is directed
	 */
	protected boolean directed;

	/**
	 * Construct a (possibly directed) edge between two labeled vertices. When edge
	 * is directed, vtx1 specifies source. When undirected, order of vertices is
	 * unimportant. Label on edge is any type, and may be null. Edge is initially
	 * unvisited.
	 *
	 * @param vtx1     - the label of a vertex (source if directed)
	 * @param vtx2     - the label of another vertex (destination if directed)
	 * @param label    - the label associated with the edge
	 * @param directed - true if this edge is directed
	 */
	public Edge(V vtx1, V vtx2, E label, boolean directed) {
		this.here = vtx1;
		this.there = vtx2;
		this.label = label;
		this.visited = false;
		this.directed = directed;
	}

	/**
	 * @return the first vertex (or source if directed)
	 */
	public V here() {
		return here;
	}

	/**
	 * @return the second vertex (or destination if directed)
	 */
	public V there() {
		return there;
	}

	/**
	 * Sets the label associated with the edge. May be null.
	 *
	 * @param label - any object to label edge, or null
	 */
	public void setLabel(E label) {
		this.label = label;
	}

	/**
	 * Get label associated with edge
	 *
	 * @return the label found on the edge
	 */
	public E label() {
		return label;
	}

	/**
	 * Test and set visited flag on vertex
	 *
	 * @return true if edge was visited previously
	 */
	public boolean visit() {
		boolean was = visited;
		visited = true;
		return was;
	}

	/**
	 * Check to see if edge has been visited.
	 *
	 * @return true if edge has been visited
	 */
	public boolean isVisited() {
		return visited;
	}

	/**
	 * Check to see if edge is directed
	 *
	 * @return true if edge is directed
	 */
	public boolean isDirected() {
		return directed;
	}

	/**
	 * Clear the visited flag associated with edge
	 */
	public void reset() {
		visited = false;
	}

	/**
	 * Test for equality of edges. Undirected edges are equal if they connect the
	 * same vertices. Directed edges must have same direction.
	 *
	 * @param o - the other edge
	 * @return true if this edge is equal to other edge
	 */
	@Override
	public boolean equals(Object o) {
		Edge<?, ?> e = (Edge<?, ?>) o;
		return ((here.equals(e.here) && there.equals(e.there))
				|| (!directed && (here.equals(e.there) && there.equals(e.here))));
	}

	@Override
	public int hashCode() {
		return directed ? here.hashCode() - there.hashCode() : here.hashCode() ^ there.hashCode();
	}
}

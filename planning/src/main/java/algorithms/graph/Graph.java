package algorithms.graph;

import java.util.Iterator;

/**
 * The interface describing all Graph objects.<br>
 * <br>
 * Graphs are collections of vertices connected by a set of edges. Edges may or
 * may not be directed. Portions of the graph may be marked visited to support
 * iterative algorithms. Iteration is provided over vertices, edges, and
 * vertices adjacent to a particular vertex.
 *
 * @param <V> - label type for vertices
 * @param <E> - label type for edges
 */
public interface Graph<V, E> {

	/**
	 * Add a vertex to the graph.
	 *
	 * @param label label of the vertex; should be non-null
	 */
	public void add(V label);

	/**
	 * Add an edge between two vertices within the graph. Edge is directed if graph
	 * is directed. Duplicate edges are silently replaced. Labels on edges may be
	 * null.
	 *
	 * @param vtx1  - first (or source, if directed) vertex
	 * @param vtx2  - second (or destination, if directed) vertex
	 * @param label - label associated with the edge
	 */
	public void addEdge(V vtx1, V vtx2, E label);

	/**
	 * Remove a vertex from the graph. Associated edges are also removed.
	 * Non-vertices are silently ignored.
	 *
	 * @param label the label of the vertex within the graph
	 * @return label associated with the vertex
	 */
	public V remove(V label);

	/**
	 * Remove possible edge between vertices with specified labels.
	 *
	 * @param vLabel1 - first (or source, if directed) vertex
	 * @param vLabel2 - second (or destination, if directed) vertex
	 * @return label associated with the edge removed
	 */
	public E removeEdge(V vLabel1, V vLabel2);

	/**
	 * Get reference to actual label of vertex. Vertex labels are matched using
	 * their equals method, which may or may not test for actual equivalence. Result
	 * remains part of graph.
	 *
	 * @param label - label of the vertex thought
	 * @return actual label, or null if none is found
	 */
	public V get(V label);

	/**
	 * Get reference to actual edge. Edge is identified by the labels on associated
	 * vertices.
	 *
	 * @param label1 - first (or source, if directed) vertex
	 * @param label2 - second (or destination, if directed) vertex
	 * @return the edge, if found, or null
	 */
	public Edge<V, E> getEdge(V label1, V label2);

	/**
	 * Test for vertex membership.
	 *
	 * @param label - the label1 of the vertex thought
	 * @return true if vertex with matching label is found
	 */
	public boolean contains(V label);

	/**
	 * Test for edge membership.
	 *
	 * @param vLabel1 - first (or source, if directed) vertex
	 * @param vLabel2 - second (or destination, if directed) vertex
	 * @return true if the edge exists within the graph
	 */
	public boolean containsEdge(V vLabel1, V vLabel2);

	/**
	 * Test and set visited flag of vertex
	 *
	 * @param label - label of vertex to be visited
	 * @return previous value of visited flag on vertex
	 */
	public boolean visit(V label);

	/**
	 * Test and set visited flag of edge.
	 *
	 * @param e - edge object that is part of graph
	 * @return previous value of the edge's visited flag
	 */
	public boolean visitEdge(Edge<V, E> e);

	/**
	 * Return visited flag of vertex.
	 *
	 * @param label - label of vertex
	 * @return true if vertex has been visited
	 */
	public boolean isVisited(V label);

	/**
	 * Return visited flag of edge.
	 *
	 * @param e - edge of graph to be considered
	 * @return true if the edge has been visited
	 */
	public boolean isVisitedEdge(Edge<V, E> e);

	/**
	 * Clear visited flags of edges and vertices.
	 */
	public void reset();

	/**
	 * Determine number of vertices within graph.
	 *
	 * @return the number of vertices within graph
	 */
	public int size();

	/**
	 * Determine out degree of vertex.
	 *
	 * @param label - label associated with vertex
	 * @return the number of edges with this vertex as source
	 */
	public int degree(V label);

	/**
	 * Determine the number of edges in graph.
	 *
	 * @return number of edges in graph
	 */
	public int edgeCount();

	/**
	 * Construct vertex iterator. Vertices are not visited in any guaranteed order.
	 *
	 * @return iterator traversing vertices in graph
	 */
	public Iterator<V> iterator();

	/**
	 * Construct an adjacent vertex iterator. Adjacent vertices (those on
	 * destination of edge, if directed) are considered, but not in any guaranteed
	 * order.
	 *
	 * @param label - label of the vertex
	 * @return iterator traversing the adjacent vertices of labeled vertex
	 */
	public Iterator<V> neighbors(V label);

	/**
	 * Construct an iterator over all edges. Every directed/undirected edge is
	 * considered exactly once. Order is not guaranteed.
	 *
	 * @return Iterator over edges
	 */
	public Iterator<Edge<V, E>> edges();

	/**
	 * Remove all vertices (and thus, edges) of the graph.
	 */
	public void clear();

	/**
	 * Determine if graph is empty.
	 *
	 * @return true if there are no vertices in graph
	 */
	public boolean isEmpty();

	/**
	 * Determine if graph is directed.
	 *
	 * @return true if the graph is directed
	 */
	public boolean isDirected();
}

package structures.graph;

/**
 * A GraphListUndirected is a list-based graph representation that consists of a
 * collection of vertices and undirected edges. Portions of the graph may be
 * marked visited to support iterative algorithms.<br>
 * Iteration is provided over vertices, edges, and vertices adjacent to a
 * particular vertex.
 *
 * @see https://github.com/sliuu/word_gen/blob/master/structure5/GraphListUndirected.java
 */
public class GraphListUndirected<V, E> extends GraphList<V, E> {

	/**
	 * Construct an undirected, adjacency-list based graph
	 *
	 * @param directed
	 */
	protected GraphListUndirected() {
		super(false);
	}

	@Override
	public void addEdge(V vLabel1, V vLabel2, E eLabel) {
		GraphListVertex<V, E> vertex1 = dict.get(vLabel1);
		GraphListVertex<V, E> vertex2 = dict.get(vLabel2);
		Edge<V, E> edge = new Edge<V, E>(vertex1.label(), vertex2.label(), eLabel, false);
		vertex1.addEdge(edge);
		vertex2.addEdge(edge);
	}

	@Override
	public E removeEdge(V vLabel1, V vLabel2) {
		GraphListVertex<V, E> vertex1 = dict.get(vLabel1);
		GraphListVertex<V, E> vertex2 = dict.get(vLabel2);
		Edge<V, E> edge = new Edge<V, E>(vertex1.label(), vertex2.label(), null, false);
		vertex2.removeEdge(edge);
		edge = vertex1.removeEdge(edge);
		return edge == null ? null : edge.label();
	}

	@Override
	public int edgeCount() {
		int count = 0;
		for (GraphListVertex<V, E> vertex : dict.values()) {
			count += vertex.degree();
		}
		return count / 2;
	}
}

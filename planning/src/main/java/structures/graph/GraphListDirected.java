package structures.graph;

/**
 * A GraphListDirected is a list-based graph representation that consists of a
 * collection of vertices and directed edges. Portions of the graph may be
 * marked visited to support iterative algorithms.<br>
 * Iteration is provided over vertices, edges, and vertices adjacent to a
 * particular vertex.
 *
 * @see https://github.com/sliuu/word_gen/blob/master/structure5/GraphListDirected.java
 */
public class GraphListDirected<V, E> extends GraphList<V, E> {

	/**
	 * Construct a directed, adjacency-list based graph.
	 */
	public GraphListDirected() {
		super(true);
	}

	@Override
	public void addEdge(V vLabel1, V vLabel2, E eLabel) {
		GraphListVertex<V, E> vertex1 = dict.get(vLabel1);
		GraphListVertex<V, E> vertex2 = dict.get(vLabel2);
		Edge<V, E> edge = new Edge<V, E>(vertex1.label(), vertex2.label(), eLabel, true);
		vertex1.addEdge(edge);
	}

	@Override
	public E removeEdge(V vLabel1, V vLabel2) {
		GraphListVertex<V, E> vertex1 = dict.get(vLabel1);
		GraphListVertex<V, E> vertex2 = dict.get(vLabel2);
		Edge<V, E> edge = new Edge<V, E>(vertex1.label(), vertex2.label(), null, true);
		edge = vertex1.removeEdge(edge);
		return edge == null ? null : edge.label();
	}

	@Override
	public int edgeCount() {
		int count = 0;
		for (GraphListVertex<V, E> vertex : dict.values()) {
			count += vertex.degree();
		}
		return count;
	}
}

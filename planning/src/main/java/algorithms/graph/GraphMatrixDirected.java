package algorithms.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A GraphMatrixDirected is a matrix-based graph representation that consists of
 * a collection of vertices and directed edges. Portions of the graph may be
 * marked visited to support iterative algorithms. Iteration is provided over
 * vertices, edges and vertices adjacent to a particular vertex.<br>
 * GraphMatrix differs from GraphList in that the user must commit to an upper
 * bound on number of vertices in graph.
 *
 * @see https://github.com/sliuu/word_gen/blob/master/structure5/GraphMatrixDirected.java
 */
public class GraphMatrixDirected<V, E> extends GraphMatrix<V, E> {

	/**
	 * construct a directed, adjacency-matrix based graph
	 *
	 * @param size the maximum number of vertices allows in graph
	 */
	public GraphMatrixDirected(int size) {
		super(size, true);
	}

	/**
	 * construct an traversal over all edges
	 *
	 * @return Iterator over edges
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Iterator<Edge<V, E>> edges() {
		List<Edge<V, E>> list = new ArrayList<Edge<V, E>>();
		for (int row = size - 1; row >= 0; row--) {
			for (int col = size - 1; col >= 0; col--) {
				Edge<V, E> e = (Edge<V, E>) data[row][col];
				if (e != null) {
					list.add(e);
				}
			}
		}
		return list.iterator();
	}

	/**
	 * add an edge between two vertices within the graph. Edge is directed.
	 * Duplicate edges are silently replaced. Labels on edges may be null
	 *
	 * @param vLabel1 source vertex
	 * @param vLabel2 destination vertex
	 * @param label   label associated with the edge
	 */
	@Override
	public void addEdge(V vLabel1, V vLabel2, E label) {
		GraphMatrixVertex<V> vtx1, vtx2;
		// get vertices
		vtx1 = dict.get(vLabel1);
		vtx2 = dict.get(vLabel2);
		// update matrix with new edge
		Edge<V, E> e = new Edge<V, E>(vtx1.label(), vtx2.label(), label, true);
		data[vtx1.index()][vtx2.index()] = e;
	}

	/**
	 * remove possible edge between vertices labeled vLabel1 and vLabel2. vLabel1 is
	 * the source
	 *
	 * @param vLabel1 source vertex
	 * @param vLabel2 destination vertex
	 * @return the label associated with the edge removed
	 */
	@SuppressWarnings("unchecked")
	@Override
	public E removeEdge(V vLabel1, V vLabel2) {
		// get indices
		int row = dict.get(vLabel1).index();
		int col = dict.get(vLabel2).index();
		// cache old value
		Edge<V, E> e = (Edge<V, E>) data[row][col];
		// update matrix
		data[row][col] = null;
		return e == null ? null : e.label();
	}

	/**
	 * determine the number of edges in graph
	 *
	 * @return number of edges in graph
	 */
	@Override
	public int edgeCount() {
		// count non-null entries in table
		int sum = 0;
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				if (data[row][col] != null) {
					sum++;
				}
			}
		}
		return sum;
	}

	/**
	 * construct a string representation of graph
	 *
	 * @return string representation graph
	 */
	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		Iterator<V> source = iterator();
		Iterator<V> dest;

		s.append("<GraphMatrixDirected:");
		while (source.hasNext()) {
			V srcVal = source.next();
			s.append(" (" + srcVal + "->");
			dest = neighbors(srcVal);
			while (dest.hasNext()) {
				s.append(srcVal + "->" + dest.next());
			}
			s.append(")");
		}
		s.append(">");
		return s.toString();
	}
}

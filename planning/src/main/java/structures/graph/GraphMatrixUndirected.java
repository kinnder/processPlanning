package structures.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A GraphMatrixUndirected is a matrix-based graph representation that consists
 * of a collection of vertices and undirected edges. Portions of the graph may
 * be marked visited to support iterative algorithms.<br>
 * Iteration is provided over vertices, edges, and vertices adjacent to
 * particular vertex.<br>
 * GraphMatrix differs from GraphList in that the user must commit to an upper
 * bound on number of vertices in graph.
 *
 * @see https://github.com/sliuu/word_gen/blob/master/structure5/GraphMatrixUndirected.java
 */
public class GraphMatrixUndirected<V, E> extends GraphMatrix<V, E> {

	/**
	 * Construct an undirected, adjacency-matrix based graph.
	 *
	 * @param size - maximum number of vertices allowed in graph
	 */
	public GraphMatrixUndirected(int size) {
		super(size, false);
	}

	@Override
	public void addEdge(V vLabel1, V vLabel2, E label) {
		GraphMatrixVertex<V> vtx1, vtx2;
		// get vertices
		vtx1 = vertexData.get(vLabel1);
		vtx2 = vertexData.get(vLabel2);
		// update matrix with new edge
		Edge<V, E> e = new Edge<V, E>(vtx1.label(), vtx2.label(), label, false);
		edgeData[vtx1.index()][vtx2.index()] = e;
		edgeData[vtx2.index()][vtx1.index()] = e;
	}

	@Override
	public E removeEdge(V vLabel1, V vLabel2) {
		// get indices
		int row = vertexData.get(vLabel1).index();
		int col = vertexData.get(vLabel2).index();
		// cache old value
		Edge<V, E> e = (Edge<V, E>) edgeData[row][col];
		// update matrix
		edgeData[row][col] = null;
		edgeData[col][row] = null;
		return e == null ? null : e.label();
	}

	@Override
	public int edgeCount() {
		// count non-null entries in table
		int sum = 0;
		for (int row = 0; row < size; row++) {
			for (int col = row; col < size; col++) {
				if (edgeData[row][col] != null) {
					sum++;
				}
			}
		}
		return sum;
	}

	@Override
	public Iterator<Edge<V, E>> edges() {
		List<Edge<V, E>> list = new ArrayList<Edge<V, E>>();
		for (int row = size - 1; row >= 0; row--) {
			for (int col = size - 1; col >= row; col--) {
				Edge<V, E> e = (Edge<V, E>) edgeData[row][col];
				if (e != null) {
					list.add(e);
				}
			}
		}
		return list.iterator();
	}

	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		Iterator<V> source = iterator();
		Iterator<V> dest;

		s.append("<GraphMatrixUndirected:");
		while (source.hasNext()) {
			V srcValue = source.next();
			s.append(" (" + srcValue + "->");
			dest = neighbors(srcValue);
			while (dest.hasNext()) {
				s.append(srcValue + "->" + dest.next());
			}
		}
		s.append(">");
		return s.toString();
	}
}

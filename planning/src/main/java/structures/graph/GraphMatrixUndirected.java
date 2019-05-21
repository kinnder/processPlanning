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
	public void addEdge(V vLabel1, V vLabel2, E elabel) {
		GraphMatrixVertex<V> vertex1 = vertexData.get(vLabel1);
		GraphMatrixVertex<V> vertex2 = vertexData.get(vLabel2);
		Edge<V, E> edge = new Edge<V, E>(vertex1.label(), vertex2.label(), elabel, false);
		edgeData[vertex1.index()][vertex2.index()] = edge;
		edgeData[vertex2.index()][vertex1.index()] = edge;
	}

	@Override
	public E removeEdge(V vLabel1, V vLabel2) {
		int vIndex1 = vertexData.get(vLabel1).index();
		int vIndex2 = vertexData.get(vLabel2).index();
		Edge<V, E> edge = (Edge<V, E>) edgeData[vIndex1][vIndex2];
		edgeData[vIndex1][vIndex2] = null;
		edgeData[vIndex2][vIndex1] = null;
		return edge == null ? null : edge.label();
	}

	@Override
	public int edgeCount() {
		int sum = 0;
		for (int vIndex1 = 0; vIndex1 < size; vIndex1++) {
			for (int vIndex2 = vIndex1; vIndex2 < size; vIndex2++) {
				if (edgeData[vIndex1][vIndex2] != null) {
					sum++;
				}
			}
		}
		return sum;
	}

	@Override
	public Iterator<Edge<V, E>> edges() {
		List<Edge<V, E>> result = new ArrayList<Edge<V, E>>();
		for (int vIndex1 = size - 1; vIndex1 >= 0; vIndex1--) {
			for (int vIndex2 = size - 1; vIndex2 >= vIndex1; vIndex2--) {
				Edge<V, E> edge = (Edge<V, E>) edgeData[vIndex1][vIndex2];
				if (edge != null) {
					result.add(edge);
				}
			}
		}
		return result.iterator();
	}
}

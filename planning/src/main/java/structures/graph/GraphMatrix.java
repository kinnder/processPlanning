package structures.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Implementation of graph using adjacency matrices.<br>
 * User must commit to maximum size of graph (in vertices), it may be smaller.
 * Edges are stored in matrix. Not suitable for large graphs.<br>
 * Class is abstract: you must use GraphMatrixDirected or GraphMatrixUndirected
 * to construct particular instances of graph.
 *
 * @see https://github.com/sliuu/word_gen/blob/master/structure5/GraphMatrix.java
 */
abstract public class GraphMatrix<V, E> implements Graph<V, E> {

	/**
	 * Maximum number of vertices in graph.
	 */
	protected int size;

	/**
	 * The edge data. Every edge appears on one(directed) or two(undirected)
	 * locations within graph.
	 */
	protected Edge<V, E> edgeData[][];

	/**
	 * The vertex data. Mapping between vertex labels and vertex structures.
	 */
	protected Map<V, GraphMatrixVertex<V>> vertexData;

	/**
	 * List of free vertex indexes within graph.
	 */
	protected List<Integer> freeIndexes;

	/**
	 * Whether or not graph is directed.
	 */
	protected boolean directed;

	/**
	 * Constructor of directed/undirected GraphMatrix.
	 *
	 * @param size     - maximum size of graph
	 * @param directed - true if graph is to be directed
	 */
	@SuppressWarnings("unchecked")
	protected GraphMatrix(int size, boolean directed) {
		this.size = size;
		this.directed = directed;

		edgeData = new Edge[size][size];
		vertexData = new Hashtable<V, GraphMatrixVertex<V>>(size);

		fillFreeIndexes();
	}

	private void fillFreeIndexes() {
		freeIndexes = new ArrayList<Integer>();
		for (int index = size - 1; index >= 0; index--) {
			freeIndexes.add(index);
		}
	}

	@Override
	public void add(V vLabel) {
		if (vertexData.containsKey(vLabel)) {
			return;
		}

		int index = freeIndexes.remove(0);
		vertexData.put(vLabel, new GraphMatrixVertex<V>(vLabel, index));
	}

	@Override
	public V remove(V vLabel) {
		GraphMatrixVertex<V> vertex = vertexData.remove(vLabel);
		if (vertex == null) {
			return null;
		}

		int index = vertex.index();
		for (int i = 0; i < size; i++) {
			edgeData[i][index] = null;
			edgeData[index][i] = null;
		}

		freeIndexes.add(index);
		return vertex.label();
	}

	@Override
	public V get(V vLabel) {
		GraphMatrixVertex<V> vertex = vertexData.get(vLabel);
		return vertex.label();
	}

	@Override
	public Edge<V, E> getEdge(V vLabel1, V vLabel2) {
		int vIndex1, vIndex2;
		vIndex1 = vertexData.get(vLabel1).index();
		vIndex2 = vertexData.get(vLabel2).index();
		return (Edge<V, E>) edgeData[vIndex1][vIndex2];
	}

	@Override
	public boolean contains(V vLabel) {
		return vertexData.containsKey(vLabel);
	}

	@Override
	public boolean containsEdge(V vLabel1, V vLabel2) {
		GraphMatrixVertex<V> vertex1 = vertexData.get(vLabel1);
		GraphMatrixVertex<V> vertex2 = vertexData.get(vLabel2);
		return edgeData[vertex1.index()][vertex2.index()] != null;
	}

	@Override
	public boolean visit(V vLabel) {
		Vertex<V> vertex = vertexData.get(vLabel);
		return vertex.visit();
	}

	@Override
	public boolean visitEdge(Edge<V, E> e) {
		return e.visit();
	}

	@Override
	public boolean isVisited(V vLabel) {
		GraphMatrixVertex<V> vertex = vertexData.get(vLabel);
		return vertex.isVisited();
	}

	@Override
	public boolean isVisitedEdge(Edge<V, E> e) {
		return e.isVisited();
	}

	@Override
	public void reset() {
		for (GraphMatrixVertex<V> vertex : vertexData.values()) {
			vertex.reset();
		}
		for (int index1 = 0; index1 < size; index1++) {
			for (int index2 = 0; index2 < size; index2++) {
				Edge<V, E> edge = edgeData[index1][index2];
				if (edge != null) {
					edge.reset();
				}
			}
		}
	}

	@Override
	public int size() {
		return vertexData.size();
	}

	@Override
	public int degree(V vLabel) {
		int vIndex = vertexData.get(vLabel).index();
		int result = 0;
		for (int index = 0; index < size; index++) {
			if (edgeData[vIndex][index] != null) {
				result++;
			}
		}
		return result;
	}

	@Override
	public List<V> vertices() {
		return Collections.unmodifiableList(new ArrayList<V>(vertexData.keySet()));
	}

	@Override
	public List<V> neighbors(V vLabel) {
		GraphMatrixVertex<V> vertex = vertexData.get(vLabel);
		List<V> result = new ArrayList<V>();
		for (int index = size - 1; index >= 0; index--) {
			Edge<V, E> edge = edgeData[vertex.index()][index];
			if (edge != null) {
				if (edge.here().equals(vertex.label())) {
					result.add(edge.there());
				} else {
					result.add(edge.here());
				}
			}
		}
		return Collections.unmodifiableList(result);
	}

	@Override
	public void clear() {
		vertexData.clear();
		for (int index1 = 0; index1 < size; index1++) {
			for (int index2 = 0; index2 < size; index2++) {
				edgeData[index1][index2] = null;
			}
		}
		fillFreeIndexes();
	}

	@Override
	public boolean isEmpty() {
		return vertexData.isEmpty();
	}

	@Override
	public boolean isDirected() {
		return directed;
	}
}

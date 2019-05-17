package algorithms.graph;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
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
	protected Edge<V, E> data[][];

	/**
	 * Translation between vertex labels and vertex structures.
	 */
	protected Map<V, GraphMatrixVertex<V>> dict;

	/**
	 * List of free vertex indices within graph.
	 */
	protected List<Integer> freeList;

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
		// the following constructs a size x size matrix
		data = (Edge<V, E>[][]) new Object[size][size];
		// label to index translation table
		dict = new Hashtable<V, GraphMatrixVertex<V>>(size);
		// put all indices in the free list
		freeList = new ArrayList<Integer>();
		for (int row = size - 1; row >= 0; row--) {
			freeList.add(Integer.valueOf(row));
		}
	}

	@Override
	public void add(V label) {
		// if there already, do nothing
		if (dict.containsKey(label)) {
			return;
		}
		// allocate a free row and column
		int row = freeList.remove(0).intValue();
		// add vertex to dictionary
		dict.put(label, new GraphMatrixVertex<V>(label, row));
	}

	@Override
	public V remove(V label) {
		// find and extract vertex
		GraphMatrixVertex<V> vert;
		vert = dict.remove(label);
		if (vert == null) {
			return null;
		}
		// remove vertex from matrix
		int index = vert.index();
		// clear row and column entries
		for (int row = 0; row < size; row++) {
			data[row][index] = null;
			data[index][row] = null;
		}
		// add node index to free list
		freeList.add(Integer.valueOf(index));
		return vert.label();
	}

	@Override
	public V get(V label) {
		GraphMatrixVertex<V> vert;
		vert = dict.get(label);
		return vert.label();
	}

	@Override
	public Edge<V, E> getEdge(V label1, V label2) {
		int row, col;
		row = dict.get(label1).index();
		col = dict.get(label2).index();
		return (Edge<V, E>) data[row][col];
	}

	@Override
	public boolean contains(V label) {
		return dict.containsKey(label);
	}

	@Override
	public boolean containsEdge(V vLabel1, V vLabel2) {
		GraphMatrixVertex<V> vtx1, vtx2;
		vtx1 = dict.get(vLabel1);
		vtx2 = dict.get(vLabel2);
		return data[vtx1.index()][vtx2.index()] != null;
	}

	@Override
	public boolean visit(V label) {
		Vertex<V> vert = dict.get(label);
		return vert.visit();
	}

	@Override
	public boolean visitEdge(Edge<V, E> e) {
		return e.visit();
	}

	@Override
	public boolean isVisited(V label) {
		GraphMatrixVertex<V> vert;
		vert = dict.get(label);
		return vert.isVisited();
	}

	@Override
	public boolean isVisitedEdge(Edge<V, E> e) {
		return e.isVisited();
	}

	@Override
	public void reset() {
		Iterator<GraphMatrixVertex<V>> it = dict.values().iterator();
		while (it.hasNext()) {
			it.next().reset();
		}
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				Edge<V, E> e = (Edge<V, E>) data[row][col];
				if (e != null) {
					e.reset();
				}
			}
		}
	}

	@Override
	public int size() {
		return dict.size();
	}

	@Override
	public int degree(V label) {
		// get index
		int row = dict.get(label).index();
		int col;
		int result = 0;
		// count non-null columns in row
		for (col = 0; col < size; col++) {
			if (data[row][col] != null) {
				result++;
			}
		}
		return result;
	}

	@Override
	public Iterator<V> iterator() {
		return dict.keySet().iterator();
	}

	@Override
	public Iterator<V> neighbors(V label) {
		GraphMatrixVertex<V> vert;
		vert = dict.get(label);
		List<V> list = new ArrayList<V>();
		for (int row = size - 1; row >= 0; row--) {
			Edge<V, E> e = (Edge<V, E>) data[vert.index()][row];
			if (e != null) {
				if (e.here().equals(vert.label())) {
					list.add(e.there());
				} else {
					list.add(e.here());
				}
			}
		}
		return list.iterator();
	}

	@Override
	public void clear() {
		dict.clear();
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				data[row][col] = null;
			}
		}
		freeList = new ArrayList<Integer>();
		for (int row = size - 1; row >= 0; row--) {
			freeList.add(Integer.valueOf(row));
		}
	}

	@Override
	public boolean isEmpty() {
		return dict.isEmpty();
	}

	@Override
	public boolean isDirected() {
		return directed;
	}
}

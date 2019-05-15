package algorithms.graph;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @see https://github.com/sliuu/word_gen/blob/master/structure5/GraphMatrix.java
 */
abstract public class GraphMatrix<V, E> implements Graph<V, E> {

	protected int size; // allocation size for graph

	protected Object data[][]; // matrix - array of arrays

	protected Map<V, GraphMatrixVertex<V>> dict; // labels ->vertices

	protected List<Integer> freeList; // available indices in matrix

	protected boolean directed; // graph is directed

	protected GraphMatrix(int size, boolean dir) {
		this.size = size; // set maximum size
		directed = dir; // fix direction of edges
		// the following construcs a size x size matrix
		data = new Object[size][size];
		// label to index translation table
		dict = new Hashtable<V, GraphMatrixVertex<V>>(size);
		// put all indices in the free list
		freeList = new ArrayList<Integer>();
		for (int row = size - 1; row >= 0; row--) {
			freeList.add(new Integer(row));
		}
	}

	@Override
	public void add(V label) {
		// pre: label is a non-null label for vertex
		// post: a vertex with label is added to graph. if vertex with label is already
		// in graph, no action
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
		// pre: label is non-null vertex label
		// post: vertex with "equals" label is removed, if found
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
		freeList.add(new Integer(index));
		return vert.label();
	}

	@Override
	public Vertex<V> get(V label) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Edge<V, E> getEdge(V label1, V label2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(V label) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsEdge(V vLabel1, V vLabel2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean visit(V label) {
		// post : sets visited flag on vertex, returns previous value
		Vertex<V> vert = dict.get(label);
		return vert.visit();
	}

	@Override
	public boolean visitEdge(Edge<V, E> e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isVisited(V label) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isVisitedEdge(Edge<V, E> e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int degree(V label) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterator<V> iterator() {
		// post: returns traversal across all vertices of graph
		return dict.keySet().iterator();
	}

	@Override
	public Iterator<V> neighbors(V label) {
		// pre: label is label of vertex in graph
		// post: returns traversal over vertices adj to vertex each edge beginning at
		// label visited exactly once
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
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDirected() {
		// TODO Auto-generated method stub
		return false;
	}
}

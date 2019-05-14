package algorithms.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @see https://github.com/sliuu/word_gen/blob/master/structure5/GraphMatrixDirected.java
 */
public class GraphMatrixDirected<V, E> extends GraphMatrix<V, E> {

	public GraphMatrixDirected(int size) {
		// pre: size>0
		// post: constructs an empty graph that may be expanded to at most size
		// vertices. Graph is directed if dir true and undirected otherwise
		super(size, true);
	}

	@Override
	public Iterator<Edge<V, E>> edges() {
		// post: returns traversal across all edges of graph (returns Edges)
		List<Edge<V, E>> list = new ArrayList<Edge<V, E>>();
		for (int row = size - 1; row >= 0; row--) {
			for (int col = size - 1; col >= 0; col--) {
				Edge<V, E> e = (Edge<V, E>) data[row][col];
				if (e != null)
					list.add(e);
			}
		}
		return list.iterator();
	}

	@Override
	public void addEdge(V vLabel1, V vLabel2, E label) {
		// pre: vLabel1 and vLabel2 are labels of existing vertices, v1 & v2
		// post: an edge is inserted between vLabel1 and vLabel2; if edge is new, it
		// is labeled with label(can be null)
		GraphMatrixVertex<V> vtx1, vtx2;
		// get vertices
		vtx1 = dict.get(vLabel1);
		vtx2 = dict.get(vLabel2);
		// update matrix with new edge
		Edge<V, E> e = new Edge<V, E>(vtx1.label(), vtx2.label(), label, true);
		data[vtx1.index()][vtx2.index()] = e;
	}

	@Override
	public E removeEdge(V vLabel1, V vLabel2) {
		// pre: vLabel1 and vLabel2 are labels of existing vertices
		// post: edge is removed, its label is returned
		// get indices
		int row = dict.get(vLabel1).index();
		int col = dict.get(vLabel2).index();
		// cache old value
		Edge<V, E> e = (Edge<V, E>) data[row][col];
		// update matrix
		data[row][col] = null;
		if (e == null) {
			return null;
		} else {
			return e.label();
		}
	}
}

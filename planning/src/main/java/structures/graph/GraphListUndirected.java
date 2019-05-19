package structures.graph;

import java.util.Iterator;

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
	public void addEdge(V vtx1, V vtx2, E label) {
		GraphListVertex<V, E> v1 = dict.get(vtx1);
		GraphListVertex<V, E> v2 = dict.get(vtx2);
		Edge<V, E> e = new Edge<V, E>(v1.label(), v2.label(), label, false);
		v1.addEdge(e);
		v2.addEdge(e);
	}

	@Override
	public V remove(V label) {
		GraphListVertex<V, E> v = dict.get(label);

		Iterator<V> vi = neighbors(label);
		while (vi.hasNext()) {
			V v2 = vi.next();
			removeEdge(label, v2);
		}
		dict.remove(label);
		return v.label();
	}

	@Override
	public E removeEdge(V vLabel1, V vLabel2) {
		GraphListVertex<V, E> v1 = dict.get(vLabel1);
		GraphListVertex<V, E> v2 = dict.get(vLabel2);
		Edge<V, E> e = new Edge<V, E>(v1.label(), v2.label(), null, false);
		v2.removeEdge(e);
		e = v1.removeEdge(e);
		if (e == null) {
			return null;
		} else {
			return e.label();
		}
	}

	@Override
	public int edgeCount() {
		int count = 0;
		Iterator<GraphListVertex<V, E>> i = dict.values().iterator();
		while (i.hasNext()) {
			count += i.next().degree();
		}
		return count / 2;
	}

	@Override
	public String toString() {
		return "<GraphListUndirected: " + dict.toString() + ">";
	}
}

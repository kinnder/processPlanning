package algorithms.graph;

import java.util.Iterator;

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
	public void addEdge(V vtx1, V vtx2, E label) {
		GraphListVertex<V, E> v1 = dict.get(vtx1);
		GraphListVertex<V, E> v2 = dict.get(vtx2);
		Edge<V, E> e = new Edge<V, E>(v1.label(), v2.label(), label, true);
		v1.addEdge(e);
	}

	@Override
	public V remove(V label) {
		GraphListVertex<V, E> v = dict.get(label);

		Iterator<V> vi = iterator();
		while (vi.hasNext()) {
			V v2 = vi.next();
			if (!label.equals(v2)) {
				removeEdge(v2, label);
			}
		}
		dict.remove(label);
		return v.label();
	}

	@Override
	public E removeEdge(V vLabel1, V vLabel2) {
		GraphListVertex<V, E> v1 = dict.get(vLabel1);
		GraphListVertex<V, E> v2 = dict.get(vLabel2);
		Edge<V, E> e = new Edge<V, E>(v1.label(), v2.label(), null, true);
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
		return count;
	}

	@Override
	public String toString() {
		return "<GraphListDirected: " + dict.toString() + ">";
	}
}

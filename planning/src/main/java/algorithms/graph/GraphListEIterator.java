package algorithms.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * An iterator over all edges. Every directed/undirected edge is considered
 * exactly once. Order is not guaranteed.
 *
 * @param <V> - label type for vertices
 * @param <E> - label type for edges
 *
 * @see https://github.com/sliuu/word_gen/blob/master/structure5/GraphListEIterator.java
 */
class GraphListEIterator<V, E> implements Iterator<Edge<V, E>> {

	protected Iterator<Edge<V, E>> edges;

	public GraphListEIterator(Map<V, GraphListVertex<V, E>> dict) {
		List<Edge<V, E>> l = new ArrayList<Edge<V, E>>();
		Iterator<GraphListVertex<V, E>> dictIterator = dict.values().iterator();
		while (dictIterator.hasNext()) {
			GraphListVertex<V, E> vtx = (GraphListVertex<V, E>) dictIterator.next();
			Iterator<Edge<V, E>> vtxIterator = vtx.adjacentEdges();
			while (vtxIterator.hasNext()) {
				Edge<V, E> e = vtxIterator.next();
				if (vtx.label().equals(e.here())) {
					l.add(e);
				}
			}
		}
		edges = l.iterator();
	}

	/**
	 * Resets the iterator to first edge.
	 */
	public void reset() {
	}

	@Override
	public boolean hasNext() {
		return edges.hasNext();
	}

	/**
	 * @return current element
	 */
	public Edge<V, E> get() {
		return null;
	}

	@Override
	public Edge<V, E> next() {
		return edges.next();
	}

}

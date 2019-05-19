package structures.graph;

import java.util.Iterator;

/**
 * An adjacent vertex iterator. Adjacent vertices (those on destination of edge,
 * if directed) are considered, but not in any guaranteed order.
 *
 * @param <V> - label type for vertices
 * @param <E> - label type for edges
 *
 * @see https://github.com/sliuu/word_gen/blob/master/structure5/GraphListAIterator.java
 */
class GraphListAIterator<V, E> implements Iterator<V> {

	protected Iterator<Edge<V, E>> edges;

	protected V vertex;

	/**
	 * Returns iterator over vertices adjacent to v.
	 *
	 * @param i
	 * @param v
	 */
	public GraphListAIterator(Iterator<Edge<V, E>> i, V v) {
		this.edges = i;
		this.vertex = v;
	}

	/**
	 * Resets iterator.
	 */
	public void reset() {
	}

	@Override
	public boolean hasNext() {
		return edges.hasNext();
	}

	@Override
	public V next() {
		Edge<V, E> e = edges.next();
		if (vertex.equals(e.here())) {
			return e.there();
		} else {
			return e.here();
		}
	}

	/**
	 * @return current adjacent vertex
	 */
	public V get() {
		return null;
	}
}

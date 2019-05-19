package structures.graph;

/**
 * A private implementation of a vertex for use in graphs. A vertex is capable
 * of holding a label and has a flag that can be set to mark is as visited.
 *
 * @see https://github.com/sliuu/word_gen/blob/master/structure5/Vertex.java
 */
public class Vertex<V> {

	/**
	 * Label associated with vertex.
	 */
	protected V label;

	/**
	 * Whether or not the vertex has been visited.
	 */
	protected boolean visited;

	/**
	 * Construct a vertex with an associated label.
	 *
	 * @param label - label to be associated with vertex
	 */
	public Vertex(V label) {
		this.label = label;
		visited = false;
	}

	/**
	 * Fetch the label associated with vertex.
	 *
	 * @return label associated with vertex
	 */
	public V label() {
		return label;
	}

	/**
	 * Test and set the visited flag.
	 *
	 * @return value of the flag before marking
	 */
	public boolean visit() {
		boolean was = visited;
		visited = true;
		return was;
	}

	/**
	 * Determine if the vertex has been visited.
	 *
	 * @return true if the vertex has been visited
	 */
	public boolean isVisited() {
		return visited;
	}

	/**
	 * Clears the visited flag.
	 */
	public void reset() {
		visited = false;
	}

	@Override
	public boolean equals(Object o) {
		Vertex<?> v = (Vertex<?>) o;
		return label.equals(v.label);
	}

	@Override
	public int hashCode() {
		return label.hashCode();
	}

	@Override
	public String toString() {
		return "Vertex: " + label + ">";
	}
}

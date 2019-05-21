package structures.graph;

/**
 * A private implementation of a vertex for use in graphs that are internally
 * represented as a Matrix. A vertex is capable of holding a label and has a
 * flag that can be set to mark it as visited.
 *
 * @see https://github.com/sliuu/word_gen/blob/master/structure5/GraphMatrixVertex.java
 */
class GraphMatrixVertex<V> extends Vertex<V> {

	/**
	 * Index associated with vertex.
	 */
	protected int index;

	/**
	 * Constructs a new augmented vertex.
	 *
	 * @param label
	 * @param index
	 */
	public GraphMatrixVertex(V label, int index) {
		super(label);
		this.index = index;
	}

	/**
	 * @return index associated with vertex
	 */
	public int index() {
		return index;
	}
}

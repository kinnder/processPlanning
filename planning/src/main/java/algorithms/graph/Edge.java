package algorithms.graph;

public class Edge<V, E> {

	public Edge(V vtx1, V vtx2, E label, boolean directed) {
		// post: edge associates vtx1 and vtx2; labeled with label; directed if
		// "directed" set true
	}

	public V here() {
		// post: returns first node in edge
		return null;
	}

	public V there() {
		// post: returns second node in edge
		return null;
	}

	public void setLabel(E label) {
		// post: sets label of this edge to label
	}

	public E label() {
		// post: returns label associated with this edge
		return null;
	}

	public boolean visit() {
		// post: visits edge, returns whether previously visited
		return false;
	}

	public boolean isVisited() {
		// post: returns true if edge has been visited
		return false;
	}

	public boolean isDirected() {
		// post: returns true if edge is directed
		return false;
	}

	public void reset() {
		// post: resets edge's visited flag to initial state
	}

	public boolean equals(Object o) {
		// post: returns true if edges connect same vertices
		return false;
	}
}

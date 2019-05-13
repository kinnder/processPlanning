package algorithms.graph;

public class Vertex<V> {

	public Vertex(V label) {
		// post: constructs unvisited vertex with label
	}

	public V label() {
		// post: returns user label associated with vertex
		return null;
	}

	public boolean visit() {
		// post: returns, then marks vertex as being visited
		return false;
	}

	public boolean isVisited() {
		// post: returns true if vertex has been visited
		return false;
	}

	public void reset() {
		// post: marks vertex unvisited
	}

	public boolean equals(Object o) {
		// post: returns true if vertex label are equal
		return false;
	}
}

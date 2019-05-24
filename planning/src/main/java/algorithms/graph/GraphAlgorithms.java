package algorithms.graph;

import java.util.ArrayList;
import java.util.List;

import structures.graph.Edge;
import structures.graph.Graph;

public class GraphAlgorithms {

	static public <V, E> void reachableFrom(Graph<V, E> g, V vertexLabel)
	// pre: g is a non-null graph, vertexLabel labels a vertex of g
	// post: unvisited vertices reachable from vertex are visited
	{
		g.visit(vertexLabel); // visit this vertex
		// recursively visit unvisited neighbor vertices
		for (V neighbor : g.neighbors(vertexLabel)) {
			if (!g.isVisited(neighbor)) {
				reachableFrom(g, neighbor); // depth-first search
			}
		}
	}

	static public <V, E> List<V> topologicalSort(Graph<V, E> g)
	// pre: g is non-null
	// post: returns list of all vertices of g, topologically ordered
	{
		// construct result list
		List<V> l = new ArrayList<V>();
		for (V v : g.vertices()) {
			// perform depth-first search on unvisited vertices
			if (!g.isVisited(v)) {
				DFS(g, v, l);
			}
		}
		// result is queue of vertex labels
		return l;
	}

	static protected <V, E> void DFS(Graph<V, E> g, V n, List<V> l)
	// post: performs depth-first search enqueuing
	// unvisited descendants of node n into l
	{
		g.visit(n); // mark node visited
		for (V neighbor : g.neighbors(n)) {
			// potentially deepen search if neighbor not visited
			if (!g.isVisited(neighbor)) {
				DFS(g, neighbor, l);
			}
		}
		l.add(n); // add this value once decendants added
	}

	// transitive closure
	static public <V, E> void warshall(Graph<V, E> g)
	// pre: g is non-null
	// post: g contains edge (a,b) if there is a path from a to b
	{
		for (V w : g.vertices()) {
			for (V u : g.vertices()) {
				for (V v : g.vertices()) {
					// check for edge from u to v via w
					if (g.containsEdge(u, w) && g.containsEdge(w, v)) {
						g.addEdge(u, v, null);
					}
				}
			}
		}
	}

	// all pairs minimum distance
	static public <V, E> void floyd(Graph<V, Integer> g)
	// post: g contains edge (a,b) if there is a path from a to b
	{
		for (V w : g.vertices()) {
			for (V u : g.vertices()) {
				for (V v : g.vertices()) {
					if (g.containsEdge(u, w) && g.containsEdge(w, v)) {
						Edge<V, Integer> leg1 = g.getEdge(u, w);
						Edge<V, Integer> leg2 = g.getEdge(w, v);
						Integer leg1Dist = leg1.label();
						Integer leg2Dist = leg2.label();
						Integer newDist = leg1Dist + leg2Dist;
						if (g.containsEdge(u, v)) {
							Edge<V, Integer> across = g.getEdge(u, v);
							Integer acrossDist = across.label();
							if (newDist < acrossDist)
								across.setLabel(newDist);
						} else {
							g.addEdge(u, v, newDist);
						}
					}
				}
			}
		}
	}

	// minimum spanning tree
	/*
	 * static public void mcst(Graph<String, Integer> g) // pre: g is a graph //
	 * post: edges of minimum spanning tree of a component are visited { // keep
	 * edges ranked by length // PriorityQueue<ComparableEdge<String, Integer>> q =
	 * new SkewHeap<ComparableEdge<String, Integer>>(); String v = null; // current
	 * vertex Edge<String, Integer> e; // current edge boolean searching; // looking
	 * for a nearby vertex g.reset(); // clear visited flags // select a node from
	 * the graph, if any Iterator<String> vi = g.iterator(); if (!vi.hasNext())
	 * return; v = vi.next(); do { // visit the vertex and add all outgoing edges
	 * g.visit(v); Iterator<String> ai = g.neighbors(v); while (ai.hasNext()) { //
	 * turn it into outgoing edge e = g.getEdge(v, ai.next()); // add the edge to
	 * the queue // q.add(new ComparableEdge<String, Integer>(e)); } searching =
	 * true; // while (searching && !q.isEmpty()) { // grab next shortest edge on
	 * tree fringe // e = q.remove(); // does this edge take us somewhere new? v =
	 * e.there(); if (g.isVisited(v)) v = e.here(); if (!g.isVisited(v)) { searching
	 * = false; g.visitEdge(g.getEdge(e.here(), e.there())); } } } while
	 * (!searching); }
	 */

	// Single-Source Shortest Paths
	/*
	 * public static Map<String, ComparableAssociation<Integer, Edge<String,
	 * Integer>>> dijkstra(Graph<String, Integer> g, String start) // pre: g is a
	 * graph; start is source vertex // post: returns a dictionary of vertex-based
	 * results // value is association (total-distance,prior-edge) { // keep a
	 * priority queue of distances from source
	 * PriorityQueue<ComparableAssociation<Integer, Edge<String, Integer>>> q = new
	 * SkewHeap<ComparableAssociation<Integer, Edge<String, Integer>>>(); //
	 * results, sorted by vertex Map<String, ComparableAssociation<Integer,
	 * Edge<String, Integer>>> result = new Table<String,
	 * ComparableAssociation<Integer, Edge<String, Integer>>>(); String v = start;
	 * // last vertex added // result is a (total-distance,previous-edge) pair
	 * ComparableAssociation<Integer, Edge<String, Integer>> possible = new
	 * ComparableAssociation<Integer, Edge<String, Integer>>( 0, null); // as long
	 * as we add a new vertex... while (v != null) { if (!result.containsKey(v)) {
	 * // visit node v - record incoming edge result.put(v, possible); // vDist is
	 * shortest distance to v int vDist = possible.getKey(); // compute and consider
	 * distance to each neighbor Iterator<String> ai = g.neighbors(v); while
	 * (ai.hasNext()) { // get edge to neighbor Edge<String, Integer> e =
	 * g.getEdge(v, ai.next()); // construct (distance,edge) pair for possible
	 * result possible = new ComparableAssociation<Integer, Edge<String,
	 * Integer>>(vDist + e.label(), e); q.add(possible); // add to priority queue }
	 * } // now, get closest (possibly unvisited) vertex if (!q.isEmpty()) {
	 * possible = q.remove(); // get destination vertex (take care w/undirected
	 * graphs) v = possible.getValue().there(); if (result.containsKey(v)) v =
	 * possible.getValue().here(); } else { // no new vertex (algorithm stops) v =
	 * null; } } return result; }
	 */
}

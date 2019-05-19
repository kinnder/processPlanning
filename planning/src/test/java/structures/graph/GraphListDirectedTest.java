package structures.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import structures.graph.Graph;
import structures.graph.GraphListDirected;

public class GraphListDirectedTest {

	GraphListDirected<String, String> testable;

	@BeforeEach
	public void setUp() {
		testable = new GraphListDirected<String, String>();
	}

	@Test
	public void add() {
		testable.add("harry");
		testable.add("sally");
		testable.addEdge("harry", "sally", "friendly");
	}

	@Test
	public void test() {
		Graph<String, Double> theaters = new GraphListDirected<String, Double>();

		String[] locations = new String[] { "TCL 312", "Images Cinema", "Movie Plex 3", "Cinema 1,2,&3", "Cinema 7",
				"Berkshire Mall Cinemas", "Hathaway's Drive Inn Theatre", "Hollywood Drive-In Theatre" };

		double[] distances = new double[] { -1, 0.0, 12.6, 12.9, 12.9, 14.7, 16.5, 18.0 };

		for (int i = 0; i < locations.length; i++) {
			theaters.add(locations[i]);
		}
		for (int i = 1; i < distances.length; i++) {
			theaters.addEdge(locations[0], locations[i], new Double(distances[i]));
		}
	}
}

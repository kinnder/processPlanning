package algorithms.graph;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import structures.graph.Graph;
import structures.graph.GraphListDirected;

public class ReachabilityTest {

	@RegisterExtension
	JUnit5Mockery context = new JUnit5Mockery() {
		{
			setImposteriser(ByteBuddyClassImposteriser.INSTANCE);
		}
	};

	@AfterEach
	public void teardown() {
		context.assertIsSatisfied();
	}

	Reachability testable;

	@BeforeEach
	public void setup() {
		testable = new Reachability();
	}

	@Test
	public void compute() {
		Graph<String, String> graph = new GraphListDirected<String, String>();
		graph.add("Compiler design");
		graph.add("Languages");
		graph.add("Operating systems");
		graph.add("Vision");
		graph.add("Organization");
		graph.add("Modeling");
		graph.add("A.I.");
		graph.add("Data structures");
		graph.add("Java");
		graph.add("Graphics");
		graph.add("Networks");
		graph.add("Surfing");
		graph.add("Linear algebra");
		graph.add("Algorithms");
		graph.add("Theory");
		graph.add("Discrete math");
		graph.add("Parallel systems");
		graph.addEdge("Compiler design", "Languages", null);
		graph.addEdge("Compiler design", "Theory", null);
		graph.addEdge("Compiler design", "Organization", null);
		graph.addEdge("Languages", "Data structures", null);
		graph.addEdge("Operating systems", "Algorithms", null);
		graph.addEdge("Operating systems", "Organization", null);
		graph.addEdge("Vision", "Data structures", null);
		graph.addEdge("Organization", "Java", null);
		graph.addEdge("A.I.", "Discrete math", null);
		graph.addEdge("A.I.", "Algorithms", null);
		graph.addEdge("Data structures", "Java", null);
		graph.addEdge("Graphics", "Data structures", null);
		graph.addEdge("Graphics", "Linear algebra", null);
		graph.addEdge("Networks", "Data structures", null);
		graph.addEdge("Networks", "Organization", null);
		graph.addEdge("Algorithms", "Data structures", null);
		graph.addEdge("Algorithms", "Discrete math", null);
		graph.addEdge("Theory", "Algorithms", null);
		graph.addEdge("Parallel systems", "Algorithms", null);
		graph.addEdge("Parallel systems", "Organization", null);

		graph.reset();
		Reachability.compute(graph, "Compiler design");
		assertTrue(graph.isVisited("Compiler design"));
		assertTrue(graph.isVisited("Languages"));
		assertTrue(graph.isVisited("Organization"));
		assertTrue(graph.isVisited("Data structures"));
		assertTrue(graph.isVisited("Java"));
		assertTrue(graph.isVisited("Algorithms"));
		assertTrue(graph.isVisited("Theory"));
		assertTrue(graph.isVisited("Discrete math"));
	}
}

package algorithms.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import structures.graph.Graph;
import structures.graph.GraphListDirected;

public class TopologicalSortTest {

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

	TopologicalSort testable;

	@BeforeEach
	public void setup() {
		testable = new TopologicalSort();
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
		List<String> courses = TopologicalSort.compute(graph);
		assertEquals("Java", courses.get(0));
		assertEquals("Data structures", courses.get(1));
		assertEquals("Languages", courses.get(2));
		assertEquals("Discrete math", courses.get(3));
		assertEquals("Algorithms", courses.get(4));
		assertEquals("Theory", courses.get(5));
		assertEquals("Organization", courses.get(6));
		assertEquals("Compiler design", courses.get(7));
		assertEquals("Modeling", courses.get(8));
		assertEquals("Surfing", courses.get(9));
		assertEquals("Parallel systems", courses.get(10));
		assertEquals("Linear algebra", courses.get(11));
		assertEquals("Graphics", courses.get(12));
		assertEquals("Networks", courses.get(13));
		assertEquals("A.I.", courses.get(14));
		assertEquals("Vision", courses.get(15));
		assertEquals("Operating systems", courses.get(16));
	}
}

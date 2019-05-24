package algorithms.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import structures.graph.Graph;
import structures.graph.GraphMatrixUndirected;

public class TransitiveClosureTest {

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

	TransitiveClosure testable;

	@BeforeEach
	public void setup() {
		testable = new TransitiveClosure();
	}

	@Test
	public void compute() {
		Graph<String, Integer> graph = new GraphMatrixUndirected<String, Integer>(12);
		graph.add("Montpelier");
		graph.add("Boston");
		graph.add("Dover");
		graph.add("Albany");
		graph.add("Trenton");
		graph.add("Athens");
		graph.add("Kuala Lumpur");
		graph.add("Bangkok");
		graph.add("Harrisburg");
		graph.add("Salt Lake City");
		graph.add("Sacramento");
		graph.add("Phoenix");
		graph.addEdge("Montpelier", "Boston", 130);
		graph.addEdge("Boston", "Dover", 250);
		graph.addEdge("Boston", "Albany", 120);
		graph.addEdge("Albany", "Trenton", 150);
		graph.addEdge("Trenton", "Athens", 4200);
		graph.addEdge("Athens", "Kuala Lumpur", 4700);
		graph.addEdge("Kuala Lumpur", "Bangkok", 650);
		graph.addEdge("Trenton", "Harrisburg", 100);
		graph.addEdge("Harrisburg", "Salt Lake City", 1500);
		graph.addEdge("Salt Lake City", "Sacramento", 450);
		graph.addEdge("Sacramento", "Phoenix", 550);

		TransitiveClosure.compute(graph);
		assertTrue(graph.containsEdge("Montpelier", "Phoenix"));
		assertEquals(3000, graph.getEdge("Montpelier", "Phoenix").label());
	}
}

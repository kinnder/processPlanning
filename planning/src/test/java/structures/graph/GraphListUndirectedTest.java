package structures.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.List;

import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class GraphListUndirectedTest {

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

	GraphListUndirected<String, String> testable;

	@BeforeEach
	public void setUp() {
		testable = new GraphListUndirected<String, String>();
	}

	@Test
	public void neighbors() {
		testable.add("object-a");
		testable.add("object-b");
		testable.add("object-c");
		testable.add("object-d");
		testable.addEdge("object-a", "object-b", "relation");
		testable.addEdge("object-b", "object-c", "relation");
		testable.addEdge("object-c", "object-d", "relation");
		testable.addEdge("object-d", "object-a", "relation");

		List<String> neighbors = testable.neighbors("object-a");
		assertEquals(2, neighbors.size());
		assertEquals("object-b", neighbors.get(0));
		assertEquals("object-d", neighbors.get(1));
	}

	@Test
	public void edges() {
		testable.add("object-a");
		testable.add("object-b");
		testable.add("object-c");
		testable.addEdge("object-a", "object-b", "relation-ab");
		testable.addEdge("object-b", "object-c", "relation-bc");

		List<Edge<String, String>> edges = testable.edges();
		assertEquals(2, edges.size());
		assertEquals("relation-bc", edges.get(0).label());
		assertEquals("relation-ab", edges.get(1).label());
	}

	@Test
	public void addEdge() {
		testable.add("object-a");
		testable.add("object-b");
		testable.addEdge("object-a", "object-b", "relation-ab");

		assertNotNull(testable.getEdge("object-a", "object-b"));
		assertNotNull(testable.getEdge("object-b", "object-a"));
	}

	@Test
	public void removeEdge() {
		testable.add("object-a");
		testable.add("object-b");
		testable.addEdge("object-a", "object-b", "relation-ab");

		assertNotNull(testable.removeEdge("object-a", "object-b"));

		assertNull(testable.removeEdge("object-a", "object-b"));
	}

	@Test
	public void edgeCount() {
		testable.add("object-a");
		testable.add("object-b");
		testable.addEdge("object-a", "object-b", "relation-ab");

		assertEquals(1, testable.edgeCount());
	}
}

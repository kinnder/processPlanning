package structures.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;

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

		Iterator<String> neighbors = testable.neighbors("object-a");
		assertTrue(neighbors.hasNext());
		assertEquals("object-b", neighbors.next());
		assertTrue(neighbors.hasNext());
		assertEquals("object-d", neighbors.next());
		assertFalse(neighbors.hasNext());
	}

	@Test
	public void edges() {
		testable.add("object-a");
		testable.add("object-b");
		testable.add("object-c");
		testable.addEdge("object-a", "object-b", "relation-ab");
		testable.addEdge("object-b", "object-c", "relation-bc");

		Iterator<Edge<String, String>> edges = testable.edges();
		assertTrue(edges.hasNext());
		assertEquals("relation-bc", edges.next().label());
		assertTrue(edges.hasNext());
		assertEquals("relation-ab", edges.next().label());
		assertFalse(edges.hasNext());
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

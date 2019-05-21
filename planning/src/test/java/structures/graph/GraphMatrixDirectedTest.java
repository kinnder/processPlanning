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

public class GraphMatrixDirectedTest {

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

	GraphMatrixDirected<String, String> testable;

	final int graphSize = 10;

	@BeforeEach
	public void setup() {
		testable = new GraphMatrixDirected<String, String>(graphSize);
	}

	@Test
	public void add() {
		testable.add("object-a");
		assertEquals(1, testable.size());

		testable.add("object-a");
		assertEquals(1, testable.size());
	}

	@Test
	public void remove() {
		assertNull(testable.remove("object-a"));

		testable.add("object-a");
		assertEquals("object-a", testable.remove("object-a"));
	}

	@Test
	public void get() {
		testable.add("object-a");
		assertEquals("object-a", testable.get("object-a"));
	}

	@Test
	public void getEdge() {
		testable.add("object-a");
		testable.add("object-b");
		testable.addEdge("object-a", "object-b", "relation-ab");
		assertNotNull(testable.getEdge("object-a", "object-b"));
	}

	@Test
	public void contains() {
		assertFalse(testable.contains("object-a"));

		testable.add("object-a");
		assertTrue(testable.contains("object-a"));
	}

	@Test
	public void containsEdge() {
		testable.add("object-a");
		testable.add("object-b");
		assertFalse(testable.containsEdge("object-a", "object-b"));

		testable.addEdge("object-a", "object-b", "relation-ab");
		assertTrue(testable.containsEdge("object-a", "object-b"));
	}

	@Test
	public void visit() {
		testable.add("object-a");

		assertFalse(testable.visit("object-a"));
	}

	@Test
	public void visitEdge() {
		testable.add("object-a");
		testable.add("object-b");
		testable.addEdge("object-a", "object-b", "relation-ab");

		Edge<String, String> edge = testable.getEdge("object-a", "object-b");
		assertFalse(testable.visitEdge(edge));
	}

	@Test
	public void isVisited() {
		testable.add("object-a");
		assertFalse(testable.isVisited("object-a"));
	}

	@Test
	public void isVisitedEdge() {
		testable.add("object-a");
		testable.add("object-b");
		testable.addEdge("object-a", "object-b", "relation-ab");

		Edge<String, String> edge = testable.getEdge("object-a", "object-b");
		assertFalse(testable.isVisitedEdge(edge));
	}

	@Test
	public void reset() {
		testable.add("object-a");
		testable.add("object-b");
		testable.addEdge("object-a", "object-b", "relation-ab");
		testable.visit("object-a");
		Edge<String, String> edge = testable.getEdge("object-a", "object-b");
		testable.visitEdge(edge);

		testable.reset();
		assertFalse(testable.isVisited("object-a"));
		assertFalse(testable.isVisitedEdge(edge));
	}

	@Test
	public void size() {
		assertEquals(0, testable.size());

		testable.add("object-a");
		assertEquals(1, testable.size());
	}

	@Test
	public void degree() {
		testable.add("object-a");
		testable.add("object-b");
		testable.addEdge("object-a", "object-b", "relation-ab");

		assertEquals(1, testable.degree("object-a"));
	}

	@Test
	public void iterator() {
		assertNotNull(testable.iterator());
	}

	@Test
	public void clear() {
		testable.add("object-a");

		testable.clear();
		assertTrue(testable.isEmpty());
	}

	@Test
	public void isEmpty() {
		assertTrue(testable.isEmpty());

		testable.add("object-a");
		assertFalse(testable.isEmpty());
	}

	@Test
	public void isDirected() {
		assertTrue(testable.isDirected());
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
		assertEquals("relation-ab", edges.next().label());
		assertTrue(edges.hasNext());
		assertEquals("relation-bc", edges.next().label());
		assertFalse(edges.hasNext());
	}

	@Test
	public void addEdge() {
		testable.add("object-a");
		testable.add("object-b");
		testable.addEdge("object-a", "object-b", "relation-ab");

		assertNotNull(testable.getEdge("object-a", "object-b"));
		assertNull(testable.getEdge("object-b", "object-a"));
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

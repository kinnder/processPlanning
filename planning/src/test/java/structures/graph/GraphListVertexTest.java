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

public class GraphListVertexTest {

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

	GraphListVertex<String, String> testable;

	@BeforeEach
	public void setUp() {
		testable = new GraphListVertex<String, String>("a");
	}

	@Test
	public void addEdge() {
		Edge<String, String> edge = new Edge<String, String>("a", "b", "link", false);

		testable.addEdge(edge);
		assertEquals(1, testable.degree());

		testable.addEdge(edge);
		assertEquals(1, testable.degree());
	}

	@Test
	public void containsEdge() {
		Edge<String, String> edge = new Edge<String, String>("a", "b", "link", false);

		assertFalse(testable.containsEdge(edge));

		testable.addEdge(edge);
		assertTrue(testable.containsEdge(edge));
	}

	@Test
	public void removeEdge() {
		Edge<String, String> edge = new Edge<String, String>("a", "b", "link", false);
		testable.addEdge(edge);

		assertNotNull(testable.removeEdge(edge));
		assertNull(testable.removeEdge(edge));
	}

	@Test
	public void getEdge() {
		Edge<String, String> edge1 = new Edge<String, String>("a", "b", "link-1", false);
		Edge<String, String> edge2 = new Edge<String, String>("a", "c", "link-2", false);
		testable.addEdge(edge1);
		testable.addEdge(edge2);

		assertEquals(edge2, testable.getEdge(edge2));
	}

	@Test
	public void degree() {
		assertEquals(0, testable.degree());
	}

	@Test
	public void adjacentVertices() {
		Edge<String, String> edge1 = new Edge<String, String>("a", "b", "link-1", false);
		Edge<String, String> edge2 = new Edge<String, String>("a", "c", "link-2", false);
		testable.addEdge(edge1);
		testable.addEdge(edge2);

		Iterator<String> vLabels = testable.adjacentVertices();
		assertTrue(vLabels.hasNext());
		assertEquals("b", vLabels.next());
		assertTrue(vLabels.hasNext());
		assertEquals("c", vLabels.next());
		assertFalse(vLabels.hasNext());
	}

	@Test
	public void adjacentEdges() {
		Edge<String, String> edge1 = new Edge<String, String>("a", "b", "link-1", false);
		Edge<String, String> edge2 = new Edge<String, String>("a", "c", "link-2", false);
		testable.addEdge(edge1);
		testable.addEdge(edge2);

		Iterator<Edge<String, String>> edges = testable.adjacentEdges();
		assertTrue(edges.hasNext());
		assertEquals("link-1", edges.next().label());
		assertTrue(edges.hasNext());
		assertEquals("link-2", edges.next().label());
		assertFalse(edges.hasNext());
	}
}

package structures.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class EdgeTest {

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

	Edge<String, String> testable;

	@BeforeEach
	public void setUp() {
		testable = new Edge<String, String>("a", "b", "link", false);
	}

	@Test
	public void here() {
		assertEquals("a", testable.here());
	}

	@Test
	public void there() {
		assertEquals("b", testable.there());
	}

	@Test
	public void setLabel() {
		testable.setLabel("new link");
		assertEquals("new link", testable.label());
	}

	@Test
	public void label() {
		assertEquals("link", testable.label());
	}

	@Test
	public void visit() {
		assertFalse(testable.visit());
		assertTrue(testable.isVisited());
	}

	@Test
	public void isVisited() {
		assertFalse(testable.isVisited());
	}

	@Test
	public void isDirected() {
		assertFalse(testable.isDirected());
	}

	@Test
	public void reset() {
		testable.reset();
		assertFalse(testable.isVisited());
	}

	@Test
	public void equals_undirected() {
		Edge<String, String> edge;

		edge = new Edge<String, String>("a", "b", "link", false);
		assertTrue(testable.equals(edge));

		edge = new Edge<String, String>("b", "a", "link", false);
		assertTrue(testable.equals(edge));

		edge = new Edge<String, String>("a", "d", "link", false);
		assertFalse(testable.equals(edge));
		
		edge = new Edge<String, String>("d", "a", "link", false);
		assertFalse(testable.equals(edge));
	}

	@Test
	public void equals_directed() {
		testable = new Edge<String, String>("a", "b", "link", true);
		Edge<String, String> edge;
		
		edge = new Edge<String, String>("b", "a", "link", false);
		assertFalse(testable.equals(edge));
	}
	
	@Test
	public void hashCode_undirected() {
		assertEquals(3, testable.hashCode());
	}
	
	@Test
	public void hashCode_directed() {
		testable = new Edge<String, String>("a", "b", "link", true);
		assertEquals(-1, testable.hashCode());
	}
}

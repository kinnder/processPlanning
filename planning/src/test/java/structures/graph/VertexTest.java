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

public class VertexTest {

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

	Vertex<String> testable;

	@BeforeEach
	public void setUp() {
		testable = new Vertex<String>("a");
	}

	@Test
	public void getLabel() {
		assertEquals("a", testable.label());
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
	public void reset() {
		testable.reset();
		assertFalse(testable.isVisited());
	}

	@Test
	public void equals() {
		Vertex<String> vertex;

		vertex = new Vertex<String>("a");
		assertTrue(testable.equals(vertex));

		vertex = new Vertex<String>("b");
		assertFalse(testable.equals(vertex));
	}

	@Test
	public void hashCode_case() {
		assertEquals(97, testable.hashCode());
	}
}

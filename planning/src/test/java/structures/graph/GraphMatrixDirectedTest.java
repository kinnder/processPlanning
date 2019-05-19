package structures.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
		testable.addEdge("object-a", "object-b", "relation");
		assertNotNull(testable.getEdge("object-a", "object-b"));
	}

	@Test
	public void contains() {
		assertFalse(testable.contains("object-a"));

		testable.add("object-a");
		assertTrue(testable.contains("object-a"));
	}
}

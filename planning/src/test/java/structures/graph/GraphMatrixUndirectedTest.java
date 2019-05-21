package structures.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;

import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class GraphMatrixUndirectedTest {

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

	GraphMatrixUndirected<String, String> testable;

	final int graphSize = 10;

	@BeforeEach
	public void setup() {
		testable = new GraphMatrixUndirected<String, String>(graphSize);
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
}

package planning.method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.SystemOperation;

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

	Edge testable;

	SystemOperation systemOperation_mock;

	String beginNodeId;

	String endNodeId;

	@BeforeEach
	public void setup() {
		systemOperation_mock = context.mock(SystemOperation.class, "action");
		beginNodeId = "node-begin-id";
		endNodeId = "node-end-id";

		testable = new Edge(beginNodeId, endNodeId, systemOperation_mock);
	}

	@Test
	public void equals() {
		final Edge edge = new Edge(testable.getId(), beginNodeId, endNodeId, systemOperation_mock);
		assertTrue(testable.equals(edge));
	}

	@Test
	public void equals_null() {
		assertFalse(testable.equals(null));
	}

	@Test
	public void equals_self() {
		assertTrue(testable.equals(testable));
	}

	@Test
	public void equals_type() {
		assertFalse(testable.equals(new Object()));
	}

	@Test
	public void equals_differentId() {
		final Edge edge = new Edge("differentId", beginNodeId, endNodeId, systemOperation_mock);
		assertFalse(testable.equals(edge));
	}

	@Test
	public void getSystemOperation() {
		assertEquals(systemOperation_mock, testable.getSystemOperation());
	}

	@Test
	public void setSystemOperation() {
		final SystemOperation newSystemOperation_mock = context.mock(SystemOperation.class, "new-operation");
		testable.setSystemOperation(newSystemOperation_mock);
		assertEquals(newSystemOperation_mock, testable.getSystemOperation());
	}

	@Test
	public void getId() {
		testable = new Edge("id", beginNodeId, endNodeId, systemOperation_mock);
		assertEquals("id", testable.getId());
	}

	@Test
	public void setId() {
		testable.setId("new-id");
		assertEquals("new-id", testable.getId());
	}

	@Test
	public void hashCode_test() {
		testable = new Edge("id", beginNodeId, endNodeId, systemOperation_mock);
		assertEquals(3355, testable.hashCode());
	}

	@Test
	public void getBeginNodeId() {
		assertEquals(beginNodeId, testable.getBeginNodeId());
	}

	@Test
	public void setBeginNodeId() {
		testable.setBeginNodeId("new-begin-node-id");
		assertEquals("new-begin-node-id", testable.getBeginNodeId());
	}

	@Test
	public void getEndNodeId() {
		assertEquals(endNodeId, testable.getEndNodeId());
	}

	@Test
	public void setEndNodeId() {
		testable.setEndNodeId("new-end-node-id");
		assertEquals("new-end-node-id", testable.getEndNodeId());
	}

	@Test
	public void toString_test() {
		testable = new Edge("id123", beginNodeId, endNodeId, systemOperation_mock);
		assertEquals("edge-id123", testable.toString());
	}
}

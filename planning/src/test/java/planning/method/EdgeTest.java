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

	@BeforeEach
	public void setup() {
		systemOperation_mock = context.mock(SystemOperation.class, "action");

		testable = new Edge(systemOperation_mock);
	}

	@Test
	public void equals() {
		final Edge edge = new Edge(testable.getId(), testable.getSystemOperation());
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
	public void equals_differentId() {
		final Edge edge = new Edge("differentId", systemOperation_mock);
		assertFalse(testable.equals(edge));
	}

	@Test
	public void getSystemOperation() {
		assertEquals(systemOperation_mock, testable.getSystemOperation());
	}

	@Test
	public void getId() {
		testable = new Edge("id", systemOperation_mock);
		assertEquals("id", testable.getId());
	}

	@Test
	public void hashCode_test() {
		testable = new Edge("id", systemOperation_mock);
		assertEquals(3355, testable.hashCode());
	}
}

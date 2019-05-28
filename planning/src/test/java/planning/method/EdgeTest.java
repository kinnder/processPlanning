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

import planning.model.Element;
import planning.model.SystemVariant;

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

	Element element_mock;

	SystemVariant systemVariant_mock;

	@BeforeEach
	public void setup() {
		element_mock = context.mock(Element.class, "element");
		systemVariant_mock = context.mock(SystemVariant.class, "systemVariant");

		testable = new Edge(element_mock, systemVariant_mock);
	}

	@Test
	public void equals() {
		final Edge edge = new Edge(testable.getId(), testable.getElement(), testable.getSystemVariant());
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
		final Edge edge = new Edge("differentId", element_mock, systemVariant_mock);
		assertFalse(testable.equals(edge));
	}

	@Test
	public void getElement() {
		assertEquals(element_mock, testable.getElement());
	}

	@Test
	public void getId() {
		testable = new Edge("id", element_mock, systemVariant_mock);
		assertEquals("id", testable.getId());
	}

	@Test
	public void getSystemVariant() {
		assertEquals(systemVariant_mock, testable.getSystemVariant());
	}

	@Test
	public void hashCode_test() {
		testable = new Edge("id", element_mock, systemVariant_mock);
		assertEquals(3355, testable.hashCode());
	}
}

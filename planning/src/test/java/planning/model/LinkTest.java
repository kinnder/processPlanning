package planning.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class LinkTest {

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

	Link testable;

	System system_mock;

	@BeforeEach
	public void setup() {
		system_mock = context.mock(System.class, "system");

		testable = new Link("link", system_mock, "id");
	}

	@Test
	public void clone_test() {
		assertTrue(testable != testable.clone());
	}

	@Test
	public void equals() {
		final SystemObject object_mock = context.mock(SystemObject.class);

		context.checking(new Expectations() {
			{
				oneOf(system_mock).getObjectById("id");
				will(returnValue(object_mock));

				oneOf(system_mock).getObjectById("id");
				will(returnValue(object_mock));
			}
		});
		assertTrue(testable.equals(testable.clone()));
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
	public void equals_differentName() {
		assertFalse(testable.equals(new Link("different", system_mock, "value")));
	}

	@Test
	public void equals_differentId() {
		assertFalse(testable.equals(new Link("link", system_mock, "different")));
	}

	@Test
	public void equals_differentObjects() {
		final SystemObject object_mock = context.mock(SystemObject.class);

		context.checking(new Expectations() {
			{
				oneOf(system_mock).getObjectById("id");
				will(returnValue(object_mock));

				oneOf(system_mock).getObjectById("id");
				will(returnValue(null));
			}
		});

		assertFalse(testable.equals(testable.clone()));
	}

	@Test
	public void matches() {
		final Link template = new Link("link", system_mock, "id");
		assertTrue(testable.matches(template));
	}

	@Test
	public void matches_differentName() {
		final Link template = new Link("different", system_mock, "id");
		assertFalse(testable.matches(template));
	}

	@Test
	public void getName() {
		assertEquals("link", testable.getName());
	}

	@Test
	public void setSystem() {
		final System system_mock = context.mock(System.class, "new-system");

		testable.setSystem(system_mock);
	}
}

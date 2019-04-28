package planning.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class SystemTest {

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

	System testable;

	@BeforeEach
	public void setup() {
		testable = new System();
	}

	@Test
	public void clone_test() {
		final SystemObject object_1_mock = context.mock(SystemObject.class, "object-1");
		final SystemObject object_2_mock = context.mock(SystemObject.class, "object-2");

		context.checking(new Expectations() {
			{
				oneOf(object_1_mock).clone();
				will(returnValue(object_2_mock));

				oneOf(object_2_mock).updateLinks(with(any(System.class)));
			}
		});

		testable.addObject(object_1_mock);

		assertTrue(testable != testable.clone());
	}

	@Test
	public void equals() {
		final SystemObject object_mock = context.mock(SystemObject.class, "object");
		testable.addObject(object_mock);

		final System system = new System();
		system.addObject(object_mock);

		assertTrue(testable.equals(system));
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
	public void equals_differentObjectAmount() {
		final SystemObject object_mock = context.mock(SystemObject.class, "object");

		final System system = new System();
		system.addObject(object_mock);

		assertFalse(testable.equals(system));
	}

	@Test
	public void equals_differentObject() {
		final SystemObject object_1_mock = context.mock(SystemObject.class, "object-1");
		testable.addObject(object_1_mock);

		final System system = new System();
		final SystemObject object_2_mock = context.mock(SystemObject.class, "object-2");
		system.addObject(object_2_mock);

		assertFalse(testable.equals(system));
	}

	@Test
	public void getObjectById() {
		final SystemObject object_1 = new SystemObject("object-1", "id-1");
		final SystemObject object_2 = new SystemObject("object-2", "id-2");
		testable.addObject(object_1);
		testable.addObject(object_2);

		assertEquals(object_2, testable.getObjectById("id-2"));
	}

	@Test
	public void getObjectById_notFound() {
		assertNull(testable.getObjectById("id"));
	}
}

package planning.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

	@BeforeEach
	public void setup() {
		testable = new Link("link", "id");
	}

	@Test
	public void clone_test() {
		assertTrue(testable != testable.clone());
	}

	@Test
	public void equals() {
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
		assertFalse(testable.equals(new Link("different", "value")));
	}

	@Test
	public void equals_differentId() {
		assertFalse(testable.equals(new Link("link", "different")));
	}

	@Test
	public void equals_objectId_null() {
		testable = new Link("link", null);
		assertFalse(testable.equals(new Link("link", "value")));
	}

	@Test
	public void equals_ids_null() {
		testable = new Link("link", null);
		assertTrue(testable.equals(new Link("link", null)));
	}

	@Test
	public void equals_differentName_ids_null() {
		testable = new Link("link", null);
		assertFalse(testable.equals(new Link("different", null)));
	}

	@Test
	public void matches() {
		final Link template = new Link("link", "id");
		assertTrue(testable.matches(template));
	}

	@Test
	public void matches_new() {
		final Link template = new Link("link", "id-template");
		final IdsMatching idsMatching_mock = context.mock(IdsMatching.class);

		context.checking(new Expectations() {
			{
				oneOf(idsMatching_mock).get("id-template");
				will(returnValue("id"));
			}
		});

		assertTrue(testable.matches(template, idsMatching_mock));
	}

	@Test
	public void matches_differentName() {
		final Link template = new Link("different", "id");
		assertFalse(testable.matches(template));
	}

	@Test
	public void matches_new_differentName() {
		final Link template = new Link("different", "id");
		final IdsMatching idsMatching_mock = context.mock(IdsMatching.class);

		assertFalse(testable.matches(template, idsMatching_mock));
	}

	@Test
	public void matches_new_differentId() {
		final Link template = new Link("link", "another-id-template");
		final IdsMatching idsMatching_mock = context.mock(IdsMatching.class);

		context.checking(new Expectations() {
			{
				oneOf(idsMatching_mock).get("another-id-template");
				will(returnValue("another-id"));
			}
		});

		assertFalse(testable.matches(template, idsMatching_mock));
	}

	@Test
	public void getName() {
		assertEquals("link", testable.getName());
	}

	@Test
	public void getObjectId() {
		assertEquals("id", testable.getObjectId());
	}

	@Test
	public void setObjectId() {
		testable.setObjectId("new id");
	}
}

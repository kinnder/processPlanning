package planning.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class LinkTemplateTest {

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

	LinkTemplate testable;

	@BeforeEach
	public void setup() {
		testable = new LinkTemplate("link", "id-template-1", "id-template-2");
	}

	@Test
	public void matches() {
		final IdsMatching idsMatching_mock = context.mock(IdsMatching.class);

		context.checking(new Expectations() {
			{
				oneOf(idsMatching_mock).get("id-template-1");
				will(returnValue("id-1"));

				oneOf(idsMatching_mock).get("id-template-2");
				will(returnValue("id-2"));
			}
		});

		assertTrue(testable.matches(new Link("link", "id-1", "id-2"), idsMatching_mock));
	}

	@Test
	public void matches_differentName() {
		final IdsMatching idsMatching_mock = context.mock(IdsMatching.class);

		assertFalse(testable.matches(new Link("different", "id-1", null), idsMatching_mock));
	}

	@Test
	public void matches_differentObjectId1() {
		final IdsMatching idsMatching_mock = context.mock(IdsMatching.class);

		context.checking(new Expectations() {
			{
				oneOf(idsMatching_mock).get("id-template-1");
				will(returnValue("id-1"));
			}
		});

		assertFalse(testable.matches(new Link("link", "id-dirrent", "id-2"), idsMatching_mock));
	}

	@Test
	public void matches_differentObjectId2() {
		final IdsMatching idsMatching_mock = context.mock(IdsMatching.class);

		context.checking(new Expectations() {
			{
				oneOf(idsMatching_mock).get("id-template-1");
				will(returnValue("id-1"));

				oneOf(idsMatching_mock).get("id-template-2");
				will(returnValue("id-2"));
			}
		});

		assertFalse(testable.matches(new Link("link", "id-1", "id-different"), idsMatching_mock));
	}

	@Test
	public void matches_ids_null() {
		testable = new LinkTemplate("link", null, null);
		final IdsMatching idsMatching_mock = context.mock(IdsMatching.class);

		context.checking(new Expectations() {
			{
				oneOf(idsMatching_mock).get(null);
				will(returnValue(null));

				oneOf(idsMatching_mock).get(null);
				will(returnValue(null));
			}
		});

		assertTrue(testable.matches(new Link("link", null, null), idsMatching_mock));
	}

	@Test
	public void matches_objectIds_null() {
		final IdsMatching idsMatching_mock = context.mock(IdsMatching.class);

		context.checking(new Expectations() {
			{
				oneOf(idsMatching_mock).get("id-template-1");
				will(returnValue("id-1"));
			}
		});

		assertFalse(testable.matches(new Link("link", null, null), idsMatching_mock));
	}

	@Test
	public void getName() {
		assertEquals("link", testable.getName());
	}

	@Test
	public void getObjectId1() {
		assertEquals("id-template-1", testable.getObjectId1());
	}

	@Test
	public void getObjectId2() {
		assertEquals("id-template-2", testable.getObjectId2());
	}
}

package planning.model;

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
		testable = new LinkTemplate("link", "id-template");
	}

	@Test
	public void matches() {
		final IdsMatching idsMatching_mock = context.mock(IdsMatching.class);

		context.checking(new Expectations() {
			{
				oneOf(idsMatching_mock).get("id-template");
				will(returnValue("id"));
			}
		});

		assertTrue(testable.matches(new Link("link", "id"), idsMatching_mock));
	}

	@Test
	public void matches_differentName() {
		final IdsMatching idsMatching_mock = context.mock(IdsMatching.class);

		assertFalse(testable.matches(new Link("different", "id"), idsMatching_mock));
	}

	@Test
	public void matches_differentId() {
		final IdsMatching idsMatching_mock = context.mock(IdsMatching.class);

		context.checking(new Expectations() {
			{
				oneOf(idsMatching_mock).get("id-template");
				will(returnValue("id"));
			}
		});

		assertFalse(testable.matches(new Link("link", "another-id-template"), idsMatching_mock));
	}

	@Test
	public void matches_ids_null() {
		testable = new LinkTemplate("link", null);
		final IdsMatching idsMatching_mock = context.mock(IdsMatching.class);

		assertTrue(testable.matches(new Link("link", null), idsMatching_mock));
	}

	@Test
	public void matches_objectId_null() {
		testable = new LinkTemplate("link", null);
		final IdsMatching idsMatching_mock = context.mock(IdsMatching.class);

		context.checking(new Expectations() {
			{
				oneOf(idsMatching_mock).get(null);
				will(returnValue(null));
			}
		});

		assertFalse(testable.matches(new Link("link", "value"), idsMatching_mock));
	}
}

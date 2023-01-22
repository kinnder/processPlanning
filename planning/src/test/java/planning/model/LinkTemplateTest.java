package planning.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

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
	public void newInstance() {
		testable = new LinkTemplate();
		assertTrue(testable.getName().startsWith("linkTemplate-"));
		assertEquals("", testable.getId1());
		assertEquals("", testable.getId2());
	}

	@Test
	public void newInstance_uniqueName() {
		testable = new LinkTemplate();
		LinkTemplate testable2 = new LinkTemplate();
		assertNotEquals(testable.getName(), testable2.getName());
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
	public void setName() {
		testable.setName("new-link");
		assertEquals("new-link", testable.getName());
	}

	@Test
	public void getId1() {
		assertEquals("id-template-1", testable.getId1());
	}

	@Test
	public void setId1() {
		testable.setId1("new-id-template-1");
		assertEquals("new-id-template-1", testable.getId1());
	}

	@Test
	public void getId2() {
		assertEquals("id-template-2", testable.getId2());
	}

	@Test
	public void setId2() {
		testable.setId2("new-id-template-2");
		assertEquals("new-id-template-2", testable.getId2());
	}

	@Test
	public void clone_test() throws CloneNotSupportedException {
		assertTrue(testable != testable.clone());
	}

	@Test
	public void equals() throws CloneNotSupportedException {
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
	public void equals_type() {
		assertFalse(testable.equals(new Object()));
	}

	@Test
	public void equals_different_name() {
		assertFalse(testable.equals(new LinkTemplate("different", "id-template-1", "id-template-2")));
	}

	@Test
	public void equals_different_objectId1() {
		assertFalse(testable.equals(new LinkTemplate("link", "different", "id-template-2")));
	}

	@Test
	public void equals_different_objectId2() {
		assertFalse(testable.equals(new LinkTemplate("link", "id-template-1", "different")));
	}

	@Test
	public void getIds() {
		Set<String> ids = testable.getIds();
		assertEquals(2, ids.size());
		assertTrue(ids.contains("id-template-1"));
		assertTrue(ids.contains("id-template-2"));
	}

	@Test
	public void getIds_null_ids() {
		testable = new LinkTemplate("name", null, null);

		Set<String> ids = testable.getIds();
		assertEquals(0, ids.size());
	}
}

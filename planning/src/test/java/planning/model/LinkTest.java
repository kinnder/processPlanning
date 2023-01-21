package planning.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

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
		testable = new Link("link", "id-1", "id-2");
	}

	@Test
	public void newInstance() {
		testable = new Link();
		assertTrue(testable.getName().startsWith("link-"));
		assertEquals("", testable.getId1());
		assertEquals("", testable.getId2());
	}

	@Test
	public void newInstance_uniqueName() {
		testable = new Link();
		Link testable2 = new Link();
		assertNotEquals(testable.getName(), testable2.getName());
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
		assertFalse(testable.equals(new Link("different", "id-1", "id-2")));
	}

	@Test
	public void equals_different_objectId1() {
		assertFalse(testable.equals(new Link("link", "another", "id-2")));
	}

	@Test
	public void equals_different_objectId2() {
		assertFalse(testable.equals(new Link("link", "id-1", "another")));
	}

	@Test
	public void getName() {
		assertEquals("link", testable.getName());
	}

	@Test
	public void toString_test() {
		assertEquals("link", testable.toString());
	}

	@Test
	public void setName() {
		testable.setName("new-link-name");
		assertEquals("new-link-name", testable.getName());
	}

	@Test
	public void getId1() {
		assertEquals("id-1", testable.getId1());
	}

	@Test
	public void setId1() {
		testable.setId1("new-id-1");
		assertEquals("new-id-1", testable.getId1());
	}

	@Test
	public void getId2() {
		assertEquals("id-2", testable.getId2());
	}

	@Test
	public void setId2() {
		testable.setId2("new-id-2");
		assertEquals("new-id-2", testable.getId2());
	}

	@Test
	public void createTemplate() {
		assertNotNull(testable.createTemplate());
	}

	@Test
	public void getIds() {
		Set<String> ids = testable.getIds();
		assertEquals(2, ids.size());
		assertTrue(ids.contains("id-1"));
		assertTrue(ids.contains("id-2"));
	}

	@Test
	public void getIds_null_ids() {
		testable = new Link("name", null, null);

		Set<String> ids = testable.getIds();
		assertEquals(0, ids.size());
	}
}

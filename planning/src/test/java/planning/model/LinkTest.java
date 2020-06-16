package planning.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
	public void getObjectId1() {
		assertEquals("id-1", testable.getObjectId1());
	}

	@Test
	public void setObjectId1() {
		testable.setObjectId1("new-id-1");
		assertEquals("new-id-1", testable.getObjectId1());
	}

	@Test
	public void getObjectId2() {
		assertEquals("id-2", testable.getObjectId2());
	}

	@Test
	public void setObjectId2() {
		testable.setObjectId2("new-id-2");
		assertEquals("new-id-2", testable.getObjectId2());
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
	public void getIds_null_id() {
		testable = new Link("name", "id-1", null);

		Set<String> ids = testable.getIds();
		assertEquals(1, ids.size());
		assertTrue(ids.contains("id-1"));
	}
}

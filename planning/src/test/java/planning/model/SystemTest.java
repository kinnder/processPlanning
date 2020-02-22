package planning.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
	public void clone_test() throws CloneNotSupportedException {
		final SystemObject object_1_mock = context.mock(SystemObject.class, "object-1");
		final SystemObject object_2_mock = context.mock(SystemObject.class, "object-2");

		context.checking(new Expectations() {
			{
				oneOf(object_1_mock).clone();
				will(returnValue(object_2_mock));
			}
		});

		testable.addObject(object_1_mock);

		assertNotEquals(testable, testable.clone());
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

	@Test
	public void getObjectByName() {
		final SystemObject object_1 = new SystemObject("object-1", "id-1");
		final SystemObject object_2 = new SystemObject("object-2", "id-2");
		testable.addObject(object_1);
		testable.addObject(object_2);

		assertEquals(object_2, testable.getObjectByName("object-2"));
	}

	@Test
	public void getObjectByName_notFound() {
		assertNull(testable.getObjectByName("name"));
	}

	@Test
	public void getIds() {
		final SystemObject object_1_mock = context.mock(SystemObject.class, "object-1");
		final SystemObject object_2_mock = context.mock(SystemObject.class, "object-2");
		testable.addObject(object_1_mock);
		testable.addObject(object_2_mock);

		final Set<String> object_1_ids = new HashSet<String>();
		object_1_ids.add("id-1");
		final Set<String> object_2_ids = new HashSet<String>();
		object_2_ids.add("id-2");
		object_2_ids.add("id-1");

		context.checking(new Expectations() {
			{
				oneOf(object_1_mock).getIds();
				will(returnValue(object_1_ids));

				oneOf(object_2_mock).getIds();
				will(returnValue(object_2_ids));
			}
		});

		Set<String> systemIds = testable.getIds();
		assertEquals(2, systemIds.size());
		assertTrue(systemIds.contains("id-1"));
		assertTrue(systemIds.contains("id-2"));
	}

	@Test
	public void contains() {
		final System system_mock = context.mock(System.class, "system");
		final SystemTemplate systemTemplate_mock = context.mock(SystemTemplate.class);
		final IdsMatching idsMatching_mock = context.mock(IdsMatching.class);
		final IdsMatching idsMatching[] = new IdsMatching[] { idsMatching_mock };

		context.checking(new Expectations() {
			{
				oneOf(system_mock).createTemplate();
				will(returnValue(systemTemplate_mock));

				oneOf(systemTemplate_mock).matchIds(testable);
				will(returnValue(idsMatching));

				oneOf(idsMatching_mock).areKeysAndValuesTheSame();
				will(returnValue(true));
			}
		});

		assertTrue(testable.contains(system_mock));
	}

	@Test
	public void contains_different() {
		final System system_mock = context.mock(System.class, "system");
		final SystemTemplate systemTemplate_mock = context.mock(SystemTemplate.class);
		final IdsMatching idsMatching_mock = context.mock(IdsMatching.class);
		final IdsMatching idsMatching[] = new IdsMatching[] { idsMatching_mock };

		context.checking(new Expectations() {
			{
				oneOf(system_mock).createTemplate();
				will(returnValue(systemTemplate_mock));

				oneOf(systemTemplate_mock).matchIds(testable);
				will(returnValue(idsMatching));

				oneOf(idsMatching_mock).areKeysAndValuesTheSame();
				will(returnValue(false));
			}
		});

		assertFalse(testable.contains(system_mock));
	}

	@Test
	public void contains_no_matchings() {
		final System system_mock = context.mock(System.class, "system");
		final SystemTemplate systemTemplate_mock = context.mock(SystemTemplate.class);
		final IdsMatching idsMatching[] = new IdsMatching[] {};

		context.checking(new Expectations() {
			{
				oneOf(system_mock).createTemplate();
				will(returnValue(systemTemplate_mock));

				oneOf(systemTemplate_mock).matchIds(testable);
				will(returnValue(idsMatching));
			}
		});

		assertFalse(testable.contains(system_mock));
	}

	@Test
	public void getObjects() {
		assertTrue(testable.getObjects() instanceof Collection);
	}

	@Test
	public void createTemplate() {
		final SystemObject object_mock = context.mock(SystemObject.class);
		testable.addObject(object_mock);
		final SystemObjectTemplate template_mock = context.mock(SystemObjectTemplate.class);

		context.checking(new Expectations() {
			{
				oneOf(object_mock).createTemplate();
				will(returnValue(template_mock));
			}
		});

		SystemTemplate template = testable.createTemplate();
		assertEquals(1, template.getObjectTemplates().size());
	}

	@Test
	public void addObject() {
		final SystemObject object_mock = context.mock(SystemObject.class);
		testable.addObject(object_mock);

		assertEquals(1, testable.getObjects().size());
	}

	@Test
	public void addNewObject() {
		SystemObject object = testable.addNewObject("new-object");

		assertEquals("new-object", object.getName());
		assertEquals(1, testable.getObjects().size());
	}
}

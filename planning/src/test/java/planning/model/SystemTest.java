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
		final SystemObject object_mock = context.mock(SystemObject.class, "object");
		final SystemObject clonedObject_mock = context.mock(SystemObject.class, "object-clone");
		final Link link_mock = context.mock(Link.class, "link");
		final Link clonedLink_mock = context.mock(Link.class, "link-clone");

		context.checking(new Expectations() {
			{
				oneOf(object_mock).clone();
				will(returnValue(clonedObject_mock));

				oneOf(link_mock).clone();
				will(returnValue(clonedLink_mock));
			}
		});

		testable.addObject(object_mock);
		testable.addLink(link_mock);

		assertNotEquals(testable, testable.clone());
	}

	@Test
	public void equals() {
		final System system = new System();

		final SystemObject object_mock = context.mock(SystemObject.class, "object");
		testable.addObject(object_mock);
		system.addObject(object_mock);

		final Link link_mock = context.mock(Link.class, "link");
		testable.addLink(link_mock);
		system.addLink(link_mock);

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
	public void equals_type() {
		assertFalse(testable.equals(new Object()));
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
	public void equals_differentLinkAmount() {
		final Link link_mock = context.mock(Link.class, "link");

		final System system = new System();
		system.addLink(link_mock);

		assertFalse(testable.equals(system));
	}

	@Test
	public void equals_differentLink() {
		final Link link_1_mock = context.mock(Link.class, "link-1");
		final Link link_2_mock = context.mock(Link.class, "link-2");

		testable.addLink(link_1_mock);

		final System system = new System();
		system.addLink(link_2_mock);

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
		final SystemObject object_mock = context.mock(SystemObject.class);
		testable.addObject(object_mock);

		final Link link_mock = context.mock(Link.class);
		testable.addLink(link_mock);

		final Set<String> objectIds = new HashSet<>();
		objectIds.add("id-1");
		objectIds.add("id-2");

		final Set<String> linkIds = new HashSet<>();
		linkIds.add("id-2");
		linkIds.add("id-3");

		context.checking(new Expectations() {
			{
				oneOf(object_mock).getIds();
				will(returnValue(objectIds));

				oneOf(link_mock).getIds();
				will(returnValue(linkIds));
			}
		});

		Set<String> systemIds = testable.getIds();
		assertEquals(3, systemIds.size());
		assertTrue(systemIds.contains("id-1"));
		assertTrue(systemIds.contains("id-2"));
		assertTrue(systemIds.contains("id-3"));
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
		final SystemObjectTemplate objectTemplate_mock = context.mock(SystemObjectTemplate.class);
		final Link link_mock = context.mock(Link.class);
		testable.addLink(link_mock);
		final LinkTemplate linkTemplate_mock = context.mock(LinkTemplate.class);

		context.checking(new Expectations() {
			{
				oneOf(object_mock).createTemplate();
				will(returnValue(objectTemplate_mock));

				oneOf(link_mock).createTemplate();
				will(returnValue(linkTemplate_mock));
			}
		});

		SystemTemplate template = testable.createTemplate();
		assertEquals(1, template.getObjectTemplates().size());
		assertEquals(1, template.getLinkTemplates().size());
	}

	@Test
	public void addObject() {
		final SystemObject object_mock = context.mock(SystemObject.class);
		testable.addObject(object_mock);

		assertEquals(1, testable.getObjects().size());
	}

	@Test
	public void addNewObject() {
		final SystemObject object = testable.addNewObject("new-object");

		assertEquals("new-object", object.getName());
		assertEquals(1, testable.getObjects().size());
	}

	@Test
	public void addLink() {
		final SystemObject object1_mock = context.mock(SystemObject.class, "object-1");
		final SystemObject object2_mock = context.mock(SystemObject.class, "object-2");

		context.checking(new Expectations() {
			{
				oneOf(object1_mock).getId();
				will(returnValue("object-1-id"));

				oneOf(object2_mock).getId();
				will(returnValue("object-2-id"));
			}
		});

		testable.addLink(object1_mock, "link-name-1", "link-name-2", object2_mock);

		Collection<Link> links = testable.getLinks();
		assertEquals(2, links.size());
		assertTrue(links.contains(new Link("link-name-1", "object-1-id", "object-2-id")));
		assertTrue(links.contains(new Link("link-name-2", "object-2-id", "object-1-id")));
	}

	@Test
	public void addLink_lua() {
		testable.addLink("link-name", "object-1-id", "object-2-id");

		Collection<Link> links = testable.getLinks();
		assertEquals(1, links.size());
		assertTrue(links.contains(new Link("link-name", "object-1-id", "object-2-id")));
	}

	@Test
	public void addLink_null_object1() {
		final SystemObject object2_mock = context.mock(SystemObject.class, "object-2");

		context.checking(new Expectations() {
			{
				oneOf(object2_mock).getId();
				will(returnValue("object-2-id"));
			}
		});

		testable.addLink(null, "link-name", object2_mock);

		Collection<Link> links = testable.getLinks();
		assertEquals(1, links.size());
		assertTrue(links.contains(new Link("link-name", "object-2-id", null)));
	}

	@Test
	public void addLink_null_object2() {
		final SystemObject object1_mock = context.mock(SystemObject.class, "object-1");

		context.checking(new Expectations() {
			{
				oneOf(object1_mock).getId();
				will(returnValue("object-1-id"));
			}
		});

		testable.addLink(object1_mock, "link-name", null);

		Collection<Link> links = testable.getLinks();
		assertEquals(1, links.size());
		assertTrue(links.contains(new Link("link-name", "object-1-id", null)));
	}

	@Test
	public void getLinks() {
		assertTrue(testable.getLinks() instanceof Collection);
	}

	@Test
	public void addLink_object() {
		testable.addLink(new Link("link-name", "object-1-id", null));

		Collection<Link> links = testable.getLinks();
		assertEquals(1, links.size());
		assertTrue(links.contains(new Link("link-name", "object-1-id", null)));
	}

	@Test
	public void getLink() {
		final Link link = new Link("link-name", "object-1-id", "object-2-id");
		testable.addLink(link);

		assertEquals(link, testable.getLink("link-name", "object-1-id", "object-2-id"));
	}

	@Test
	public void getLink_notFound() {
		final Link link = new Link("link-name", "object-1-id", "object-2-id");
		testable.addLink(link);

		assertNull(testable.getLink("link-name", null, null));
	}

	@Test
	public void systemToString() {
		// TODO (2022-09-22 #72): сделать поле name не изменяемым
		testable.name = "";
		assertEquals("", testable.toString());
	}
}

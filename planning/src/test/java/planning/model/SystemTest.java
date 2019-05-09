package planning.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
	public void clone_test() {
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

	@SuppressWarnings("unchecked")
	@Test
	public void matchIds() {
		final IdsMatchingManager idsMatchingManager_mock = context.mock(IdsMatchingManager.class);
		testable = new System(idsMatchingManager_mock);

		final SystemObject object_1_mock = context.mock(SystemObject.class, "object-1");
		testable.addObject(object_1_mock);

		final System systemTemplate = new System();
		final SystemObject object_1_template_mock = context.mock(SystemObject.class, "object-1-template");
		systemTemplate.addObject(object_1_template_mock);

		final SystemObject object_1_clone_mock = context.mock(SystemObject.class, "object-1-clone");
		final IdsMatching idsMatching_mock = context.mock(IdsMatching.class);

		final Set<String> object_1_template_ids = new HashSet<String>();
		final Set<String> object_1_ids_mock = new HashSet<String>();

		context.checking(new Expectations() {
			{
				oneOf(object_1_template_mock).getObjectIds();
				will(returnValue(object_1_template_ids));

				oneOf(object_1_mock).getObjectIds();
				will(returnValue(object_1_ids_mock));

				oneOf(idsMatchingManager_mock).prepareMatchingsCandidates(with(any(Set.class)), with(any(Set.class)));

				oneOf(object_1_mock).matchesAttributes(object_1_template_mock);
				will(returnValue(true));

				oneOf(idsMatchingManager_mock).generateMatchingsFromCandidates();

				oneOf(idsMatchingManager_mock).haveUncheckedMatching();
				will(returnValue(true));

				oneOf(idsMatchingManager_mock).getUncheckedMatching();
				will(returnValue(idsMatching_mock));

				oneOf(object_1_mock).matchesAttributes(object_1_template_mock);
				will(returnValue(true));

				oneOf(object_1_mock).matchesLinks(object_1_template_mock, idsMatching_mock);
				will(returnValue(true));

				oneOf(idsMatchingManager_mock).haveUncheckedMatching();
				will(returnValue(false));

				oneOf(idsMatchingManager_mock).getMatchingsAmount();
				will(returnValue(1));

				oneOf(object_1_mock).clone();
				will(returnValue(object_1_clone_mock));

				oneOf(idsMatchingManager_mock).getMatching(0);
				will(returnValue(idsMatching_mock));
			}
		});

		SystemVariant[] systemVariants = testable.matchIds(systemTemplate);
		assertEquals(1, systemVariants.length);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void matchIds_differentAttributes() {
		final IdsMatchingManager idsMatchingManager_mock = context.mock(IdsMatchingManager.class);
		testable = new System(idsMatchingManager_mock);

		final SystemObject object_1_mock = context.mock(SystemObject.class, "object-1");
		testable.addObject(object_1_mock);

		final System systemTemplate = new System();
		final SystemObject object_1_template_mock = context.mock(SystemObject.class, "object-1-template");
		systemTemplate.addObject(object_1_template_mock);

		final Set<String> object_1_template_ids = new HashSet<String>();
		final Set<String> object_1_ids_mock = new HashSet<String>();

		context.checking(new Expectations() {
			{
				oneOf(object_1_template_mock).getObjectIds();
				will(returnValue(object_1_template_ids));

				oneOf(object_1_mock).getObjectIds();
				will(returnValue(object_1_ids_mock));

				oneOf(idsMatchingManager_mock).prepareMatchingsCandidates(with(any(Set.class)), with(any(Set.class)));

				oneOf(object_1_mock).matchesAttributes(object_1_template_mock);
				will(returnValue(false));

				oneOf(object_1_template_mock).getObjectId();
				will(returnValue("id-1-template"));

				oneOf(object_1_mock).getObjectId();
				will(returnValue("id-1"));

				oneOf(idsMatchingManager_mock).removeMatchingsCandidate("id-1-template", "id-1");

				oneOf(idsMatchingManager_mock).generateMatchingsFromCandidates();

				oneOf(idsMatchingManager_mock).haveUncheckedMatching();
				will(returnValue(false));

				oneOf(idsMatchingManager_mock).getMatchingsAmount();
				will(returnValue(0));
			}
		});

		SystemVariant[] systemVariants = testable.matchIds(systemTemplate);
		assertEquals(0, systemVariants.length);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void matchIds_differentLinks() {
		final IdsMatchingManager idsMatchingManager_mock = context.mock(IdsMatchingManager.class);
		testable = new System(idsMatchingManager_mock);

		final SystemObject object_1_mock = context.mock(SystemObject.class, "object-1");
		testable.addObject(object_1_mock);

		final System systemTemplate = new System();
		final SystemObject object_1_template_mock = context.mock(SystemObject.class, "object-1-template");
		systemTemplate.addObject(object_1_template_mock);

		final IdsMatching idsMatching_mock = context.mock(IdsMatching.class);

		final Set<String> object_1_template_ids = new HashSet<String>();
		final Set<String> object_1_ids_mock = new HashSet<String>();

		context.checking(new Expectations() {
			{
				oneOf(object_1_template_mock).getObjectIds();
				will(returnValue(object_1_template_ids));

				oneOf(object_1_mock).getObjectIds();
				will(returnValue(object_1_ids_mock));

				oneOf(idsMatchingManager_mock).prepareMatchingsCandidates(with(any(Set.class)), with(any(Set.class)));

				oneOf(object_1_mock).matchesAttributes(object_1_template_mock);
				will(returnValue(true));

				oneOf(idsMatchingManager_mock).generateMatchingsFromCandidates();

				oneOf(idsMatchingManager_mock).haveUncheckedMatching();
				will(returnValue(true));

				oneOf(idsMatchingManager_mock).getUncheckedMatching();
				will(returnValue(idsMatching_mock));

				oneOf(object_1_mock).matchesAttributes(object_1_template_mock);
				will(returnValue(true));

				oneOf(object_1_mock).matchesLinks(object_1_template_mock, idsMatching_mock);
				will(returnValue(false));

				oneOf(idsMatchingManager_mock).removeMatching(idsMatching_mock);

				oneOf(idsMatchingManager_mock).haveUncheckedMatching();
				will(returnValue(false));

				oneOf(idsMatchingManager_mock).getMatchingsAmount();
				will(returnValue(0));
			}
		});

		SystemVariant[] systemVariants = testable.matchIds(systemTemplate);
		assertEquals(0, systemVariants.length);
	}

	@Test
	public void getSystemIds() {
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
				oneOf(object_1_mock).getObjectIds();
				will(returnValue(object_1_ids));

				oneOf(object_2_mock).getObjectIds();
				will(returnValue(object_2_ids));
			}
		});

		Set<String> systemIds = testable.getSystemIds();
		assertEquals(2, systemIds.size());
		assertTrue(systemIds.contains("id-1"));
		assertTrue(systemIds.contains("id-2"));
	}
}

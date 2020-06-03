package planning.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

public class SystemTemplateTest {

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

	SystemTemplate testable;

	IdsMatchingManager idsMatchingManager_mock;

	@BeforeEach
	public void setup() {
		idsMatchingManager_mock = context.mock(IdsMatchingManager.class);

		testable = new SystemTemplate(idsMatchingManager_mock);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void matchIds() {
		final SystemObjectTemplate objectTemplate_mock = context.mock(SystemObjectTemplate.class);
		testable.addObjectTemplate(objectTemplate_mock);

		final System system = new System();
		final SystemObject object_mock = context.mock(SystemObject.class);
		system.addObject(object_mock);

		final IdsMatching idsMatching_mock = context.mock(IdsMatching.class);

		final IdsMatching idsMatching[] = new IdsMatching[] { idsMatching_mock };

		final Set<String> objectTemplate_ids = new HashSet<>();
		final Set<String> object_ids = new HashSet<>();

		context.checking(new Expectations() {
			{
				oneOf(objectTemplate_mock).getIds();
				will(returnValue(objectTemplate_ids));

				oneOf(object_mock).getIds();
				will(returnValue(object_ids));

				oneOf(idsMatchingManager_mock).prepareMatchingsCandidates(with(any(Set.class)), with(any(Set.class)));

				oneOf(objectTemplate_mock).matchesAttributes(object_mock);
				will(returnValue(true));

				oneOf(idsMatchingManager_mock).generateMatchingsFromCandidates();

				oneOf(idsMatchingManager_mock).haveUncheckedMatching();
				will(returnValue(true));

				oneOf(idsMatchingManager_mock).getUncheckedMatching();
				will(returnValue(idsMatching_mock));

				oneOf(objectTemplate_mock).matchesAttributes(object_mock);
				will(returnValue(true));

				oneOf(objectTemplate_mock).matchesLinks(object_mock, idsMatching_mock);
				will(returnValue(true));

				oneOf(idsMatchingManager_mock).haveUncheckedMatching();
				will(returnValue(false));

				oneOf(idsMatchingManager_mock).getIdsMatchings();
				will(returnValue(idsMatching));
			}
		});

		assertEquals(idsMatching, testable.matchIds(system));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void matchIds_differentAttributes() {
		final SystemObjectTemplate objectTemplate_mock = context.mock(SystemObjectTemplate.class);
		testable.addObjectTemplate(objectTemplate_mock);

		final System system = new System();
		final SystemObject object_mock = context.mock(SystemObject.class);
		system.addObject(object_mock);

		final Set<String> objectTemplate_ids = new HashSet<String>();
		final Set<String> object_ids = new HashSet<String>();

		final IdsMatching idsMatching[] = new IdsMatching[] {};

		context.checking(new Expectations() {
			{
				oneOf(objectTemplate_mock).getIds();
				will(returnValue(objectTemplate_ids));

				oneOf(object_mock).getIds();
				will(returnValue(object_ids));

				oneOf(idsMatchingManager_mock).prepareMatchingsCandidates(with(any(Set.class)), with(any(Set.class)));

				oneOf(objectTemplate_mock).matchesAttributes(object_mock);
				will(returnValue(false));

				oneOf(objectTemplate_mock).getId();
				will(returnValue("id-template"));

				oneOf(object_mock).getId();
				will(returnValue("id"));

				oneOf(idsMatchingManager_mock).removeMatchingsCandidate("id-template", "id");

				oneOf(idsMatchingManager_mock).generateMatchingsFromCandidates();

				oneOf(idsMatchingManager_mock).haveUncheckedMatching();
				will(returnValue(false));

				oneOf(idsMatchingManager_mock).getIdsMatchings();
				will(returnValue(idsMatching));
			}
		});

		assertEquals(idsMatching, testable.matchIds(system));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void matchIds_differentLinks() {
		final SystemObjectTemplate objectTemplate_mock = context.mock(SystemObjectTemplate.class);
		testable.addObjectTemplate(objectTemplate_mock);

		final System system = new System();
		final SystemObject object_mock = context.mock(SystemObject.class);
		system.addObject(object_mock);

		final IdsMatching idsMatching_mock = context.mock(IdsMatching.class);

		final Set<String> object_ids = new HashSet<String>();
		final Set<String> objectTemplate_ids = new HashSet<String>();

		final IdsMatching idsMatching[] = new IdsMatching[] {};

		context.checking(new Expectations() {
			{
				oneOf(object_mock).getIds();
				will(returnValue(object_ids));

				oneOf(objectTemplate_mock).getIds();
				will(returnValue(objectTemplate_ids));

				oneOf(idsMatchingManager_mock).prepareMatchingsCandidates(with(any(Set.class)), with(any(Set.class)));

				oneOf(objectTemplate_mock).matchesAttributes(object_mock);
				will(returnValue(true));

				oneOf(idsMatchingManager_mock).generateMatchingsFromCandidates();

				oneOf(idsMatchingManager_mock).haveUncheckedMatching();
				will(returnValue(true));

				oneOf(idsMatchingManager_mock).getUncheckedMatching();
				will(returnValue(idsMatching_mock));

				oneOf(objectTemplate_mock).matchesAttributes(object_mock);
				will(returnValue(true));

				oneOf(objectTemplate_mock).matchesLinks(object_mock, idsMatching_mock);
				will(returnValue(false));

				oneOf(idsMatchingManager_mock).removeMatching(idsMatching_mock);

				oneOf(idsMatchingManager_mock).haveUncheckedMatching();
				will(returnValue(false));

				oneOf(idsMatchingManager_mock).getIdsMatchings();
				will(returnValue(idsMatching));
			}
		});

		assertEquals(idsMatching, testable.matchIds(system));
	}

	@Test
	public void getObjectTemplates() {
		final SystemObjectTemplate objectTemplate_mock = context.mock(SystemObjectTemplate.class);
		testable.addObjectTemplate(objectTemplate_mock);

		assertEquals(1, testable.getObjectTemplates().size());
	}

	@Test
	public void getLinkTemplates() {
		assertTrue(testable.getLinkTemplates() instanceof Collection);
	}

	@Test
	public void addLinkTemplate() {
		final SystemObjectTemplate objectTemplate1_mock = context.mock(SystemObjectTemplate.class, "object-template-1");
		final SystemObjectTemplate objectTemplate2_mock = context.mock(SystemObjectTemplate.class, "object-template-2");

		context.checking(new Expectations() {
			{
				oneOf(objectTemplate1_mock).getId();
				will(returnValue("object-1-id"));

				oneOf(objectTemplate2_mock).getId();
				will(returnValue("object-2-id"));

				oneOf(objectTemplate1_mock).addLinkTemplate("link-name-1", "object-2-id");

				oneOf(objectTemplate2_mock).addLinkTemplate("link-name-2", "object-1-id");
			}
		});

		testable.addLinkTemplate(objectTemplate1_mock, "link-name-1", "link-name-2", objectTemplate2_mock);

		Collection<LinkTemplate> linkTemplates = testable.getLinkTemplates();
		assertEquals(2, linkTemplates.size());
		assertTrue(linkTemplates.contains(new LinkTemplate("link-name-1", "object-1-id", "object-2-id")));
		assertTrue(linkTemplates.contains(new LinkTemplate("link-name-2", "object-2-id", "object-1-id")));
	}

	@Test
	public void addLinkTemplate_null_object1() {
		final SystemObjectTemplate objectTemplate2_mock = context.mock(SystemObjectTemplate.class, "object-template-2");

		context.checking(new Expectations() {
			{
				oneOf(objectTemplate2_mock).getId();
				will(returnValue("object-2-id"));

				oneOf(objectTemplate2_mock).addLinkTemplate("link-name", null);
			}
		});

		testable.addLinkTemplate(null, "link-name", objectTemplate2_mock);

		Collection<LinkTemplate> linkTemplates = testable.getLinkTemplates();
		assertEquals(1, linkTemplates.size());
		assertTrue(linkTemplates.contains(new LinkTemplate("link-name", "object-2-id", null)));
	}

	@Test
	public void addLinkTemplate_null_object2() {
		final SystemObjectTemplate object1_mock = context.mock(SystemObjectTemplate.class, "object-1");

		context.checking(new Expectations() {
			{
				oneOf(object1_mock).getId();
				will(returnValue("object-1-id"));

				oneOf(object1_mock).addLinkTemplate("link-name", null);
			}
		});

		testable.addLinkTemplate(object1_mock, "link-name", null);

		Collection<LinkTemplate> linkTemplates = testable.getLinkTemplates();
		assertEquals(1, linkTemplates.size());
		assertTrue(linkTemplates.contains(new LinkTemplate("link-name", "object-1-id", null)));
	}

	@Test
	public void addLinkTemplate_object() {
		testable.addLinkTemplate(new LinkTemplate("link-name", "object-1-id", null));

		Collection<LinkTemplate> linkTemplates = testable.getLinkTemplates();
		assertEquals(1, linkTemplates.size());
		assertTrue(linkTemplates.contains(new LinkTemplate("link-name", "object-1-id", null)));
	}
}

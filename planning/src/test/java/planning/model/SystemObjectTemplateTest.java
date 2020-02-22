package planning.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.Set;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class SystemObjectTemplateTest {

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

	SystemObjectTemplate testable;

	@BeforeEach
	public void setup() {
		testable = new SystemObjectTemplate("id");
	}

	@Test
	public void getId() {
		assertEquals("id", testable.getId());
	}

	@Test
	public void matchesAttributes() {
		final AttributeTemplate attributeTemplate_1_mock = context.mock(AttributeTemplate.class, "attributeTemplate-1");
		final AttributeTemplate attributeTemplate_2_mock = context.mock(AttributeTemplate.class, "attributeTemplate-2");
		context.checking(new Expectations() {
			{
				oneOf(attributeTemplate_1_mock).getName();
				will(returnValue("attribute-1-template"));

				oneOf(attributeTemplate_2_mock).getName();
				will(returnValue("attribute-2-template"));
			}
		});
		testable.addAttributeTemplate(attributeTemplate_1_mock);
		testable.addAttributeTemplate(attributeTemplate_2_mock);

		final SystemObject object = new SystemObject("object");
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).getName();
				will(returnValue("attribute-1"));

				oneOf(attribute_2_mock).getName();
				will(returnValue("attribute-2"));
			}
		});
		object.addAttribute(attribute_1_mock);
		object.addAttribute(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(attributeTemplate_2_mock).matches(attribute_2_mock);
				will(returnValue(true));

				oneOf(attributeTemplate_1_mock).matches(attribute_1_mock);
				will(returnValue(true));

				oneOf(attributeTemplate_1_mock).matches(attribute_2_mock);
				will(returnValue(false));
			}
		});

		assertTrue(testable.matchesAttributes(object));
	}

	@Test
	public void matchesAttributes_differentAttribute() {
		final AttributeTemplate attributeTemplate_1_mock = context.mock(AttributeTemplate.class, "attributeTemplate-1");
		final AttributeTemplate attributeTemplate_3_mock = context.mock(AttributeTemplate.class, "attributeTemplate-3");
		context.checking(new Expectations() {
			{
				oneOf(attributeTemplate_1_mock).getName();
				will(returnValue("attribute-1-template"));

				oneOf(attributeTemplate_3_mock).getName();
				will(returnValue("attribute-3-template"));
			}
		});
		testable.addAttributeTemplate(attributeTemplate_1_mock);
		testable.addAttributeTemplate(attributeTemplate_3_mock);

		final SystemObject object = new SystemObject("object");
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).getName();
				will(returnValue("attribute-1"));

				oneOf(attribute_2_mock).getName();
				will(returnValue("attribute-2"));
			}
		});
		object.addAttribute(attribute_1_mock);
		object.addAttribute(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(attributeTemplate_1_mock).matches(attribute_1_mock);
				will(returnValue(true));

				oneOf(attributeTemplate_3_mock).matches(attribute_1_mock);
				will(returnValue(false));

				oneOf(attributeTemplate_1_mock).matches(attribute_2_mock);
				will(returnValue(false));

				oneOf(attributeTemplate_3_mock).matches(attribute_2_mock);
				will(returnValue(false));
			}
		});

		assertFalse(testable.matchesAttributes(object));
	}

	@Test
	public void matchesLinks() {
		final LinkTemplate linkTemplate_1_mock = context.mock(LinkTemplate.class, "linkTemplate-1");
		final LinkTemplate linkTemplate_2_mock = context.mock(LinkTemplate.class, "linkTemplate-2");
		context.checking(new Expectations() {
			{
				oneOf(linkTemplate_1_mock).getName();
				will(returnValue("link-1-template"));

				oneOf(linkTemplate_2_mock).getName();
				will(returnValue("link-2-template"));
			}
		});
		testable.addLinkTemplate(linkTemplate_1_mock);
		testable.addLinkTemplate(linkTemplate_2_mock);

		final SystemObject object = new SystemObject("object");
		final Link link_1_mock = context.mock(Link.class, "link-1");
		final Link link_2_mock = context.mock(Link.class, "link-2");
		object.addLink(link_1_mock);
		object.addLink(link_2_mock);

		final IdsMatching idsMatching_mock = context.mock(IdsMatching.class);

		context.checking(new Expectations() {
			{
				oneOf(linkTemplate_1_mock).matches(link_1_mock, idsMatching_mock);
				will(returnValue(true));

				oneOf(linkTemplate_2_mock).matches(link_2_mock, idsMatching_mock);
				will(returnValue(true));
			}
		});

		assertTrue(testable.matchesLinks(object, idsMatching_mock));
	}

	@Test
	public void matechsLinks_differentLink() {
		final LinkTemplate linkTemplate_1_mock = context.mock(LinkTemplate.class, "linkTemplate-1");
		final LinkTemplate linkTemplate_3_mock = context.mock(LinkTemplate.class, "linkTemplate-3");
		context.checking(new Expectations() {
			{
				oneOf(linkTemplate_1_mock).getName();
				will(returnValue("link-1-template"));

				oneOf(linkTemplate_3_mock).getName();
				will(returnValue("link-3-template"));
			}
		});
		testable.addLinkTemplate(linkTemplate_1_mock);
		testable.addLinkTemplate(linkTemplate_3_mock);

		final SystemObject object = new SystemObject("object");
		final Link link_1_mock = context.mock(Link.class, "link-1");
		final Link link_2_mock = context.mock(Link.class, "link-2");
		object.addLink(link_1_mock);
		object.addLink(link_2_mock);

		final IdsMatching idsMatching_mock = context.mock(IdsMatching.class);

		context.checking(new Expectations() {
			{
				oneOf(linkTemplate_1_mock).matches(link_1_mock, idsMatching_mock);
				will(returnValue(true));

				oneOf(linkTemplate_3_mock).matches(link_2_mock, idsMatching_mock);
				will(returnValue(false));
			}
		});

		assertFalse(testable.matchesLinks(object, idsMatching_mock));
	}

	@Test
	public void getIds() {
		final LinkTemplate link_mock = context.mock(LinkTemplate.class, "link");
		context.checking(new Expectations() {
			{
				oneOf(link_mock).getName();
				will(returnValue("link"));
			}
		});
		testable.addLinkTemplate(link_mock);

		context.checking(new Expectations() {
			{
				oneOf(link_mock).getObjectId();
				will(returnValue("id-2"));
			}
		});

		Set<String> ids = testable.getIds();
		assertEquals(2, ids.size());
		assertTrue(ids.contains("id-2"));
	}

	@Test
	public void getIds_nullValuedLink() {
		final LinkTemplate link_mock = context.mock(LinkTemplate.class, "link");
		context.checking(new Expectations() {
			{
				oneOf(link_mock).getName();
				will(returnValue("link"));
			}
		});
		testable.addLinkTemplate(link_mock);

		context.checking(new Expectations() {
			{
				oneOf(link_mock).getObjectId();
				will(returnValue(null));
			}
		});

		Set<String> ids = testable.getIds();
		assertEquals(1, ids.size());
	}

	@Test
	public void getAttributeTemplates() {
		final AttributeTemplate attributeTemplate_1_mock = context.mock(AttributeTemplate.class, "attributeTemplate-1");
		final AttributeTemplate attributeTemplate_2_mock = context.mock(AttributeTemplate.class, "attributeTemplate-2");
		context.checking(new Expectations() {
			{
				oneOf(attributeTemplate_1_mock).getName();
				will(returnValue("attribute-1-template"));

				oneOf(attributeTemplate_2_mock).getName();
				will(returnValue("attribute-2-template"));
			}
		});
		testable.addAttributeTemplate(attributeTemplate_1_mock);
		testable.addAttributeTemplate(attributeTemplate_2_mock);

		Collection<AttributeTemplate> attributes = testable.getAttributeTemplates();
		assertEquals(2, attributes.size());
	}

	@Test
	public void getLinkTemplates() {
		final LinkTemplate linkTemplate_1_mock = context.mock(LinkTemplate.class, "linkTemplate-1");
		final LinkTemplate linkTemplate_2_mock = context.mock(LinkTemplate.class, "linkTemplate-2");
		context.checking(new Expectations() {
			{
				oneOf(linkTemplate_1_mock).getName();
				will(returnValue("link-1-template"));

				oneOf(linkTemplate_2_mock).getName();
				will(returnValue("link-2-template"));
			}
		});
		testable.addLinkTemplate(linkTemplate_1_mock);
		testable.addLinkTemplate(linkTemplate_2_mock);

		Collection<LinkTemplate> links = testable.getLinkTemplates();
		assertEquals(2, links.size());
	}
}

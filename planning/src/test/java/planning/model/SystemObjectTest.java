package planning.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class SystemObjectTest {

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

	SystemObject testable;

	@BeforeEach
	public void setup() {
		testable = new SystemObject("object");
	}

	@Test
	public void clone_test() {
		final Link link_mock = context.mock(Link.class, "link");
		final Link clonedLink_mock = context.mock(Link.class, "link-clone");
		final Attribute attribute_mock = context.mock(Attribute.class, "attribute");
		final Attribute clonedAttribute_mock = context.mock(Attribute.class, "attribute-clone");

		context.checking(new Expectations() {
			{
				oneOf(link_mock).getName();
				will(returnValue("link-name"));

				oneOf(attribute_mock).getName();
				will(returnValue("attribute-name"));

				oneOf(attribute_mock).clone();
				will(returnValue(clonedAttribute_mock));

				oneOf(clonedAttribute_mock).getName();
				will(returnValue("attribute-name"));

				oneOf(link_mock).clone();
				will(returnValue(clonedLink_mock));

				oneOf(clonedLink_mock).getName();
				will(returnValue("link-name"));
			}
		});

		testable.addLink(link_mock);
		testable.addAttribute(attribute_mock);

		assertTrue(testable != testable.clone());
	}

	@Test
	public void equals() {
		final Link link_mock = context.mock(Link.class, "link");
		final Attribute attribute_mock = context.mock(Attribute.class, "attribute");

		context.checking(new Expectations() {
			{
				oneOf(link_mock).getName();
				will(returnValue("link-name"));

				oneOf(attribute_mock).getName();
				will(returnValue("attribute-name"));
			}
		});
		testable.addLink(link_mock);
		testable.addAttribute(attribute_mock);

		context.checking(new Expectations() {
			{
				oneOf(link_mock).getName();
				will(returnValue("link-name"));

				oneOf(attribute_mock).getName();
				will(returnValue("attribute-name"));
			}
		});
		final SystemObject systemObject = new SystemObject("object");
		systemObject.addLink(link_mock);
		systemObject.addAttribute(attribute_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_mock).getName();
				will(returnValue("attribute-name"));

				oneOf(link_mock).getName();
				will(returnValue("link-name"));
			}
		});

		assertTrue(testable.equals(systemObject));
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
	public void equals_differentName() {
		assertFalse(testable.equals(new SystemObject("object 2")));
	}

	@Test
	public void equals_differentAttributeAmount() {
		final Attribute attribute_mock = context.mock(Attribute.class, "attribute");

		context.checking(new Expectations() {
			{
				oneOf(attribute_mock).getName();
				will(returnValue("attribute-name"));
			}
		});
		final SystemObject systemObject = new SystemObject("object");
		systemObject.addAttribute(attribute_mock);

		assertFalse(testable.equals(systemObject));
	}

	@Test
	public void equals_differentAttribute() {
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");

		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).getName();
				will(returnValue("attribute-name"));
			}
		});
		testable.addAttribute(attribute_1_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_2_mock).getName();
				will(returnValue("attribute-name"));
			}
		});
		final SystemObject systemObject = new SystemObject("object");
		systemObject.addAttribute(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).getName();
				will(returnValue("attribute-name"));
			}
		});

		assertFalse(testable.equals(systemObject));
	}

	@Test
	public void equals_differentLinkAmount() {
		final Link link_mock = context.mock(Link.class, "link");

		context.checking(new Expectations() {
			{
				oneOf(link_mock).getName();
				will(returnValue("link-name"));
			}
		});
		final SystemObject systemObject = new SystemObject("object");
		systemObject.addLink(link_mock);

		assertFalse(testable.equals(systemObject));
	}

	@Test
	public void equals_differentLink() {
		final Link link_1_mock = context.mock(Link.class, "link-1");
		final Link link_2_mock = context.mock(Link.class, "link-2");

		context.checking(new Expectations() {
			{
				oneOf(link_1_mock).getName();
				will(returnValue("link-name"));
			}
		});
		testable.addLink(link_1_mock);

		context.checking(new Expectations() {
			{
				oneOf(link_2_mock).getName();
				will(returnValue("link-name"));
			}
		});
		final SystemObject systemObject = new SystemObject("object");
		systemObject.addLink(link_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(link_1_mock).getName();
				will(returnValue("link-name"));
			}
		});

		assertFalse(testable.equals(systemObject));
	}

	@Test
	public void addLink() {
		final Link link_mock = context.mock(Link.class, "link");

		context.checking(new Expectations() {
			{
				oneOf(link_mock).getName();
				will(returnValue("link-name"));
			}
		});

		testable.addLink(link_mock);
	}

	@Test
	public void addAttribute() {
		final Attribute attribute_mock = context.mock(Attribute.class, "attribute");

		context.checking(new Expectations() {
			{
				oneOf(attribute_mock).getName();
				will(returnValue("attribute-name"));
			}
		});

		testable.addAttribute(attribute_mock);
	}

	@Test
	public void getObjectId() {
		testable = new SystemObject("object", "id");
		assertEquals("id", testable.getObjectId());
	}

	@Test
	public void matchesAttributes() {
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
		testable.addAttribute(attribute_1_mock);
		testable.addAttribute(attribute_2_mock);

		final SystemObject template = new SystemObject("template");
		final Attribute attribute_1_template_mock = context.mock(Attribute.class, "attribute-1-template");
		final Attribute attribute_2_template_mock = context.mock(Attribute.class, "attribute-2-template");
		context.checking(new Expectations() {
			{
				oneOf(attribute_1_template_mock).getName();
				will(returnValue("attribute-1-template"));

				oneOf(attribute_2_template_mock).getName();
				will(returnValue("attribute-2-template"));
			}
		});
		template.addAttribute(attribute_1_template_mock);
		template.addAttribute(attribute_2_template_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_2_mock).matches(attribute_1_template_mock);
				will(returnValue(false));

				oneOf(attribute_2_mock).matches(attribute_2_template_mock);
				will(returnValue(true));

				oneOf(attribute_1_mock).matches(attribute_1_template_mock);
				will(returnValue(true));
			}
		});

		assertTrue(testable.matchesAttributes(template));
	}

	@Test
	public void matchesAttributes_differentAttribute() {
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_3_mock = context.mock(Attribute.class, "attribute-3");
		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).getName();
				will(returnValue("attribute-1"));

				oneOf(attribute_3_mock).getName();
				will(returnValue("attribute-3"));
			}
		});
		testable.addAttribute(attribute_1_mock);
		testable.addAttribute(attribute_3_mock);

		final SystemObject template = new SystemObject("template");
		final Attribute attribute_1_template_mock = context.mock(Attribute.class, "attribute-1-template");
		final Attribute attribute_2_template_mock = context.mock(Attribute.class, "attribute-2-template");
		context.checking(new Expectations() {
			{
				oneOf(attribute_1_template_mock).getName();
				will(returnValue("attribute-1-template"));

				oneOf(attribute_2_template_mock).getName();
				will(returnValue("attribute-2-template"));
			}
		});
		template.addAttribute(attribute_1_template_mock);
		template.addAttribute(attribute_2_template_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_3_mock).matches(attribute_1_template_mock);
				will(returnValue(false));

				oneOf(attribute_1_mock).matches(attribute_1_template_mock);
				will(returnValue(true));

				oneOf(attribute_3_mock).matches(attribute_2_template_mock);
				will(returnValue(false));
			}
		});

		assertFalse(testable.matchesAttributes(template));
	}

	@Test
	public void matchesLinks() {
		final Link link_1_mock = context.mock(Link.class, "link-1");
		final Link link_2_mock = context.mock(Link.class, "link-2");
		context.checking(new Expectations() {
			{
				oneOf(link_1_mock).getName();
				will(returnValue("link-1"));

				oneOf(link_2_mock).getName();
				will(returnValue("link-2"));
			}
		});
		testable.addLink(link_1_mock);
		testable.addLink(link_2_mock);

		final SystemObject template = new SystemObject("template");
		final Link link_1_template_mock = context.mock(Link.class, "link-1-template");
		final Link link_2_template_mock = context.mock(Link.class, "link-2-template");
		context.checking(new Expectations() {
			{
				oneOf(link_1_template_mock).getName();
				will(returnValue("link-1-template"));

				oneOf(link_2_template_mock).getName();
				will(returnValue("link-2-template"));
			}
		});
		template.addLink(link_1_template_mock);
		template.addLink(link_2_template_mock);

		final IdsMatching idsMatching_mock = context.mock(IdsMatching.class);

		context.checking(new Expectations() {
			{
				oneOf(link_1_mock).matches(link_1_template_mock, idsMatching_mock);
				will(returnValue(true));

				oneOf(link_2_mock).matches(link_2_template_mock, idsMatching_mock);
				will(returnValue(true));
			}
		});

		assertTrue(testable.matchesLinks(template, idsMatching_mock));
	}

	@Test
	public void matechsLinks_differentLink() {
		final Link link_1_mock = context.mock(Link.class, "link-1");
		final Link link_3_mock = context.mock(Link.class, "link-3");
		context.checking(new Expectations() {
			{
				oneOf(link_1_mock).getName();
				will(returnValue("link-1"));

				oneOf(link_3_mock).getName();
				will(returnValue("link-3"));
			}
		});
		testable.addLink(link_1_mock);
		testable.addLink(link_3_mock);

		final SystemObject template = new SystemObject("template");
		final Link link_1_template_mock = context.mock(Link.class, "link-1-template");
		final Link link_2_template_mock = context.mock(Link.class, "link-2-template");
		context.checking(new Expectations() {
			{
				oneOf(link_1_template_mock).getName();
				will(returnValue("link-1-template"));

				oneOf(link_2_template_mock).getName();
				will(returnValue("link-2-template"));
			}
		});
		template.addLink(link_1_template_mock);
		template.addLink(link_2_template_mock);

		final IdsMatching idsMatching_mock = context.mock(IdsMatching.class);

		context.checking(new Expectations() {
			{
				oneOf(link_3_mock).matches(link_1_template_mock, idsMatching_mock);
				will(returnValue(false));

				oneOf(link_1_mock).matches(link_1_template_mock, idsMatching_mock);
				will(returnValue(true));

				oneOf(link_3_mock).matches(link_2_template_mock, idsMatching_mock);
				will(returnValue(false));
			}
		});

		assertFalse(testable.matchesLinks(template, idsMatching_mock));
	}

	@Test
	public void getAttribute() {
		final Attribute attribute_mock = context.mock(Attribute.class, "attribute");

		context.checking(new Expectations() {
			{
				oneOf(attribute_mock).getName();
				will(returnValue("attribute-name"));
			}
		});
		testable.addAttribute(attribute_mock);

		assertEquals(attribute_mock, testable.getAttribute("attribute-name"));
	}

	@Test
	public void getLink() {
		final Link link_mock = context.mock(Link.class, "link");

		context.checking(new Expectations() {
			{
				oneOf(link_mock).getName();
				will(returnValue("link-name"));
			}
		});
		testable.addLink(link_mock);

		assertEquals(link_mock, testable.getLink("link-name"));
	}

	@Test
	public void getObjectIds() {
		final Link link_mock = context.mock(Link.class, "link");

		context.checking(new Expectations() {
			{
				oneOf(link_mock).getName();
				will(returnValue("link-name"));
			}
		});
		testable.addLink(link_mock);

		context.checking(new Expectations() {
			{
				oneOf(link_mock).getObjectId();
				will(returnValue("id-2"));
			}
		});

		Set<String> systemIds = testable.getObjectIds();
		assertEquals(2, systemIds.size());
		assertTrue(systemIds.contains("id-2"));
	}

	@Test
	public void getObjectIds_nullValuedLink() {
		final Link link_mock = context.mock(Link.class, "link");

		context.checking(new Expectations() {
			{
				oneOf(link_mock).getName();
				will(returnValue("link-name"));
			}
		});
		testable.addLink(link_mock);

		context.checking(new Expectations() {
			{
				oneOf(link_mock).getObjectId();
				will(returnValue(null));
			}
		});

		Set<String> systemIds = testable.getObjectIds();
		assertEquals(1, systemIds.size());
	}

	@Test
	public void removeLink() {
		final Link link_mock = context.mock(Link.class, "link");

		context.checking(new Expectations() {
			{
				oneOf(link_mock).getName();
				will(returnValue("link-name"));
			}
		});
		testable.addLink(link_mock);

		testable.removeLink("link-name");
		assertNull(testable.getLink("link-name"));
	}
}

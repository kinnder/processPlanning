package planning.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
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
	public void getName() {
		assertEquals("object", testable.getName());
	}

	@Test
	public void clone_test() throws CloneNotSupportedException {
		final Link link_mock = context.mock(Link.class, "link");
		final Link clonedLink_mock = context.mock(Link.class, "link-clone");
		final Attribute attribute_mock = context.mock(Attribute.class, "attribute");
		final Attribute clonedAttribute_mock = context.mock(Attribute.class, "attribute-clone");

		context.checking(new Expectations() {
			{
				oneOf(attribute_mock).getName();
				will(returnValue("attribute-name"));

				oneOf(attribute_mock).clone();
				will(returnValue(clonedAttribute_mock));

				oneOf(clonedAttribute_mock).getName();
				will(returnValue("attribute-name"));

				oneOf(link_mock).clone();
				will(returnValue(clonedLink_mock));
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
				oneOf(attribute_mock).getName();
				will(returnValue("attribute-name"));
			}
		});
		testable.addLink(link_mock);
		testable.addAttribute(attribute_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_mock).getName();
				will(returnValue("attribute-name"));
			}
		});
		final SystemObject systemObject = new SystemObject("object");
		systemObject.addLink(link_mock);
		systemObject.addAttribute(attribute_mock);

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

		assertFalse(testable.equals(systemObject));
	}

	@Test
	public void equals_differentLink() {
		final Link link_1_mock = context.mock(Link.class, "link-1");
		final Link link_2_mock = context.mock(Link.class, "link-2");

		testable.addLink(link_1_mock);

		final SystemObject systemObject = new SystemObject("object");
		systemObject.addLink(link_2_mock);

		assertFalse(testable.equals(systemObject));
	}

	@Test
	public void addLink() {
		final Link link_mock = context.mock(Link.class, "link");

		testable.addLink(link_mock);
	}

	@Test
	public void addLink_with_string_string() {
		testable.addLink("linkName", "linkValue");
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
	public void addAttribute_with_parameters() {
		testable.addAttribute("attributeName", true);
	}

	@Test
	public void getId() {
		testable = new SystemObject("object", "id");
		assertEquals("id", testable.getId());
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
		final Link link = new Link("link-name", "link-value");
		testable.addLink(link);

		assertEquals(link, testable.getLink("link-name", "link-value"));
	}

	@Test
	public void getLink_notFound() {
		final Link link = new Link("link-name", "link-value");
		testable.addLink(link);

		assertNull(testable.getLink("link-name-1", "link-value-2"));
	}

	@Test
	public void getIds() {
		final Link link_mock = context.mock(Link.class, "link");
		testable.addLink(link_mock);

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
		final Link link_mock = context.mock(Link.class, "link");
		testable.addLink(link_mock);

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
	public void getAttributes() {
		assertTrue(testable.getAttributes() instanceof Collection);
	}

	@Test
	public void getLinks() {
		assertTrue(testable.getLinks() instanceof Collection);
	}

	@Test
	public void createTemplate() {
		final Attribute attribute_mock = context.mock(Attribute.class);
		context.checking(new Expectations() {
			{
				oneOf(attribute_mock).getName();
				will(returnValue("attribute-name"));
			}
		});
		testable.addAttribute(attribute_mock);

		final Link link_mock = context.mock(Link.class);
		testable.addLink(link_mock);

		final AttributeTemplate attributeTemplate_mock = context.mock(AttributeTemplate.class);
		final LinkTemplate linkTemplate_mock = context.mock(LinkTemplate.class);

		context.checking(new Expectations() {
			{
				oneOf(attribute_mock).createTemplate();
				will(returnValue(attributeTemplate_mock));

				oneOf(link_mock).createTemplate();
				will(returnValue(linkTemplate_mock));

				oneOf(attributeTemplate_mock).getName();
				will(returnValue("attribute-name"));

				oneOf(linkTemplate_mock).getName();
				will(returnValue("link-name"));
			}
		});

		SystemObjectTemplate objectTemplate = testable.createTemplate();
		assertEquals(1, objectTemplate.getAttributes().size());
		assertEquals(1, objectTemplate.getLinks().size());
	}
}

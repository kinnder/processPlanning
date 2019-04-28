package planning.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
		testable = new SystemObject("объект");
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
		final SystemObject systemObject = new SystemObject("объект");
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
		assertFalse(testable.equals(new SystemObject("объект 2")));
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
		final SystemObject systemObject = new SystemObject("объект");
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
		final SystemObject systemObject = new SystemObject("объект");
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
		final SystemObject systemObject = new SystemObject("объект");
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
		final SystemObject systemObject = new SystemObject("объект");
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
}

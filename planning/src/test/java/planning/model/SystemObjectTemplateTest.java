package planning.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
	public void newInstance() {
		testable = new SystemObjectTemplate();
		assertTrue(testable.getId().startsWith("objectTemplate-"));
	}

	@Test
	public void newInstance_uniqueName() {
		testable = new SystemObjectTemplate();
		SystemObjectTemplate testable2 = new SystemObjectTemplate();
		assertNotEquals(testable.getId(), testable2.getId());
	}

	@Test
	public void getId() {
		assertEquals("id", testable.getId());
	}

	@Test
	public void setId() {
		testable.setId("new-id");
		assertEquals("new-id", testable.getId());
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
	public void getIds() {
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
	public void removeAttributeTemplate() {
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

		context.checking(new Expectations() {
			{
				oneOf(attributeTemplate_1_mock).getName();
				will(returnValue("attribute-1-template"));
			}
		});
		testable.removeAttributeTemplate(attributeTemplate_1_mock);
		Collection<AttributeTemplate> attributes = testable.getAttributeTemplates();
		assertEquals(1, attributes.size());
	}

	@Test
	public void toString_test() {
		assertEquals("Object Template", testable.toString());
	}
}

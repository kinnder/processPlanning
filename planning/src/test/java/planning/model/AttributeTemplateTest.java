package planning.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class AttributeTemplateTest {

	@RegisterExtension
	JUnit5Mockery context = new JUnit5Mockery();

	@AfterEach
	public void teardown() {
		context.assertIsSatisfied();
	}

	AttributeTemplate testable;

	@BeforeEach
	public void setup() {
		testable = new AttributeTemplate("attribute", "value");
	}

	@Test
	public void newInstance() {
		testable = new AttributeTemplate();
		assertTrue(testable.getName().startsWith("attributeTemplate-"));
		assertNull(testable.getValue());
	}

	@Test
	public void newInstance_uniqueName() {
		testable = new AttributeTemplate();
		AttributeTemplate testable2 = new AttributeTemplate();
		assertNotEquals(testable.getName(), testable2.getName());
	}

	@Test
	public void matches() {
		final Attribute attribute = new Attribute("attribute", "value");
		assertTrue(testable.matches(attribute));
	}

	@Test
	public void matches_differentName() {
		final Attribute attribute = new Attribute("different", "value");
		assertFalse(testable.matches(attribute));
	}

	@Test
	public void matches_differentType() {
		final Attribute attribute = new Attribute("attribute", false);
		assertFalse(testable.matches(attribute));
	}

	@Test
	public void matches_differentValue() {
		final Attribute attribute = new Attribute("attribute", "different");
		assertFalse(testable.matches(attribute));
	}

	@Test
	public void matches_anyValue() {
		final Attribute attribute = new Attribute("attribute", "different");
		assertFalse(testable.matches(attribute));

		testable = new AttributeTemplate("attribute");
		assertTrue(testable.matches(attribute));
	}

	@Test
	public void getName() {
		assertEquals("attribute", testable.getName());
	}

	@Test
	public void setName() {
		testable.setName("new-attribute");
		assertEquals("new-attribute", testable.getName());
	}

	@Test
	public void getValue() {
		Object value = new Object();
		testable = new AttributeTemplate("attribute", value);
		assertEquals(value, testable.getValue());
	}

	@Test
	public void setValue() {
		Object value_1 = new Object();
		testable = new AttributeTemplate("attribute", value_1);

		Object value_2 = new Object();
		testable.setValue(value_2);
		assertEquals(value_2, testable.getValue());
	}

	@Test
	public void getType() {
		testable = new AttributeTemplate("attribute", "abc");
		assertEquals(AttributeType.STRING, testable.getType());
	}

	@Test
	public void getType_null() {
		testable = new AttributeTemplate("attribute");
		assertEquals(AttributeType.NULL, testable.getType());
	}

	@Test
	public void setType_boolean() {
		testable = new AttributeTemplate("attribute", "true");
		assertEquals(AttributeType.STRING, testable.getType());

		testable.setType(AttributeType.BOOLEAN);
		assertEquals(AttributeType.BOOLEAN, testable.getType());
	}

	@Test
	public void setType_integer() {
		testable = new AttributeTemplate("attribute", "345");
		assertEquals(AttributeType.STRING, testable.getType());

		testable.setType(AttributeType.INTEGER);
		assertEquals(AttributeType.INTEGER, testable.getType());
	}

	@Test
	public void setType_string() {
		testable = new AttributeTemplate("attribute", false);
		assertEquals(AttributeType.BOOLEAN, testable.getType());

		testable.setType(AttributeType.STRING);
		assertEquals(AttributeType.STRING, testable.getType());
	}

	@Test
	public void setType_null() {
		testable = new AttributeTemplate("attribute", true);
		assertEquals(AttributeType.BOOLEAN, testable.getType());

		testable.setType(AttributeType.NULL);
		assertEquals(AttributeType.NULL, testable.getType());
	}

	@Test
	public void setType_nullValue() {
		testable = new AttributeTemplate("attribute");
		assertEquals(AttributeType.NULL, testable.getType());

		testable.setType(AttributeType.OBJECT);
		assertEquals(AttributeType.NULL, testable.getType());
	}

	@Test
	public void setType_object() {
		testable = new AttributeTemplate("attribute", Float.valueOf(12.3f));
		assertEquals(AttributeType.OBJECT, testable.getType());

		testable.setType(AttributeType.OBJECT);
		assertEquals(AttributeType.OBJECT, testable.getType());
	}
}

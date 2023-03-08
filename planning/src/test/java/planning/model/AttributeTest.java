package planning.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

class AttributeTest {

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

	Attribute testable;

	@BeforeEach
	public void setup() {
		testable = new Attribute("attribute", "value");
	}

	@Test
	public void newInstance() {
		testable = new Attribute();
		assertTrue(testable.getName().startsWith("attribute-"));
		assertNull(testable.getValue());
	}

	@Test
	public void newInstance_uniqueName() {
		testable = new Attribute();
		Attribute testable2 = new Attribute();
		assertNotEquals(testable.getName(), testable2.getName());
	}

	@Test
	public void newInstance_onlyName() {
		testable = new Attribute("attribute-with-only-name");
		assertEquals("attribute-with-only-name", testable.getName());
		assertNull(testable.getValue());
	}

	@Test
	public void clone_test() throws CloneNotSupportedException {
		Attribute clone = testable.clone();
		assertTrue(clone != testable);
		assertEquals(clone.getName(), testable.getName());
		assertEquals(clone.getValue(), testable.getValue());
	}

	@Test
	public void equals() throws CloneNotSupportedException {
		assertTrue(testable.equals(testable.clone()));
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
	public void equals_differentName() {
		assertFalse(testable.equals(new Attribute("different", "value")));
	}

	@Test
	public void equals_differentValue() {
		assertFalse(testable.equals(new Attribute("attribute", "different")));
	}

	@Test
	public void equals_differentType() {
		assertFalse(testable.equals(new Attribute("attribute", false)));
	}

	@Test
	public void getValueAsBoolean() {
		final boolean value = true;
		testable = new Attribute("attribute", value);
		assertEquals(value, testable.getValueAsBoolean());
	}

	@Test
	public void getValueAsString() {
		String value = "value";
		testable = new Attribute("attribute", value);
		assertEquals(value, testable.getValueAsString());
	}

	@Test
	public void getValueAsInteger() {
		Integer value = Integer.valueOf(123);
		testable = new Attribute("attribute", value);
		assertEquals(value, testable.getValueAsInteger());
	}

	@Test
	public void getValue() {
		Object value = new Object();
		testable = new Attribute("attribute", value);
		assertEquals(value, testable.getValue());
	}

	@Test
	public void setValue() {
		testable.setValue("new value");
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
	public void createTemplate() {
		assertNotNull(testable.createTemplate());
	}

	@Test
	public void getType() {
		testable = new Attribute("attribute", "abc");
		assertEquals(AttributeType.STRING, testable.getType());
	}

	@Test
	public void getType_null() {
		testable = new Attribute("attribute");
		assertEquals(AttributeType.NULL, testable.getType());
	}

	@Test
	public void setType_boolean() {
		testable = new Attribute("attribute", "true");
		assertEquals(AttributeType.STRING, testable.getType());

		testable.setType(AttributeType.BOOLEAN);
		assertEquals(AttributeType.BOOLEAN, testable.getType());
	}

	@Test
	public void setType_integer() {
		testable = new Attribute("attribute", "345");
		assertEquals(AttributeType.STRING, testable.getType());

		testable.setType(AttributeType.INTEGER);
		assertEquals(AttributeType.INTEGER, testable.getType());
	}

	@Test
	public void setType_string() {
		testable = new Attribute("attribute", false);
		assertEquals(AttributeType.BOOLEAN, testable.getType());

		testable.setType(AttributeType.STRING);
		assertEquals(AttributeType.STRING, testable.getType());
	}

	@Test
	public void setType_null() {
		testable = new Attribute("attribute", true);
		assertEquals(AttributeType.BOOLEAN, testable.getType());

		testable.setType(AttributeType.NULL);
		assertEquals(AttributeType.NULL, testable.getType());
	}

	@Test
	public void setType_nullValue() {
		testable = new Attribute("attribute");
		assertEquals(AttributeType.NULL, testable.getType());

		testable.setType(AttributeType.OBJECT);
		assertEquals(AttributeType.NULL, testable.getType());
	}

	@Test
	public void setType_object() {
		testable = new Attribute("attribute", Float.valueOf(12.3f));
		assertEquals(AttributeType.OBJECT, testable.getType());

		testable.setType(AttributeType.OBJECT);
		assertEquals(AttributeType.OBJECT, testable.getType());
	}
}

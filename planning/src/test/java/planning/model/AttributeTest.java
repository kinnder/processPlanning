package planning.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
	public void clone_boolean() {
		testable = new Attribute("attribute", true);
		assertTrue(testable != testable.clone());
	}

	@Test
	public void clone_string() {
		testable = new Attribute("attribute", "value");
		assertTrue(testable != testable.clone());
	}

	@Test
	public void clone_object() {
		testable = new Attribute("attribute", new Object());
		assertTrue(testable != testable.clone());
	}

	@Test
	public void equals() {
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
	public void getValueAsObject() {
		Object value = new Object();
		testable = new Attribute("attribute", value);
		assertEquals(value, testable.getValueAsObject());
	}

	@Test
	public void getValueAsInteger() {
		Integer value = new Integer(123);
		testable = new Attribute("attribute", value);
		assertEquals(value, testable.getValueAsInteger());
	}

	@Test
	public void getName() {
		assertEquals("attribute", testable.getName());
	}

	@Test
	public void setValue() {
		testable.setValue("new value");
	}

	@Test
	public void createTemplate_boolean() {
		testable = new Attribute("attribute", true);
		assertNotNull(testable.createTemplate());
	}

	@Test
	public void createTemplate_string() {
		testable = new Attribute("attribute", "value");
		assertNotNull(testable.createTemplate());
	}

	@Test
	public void createTemplate_object() {
		testable = new Attribute("attribute", new Object());
		assertNotNull(testable.createTemplate());
	}

	@Test
	public void getType() {
		assertEquals(AttributeType.String, testable.getType());
	}
}

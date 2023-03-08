package planning.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class AttributeTypeTest {

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

	@Test
	public void fromValue_boolean() {
		assertEquals(AttributeType.BOOLEAN, AttributeType.fromValue(Boolean.valueOf(true)));
	}

	@Test
	public void fromString_boolean() {
		assertEquals(AttributeType.BOOLEAN, AttributeType.fromString("boolean"));
		assertEquals(AttributeType.BOOLEAN, AttributeType.fromString("BOOLEAN"));
	}

	@Test
	public void toString_boolean() {
		assertEquals("boolean", AttributeType.BOOLEAN.toString());
	}

	@Test
	public void fromValue_integer() {
		assertEquals(AttributeType.INTEGER, AttributeType.fromValue(Integer.valueOf(345)));
	}

	@Test
	public void fromString_integer() {
		assertEquals(AttributeType.INTEGER, AttributeType.fromString("integer"));
		assertEquals(AttributeType.INTEGER, AttributeType.fromString("INTEGER"));
	}

	@Test
	public void toString_integer() {
		assertEquals("integer", AttributeType.INTEGER.toString());
	}

	@Test
	public void fromValue_string() {
		assertEquals(AttributeType.STRING, AttributeType.fromValue(String.valueOf("abc")));
	}

	@Test
	public void fromString_string() {
		assertEquals(AttributeType.STRING, AttributeType.fromString("string"));
		assertEquals(AttributeType.STRING, AttributeType.fromString("STRING"));
	}

	@Test
	public void toString_string() {
		assertEquals("string", AttributeType.STRING.toString());
	}

	@Test
	public void fromValue_object() {
		assertEquals(AttributeType.OBJECT, AttributeType.fromValue(new Object()));
	}

	@Test
	public void fromString_object() {
		assertEquals(AttributeType.OBJECT, AttributeType.fromString("object"));
		assertEquals(AttributeType.OBJECT, AttributeType.fromString("OBJECT"));
	}

	@Test
	public void toString_object() {
		assertEquals("object", AttributeType.OBJECT.toString());
	}

	@Test
	public void formValue_null() {
		assertEquals(AttributeType.NULL, AttributeType.fromValue(null));
	}

	@Test
	public void fromString_null() {
		assertEquals(AttributeType.NULL, AttributeType.fromString("null"));
		assertEquals(AttributeType.NULL, AttributeType.fromString("NULL"));
	}

	@Test
	public void toString_null() {
		assertEquals("null", AttributeType.NULL.toString());
	}
}

package planning.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jmock.junit5.JUnit5Mockery;
import org.junit.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class AttributeTypeTest {

	@RegisterExtension
	JUnit5Mockery context = new JUnit5Mockery();

	@Test
	public void getTypeOf_Boolean() {
		assertEquals(AttributeType.Boolean, AttributeType.getTypeOf(true));
	}

	@Test
	public void getTypeOf_Integer() {
		assertEquals(AttributeType.Integer, AttributeType.getTypeOf(new Integer(123)));
	}

	@Test
	public void getTypeOf_String() {
		assertEquals(AttributeType.String, AttributeType.getTypeOf("string"));
	}

	@Test
	public void getTypeOf_Object() {
		assertEquals(AttributeType.Object, AttributeType.getTypeOf(new Object()));
	}
}

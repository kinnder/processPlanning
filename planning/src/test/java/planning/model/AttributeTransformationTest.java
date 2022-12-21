package planning.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class AttributeTransformationTest {

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

	AttributeTransformation testable;

	@BeforeEach
	public void setup() {
		testable = new AttributeTransformation("id-template", "attribute-name", "attribute-value");
	}

	@Test
	public void applyTo() {
		final SystemObject object_mock = context.mock(SystemObject.class);
		final Attribute attribute_mock = context.mock(Attribute.class);
		final SystemVariant systemVariant_mock = context.mock(SystemVariant.class);

		context.checking(new Expectations() {
			{
				oneOf(systemVariant_mock).getObjectByIdMatch("id-template");
				will(returnValue(object_mock));

				oneOf(object_mock).getAttribute("attribute-name");
				will(returnValue(attribute_mock));

				oneOf(attribute_mock).setValue("attribute-value");
			}
		});

		testable.applyTo(systemVariant_mock);
	}

	@Test
	public void getAttributeName() {
		assertEquals("attribute-name", testable.getAttributeName());
	}

	@Test
	public void setAttributeName() {
		testable.setAttributeName("new-attribute-name");
		assertEquals("new-attribute-name", testable.getAttributeName());
	}

	@Test
	public void getAttributeValue() {
		assertEquals("attribute-value", testable.getAttributeValue());
	}

	@Test
	public void setAttributeValue() {
		testable.setAttributeValue("new-attribute-value");
		assertEquals("new-attribute-value", testable.getAttributeValue());
	}

	@Test
	public void toString_test() {
		assertEquals("attribute-transformation", testable.toString());
	}
}

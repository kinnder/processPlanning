package planning.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.jdom2.Element;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class ValueXMLSchemaTest {

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

	ValueXMLSchema testable;

	@BeforeEach
	public void setup() {
		testable = new ValueXMLSchema();
	}

	@Test
	public void parseValue() {
		final Element root_mock = context.mock(Element.class, "root");

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getAttributeValue("type", "string");
				will(returnValue("string"));

				oneOf(root_mock).getText();
				will(returnValue("value"));
			}
		});

		Object result = testable.parseValue(root_mock);
		assertNotNull(result);
		assertEquals("value", result);
	}

	@Test
	public void parseValue_boolean() {
		final Element root_mock = context.mock(Element.class, "root");

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getAttributeValue("type", "string");
				will(returnValue("boolean"));

				oneOf(root_mock).getText();
				will(returnValue("true"));
			}
		});

		Object result = testable.parseValue(root_mock);
		assertNotNull(result);
		assertEquals(true, result);
	}

	@Test
	public void parseValue_integer() {
		final Element root_mock = context.mock(Element.class, "root");

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getAttributeValue("type", "string");
				will(returnValue("integer"));

				oneOf(root_mock).getText();
				will(returnValue("10"));
			}
		});

		Object result = testable.parseValue(root_mock);
		assertNotNull(result);
		assertEquals(10, result);
	}

	@Test
	public void parseValue_with_null() {
		Object result = testable.parseValue(null);
		assertNull(result);
	}

	@Test
	public void combineValue() {
		final Object value = new String("string-value");

		Element element = testable.combineValue(value);
		assertEquals("string-value", element.getText());
		assertNull(element.getAttribute("type"));
	}

	@Test
	public void combineValue_integer() {
		final Object value = Integer.valueOf(123);

		Element element = testable.combineValue(value);
		assertEquals("123", element.getText());
		assertEquals("integer", element.getAttributeValue("type"));
	}

	@Test
	public void combineValue_boolean() {
		final Object value = Boolean.valueOf(true);

		Element element = testable.combineValue(value);
		assertEquals("true", element.getText());
		assertEquals("boolean", element.getAttributeValue("type"));
	}
}

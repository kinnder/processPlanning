package application.storage.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.Attribute;

public class AttributeXMLSchemaTest {

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

	AttributeXMLSchema testable;

	ValueXMLSchema valueXMLSchema_mock;

	@BeforeEach
	public void setup() {
		valueXMLSchema_mock = context.mock(ValueXMLSchema.class);

		testable = new AttributeXMLSchema(valueXMLSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new AttributeXMLSchema();
	}

	@Test
	public void parse() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");
		final Element value_mock = context.mock(Element.class, "value");
		final Object value = new Object();

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildText("name");
				will(returnValue("name"));

				oneOf(valueXMLSchema_mock).getSchemaName();
				will(returnValue("value"));

				oneOf(root_mock).getChild("value");
				will(returnValue(value_mock));

				oneOf(valueXMLSchema_mock).parse(value_mock);
				will(returnValue(value));
			}
		});

		Attribute result = testable.parse(root_mock);
		assertNotNull(result);
		assertEquals("name", result.getName());
		assertEquals(value, result.getValue());
	}

	@Test
	public void combine() {
		final Attribute attribute_mock = context.mock(Attribute.class);
		final Object value_mock = context.mock(Object.class);
		final Element value = new Element("value");

		context.checking(new Expectations() {
			{
				oneOf(attribute_mock).getName();
				will(returnValue("attribute-name"));

				oneOf(attribute_mock).getValue();
				will(returnValue(value_mock));

				oneOf(valueXMLSchema_mock).combine(value_mock);
				will(returnValue(value));
			}
		});

		Element element = testable.combine(attribute_mock);
		assertEquals("attribute-name", element.getChildText("name"));
		assertNotNull(element.getChild("value"));
	}

	@Test
	public void combine_with_null() {
		final Attribute attribute_mock = context.mock(Attribute.class);

		context.checking(new Expectations() {
			{
				oneOf(attribute_mock).getName();
				will(returnValue("attribute-name"));

				oneOf(attribute_mock).getValue();
				will(returnValue(null));
			}
		});

		Element element = testable.combine(attribute_mock);
		assertEquals("attribute-name", element.getChildText("name"));
		assertNull(element.getChild("value"));
	}

	@Test
	public void getSchemaName() {
		assertEquals("attribute", testable.getSchemaName());
	}
}

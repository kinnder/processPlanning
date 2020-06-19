package planning.storage;

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

import planning.model.AttributeTemplate;

public class AttributeTemplateXMLSchemaTest {

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

	AttributeTemplateXMLSchema testable;

	ValueXMLSchema valueXMLSchema_mock;

	@BeforeEach
	public void setup() {
		valueXMLSchema_mock = context.mock(ValueXMLSchema.class);

		testable = new AttributeTemplateXMLSchema(valueXMLSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new AttributeTemplateXMLSchema();
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
		AttributeTemplate result = testable.parse(root_mock);
		assertNotNull(result);
		assertEquals("name", result.getName());
		assertEquals(value, result.getValue());
	}

	@Test
	public void parse_with_null() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildText("name");
				will(returnValue("name"));

				oneOf(valueXMLSchema_mock).getSchemaName();
				will(returnValue("value"));

				oneOf(root_mock).getChild("value");
				will(returnValue(null));

				oneOf(valueXMLSchema_mock).parse(null);
				will(returnValue(null));
			}
		});
		AttributeTemplate result = testable.parse(root_mock);
		assertNotNull(result);
		assertEquals("name", result.getName());
	}

	@Test
	public void combine() {
		final AttributeTemplate attributeTemplate_mock = context.mock(AttributeTemplate.class);
		final Object value_mock = context.mock(Object.class);
		final Element value = new Element("value");

		context.checking(new Expectations() {
			{
				oneOf(attributeTemplate_mock).getName();
				will(returnValue("attribute-name"));

				oneOf(attributeTemplate_mock).getValue();
				will(returnValue(value_mock));

				oneOf(valueXMLSchema_mock).combine(value_mock);
				will(returnValue(value));
			}
		});

		Element element = testable.combine(attributeTemplate_mock);
		assertEquals("attribute-name", element.getChildText("name"));
		assertNotNull(element.getChild("value"));
	}

	@Test
	public void combine_empty_value() {
		final AttributeTemplate attributeTemplate_mock = context.mock(AttributeTemplate.class);

		context.checking(new Expectations() {
			{
				oneOf(attributeTemplate_mock).getName();
				will(returnValue("attribute-name"));

				oneOf(attributeTemplate_mock).getValue();
				will(returnValue(null));
			}
		});

		Element element = testable.combine(attributeTemplate_mock);
		assertEquals("attribute-name", element.getChildText("name"));
		assertNull(element.getChild("value"));
	}

	@Test
	public void getSchemaName() {
		assertEquals("attributeTemplate", testable.getSchemaName());
	}
}

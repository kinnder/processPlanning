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

	@BeforeEach
	public void setup() {
		testable = new AttributeTemplateXMLSchema();
	}

	@Test
	public void parse() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");
		final Element value_mock = context.mock(Element.class, "value");

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildText("name");
				will(returnValue("name"));

				oneOf(root_mock).getChild("value");
				will(returnValue(value_mock));

				// <-- parseValue

				oneOf(value_mock).getAttributeValue("type", "string");

				oneOf(value_mock).getText();

				// parseValue -->
			}
		});
		AttributeTemplate result = testable.parse(root_mock);
		assertNotNull(result);
		assertEquals("name", result.getName());
		assertEquals("", result.getValue());
	}

	@Test
	public void parse_with_null() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildText("name");
				will(returnValue("name"));

				oneOf(root_mock).getChild("value");
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

		context.checking(new Expectations() {
			{
				oneOf(attributeTemplate_mock).getName();
				will(returnValue("attribute-name"));

				oneOf(attributeTemplate_mock).getValue();
				will(returnValue(value_mock));
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
}

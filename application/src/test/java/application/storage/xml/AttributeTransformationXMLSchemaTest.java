package application.storage.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.AttributeTransformation;

public class AttributeTransformationXMLSchemaTest {

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

	AttributeTransformationXMLSchema testable;

	ValueXMLSchema valueXMLSchema_mock;

	@BeforeEach
	public void setup() {
		valueXMLSchema_mock = context.mock(ValueXMLSchema.class);

		testable = new AttributeTransformationXMLSchema(valueXMLSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new AttributeTransformationXMLSchema();
	}

	@Test
	public void parse() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");
		final Element value_mock = context.mock(Element.class, "value");
		final Object value = new Object();

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildText("objectId");
				will(returnValue("objectId"));

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

		assertTrue(testable.parse(root_mock) instanceof AttributeTransformation);
	}

	@Test
	public void combine() {
		final AttributeTransformation attributeTransformation_mock = context.mock(AttributeTransformation.class);
		final Object value_mock = context.mock(Object.class);
		final Element value = new Element("value");

		context.checking(new Expectations() {
			{
				oneOf(attributeTransformation_mock).getObjectId();
				will(returnValue("id"));

				oneOf(attributeTransformation_mock).getAttributeName();
				will(returnValue("name"));

				oneOf(attributeTransformation_mock).getAttributeValue();
				will(returnValue(value_mock));

				oneOf(valueXMLSchema_mock).combine(value_mock);
				will(returnValue(value));
			}
		});

		Element element = testable.combine(attributeTransformation_mock);
		assertEquals("id", element.getChildText("objectId"));
		assertEquals("name", element.getChildText("name"));
		assertEquals(value, element.getChild("value"));
	}

	@Test
	public void getSchemaName() {
		assertEquals("attributeTransformation", testable.getSchemaName());
	}
}

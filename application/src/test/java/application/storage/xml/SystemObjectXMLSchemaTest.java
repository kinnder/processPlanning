package application.storage.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

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
import planning.model.SystemObject;

public class SystemObjectXMLSchemaTest {

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

	AttributeXMLSchema attributeXMLSchema_mock;

	SystemObjectXMLSchema testable;

	@BeforeEach
	public void setup() {
		attributeXMLSchema_mock = context.mock(AttributeXMLSchema.class);

		testable = new SystemObjectXMLSchema(attributeXMLSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new SystemObjectXMLSchema();
	}

	@Test
	public void parse() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");
		final List<Element> attributes = new ArrayList<>();
		final Element attribute_mock = context.mock(Element.class, "attribute");
		attributes.add(attribute_mock);
		final Attribute attribute = new Attribute("attribute-name", "attribute-value");

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildText("name");
				will(returnValue("object-name"));

				oneOf(root_mock).getChildText("id");
				will(returnValue("object-id"));

				oneOf(attributeXMLSchema_mock).getSchemaName();
				will(returnValue("attribute"));

				oneOf(root_mock).getChildren("attribute");
				will(returnValue(attributes));

				oneOf(attributeXMLSchema_mock).parse(attribute_mock);
				will(returnValue(attribute));
			}
		});

		assertTrue(testable.parse(root_mock) instanceof SystemObject);
	}

	@Test
	public void combine() {
		final SystemObject systemObject_mock = context.mock(SystemObject.class);
		final Attribute attribute_mock = context.mock(Attribute.class);
		final List<Attribute> attributes = new ArrayList<>();
		attributes.add(attribute_mock);
		final Element attribute = new Element("attribute");

		context.checking(new Expectations() {
			{
				oneOf(systemObject_mock).getName();
				will(returnValue("object-name"));

				oneOf(systemObject_mock).getId();
				will(returnValue("object-id"));

				oneOf(systemObject_mock).getAttributes();
				will(returnValue(attributes));

				oneOf(attributeXMLSchema_mock).combine(attribute_mock);
				will(returnValue(attribute));
			}
		});

		Element element = testable.combine(systemObject_mock);
		assertEquals("object-name", element.getChildText("name"));
		assertEquals("object-id", element.getChildText("id"));
		assertNotNull(element.getChild("attribute"));
	}

	@Test
	public void getSchemaName() {
		assertEquals("systemObject", testable.getSchemaName());
	}
}

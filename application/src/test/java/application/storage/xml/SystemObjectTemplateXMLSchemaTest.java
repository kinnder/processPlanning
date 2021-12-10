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

import planning.model.AttributeTemplate;
import planning.model.SystemObjectTemplate;

public class SystemObjectTemplateXMLSchemaTest {

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

	SystemObjectTemplateXMLSchema testable;

	AttributeTemplateXMLSchema attributeTemplateXMLSchema_mock;

	@BeforeEach
	public void setup() {
		attributeTemplateXMLSchema_mock = context.mock(AttributeTemplateXMLSchema.class);

		testable = new SystemObjectTemplateXMLSchema(attributeTemplateXMLSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new SystemObjectTemplateXMLSchema();
	}

	@Test
	public void parse() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");
		final List<Element> attributeTemplateElements = new ArrayList<>();
		final Element attributeTemplateElement_mock = context.mock(Element.class, "attributeTemplate-element");
		attributeTemplateElements.add(attributeTemplateElement_mock);
		final AttributeTemplate attributeTemplate = new AttributeTemplate("attributeTemplate");

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildText("id");
				will(returnValue("object-id"));

				oneOf(attributeTemplateXMLSchema_mock).getSchemaName();
				will(returnValue("attributeTemplate"));

				oneOf(root_mock).getChildren("attributeTemplate");
				will(returnValue(attributeTemplateElements));

				oneOf(attributeTemplateXMLSchema_mock).parse(attributeTemplateElement_mock);
				will(returnValue(attributeTemplate));
			}
		});

		assertTrue(testable.parse(root_mock) instanceof SystemObjectTemplate);
	}

	@Test
	public void combine() {
		final SystemObjectTemplate systemObjectTemplate_mock = context.mock(SystemObjectTemplate.class);
		final AttributeTemplate attributeTemplate_mock = context.mock(AttributeTemplate.class);
		final List<AttributeTemplate> attributeTemplates = new ArrayList<>();
		attributeTemplates.add(attributeTemplate_mock);
		final Element attributeTemplateElement = new Element("attributeTemplate");

		context.checking(new Expectations() {
			{
				oneOf(systemObjectTemplate_mock).getId();
				will(returnValue("object-id"));

				oneOf(systemObjectTemplate_mock).getAttributeTemplates();
				will(returnValue(attributeTemplates));

				oneOf(attributeTemplateXMLSchema_mock).combine(attributeTemplate_mock);
				will(returnValue(attributeTemplateElement));
			}
		});

		Element element = testable.combine(systemObjectTemplate_mock);
		assertEquals("object-id", element.getChildText("id"));
		assertNotNull(element.getChild("attributeTemplate"));
	}

	@Test
	public void getSchemaName() {
		assertEquals("objectTemplate", testable.getSchemaName());
	}
}

package planning.storage;

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
import planning.model.LinkTemplate;
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

	@BeforeEach
	public void setup() {
		testable = new SystemObjectTemplateXMLSchema();
	}

	@Test
	public void parse() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");
		final List<Element> attributeTemplates = new ArrayList<>();
		final Element attributeTemplate_mock = context.mock(Element.class, "attributeTemplate");
		attributeTemplates.add(attributeTemplate_mock);
		final List<Element> linkTemplates = new ArrayList<>();
		final Element linkTemplate_mock = context.mock(Element.class, "linkTemplate");
		linkTemplates.add(linkTemplate_mock);

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildText("objectId");
				will(returnValue("objectId"));

				oneOf(root_mock).getChildren("attributeTemplate");
				will(returnValue(attributeTemplates));

				// <-- parseAttributeTemplate

				oneOf(attributeTemplate_mock).getChildText("name");

				oneOf(attributeTemplate_mock).getChild("value");

				// parseAttributeTemplate -->

				oneOf(root_mock).getChildren("linkTemplate");
				will(returnValue(linkTemplates));

				// <-- parseLinkTemplate

				oneOf(linkTemplate_mock).getChildText("name");

				oneOf(linkTemplate_mock).getChildText("value");

				// parseLinkTemplate -->
			}
		});

		assertTrue(testable.parse(root_mock) instanceof SystemObjectTemplate);
	}

	@Test
	public void combine() {
		final SystemObjectTemplate systemObjectTemplate_mock = context.mock(SystemObjectTemplate.class);
		final AttributeTemplate attributeTemplate_mock = context.mock(AttributeTemplate.class);
		final LinkTemplate linkTemplate_mock = context.mock(LinkTemplate.class);
		final List<AttributeTemplate> attributeTemplates = new ArrayList<>();
		attributeTemplates.add(attributeTemplate_mock);
		final List<LinkTemplate> linkTemplates = new ArrayList<>();
		linkTemplates.add(linkTemplate_mock);

		context.checking(new Expectations() {
			{
				oneOf(systemObjectTemplate_mock).getId();
				will(returnValue("id"));

				oneOf(systemObjectTemplate_mock).getAttributeTemplates();
				will(returnValue(attributeTemplates));

				// <-- combineAttributeTemplate

				oneOf(attributeTemplate_mock).getName();

				oneOf(attributeTemplate_mock).getValue();

				// combineAttributeTemplate -->

				oneOf(systemObjectTemplate_mock).getLinkTemplates();
				will(returnValue(linkTemplates));

				// <-- combineLinkTemplate

				oneOf(linkTemplate_mock).getName();

				oneOf(linkTemplate_mock).getObjectId1();

				// combineLinkTemplate -->
			}
		});

		Element element = testable.combine(systemObjectTemplate_mock);
		assertEquals("id", element.getChildText("objectId"));
		assertNotNull(element.getChild("attributeTemplate"));
		assertNotNull(element.getChild("linkTemplate"));
	}
}

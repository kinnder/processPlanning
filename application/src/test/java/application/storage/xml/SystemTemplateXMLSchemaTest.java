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

import planning.model.LinkTemplate;
import planning.model.SystemObjectTemplate;
import planning.model.SystemTemplate;

public class SystemTemplateXMLSchemaTest {

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

	SystemObjectTemplateXMLSchema systemObjectTemplateSchema_mock;

	LinkTemplateXMLSchema linkTemplateSchema_mock;

	SystemTemplateXMLSchema testable;

	@BeforeEach
	public void setup() {
		systemObjectTemplateSchema_mock = context.mock(SystemObjectTemplateXMLSchema.class);
		linkTemplateSchema_mock = context.mock(LinkTemplateXMLSchema.class);

		testable = new SystemTemplateXMLSchema(systemObjectTemplateSchema_mock, this.linkTemplateSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new SystemTemplateXMLSchema();
	}

	@Test
	public void parse() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");
		final List<Element> objectTemplates = new ArrayList<>();
		final Element objectTemplate_mock = context.mock(Element.class, "objectTemplate");
		objectTemplates.add(objectTemplate_mock);
		final List<Element> linkTemplates = new ArrayList<>();
		final Element linkTemplate_mock = context.mock(Element.class, "linkTemplate");
		linkTemplates.add(linkTemplate_mock);
		final SystemObjectTemplate systemObjectTemplate = new SystemObjectTemplate("object-template");
		final LinkTemplate linkTemplate = new LinkTemplate("link-template", "link-object1-id", "link-object2-id");

		context.checking(new Expectations() {
			{
				oneOf(systemObjectTemplateSchema_mock).getSchemaName();
				will(returnValue("objectTemplate"));

				oneOf(root_mock).getChildren("objectTemplate");
				will(returnValue(objectTemplates));

				oneOf(systemObjectTemplateSchema_mock).parse(objectTemplate_mock);
				will(returnValue(systemObjectTemplate));

				oneOf(linkTemplateSchema_mock).getSchemaName();
				will(returnValue("linkTemplate"));

				oneOf(root_mock).getChildren("linkTemplate");
				will(returnValue(linkTemplates));

				oneOf(linkTemplateSchema_mock).parse(linkTemplate_mock);
				will(returnValue(linkTemplate));
			}
		});

		assertTrue(testable.parse(root_mock) instanceof SystemTemplate);
	}

	@Test
	public void combine() {
		final SystemTemplate systemTemplate_mock = context.mock(SystemTemplate.class);
		final List<SystemObjectTemplate> systemObjectTemplates = new ArrayList<>();
		final SystemObjectTemplate systemObjectTemplate_mock = context.mock(SystemObjectTemplate.class);
		systemObjectTemplates.add(systemObjectTemplate_mock);
		final List<LinkTemplate> linkTemplates = new ArrayList<>();
		final LinkTemplate linkTemplate_mock = context.mock(LinkTemplate.class);
		linkTemplates.add(linkTemplate_mock);
		final Element systemObjectTemplate = new Element("objectTemplate");
		final Element linkTemplate = new Element("linkTemplate");

		context.checking(new Expectations() {
			{
				oneOf(systemTemplate_mock).getObjectTemplates();
				will(returnValue(systemObjectTemplates));

				oneOf(systemObjectTemplateSchema_mock).combine(systemObjectTemplate_mock);
				will(returnValue(systemObjectTemplate));

				oneOf(systemTemplate_mock).getLinkTemplates();
				will(returnValue(linkTemplates));

				oneOf(linkTemplateSchema_mock).combine(linkTemplate_mock);
				will(returnValue(linkTemplate));
			}
		});

		Element element = testable.combine(systemTemplate_mock);
		assertNotNull(element.getChild("objectTemplate"));
		assertNotNull(element.getChild("linkTemplate"));
	}

	@Test
	public void getSchemaName() {
		assertEquals("systemTemplate", testable.getSchemaName());
	}
}

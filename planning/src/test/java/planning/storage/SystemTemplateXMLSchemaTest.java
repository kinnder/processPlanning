package planning.storage;

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

	SystemTemplateXMLSchema testable;

	@BeforeEach
	public void setup() {
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

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildren("objectTemplate");
				will(returnValue(objectTemplates));

				// <-- parseObjectTemplate

				oneOf(objectTemplate_mock).getChildText("objectId");

				oneOf(objectTemplate_mock).getChildren("attributeTemplate");

				// parseObjectTemplate -->

				oneOf(root_mock).getChildren("linkTemplate");
				will(returnValue(linkTemplates));

				// <-- parseLinkTemplate

				oneOf(linkTemplate_mock).getChildText("name");

				oneOf(linkTemplate_mock).getChildText("objectId1");

				oneOf(linkTemplate_mock).getChildText("objectId2");

				// parseLinkTemplate -->
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

		context.checking(new Expectations() {
			{
				oneOf(systemTemplate_mock).getObjectTemplates();
				will(returnValue(systemObjectTemplates));

				// <-- combineSystemObjectTemplate

				oneOf(systemObjectTemplate_mock).getId();

				oneOf(systemObjectTemplate_mock).getAttributeTemplates();

				// combineSystemObjectTemplate -->

				oneOf(systemTemplate_mock).getLinkTemplates();
				will(returnValue(linkTemplates));

				// <-- combineLinkTemplate

				oneOf(linkTemplate_mock).getName();

				oneOf(linkTemplate_mock).getObjectId1();

				oneOf(linkTemplate_mock).getObjectId2();

				// combineLinkTemplate -->

			}
		});

		Element element = testable.combine(systemTemplate_mock);
		assertNotNull(element.getChild("objectTemplate"));
	}
}

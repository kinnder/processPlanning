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

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildren("objectTemplate");
				will(returnValue(objectTemplates));

				// <-- parseObjectTemplate

				oneOf(objectTemplate_mock).getChildText("objectId");

				oneOf(objectTemplate_mock).getChildren("attributeTemplate");

				oneOf(objectTemplate_mock).getChildren("linkTemplate");

				// parseObjectTemplate -->
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

		context.checking(new Expectations() {
			{
				oneOf(systemTemplate_mock).getObjectTemplates();
				will(returnValue(systemObjectTemplates));

				// <-- combineSystemObjectTemplate

				oneOf(systemObjectTemplate_mock).getId();

				oneOf(systemObjectTemplate_mock).getAttributeTemplates();

				oneOf(systemObjectTemplate_mock).getLinkTemplates();

				// combineSystemObjectTemplate -->
			}
		});

		Element element = testable.combine(systemTemplate_mock);
		assertNotNull(element.getChild("objectTemplate"));
	}
}

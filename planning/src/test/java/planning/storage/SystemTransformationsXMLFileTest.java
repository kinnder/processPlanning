package planning.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.Action;
import planning.model.ActionParameterUpdater;
import planning.model.ActionPreConditionChecker;
import planning.model.AttributeTemplate;
import planning.model.AttributeTransformation;
import planning.model.LinkTemplate;
import planning.model.LinkTransformation;
import planning.model.SystemObjectTemplate;
import planning.model.SystemTemplate;
import planning.model.SystemTransformation;

public class SystemTransformationsXMLFileTest {

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

	SystemTransformationsXMLFile testable;

	@BeforeEach
	public void setup() {
		testable = new SystemTransformationsXMLFile();
	}

	@Test
	public void getSystemTransformations() {
		assertEquals(0, testable.getSystemTransformations().length);
	}

	@Test
	public void load() throws JDOMException, IOException {
		final SAXBuilder saxBuilder_mock = context.mock(SAXBuilder.class);
		testable = new SystemTransformationsXMLFile(saxBuilder_mock);

		final URL url = new URL("file:/systemTransformations.xml");
		final Document document_mock = context.mock(Document.class);
		final Element element_mock = context.mock(Element.class);

		context.checking(new Expectations() {
			{
				oneOf(saxBuilder_mock).build(url);
				will(returnValue(document_mock));

				oneOf(document_mock).getRootElement();
				will(returnValue(element_mock));

				// <-- parseSystemTransformations
				oneOf(element_mock).getChildren("systemTransformation");

				// parseSystemTransformations -->
			}
		});

		testable.load(url);
	}

	@Test
	public void parseSystemTransformations() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");
		final List<Element> systemTransformations = new ArrayList<>();
		final Element systemTransformation_mock = context.mock(Element.class, "systemTransformation");
		systemTransformations.add(systemTransformation_mock);

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildren("systemTransformation");
				will(returnValue(systemTransformations));

				// <-- parseSystemTransformation

				oneOf(systemTransformation_mock).getChildText("name");

				oneOf(systemTransformation_mock).getChild("action");

				oneOf(systemTransformation_mock).getChild("systemTemplate");

				oneOf(systemTransformation_mock).getChild("transformations");

				// parseSystemTransformation -->
			}
		});

		assertEquals(1, testable.parseSystemTransformations(root_mock).length);
	}

	@Test
	public void parseSystemTransformation() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");
		final Element actionElement_mock = context.mock(Element.class, "actionElement");
		final Element systemTemplateElement_mock = context.mock(Element.class, "systemTemplateElement");
		final Element transformationsElement_mock = context.mock(Element.class, "transformationsElement");

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildText("name");
				will(returnValue("name"));

				oneOf(root_mock).getChild("action");
				will(returnValue(actionElement_mock));

				// <-- parseAction

				oneOf(actionElement_mock).getChildText("name");

				oneOf(actionElement_mock).getChildren("preConditionChecker");

				oneOf(actionElement_mock).getChildren("parameterUpdater");

				// parseAction -->

				oneOf(root_mock).getChild("systemTemplate");
				will(returnValue(systemTemplateElement_mock));

				// <-- parseSystemTemplate

				oneOf(systemTemplateElement_mock).getChildren("objectTemplate");

				// parseSystemTemplate -->

				oneOf(root_mock).getChild("transformations");
				will(returnValue(transformationsElement_mock));

				// <-- parseTransformations

				oneOf(transformationsElement_mock).getChildren("linkTransformation");

				oneOf(transformationsElement_mock).getChildren("attributeTransformation");

				// parseTransformation -->
			}
		});

		assertTrue(testable.parseSystemTransformation(root_mock) instanceof SystemTransformation);
	}

	@Test
	public void parseAction() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");
		final List<Element> preConditionCheckers = new ArrayList<>();
		final Element preConditionChecker_mock = context.mock(Element.class, "preConditionChecker");
		preConditionCheckers.add(preConditionChecker_mock);
		final List<Element> parameterUpdaters = new ArrayList<>();
		final Element parameterUpdater_mock = context.mock(Element.class, "parameterUpdater");
		parameterUpdaters.add(parameterUpdater_mock);

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildText("name");
				will(returnValue("action-name"));

				oneOf(root_mock).getChildren("preConditionChecker");
				will(returnValue(preConditionCheckers));

				// <-- parsePreConditionChecker

				oneOf(preConditionChecker_mock).getChildren("line");

				// parsePreConditionChecker -->

				oneOf(root_mock).getChildren("parameterUpdater");
				will(returnValue(parameterUpdaters));

				// <-- parseParameterUpdater

				oneOf(parameterUpdater_mock).getChildren("line");

				// parseParameterUpdater -->
			}
		});

		assertTrue(testable.parseAction(root_mock) instanceof Action);
	}

	@Test
	public void parseParameterUpdater() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");
		final List<Element> lines = new ArrayList<>();
		final Element line_mock = context.mock(Element.class, "line");
		lines.add(line_mock);
		final Attribute attribute_mock = context.mock(Attribute.class);

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildren("line");
				will(returnValue(lines));

				oneOf(line_mock).getAttribute("n");
				will(returnValue(attribute_mock));

				oneOf(attribute_mock).getIntValue();
				will(returnValue(1));

				oneOf(line_mock).getText();
				will(returnValue("local systemVariant = ..."));
			}
		});

		assertTrue(testable.parseParameterUpdater(root_mock) instanceof ActionParameterUpdater);
	}

	@Test
	public void parsePreConditionChecker() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");
		final List<Element> lines = new ArrayList<>();
		final Element line_mock = context.mock(Element.class, "line");
		lines.add(line_mock);
		final Attribute attribute_mock = context.mock(Attribute.class);

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildren("line");
				will(returnValue(lines));

				oneOf(line_mock).getAttribute("n");
				will(returnValue(attribute_mock));

				oneOf(attribute_mock).getIntValue();
				will(returnValue(1));

				oneOf(line_mock).getText();
				will(returnValue("local systemVariant = ..."));
			}
		});

		assertTrue(testable.parsePreConditionChecker(root_mock) instanceof ActionPreConditionChecker);
	}

	@Test
	public void parseTransformations() {
		final Element root_mock = context.mock(Element.class, "root");
		final List<Element> linkTransformations = new ArrayList<>();
		final Element linkTransformation_mock = context.mock(Element.class, "linkTransformation");
		linkTransformations.add(linkTransformation_mock);
		final List<Element> attributeTransformations = new ArrayList<>();
		final Element attributeTransformation_mock = context.mock(Element.class, "attributeTransformation");
		attributeTransformations.add(attributeTransformation_mock);

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildren("linkTransformation");
				will(returnValue(linkTransformations));

				// <-- parseLinkTransformation

				oneOf(linkTransformation_mock).getChildText("objectId");

				oneOf(linkTransformation_mock).getChildText("name");

				oneOf(linkTransformation_mock).getChildText("oldValue");

				oneOf(linkTransformation_mock).getChildText("newValue");

				// parseLinkTransformation -->

				oneOf(root_mock).getChildren("attributeTransformation");
				will(returnValue(attributeTransformations));

				// <-- parseAttributeTransformation

				oneOf(attributeTransformation_mock).getChildText("objectId");

				oneOf(attributeTransformation_mock).getChildText("name");

				oneOf(attributeTransformation_mock).getChild("value");

				// parseAttributeTransformation -->
			}
		});

		assertEquals(2, testable.parseTransformations(root_mock).length);
	}

	@Test
	public void parseAttributeTransformation() {
		final Element root_mock = context.mock(Element.class, "root");
		final Element value_mock = context.mock(Element.class, "value");

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildText("objectId");
				will(returnValue("objectId"));

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

		assertTrue(testable.parseAttributeTransformation(root_mock) instanceof AttributeTransformation);
	}

	@Test
	public void parseLinkTransformation() {
		final Element root_mock = context.mock(Element.class, "root");

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildText("objectId");
				will(returnValue("objectId"));

				oneOf(root_mock).getChildText("name");
				will(returnValue("name"));

				oneOf(root_mock).getChildText("oldValue");
				will(returnValue("oldValue"));

				oneOf(root_mock).getChildText("newValue");
				will(returnValue("newValue"));
			}
		});

		assertTrue(testable.parseLinkTransformation(root_mock) instanceof LinkTransformation);
	}

	@Test
	public void parseSystemTemplate() {
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

		assertTrue(testable.parseSystemTemplate(root_mock) instanceof SystemTemplate);
	}

	@Test
	public void parseSystemObjectTemplate() {
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

		assertTrue(testable.parseSystemObjectTemplate(root_mock) instanceof SystemObjectTemplate);
	}

	@Test
	public void parseAttributeTemplate() {
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
		AttributeTemplate result = testable.parseAtttributeTemplate(root_mock);
		assertNotNull(result);
		assertEquals("name", result.getName());
		assertEquals("", result.getValue());
	}

	@Test
	public void parseLinkTemplate() {
		final Element root_mock = context.mock(Element.class, "root");

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildText("name");
				will(returnValue("name"));

				oneOf(root_mock).getChildText("value");
				will(returnValue("value"));
			}
		});
		LinkTemplate result = testable.parseLinkTemplate(root_mock);
		assertNotNull(result);
		assertEquals("name", result.getName());
		assertEquals("value", result.getObjectId());
	}

	@Test
	public void parseLinkTemplate_with_null() {
		final Element root_mock = context.mock(Element.class, "root");

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildText("name");
				will(returnValue("name"));

				oneOf(root_mock).getChildText("value");
				will(returnValue(null));
			}
		});
		LinkTemplate result = testable.parseLinkTemplate(root_mock);
		assertNotNull(result);
		assertEquals("name", result.getName());
		assertEquals(null, result.getObjectId());
	}

	@Test
	public void parseValue() {
		final Element root_mock = context.mock(Element.class, "root");

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getAttributeValue("type", "string");
				will(returnValue("string"));

				oneOf(root_mock).getText();
				will(returnValue("value"));
			}
		});

		Object result = testable.parseValue(root_mock);
		assertNotNull(result);
		assertEquals("value", result);
	}

	@Test
	public void parseValue_boolean() {
		final Element root_mock = context.mock(Element.class, "root");

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getAttributeValue("type", "string");
				will(returnValue("boolean"));

				oneOf(root_mock).getText();
				will(returnValue("true"));
			}
		});

		Object result = testable.parseValue(root_mock);
		assertNotNull(result);
		assertEquals(true, result);
	}

	@Test
	public void parseValue_integer() {
		final Element root_mock = context.mock(Element.class, "root");

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getAttributeValue("type", "string");
				will(returnValue("integer"));

				oneOf(root_mock).getText();
				will(returnValue("10"));
			}
		});

		Object result = testable.parseValue(root_mock);
		assertNotNull(result);
		assertEquals(10, result);
	}

	@Test
	public void parseValue_with_null() {
		Object result = testable.parseValue(null);
		assertNull(result);
	}
}

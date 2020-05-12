package planning.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.method.SystemTransformations;
import planning.model.Action;
import planning.model.ActionParameterUpdater;
import planning.model.ActionPreConditionChecker;
import planning.model.AttributeTemplate;
import planning.model.AttributeTransformation;
import planning.model.LinkTemplate;
import planning.model.LinkTransformation;
import planning.model.LuaScriptActionParameterUpdater;
import planning.model.LuaScriptActionPreConditionChecker;
import planning.model.SystemObjectTemplate;
import planning.model.SystemTemplate;
import planning.model.SystemTransformation;
import planning.model.Transformation;

public class SystemTransformationsXMLSchemaTest {

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

	SystemTransformationsXMLSchema testable;

	@BeforeEach
	public void setup() {
		testable = new SystemTransformationsXMLSchema();
	}

	@Test
	public void parse() throws DataConversionException {
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

		assertEquals(1, ((SystemTransformations) testable.parse(root_mock)).size());
	}

	@Test
	public void combine() {
		final SystemTransformation systemTransformation_mock = context.mock(SystemTransformation.class);
		final SystemTransformations systemTransformations = new SystemTransformations();
		systemTransformations.add(systemTransformation_mock);

		context.checking(new Expectations() {
			{
				// <-- combineSystemTransformation
				oneOf(systemTransformation_mock).getName();

				oneOf(systemTransformation_mock).getAction();

				oneOf(systemTransformation_mock).getSystemTemplate();

				oneOf(systemTransformation_mock).getTransformations();

				// combineSystemTransformation -->
			}
		});

		Element element = testable.combine(systemTransformations);
		assertEquals("systemTransformations", element.getName());
		assertEquals("../systemTransformations.xsd", element.getAttributeValue("noNamespaceSchemaLocation",
				Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance")));
		assertNotNull(element.getChild("systemTransformation"));
		assertEquals(1, element.getChildren("systemTransformation").size());
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
	public void combineSystemTransformation() {
		final SystemTransformation systemTransformation_mock = context.mock(SystemTransformation.class);
		final Action action_mock = context.mock(Action.class);
		final SystemTemplate systemTemplate_mock = context.mock(SystemTemplate.class);
		final Transformation transformation_mock = context.mock(Transformation.class);
		final Transformation[] transformations = new Transformation[] { transformation_mock };

		context.checking(new Expectations() {
			{
				oneOf(systemTransformation_mock).getName();
				will(returnValue("ELEMENT"));

				oneOf(systemTransformation_mock).getAction();
				will(returnValue(action_mock));

				// <-- combineAction

				oneOf(action_mock).getName();

				oneOf(action_mock).getPreConditionCheckers();

				oneOf(action_mock).getParameterUpdaters();

				// combineAction -->

				oneOf(systemTransformation_mock).getSystemTemplate();
				will(returnValue(systemTemplate_mock));

				// <-- combineSystemTemplate

				oneOf(systemTemplate_mock).getObjectTemplates();

				// combineSystemTemplate -->

				oneOf(systemTransformation_mock).getTransformations();
				will(returnValue(transformations));

				// <-- combineTransformation

				oneOf(transformation_mock).getObjectId();

				// combineTransformation -->
			}
		});

		Element element = testable.combineSystemTransformation(systemTransformation_mock);
		assertEquals("ELEMENT", element.getChildText("name"));
		assertNotNull(element.getChild("systemTemplate"));
		assertNotNull(element.getChild("transformations"));
		assertNotNull(element.getChild("action"));
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
	public void combineAction() {
		final Action action_mock = context.mock(Action.class);
		final LuaScriptActionPreConditionChecker preConditionChecker_mock = context
				.mock(LuaScriptActionPreConditionChecker.class);
		final List<ActionPreConditionChecker> preConditionCheckers = new ArrayList<>();
		preConditionCheckers.add(preConditionChecker_mock);
		final LuaScriptActionParameterUpdater parameterUpdater_mock = context
				.mock(LuaScriptActionParameterUpdater.class);
		final List<ActionParameterUpdater> parameterUpdaters = new ArrayList<>();
		parameterUpdaters.add(parameterUpdater_mock);

		context.checking(new Expectations() {
			{
				oneOf(action_mock).getName();
				will(returnValue("OPERATION"));

				oneOf(action_mock).getPreConditionCheckers();
				will(returnValue(preConditionCheckers));

				// <-- combinePreConditionCheckers

				oneOf(preConditionChecker_mock).getScript();

				// combinePreConditionCheckers -->

				oneOf(action_mock).getParameterUpdaters();
				will(returnValue(parameterUpdaters));

				// <-- combineParameterUpdaters

				oneOf(parameterUpdater_mock).getScript();

				// combineParameterUpdaters -->
			}
		});

		Element element = testable.combineAction(action_mock);
		assertEquals("OPERATION", element.getChildText("name"));
		assertNotNull(element.getChild("preConditionChecker"));
		assertNotNull(element.getChild("parameterUpdater"));
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
	public void combineParameterUpdater() {
		final LuaScriptActionParameterUpdater parameterUpdater_mock = context
				.mock(LuaScriptActionParameterUpdater.class);

		context.checking(new Expectations() {
			{
				oneOf(parameterUpdater_mock).getScript();
				will(returnValue(
						"local systemVariant = ...\nlocal object = systemVariant:getObjectByIdMatch('ID-PLANE-X-TARGET')"));
			}
		});

		Element element = testable.combineParameterUpdater(parameterUpdater_mock);
		List<Element> lines = element.getChildren("line");
		assertEquals(2, lines.size());
		assertEquals("local systemVariant = ...", lines.get(0).getText());
		assertEquals("1", lines.get(0).getAttributeValue("n"));
		assertEquals("local object = systemVariant:getObjectByIdMatch('ID-PLANE-X-TARGET')", lines.get(1).getText());
		assertEquals("2", lines.get(1).getAttributeValue("n"));
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
	public void combinePreConditionChecker() {
		final LuaScriptActionPreConditionChecker preConditionChecker_mock = context
				.mock(LuaScriptActionPreConditionChecker.class);

		context.checking(new Expectations() {
			{
				oneOf(preConditionChecker_mock).getScript();
				will(returnValue(
						"local systemVariant = ...\nlocal object = systemVariant:getObjectByIdMatch('ID-PLANE-X-TARGET')"));
			}
		});

		Element element = testable.combinePreConditionChecker(preConditionChecker_mock);
		List<Element> lines = element.getChildren("line");
		assertEquals(2, lines.size());
		assertEquals("local systemVariant = ...", lines.get(0).getText());
		assertEquals("1", lines.get(0).getAttributeValue("n"));
		assertEquals("local object = systemVariant:getObjectByIdMatch('ID-PLANE-X-TARGET')", lines.get(1).getText());
		assertEquals("2", lines.get(1).getAttributeValue("n"));
	}

	@Test
	public void parseTransformations() throws DataConversionException {
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
	public void combineTransformations() {
		final AttributeTransformation attributeTransformation_mock = context.mock(AttributeTransformation.class);
		final LinkTransformation linkTransformation_mock = context.mock(LinkTransformation.class);
		final Transformation transformation_mock = context.mock(Transformation.class);
		final Transformation[] transformations = new Transformation[] { attributeTransformation_mock,
				linkTransformation_mock, transformation_mock };

		context.checking(new Expectations() {
			{
				// <-- combineAttributeTransformation

				oneOf(attributeTransformation_mock).getObjectId();

				oneOf(attributeTransformation_mock).getAttributeName();

				oneOf(attributeTransformation_mock).getAttributeValue();

				// combineAttributeTransformation -->

				// <-- combineLinkTransformation

				oneOf(linkTransformation_mock).getObjectId();

				oneOf(linkTransformation_mock).getLinkName();

				oneOf(linkTransformation_mock).getLinkOldValue();

				oneOf(linkTransformation_mock).getLinkNewValue();

				// combineLinkTransformation -->

				// <-- combineTransformation

				oneOf(transformation_mock).getObjectId();

				// combineTransformation -->
			}
		});

		Element element = testable.combineTransformations(transformations);
		assertNotNull(element.getChild("attributeTransformation"));
		assertNotNull(element.getChild("linkTransformation"));
		assertNotNull(element.getChild("transformation"));
	}

	@Test
	public void combineTransformation() {
		final Transformation transformation_mock = context.mock(Transformation.class);

		context.checking(new Expectations() {
			{
				oneOf(transformation_mock).getObjectId();
				will(returnValue("id"));
			}
		});

		Element element = testable.combineTransformation(transformation_mock);
		assertEquals("id", element.getChildText("objectId"));
	}

	@Test
	public void parseSystemTemplate() throws DataConversionException {
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
	public void combineSystemTemplate() {
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

		Element element = testable.combineSystemTemplate(systemTemplate_mock);
		assertNotNull(element.getChild("objectTemplate"));
	}

	@Test
	public void parseSystemObjectTemplate() throws DataConversionException {
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
	public void combineSystemObjectTemplate() {
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

				oneOf(linkTemplate_mock).getObjectId();

				// combineLinkTemplate -->
			}
		});

		Element element = testable.combineSystemObjectTemplate(systemObjectTemplate_mock);
		assertEquals("id", element.getChildText("objectId"));
		assertNotNull(element.getChild("attributeTemplate"));
		assertNotNull(element.getChild("linkTemplate"));
	}
}

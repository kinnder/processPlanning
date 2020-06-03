package planning.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import planning.model.Action;
import planning.model.SystemTemplate;
import planning.model.SystemTransformation;
import planning.model.Transformation;

public class SystemTransformationXMLSchemaTest {

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

	SystemTransformationXMLSchema testable;

	@BeforeEach
	public void setup() {
		testable = new SystemTransformationXMLSchema();
	}

	@Test
	public void parse() throws DataConversionException {
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

				oneOf(systemTemplateElement_mock).getChildren("linkTemplate");

				// parseSystemTemplate -->

				oneOf(root_mock).getChild("transformations");
				will(returnValue(transformationsElement_mock));

				// <-- parseTransformations

				oneOf(transformationsElement_mock).getChildren("linkTransformation");

				oneOf(transformationsElement_mock).getChildren("attributeTransformation");

				// parseTransformation -->
			}
		});

		assertTrue(testable.parse(root_mock) instanceof SystemTransformation);
	}

	@Test
	public void combine() {
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

				oneOf(systemTemplate_mock).getLinkTemplates();

				// combineSystemTemplate -->

				oneOf(systemTransformation_mock).getTransformations();
				will(returnValue(transformations));

				// <-- combineTransformation

				oneOf(transformation_mock).getObjectId();

				// combineTransformation -->
			}
		});

		Element element = testable.combine(systemTransformation_mock);
		assertEquals("ELEMENT", element.getChildText("name"));
		assertNotNull(element.getChild("systemTemplate"));
		assertNotNull(element.getChild("transformations"));
		assertNotNull(element.getChild("action"));
	}
}

package application.storage.xml;

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
import planning.model.Transformations;

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

	ActionXMLSchema actionSchema_mock;

	TransformationsXMLSchema transformationsSchema_mock;

	SystemTemplateXMLSchema systemTemplateSchema_mock;

	SystemTransformationXMLSchema testable;

	@BeforeEach
	public void setup() {
		actionSchema_mock = context.mock(ActionXMLSchema.class);
		transformationsSchema_mock = context.mock(TransformationsXMLSchema.class);
		systemTemplateSchema_mock = context.mock(SystemTemplateXMLSchema.class);

		testable = new SystemTransformationXMLSchema(actionSchema_mock, transformationsSchema_mock,
				systemTemplateSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new SystemTransformationXMLSchema();
	}

	@Test
	public void parse() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");
		final Element actionElement_mock = context.mock(Element.class, "actionElement");
		final Element systemTemplateElement_mock = context.mock(Element.class, "systemTemplateElement");
		final Element transformationsElement_mock = context.mock(Element.class, "transformationsElement");
		final Action action = new Action("action");
		final SystemTemplate systemTemplate = new SystemTemplate();
		final Transformations transformations = new Transformations();

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildText("name");
				will(returnValue("name"));

				oneOf(actionSchema_mock).getSchemaName();
				will(returnValue("action"));

				oneOf(root_mock).getChild("action");
				will(returnValue(actionElement_mock));

				oneOf(actionSchema_mock).parse(actionElement_mock);
				will(returnValue(action));

				oneOf(systemTemplateSchema_mock).getSchemaName();
				will(returnValue("systemTemplate"));

				oneOf(root_mock).getChild("systemTemplate");
				will(returnValue(systemTemplateElement_mock));

				oneOf(systemTemplateSchema_mock).parse(systemTemplateElement_mock);
				will(returnValue(systemTemplate));

				oneOf(transformationsSchema_mock).getSchemaName();
				will(returnValue("transformations"));

				oneOf(root_mock).getChild("transformations");
				will(returnValue(transformationsElement_mock));

				oneOf(transformationsSchema_mock).parse(transformationsElement_mock);
				will(returnValue(transformations));
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
		final Transformations transformations = new Transformations();
		transformations.add(transformation_mock);
		final Element action = new Element("action");
		final Element systemTemplate = new Element("systemTemplate");
		final Element transformation = new Element("transformations");

		context.checking(new Expectations() {
			{
				oneOf(systemTransformation_mock).getName();
				will(returnValue("ELEMENT"));

				oneOf(systemTransformation_mock).getAction();
				will(returnValue(action_mock));

				oneOf(actionSchema_mock).combine(action_mock);
				will(returnValue(action));

				oneOf(systemTransformation_mock).getSystemTemplate();
				will(returnValue(systemTemplate_mock));

				oneOf(systemTemplateSchema_mock).combine(systemTemplate_mock);
				will(returnValue(systemTemplate));

				oneOf(systemTransformation_mock).getTransformations();
				will(returnValue(transformations));

				oneOf(transformationsSchema_mock).combine(transformations);
				will(returnValue(transformation));
			}
		});

		Element element = testable.combine(systemTransformation_mock);
		assertEquals("ELEMENT", element.getChildText("name"));
		assertNotNull(element.getChild("systemTemplate"));
		assertNotNull(element.getChild("transformations"));
		assertNotNull(element.getChild("action"));
	}

	@Test
	public void getSchemaName() {
		assertEquals("systemTransformation", testable.getSchemaName());
	}
}

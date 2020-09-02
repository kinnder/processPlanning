package application.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

import planning.model.AttributeTransformation;
import planning.model.LinkTransformation;
import planning.model.Transformation;

public class TransformationsXMLSchemaTest {

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

	AttributeTransformationXMLSchema attributeTransformationXMLSchema_mock;

	LinkTransformationXMLSchema linkTransformationXMLSchema_mock;

	TransformationsXMLSchema testable;

	@BeforeEach
	public void setup() {
		attributeTransformationXMLSchema_mock = context.mock(AttributeTransformationXMLSchema.class);
		linkTransformationXMLSchema_mock = context.mock(LinkTransformationXMLSchema.class);

		testable = new TransformationsXMLSchema(attributeTransformationXMLSchema_mock, linkTransformationXMLSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new TransformationsXMLSchema();
	}

	@Test
	public void parse() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");
		final List<Element> linkTransformations = new ArrayList<>();
		final Element linkTransformation_mock = context.mock(Element.class, "linkTransformation");
		linkTransformations.add(linkTransformation_mock);
		final List<Element> attributeTransformations = new ArrayList<>();
		final Element attributeTransformation_mock = context.mock(Element.class, "attributeTransformation");
		attributeTransformations.add(attributeTransformation_mock);
		final AttributeTransformation attributeTransformation = new AttributeTransformation("object-id", "attribute-name", "attribute-value");
		final LinkTransformation linkTransformation = new LinkTransformation("object-id", "link-name", "link-value-old", "link-value-new");

		context.checking(new Expectations() {
			{
				oneOf(linkTransformationXMLSchema_mock).getSchemaName();
				will(returnValue("linkTransformation"));

				oneOf(root_mock).getChildren("linkTransformation");
				will(returnValue(linkTransformations));

				oneOf(linkTransformationXMLSchema_mock).parse(linkTransformation_mock);
				will(returnValue(linkTransformation));

				oneOf(attributeTransformationXMLSchema_mock).getSchemaName();
				will(returnValue("attributeTransformation"));

				oneOf(root_mock).getChildren("attributeTransformation");
				will(returnValue(attributeTransformations));

				oneOf(attributeTransformationXMLSchema_mock).parse(attributeTransformation_mock);
				will(returnValue(attributeTransformation));
			}
		});

		assertEquals(2, testable.parse(root_mock).length);
	}

	@Test
	public void combine() {
		final AttributeTransformation attributeTransformation_mock = context.mock(AttributeTransformation.class);
		final LinkTransformation linkTransformation_mock = context.mock(LinkTransformation.class);
		final Transformation transformation_mock = context.mock(Transformation.class);
		final Transformation[] transformations = new Transformation[] { attributeTransformation_mock, linkTransformation_mock, transformation_mock };
		final Element linkTransformation = new Element("linkTransformation");
		final Element attributeTransformation = new Element("attributeTransformation");

		context.checking(new Expectations() {
			{
				oneOf(attributeTransformationXMLSchema_mock).combine(attributeTransformation_mock);
				will(returnValue(attributeTransformation));

				oneOf(linkTransformationXMLSchema_mock).combine(linkTransformation_mock);
				will(returnValue(linkTransformation));

				// <-- combineTransformation

				oneOf(transformation_mock).getObjectId();

				// combineTransformation -->
			}
		});

		Element element = testable.combine(transformations);
		assertEquals(attributeTransformation, element.getChild("attributeTransformation"));
		assertEquals(linkTransformation, element.getChild("linkTransformation"));
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
	public void getSchemaName() {
		assertEquals("transformations", testable.getSchemaName());
	}
}

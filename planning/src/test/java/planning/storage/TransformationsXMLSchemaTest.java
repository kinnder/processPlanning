package planning.storage;

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

	TransformationsXMLSchema testable;

	@BeforeEach
	public void setup() {
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

		assertEquals(2, testable.parse(root_mock).length);
	}

	@Test
	public void combine() {
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

		Element element = testable.combine(transformations);
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
}

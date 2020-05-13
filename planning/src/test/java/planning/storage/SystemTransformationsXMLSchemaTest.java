package planning.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.ArrayList;
import java.util.List;

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
import planning.model.SystemTransformation;

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
}

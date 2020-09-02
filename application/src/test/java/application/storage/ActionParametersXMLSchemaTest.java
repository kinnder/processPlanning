package application.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class ActionParametersXMLSchemaTest {

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

	ValueXMLSchema valueXMLSchema_mock;

	ActionParametersXMLSchema testable;

	@BeforeEach
	public void setup() {
		valueXMLSchema_mock = context.mock(ValueXMLSchema.class);

		testable = new ActionParametersXMLSchema(valueXMLSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new ActionParametersXMLSchema();
	}

	@Test
	public void getSchemaName() {
		assertEquals("parameters", testable.getSchemaName());
	}

	@Test
	public void combine() {
		final Map<String, String> actionParameters = new HashMap<String, String>();
		actionParameters.put("parameter-name", "parameter-value");
		final Element value = new Element("value");

		context.checking(new Expectations() {
			{
				oneOf(valueXMLSchema_mock).combine("parameter-value");
				will(returnValue(value));
			}
		});

		Element element = testable.combine(actionParameters);
		assertEquals("parameters", element.getName());
		assertEquals(1, element.getChildren("parameter").size());
		Element parameter = element.getChildren().get(0);
		assertEquals("parameter", parameter.getName());
		assertEquals("parameter-name", parameter.getChildText("name"));
		assertEquals(value, parameter.getChild("value"));
	}

	@Test
	public void parse() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");
		final Element parameter_mock = context.mock(Element.class, "parameter");
		final List<Element> parameters = new ArrayList<>();
		parameters.add(parameter_mock);
		final Element value_mock = context.mock(Element.class, "value");

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildren("parameter");
				will(returnValue(parameters));

				oneOf(parameter_mock).getChildText("name");
				will(returnValue("parameter-name"));

				oneOf(valueXMLSchema_mock).getSchemaName();
				will(returnValue("value"));

				oneOf(parameter_mock).getChild("value");
				will(returnValue(value_mock));

				oneOf(valueXMLSchema_mock).parse(value_mock);
				will(returnValue("parameter-value"));
			}
		});

		assertTrue(testable.parse(root_mock) instanceof Map);
	}
}

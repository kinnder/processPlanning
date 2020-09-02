package application.storage.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
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

import planning.model.SystemOperation;

public class SystemOperationXMLSchemaTest {

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

	ActionParametersXMLSchema actionParametersXMLSchema_mock;

	SystemOperationXMLSchema testable;

	@BeforeEach
	public void setup() {
		actionParametersXMLSchema_mock = context.mock(ActionParametersXMLSchema.class);

		testable = new SystemOperationXMLSchema(actionParametersXMLSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new SystemOperationXMLSchema();
	}

	@Test
	public void combine() {
		final SystemOperation systemOperation_mock = context.mock(SystemOperation.class);
		final Map<String, String> actionParameters = new HashMap<>();
		final Element parameters = new Element("parameters");

		context.checking(new Expectations() {
			{
				oneOf(systemOperation_mock).getName();
				will(returnValue("operation-name"));

				oneOf(systemOperation_mock).getActionParameters();
				will(returnValue(actionParameters));

				oneOf(actionParametersXMLSchema_mock).combine(actionParameters);
				will(returnValue(parameters));
			}
		});

		Element element = testable.combine(systemOperation_mock);
		assertEquals("operation", element.getName());
		assertEquals("operation-name", element.getChildText("name"));
		assertEquals(parameters, element.getChild("parameters"));
	}

	@Test
	public void parse() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");
		final Element parameters_mock = context.mock(Element.class, "parameters");
		final Map<String, String> actionParameters = new HashMap<>();

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildText("name");
				will(returnValue("operation-name"));

				oneOf(actionParametersXMLSchema_mock).getSchemaName();
				will(returnValue("parameters"));

				oneOf(root_mock).getChild("parameters");
				will(returnValue(parameters_mock));

				oneOf(actionParametersXMLSchema_mock).parse(parameters_mock);
				will(returnValue(actionParameters));
			}
		});

		assertTrue(testable.parse(root_mock) instanceof SystemOperation);
	}

	@Test
	public void getSchemaName() {
		assertEquals("operation", testable.getSchemaName());
	}
}

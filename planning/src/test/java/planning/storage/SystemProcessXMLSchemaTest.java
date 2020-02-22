package planning.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.SystemOperation;
import planning.model.SystemProcess;

public class SystemProcessXMLSchemaTest {

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

	SystemProcessXMLSchema testable;

	@BeforeEach
	public void setup() {
		testable = new SystemProcessXMLSchema();
	}

	@Test
	public void combine() {
		final SystemOperation systemOperation_mock = context.mock(SystemOperation.class);
		final SystemProcess systemProcess = new SystemProcess();
		systemProcess.add(systemOperation_mock);

		context.checking(new Expectations() {
			{
				// <-- combineSystemOperation

				oneOf(systemOperation_mock).getName();

				oneOf(systemOperation_mock).getActionParameters();

				// combineSystemOperation -->
			}
		});

		Element element = testable.combine(systemProcess);
		assertEquals("process", element.getName());
		assertEquals("../process.xsd", element.getAttributeValue("noNamespaceSchemaLocation",
				Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance")));
		assertNotNull(element.getChild("operation"));
		assertEquals(1, element.getChildren("operation").size());
	}

	@Test
	public void combineSystemOperation() {
		final SystemOperation systemOperation_mock = context.mock(SystemOperation.class);
		final Map<?, ?> actionParameters_mock = context.mock(Map.class);

		context.checking(new Expectations() {
			{
				oneOf(systemOperation_mock).getName();
				will(returnValue("operation-name"));

				oneOf(systemOperation_mock).getActionParameters();
				will(returnValue(actionParameters_mock));

				// <-- combineActionParameters

				oneOf(actionParameters_mock).keySet();

				// combineActionParameters -->
			}
		});

		Element element = testable.combineSystemOperation(systemOperation_mock);
		assertEquals("operation", element.getName());
		assertEquals("operation-name", element.getChildText("name"));
		assertNotNull(element.getChild("parameters"));
	}

	@Test
	public void combineActionParameters() {
		final Map<String, String> actionParameters = new HashMap<String, String>();
		actionParameters.put("parameter-name", "parameter-value");

		Element element = testable.combineActionParameters(actionParameters);
		assertEquals("parameters", element.getName());
		assertEquals(1, element.getChildren("parameter").size());
		Element parameter = element.getChildren().get(0);
		assertEquals("parameter", parameter.getName());
		assertEquals("parameter-name", parameter.getChildText("name"));
		assertEquals("parameter-value", parameter.getChildText("value"));
	}

	@Test
	public void parse() {
		final Element element_mock = context.mock(Element.class);

		assertThrows(UnsupportedOperationException.class, () -> {
			testable.parse(element_mock);
		});
	}
}

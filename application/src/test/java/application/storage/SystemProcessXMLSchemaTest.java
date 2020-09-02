package application.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

	SystemOperationXMLSchema systemOperationXMLSchema_mock;

	SystemProcessXMLSchema testable;

	@BeforeEach
	public void setup() {
		systemOperationXMLSchema_mock = context.mock(SystemOperationXMLSchema.class);

		testable = new SystemProcessXMLSchema(systemOperationXMLSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new SystemProcessXMLSchema();
	}

	@Test
	public void combine() {
		final SystemOperation systemOperation_mock = context.mock(SystemOperation.class);
		final SystemProcess systemProcess = new SystemProcess();
		systemProcess.add(systemOperation_mock);
		final Element systemOperation = new Element("operation");

		context.checking(new Expectations() {
			{
				oneOf(systemOperationXMLSchema_mock).combine(systemOperation_mock);
				will(returnValue(systemOperation));
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
	public void parse() {
		final Element element_mock = context.mock(Element.class);

		assertThrows(UnsupportedOperationException.class, () -> {
			testable.parse(element_mock);
		});
	}

	@Test
	public void getSchemaName() {
		assertEquals("process", testable.getSchemaName());
	}
}

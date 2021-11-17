package application.storage.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
	public void parse() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "processElement");
		final Element element_mock = context.mock(Element.class, "operationElement");
		List<Element> elements = new ArrayList<>();
		elements.add(element_mock);
		final SystemOperation systemOperation_mock = context.mock(SystemOperation.class);

		context.checking(new Expectations() {
			{
				oneOf(systemOperationXMLSchema_mock).getSchemaName();
				will(returnValue("operation"));

				oneOf(root_mock).getChildren("operation");
				will(returnValue(elements));

				oneOf(systemOperationXMLSchema_mock).parse(element_mock);
				will(returnValue(systemOperation_mock));
			}
		});

		SystemProcess result = testable.parse(root_mock);
		assertTrue(result instanceof SystemProcess);
		assertEquals(1, result.size());
		assertEquals(systemOperation_mock, result.get(0));
	}

	@Test
	public void getSchemaName() {
		assertEquals("process", testable.getSchemaName());
	}
}

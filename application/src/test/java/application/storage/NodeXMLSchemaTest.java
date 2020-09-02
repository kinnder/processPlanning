package application.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import planning.method.Node;
import planning.model.System;

public class NodeXMLSchemaTest {

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

	NodeXMLSchema testable;

	SystemXMLSchema systemXMLSchema_mock;

	@BeforeEach
	public void setup() {
		systemXMLSchema_mock = context.mock(SystemXMLSchema.class);

		testable = new NodeXMLSchema(systemXMLSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new NodeXMLSchema();
	}

	@Test
	public void getSchemaName() {
		assertEquals("node", testable.getSchemaName());
	}

	@Test
	public void combine() {
		final Node node_mock = context.mock(Node.class);
		final System system_mock = context.mock(System.class);
		final Element system = new Element("system");

		context.checking(new Expectations() {
			{
				oneOf(node_mock).getId();
				will(returnValue("node-id"));

				oneOf(node_mock).getChecked();
				will(returnValue(false));

				oneOf(node_mock).getSystem();
				will(returnValue(system_mock));

				oneOf(systemXMLSchema_mock).combine(system_mock);
				will(returnValue(system));
			}
		});

		Element element = testable.combine(node_mock);
		assertEquals("node", element.getName());
		assertEquals("node-id", element.getChildText("id"));
		assertEquals("false", element.getChildText("checked"));
		assertEquals(system, element.getChild("system"));
	}

	@Test
	public void parse() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");
		final Element system_mock = context.mock(Element.class, "system");
		final System system = new System();

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildText("id");
				will(returnValue("node-id"));

				oneOf(root_mock).getChildText("checked");
				will(returnValue("true"));

				oneOf(systemXMLSchema_mock).getSchemaName();
				will(returnValue("system"));

				oneOf(root_mock).getChild("system");
				will(returnValue(system_mock));

				oneOf(systemXMLSchema_mock).parse(system_mock);
				will(returnValue(system));
			}
		});

		assertTrue(testable.parse(root_mock) instanceof Node);
	}
}

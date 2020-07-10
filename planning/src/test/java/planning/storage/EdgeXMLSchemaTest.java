package planning.storage;

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

import planning.method.Edge;
import planning.model.SystemOperation;

public class EdgeXMLSchemaTest {

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

	EdgeXMLSchema testable;

	SystemOperationXMLSchema systemOperationXMLSchema_mock;

	@BeforeEach
	public void setup() {
		systemOperationXMLSchema_mock = context.mock(SystemOperationXMLSchema.class);

		testable = new EdgeXMLSchema(systemOperationXMLSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new EdgeXMLSchema();
	}

	@Test
	public void combine() {
		final Edge edge_mock = context.mock(Edge.class);
		final SystemOperation systemOperation_mock = context.mock(SystemOperation.class);
		final Element systemOperation = new Element("systemOperation");

		context.checking(new Expectations() {
			{
				oneOf(edge_mock).getId();
				will(returnValue("edge-id"));

				oneOf(edge_mock).getBeginNodeId();
				will(returnValue("node-begin-id"));

				oneOf(edge_mock).getEndNodeId();
				will(returnValue("node-end-id"));

				oneOf(edge_mock).getSystemOperation();
				will(returnValue(systemOperation_mock));

				oneOf(systemOperationXMLSchema_mock).combine(systemOperation_mock);
				will(returnValue(systemOperation));
			}
		});

		Element element = testable.combine(edge_mock);
		assertEquals("edge", element.getName());
		assertEquals("node-begin-id", element.getChildText("beginNodeId"));
		assertEquals("node-end-id", element.getChildText("endNodeId"));
		assertEquals(systemOperation, element.getChild("systemOperation"));
	}

	@Test
	public void parse() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");
		final Element systemOperation_mock = context.mock(Element.class, "systemOperation");
		final SystemOperation systemOperation = new SystemOperation(null, null);

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildText("id");
				will(returnValue("edge-id"));

				oneOf(root_mock).getChildText("beginNodeId");
				will(returnValue("node-begin-id"));

				oneOf(root_mock).getChildText("endNodeId");
				will(returnValue("node-end-id"));

				oneOf(systemOperationXMLSchema_mock).getSchemaName();
				will(returnValue("systemOperation"));

				oneOf(root_mock).getChild("systemOperation");
				will(returnValue(systemOperation_mock));

				oneOf(systemOperationXMLSchema_mock).parse(systemOperation_mock);
				will(returnValue(systemOperation));
			}
		});

		assertTrue(testable.parse(root_mock) instanceof Edge);
	}

	@Test
	public void getSchemaName() {
		assertEquals("edge", testable.getSchemaName());
	}
}

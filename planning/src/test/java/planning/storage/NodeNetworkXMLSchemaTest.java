package planning.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import planning.method.Edge;
import planning.method.Node;
import planning.method.NodeNetwork;

public class NodeNetworkXMLSchemaTest {

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

	NodeNetworkXMLSchema testable;

	NodeXMLSchema nodeXMLSchema_mock;

	EdgeXMLSchema edgeXMLSchema_mock;

	@BeforeEach
	public void setup() {
		nodeXMLSchema_mock = context.mock(NodeXMLSchema.class);
		edgeXMLSchema_mock = context.mock(EdgeXMLSchema.class);

		testable = new NodeNetworkXMLSchema(nodeXMLSchema_mock, edgeXMLSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new NodeNetworkXMLSchema();
	}

	@Test
	public void getSchemaName() {
		assertEquals("nodeNetwork", testable.getSchemaName());
	}

	@Test
	public void combine() {
		final NodeNetwork nodeNetwork_mock = context.mock(NodeNetwork.class);
		final Node node_1_mock = context.mock(Node.class, "node-1");
		final Node node_2_mock = context.mock(Node.class, "node-2");
		List<Node> nodes = new ArrayList<>();
		nodes.add(node_1_mock);
		nodes.add(node_2_mock);
		final Edge edge_mock = context.mock(Edge.class, "edge");
		List<Edge> edges = new ArrayList<>();
		edges.add(edge_mock);
		final Element node_1 = new Element("node");
		final Element node_2 = new Element("node");
		final Element edge = new Element("edge");

		context.checking(new Expectations() {
			{
				oneOf(nodeNetwork_mock).getNodes();
				will(returnValue(nodes));

				oneOf(nodeXMLSchema_mock).combine(node_1_mock);
				will(returnValue(node_1));

				oneOf(nodeXMLSchema_mock).combine(node_2_mock);
				will(returnValue(node_2));

				oneOf(nodeNetwork_mock).getEdges();
				will(returnValue(edges));

				oneOf(edgeXMLSchema_mock).combine(edge_mock);
				will(returnValue(edge));
			}
		});

		Element element = testable.combine(nodeNetwork_mock);
		assertEquals("nodeNetwork", element.getName());
		assertEquals("../nodeNetwork.xsd", element.getAttributeValue("noNamespaceSchemaLocation",
				Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance")));
		assertEquals(2, element.getChildren("node").size());
		assertEquals(1, element.getChildren("edge").size());
	}

	@Test
	public void parse() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");
		final Element node_1_mock = context.mock(Element.class, "node-1");
		final Element node_2_mock = context.mock(Element.class, "node-2");
		final Element edge_mock = context.mock(Element.class, "edge");
		List<Element> nodes = new ArrayList<>();
		nodes.add(node_1_mock);
		nodes.add(node_2_mock);
		List<Element> edges = new ArrayList<>();
		edges.add(edge_mock);
		Node node_1 = new Node(null);
		Node node_2 = new Node(null);
		Edge edge = new Edge(node_1.getId(), node_2.getId(), null);

		context.checking(new Expectations() {
			{
				oneOf(nodeXMLSchema_mock).getSchemaName();
				will(returnValue("node"));

				oneOf(root_mock).getChildren("node");
				will(returnValue(nodes));

				oneOf(nodeXMLSchema_mock).parse(node_1_mock);
				will(returnValue(node_1));

				oneOf(nodeXMLSchema_mock).parse(node_2_mock);
				will(returnValue(node_2));

				oneOf(edgeXMLSchema_mock).getSchemaName();
				will(returnValue("edge"));

				oneOf(root_mock).getChildren("edge");
				will(returnValue(edges));

				oneOf(edgeXMLSchema_mock).parse(edge_mock);
				will(returnValue(edge));
			}
		});

		assertTrue(testable.parse(root_mock) instanceof NodeNetwork);
	}
}

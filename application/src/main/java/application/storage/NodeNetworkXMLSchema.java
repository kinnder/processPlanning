package application.storage;

import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.jdom2.Namespace;

import planning.method.Edge;
import planning.method.Node;
import planning.method.NodeNetwork;

public class NodeNetworkXMLSchema implements XMLSchema<NodeNetwork> {

	final private static String TAG_nodeNetwork = "nodeNetwork";

	@Override
	public String getSchemaName() {
		return TAG_nodeNetwork;
	}

	private NodeXMLSchema nodeXMLSchema;

	private EdgeXMLSchema edgeXMLSchema;

	public NodeNetworkXMLSchema() {
		this(new NodeXMLSchema(), new EdgeXMLSchema());
	}

	NodeNetworkXMLSchema(NodeXMLSchema nodeXMLSchema, EdgeXMLSchema edgeXMLSchema) {
		this.nodeXMLSchema = nodeXMLSchema;
		this.edgeXMLSchema = edgeXMLSchema;
	}

	@Override
	public NodeNetwork parse(Element element) throws DataConversionException {
		NodeNetwork nodeNetwork = new NodeNetwork();

		for (Element e : element.getChildren(nodeXMLSchema.getSchemaName())) {
			Node node = nodeXMLSchema.parse(e);
			nodeNetwork.addNode(node);
		}

		for (Element e : element.getChildren(edgeXMLSchema.getSchemaName())) {
			Edge edge = edgeXMLSchema.parse(e);
			nodeNetwork.addEdge(edge);
		}

		return nodeNetwork;
	}

	@Override
	public Element combine(NodeNetwork nodeNetwork) {
		Element root = new Element(TAG_nodeNetwork);
		Namespace xsiNamespace = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		root.setAttribute("noNamespaceSchemaLocation", "../nodeNetwork.xsd", xsiNamespace);

		for (Node node : nodeNetwork.getNodes()) {
			Element element = nodeXMLSchema.combine(node);
			root.addContent(element);
		}

		for (Edge edge : nodeNetwork.getEdges()) {
			Element element = edgeXMLSchema.combine(edge);
			root.addContent(element);
		}

		return root;
	}
}

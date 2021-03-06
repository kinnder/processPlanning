package application.storage.owl;

import org.apache.jena.ontology.Individual;
import planning.method.Edge;
import planning.method.Node;
import planning.method.NodeNetwork;

public class NodeNetworkOWLSchema implements OWLSchema<NodeNetwork> {

	private NodeNetworkOWLModel owlModel;

	private NodeOWLSchema nodeOWLSchema;

	private EdgeOWLSchema edgeOWLSchema;

	public NodeNetworkOWLSchema(NodeNetworkOWLModel owlModel) {
		this.owlModel = owlModel;

		this.nodeOWLSchema = new NodeOWLSchema(owlModel);
		this.edgeOWLSchema = new EdgeOWLSchema(owlModel);
	}

	@Override
	public Individual combine(NodeNetwork nodeNetwork) {
		Individual ind_nodeNetwork = owlModel.getClass_NodeNetwork().createIndividual(owlModel.getUniqueURI());
		ind_nodeNetwork.addLabel("Node Network", "en");
		ind_nodeNetwork.addLabel("Сеть узлов", "ru");

		for (Node node : nodeNetwork.getNodes()) {
			Individual ind_node = nodeOWLSchema.combine(node);
			ind_nodeNetwork.addProperty(owlModel.getObjectProperty_hasNode(), ind_node);
			ind_node.addProperty(owlModel.getObjectProperty_isNodeOf(), ind_nodeNetwork);
		}

		for (Edge edge : nodeNetwork.getEdges()) {
			Individual ind_edge = edgeOWLSchema.combine(edge);
			ind_nodeNetwork.addProperty(owlModel.getObjectProperty_hasEdge(), ind_edge);
			ind_edge.addProperty(owlModel.getObjectProperty_isEdgeOf(), ind_nodeNetwork);
		}

		return ind_nodeNetwork;
	}

	@Override
	public NodeNetwork parse(Individual individual) {
		NodeNetwork nodeNetwork = new NodeNetwork();

		owlModel.getClass_NodeNetwork().listInstances().forEachRemaining((ind_nodeNetwork) -> {
			owlModel.getClass_Node().listInstances().filterKeep((ind_node) -> {
				return ind_nodeNetwork.hasProperty(owlModel.getObjectProperty_hasNode(), ind_node);
			}).forEachRemaining((ind_node) -> {
				Node node = nodeOWLSchema.parse(ind_node.asIndividual());
				nodeNetwork.addNode(node);
			});

			owlModel.getClass_Edge().listInstances().filterKeep((ind_edge) -> {
				return ind_nodeNetwork.hasProperty(owlModel.getObjectProperty_hasEdge(), ind_edge);
			}).forEachRemaining((ind_edge) -> {
				Edge edge = edgeOWLSchema.parse(ind_edge.asIndividual());
				nodeNetwork.addEdge(edge);
			});
		});

		return nodeNetwork;
	}
}

package application.storage.owl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.util.iterator.NiceIterator;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import application.domain.AssemblyLine;
import planning.method.Edge;
import planning.method.Node;
import planning.method.NodeNetwork;
import planning.model.Action;
import planning.model.System;
import planning.model.SystemOperation;

public class NodeNetworkOWLSchemaTest {

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

	NodeNetworkOWLSchema testable;

	NodeNetworkOWLModel owlModel_mock;

	NodeOWLSchema nodeOWLSchema_mock;

	EdgeOWLSchema edgeOWLSchema_mock;

	@BeforeEach
	public void setup() {
		owlModel_mock = context.mock(NodeNetworkOWLModel.class);
		nodeOWLSchema_mock = context.mock(NodeOWLSchema.class);
		edgeOWLSchema_mock = context.mock(EdgeOWLSchema.class);

		testable = new NodeNetworkOWLSchema(owlModel_mock, nodeOWLSchema_mock, edgeOWLSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new NodeNetworkOWLSchema(new NodeNetworkOWLModel());
	}

	@Test
	public void combine() {
		final NodeNetwork nodeNetwork_mock = context.mock(NodeNetwork.class, "nodeNetwork");
		final Node node_mock = context.mock(Node.class, "node");
		final Collection<Node> nodes = Arrays.asList(node_mock);
		final Edge edge_mock = context.mock(Edge.class, "edge");
		final Collection<Edge> edges = Arrays.asList(edge_mock);
		final Individual i_nodeNetwork_mock = context.mock(Individual.class, "i-nodeNetwork");
		final Individual i_node_mock = context.mock(Individual.class, "i-node");
		final Individual i_edge_mock = context.mock(Individual.class, "i-edge");
		final ObjectProperty op_hasNode_mock = context.mock(ObjectProperty.class, "hasNode");
		final ObjectProperty op_isNodeOf_mock = context.mock(ObjectProperty.class, "isNodeOf");
		final ObjectProperty op_hasEdge_mock = context.mock(ObjectProperty.class, "hasEdge");
		final ObjectProperty op_isEdgeOf_mock = context.mock(ObjectProperty.class, "isEdgeOf");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_NodeNetwork();
				will(returnValue(i_nodeNetwork_mock));

				oneOf(i_nodeNetwork_mock).addLabel("Node network", "en");

				oneOf(i_nodeNetwork_mock).addLabel("Сеть узлов", "ru");

				oneOf(nodeNetwork_mock).getNodes();
				will(returnValue(nodes));

				oneOf(nodeOWLSchema_mock).combine(node_mock);
				will(returnValue(i_node_mock));

				oneOf(owlModel_mock).getObjectProperty_hasNode();
				will(returnValue(op_hasNode_mock));

				oneOf(i_nodeNetwork_mock).addProperty(op_hasNode_mock, i_node_mock);

				oneOf(owlModel_mock).getObjectProperty_isNodeOf();
				will(returnValue(op_isNodeOf_mock));

				oneOf(i_node_mock).addProperty(op_isNodeOf_mock, i_nodeNetwork_mock);

				oneOf(nodeNetwork_mock).getEdges();
				will(returnValue(edges));

				oneOf(edgeOWLSchema_mock).combine(edge_mock);
				will(returnValue(i_edge_mock));

				oneOf(owlModel_mock).getObjectProperty_hasEdge();
				will(returnValue(op_hasEdge_mock));

				oneOf(i_nodeNetwork_mock).addProperty(op_hasEdge_mock, i_edge_mock);

				oneOf(owlModel_mock).getObjectProperty_isEdgeOf();
				will(returnValue(op_isEdgeOf_mock));

				oneOf(i_edge_mock).addProperty(op_isEdgeOf_mock, i_nodeNetwork_mock);
			}
		});

		assertEquals(i_nodeNetwork_mock, testable.combine(nodeNetwork_mock));
	}

	@Test
	public void parse() {
		final Node node_mock = context.mock(Node.class, "node");
		final Edge edge_mock = context.mock(Edge.class, "edge");
		final OntClass oc_nodeNetwork_mock = context.mock(OntClass.class, "oc-nodeNetwork");
		final OntClass oc_node_mock = context.mock(OntClass.class, "oc-node");
		final OntClass oc_edge_mock = context.mock(OntClass.class, "oc-edge");
		final ObjectProperty op_hasNode_mock = context.mock(ObjectProperty.class, "op-hasNode");
		final ObjectProperty op_hasEdge_mock = context.mock(ObjectProperty.class, "op-hasEdge");

		final Individual i_nodeNetwork_mock = context.mock(Individual.class, "i-nodeNetwork");
		final ExtendedIterator<Individual> nodeNetworkIterator = new NiceIterator<Individual>()
				.andThen(Arrays.asList(i_nodeNetwork_mock).iterator());

		final Individual i_node_mock = context.mock(Individual.class, "i-node");
		final ExtendedIterator<Individual> nodeIterator = new NiceIterator<Individual>()
				.andThen(Arrays.asList(i_node_mock).iterator());

		final Individual i_edge_mock = context.mock(Individual.class, "i-edge");
		final ExtendedIterator<Individual> edgeIterator = new NiceIterator<Individual>()
				.andThen(Arrays.asList(i_edge_mock).iterator());

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getClass_NodeNetwork();
				will(returnValue(oc_nodeNetwork_mock));

				oneOf(oc_nodeNetwork_mock).listInstances();
				will(returnValue(nodeNetworkIterator));

				oneOf(owlModel_mock).getClass_Node();
				will(returnValue(oc_node_mock));

				oneOf(oc_node_mock).listInstances();
				will(returnValue(nodeIterator));

				oneOf(owlModel_mock).getObjectProperty_hasNode();
				will(returnValue(op_hasNode_mock));

				oneOf(i_nodeNetwork_mock).hasProperty(op_hasNode_mock, i_node_mock);
				will(returnValue(true));

				oneOf(i_node_mock).asIndividual();
				will(returnValue(i_node_mock));

				oneOf(nodeOWLSchema_mock).parse(i_node_mock);
				will(returnValue(node_mock));

				oneOf(node_mock).isUnchecked();
				will(returnValue(false));

				oneOf(owlModel_mock).getClass_Edge();
				will(returnValue(oc_edge_mock));

				oneOf(oc_edge_mock).listInstances();
				will(returnValue(edgeIterator));

				oneOf(owlModel_mock).getObjectProperty_hasEdge();
				will(returnValue(op_hasEdge_mock));

				oneOf(i_nodeNetwork_mock).hasProperty(op_hasEdge_mock, i_edge_mock);
				will(returnValue(true));

				oneOf(i_edge_mock).asIndividual();
				will(returnValue(i_edge_mock));

				oneOf(edgeOWLSchema_mock).parse(i_edge_mock);
				will(returnValue(edge_mock));

				oneOf(edge_mock).getBeginNodeId();
				will(returnValue("node-id"));

				oneOf(edge_mock).getEndNodeId();
				will(returnValue("node-id"));

				exactly(2).of(node_mock).getId();
				will(returnValue("node-id"));
			}
		});

		NodeNetwork result = testable.parse(null);
		assertTrue(result.getNodes().contains(node_mock));
		assertTrue(result.getEdges().contains(edge_mock));
	}

	@Test
	public void combine_full() {
		final NodeNetworkOWLModel owlModel = new NodeNetworkOWLModel();
		final NodeNetworkOWLSchema owlSchema = new NodeNetworkOWLSchema(owlModel);

		final NodeNetwork nodeNetwork = new NodeNetwork();
		final System node1system = AssemblyLine.initialSystem();
		final System node2system = AssemblyLine.finalSystem();
		final Action action = new Action("test-action");
		final Map<String, String> actionParameters = new HashMap<>();
		actionParameters.put("test-parameter-key", "test-parameter-value");
		final SystemOperation systemOperation = new SystemOperation(action, actionParameters);
		final Node node1 = nodeNetwork.createNode(node1system);
		final Node node2 = nodeNetwork.createNode(node2system);
		nodeNetwork.createEdge(node1, node2, systemOperation);

		owlModel.createOntologyModel();
		owlSchema.combine(nodeNetwork);
		OntModel model = owlModel.getOntologyModel();
		assertNotNull(model);
		assertEquals(277, model.listObjects().toList().size());
		assertEquals(1530, model.listStatements().toList().size());

//		 TODO (2021-03-13 #31): удалить
//		model.write(java.lang.System.out, "RDF/XML");
	}

	@Test
	public void parse_full() {
		final NodeNetworkOWLModel owlModel = new NodeNetworkOWLModel();
		final NodeNetworkOWLSchema owlSchema = new NodeNetworkOWLSchema(owlModel);

		final NodeNetwork nodeNetwork = new NodeNetwork();
		final System node1system = AssemblyLine.initialSystem();
		final System node2system = AssemblyLine.finalSystem();
		final Action action = new Action("test-action");
		final Map<String, String> actionParameters = new HashMap<>();
		actionParameters.put("test-parameter-key", "test-parameter-value");
		final SystemOperation systemOperation = new SystemOperation(action, actionParameters);
		final Node node1 = nodeNetwork.createNode(node1system);
		final Node node2 = nodeNetwork.createNode(node2system);
		nodeNetwork.createEdge(node1, node2, systemOperation);

		owlModel.createOntologyModel();
		Individual ind_nodeNetwork = owlSchema.combine(nodeNetwork);

		owlSchema.parse(ind_nodeNetwork);
	}
}

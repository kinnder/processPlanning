package application.storage.owl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.jena.ontology.Individual;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class NodeNetworkOWLModelTest {

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

	NodeNetworkOWLModel testable;

	@BeforeEach
	public void setup() {
		testable = new NodeNetworkOWLModel();
	}

	@Test
	public void getOWLSchema() {
		assertTrue(testable.getOWLSchema() instanceof NodeNetworkOWLSchema);
	}

	@Test
	public void createOntologyModel() {
		testable.createOntologyModel();

		assertEquals(NodeNetworkOWLModel.URI_Attribute, testable.getClass_Attribute().getURI());
		assertEquals(NodeNetworkOWLModel.URI_Edge, testable.getClass_Edge().getURI());
		assertEquals(NodeNetworkOWLModel.URI_Link, testable.getClass_Link().getURI());
		assertEquals(NodeNetworkOWLModel.URI_Node, testable.getClass_Node().getURI());
		assertEquals(NodeNetworkOWLModel.URI_NodeNetwork, testable.getClass_NodeNetwork().getURI());
		assertEquals(NodeNetworkOWLModel.URI_Parameter, testable.getClass_Parameter().getURI());
		assertEquals(NodeNetworkOWLModel.URI_Process, testable.getClass_Process().getURI());
		assertEquals(NodeNetworkOWLModel.URI_System, testable.getClass_System().getURI());
		assertEquals(NodeNetworkOWLModel.URI_SystemObject, testable.getClass_SystemObject().getURI());
		assertEquals(NodeNetworkOWLModel.URI_SystemOperation, testable.getClass_SystemOperation().getURI());

		assertEquals(NodeNetworkOWLModel.URI_beginNodeId, testable.getDataProperty_beginNodeId().getURI());
		assertEquals(NodeNetworkOWLModel.URI_checked, testable.getDataProperty_checked().getURI());
		assertEquals(NodeNetworkOWLModel.URI_endNodeId, testable.getDataProperty_endNodeId().getURI());
		assertEquals(NodeNetworkOWLModel.URI_id, testable.getDataProperty_id().getURI());
		assertEquals(NodeNetworkOWLModel.URI_key, testable.getDataProperty_key().getURI());
		assertEquals(NodeNetworkOWLModel.URI_name, testable.getDataProperty_name().getURI());
		assertEquals(NodeNetworkOWLModel.URI_objectId1, testable.getDataProperty_objectId1().getURI());
		assertEquals(NodeNetworkOWLModel.URI_objectId2, testable.getDataProperty_objectId2().getURI());
		assertEquals(NodeNetworkOWLModel.URI_value, testable.getDataProperty_value().getURI());

		assertEquals(NodeNetworkOWLModel.URI_hasAttribute, testable.getObjectProperty_hasAttribute().getURI());
		assertEquals(NodeNetworkOWLModel.URI_hasEdge, testable.getObjectProperty_hasEdge().getURI());
		assertEquals(NodeNetworkOWLModel.URI_hasLink, testable.getObjectProperty_hasLink().getURI());
		assertEquals(NodeNetworkOWLModel.URI_hasNode, testable.getObjectProperty_hasNode().getURI());
		assertEquals(NodeNetworkOWLModel.URI_hasParameter, testable.getObjectProperty_hasParameter().getURI());
		assertEquals(NodeNetworkOWLModel.URI_hasSystem, testable.getObjectProperty_hasSystem().getURI());
		assertEquals(NodeNetworkOWLModel.URI_hasSystemObject, testable.getObjectProperty_hasSystemObject().getURI());
		assertEquals(NodeNetworkOWLModel.URI_hasSystemObject1, testable.getObjectProperty_hasSystemObject1().getURI());
		assertEquals(NodeNetworkOWLModel.URI_hasSystemObject2, testable.getObjectProperty_hasSystemObject2().getURI());
		assertEquals(NodeNetworkOWLModel.URI_hasSystemOperation, testable.getObjectProperty_hasSystemOperation().getURI());
		assertEquals(NodeNetworkOWLModel.URI_hasBeginNode, testable.getObjectProperty_hasBeginNode().getURI());
		assertEquals(NodeNetworkOWLModel.URI_hasEndNode, testable.getObjectProperty_hasEndNode().getURI());
		assertEquals(NodeNetworkOWLModel.URI_isAttributeOf, testable.getObjectProperty_isAttributeOf().getURI());
		assertEquals(NodeNetworkOWLModel.URI_isEdgeOf, testable.getObjectProperty_isEdgeOf().getURI());
		assertEquals(NodeNetworkOWLModel.URI_isLinkOf, testable.getObjectProperty_isLinkOf().getURI());
		assertEquals(NodeNetworkOWLModel.URI_isNodeOf, testable.getObjectProperty_isNodeOf().getURI());
		assertEquals(NodeNetworkOWLModel.URI_isParameterOf, testable.getObjectProperty_isParameterOf().getURI());
		assertEquals(NodeNetworkOWLModel.URI_isSystemObjectOf, testable.getObjectProperty_isSystemObjectOf().getURI());
		assertEquals(NodeNetworkOWLModel.URI_isSystemObject1Of, testable.getObjectProperty_isSystemObject1Of().getURI());
		assertEquals(NodeNetworkOWLModel.URI_isSystemObject2Of, testable.getObjectProperty_isSystemObject2Of().getURI());
		assertEquals(NodeNetworkOWLModel.URI_isSystemOf, testable.getObjectProperty_isSystemOf().getURI());
		assertEquals(NodeNetworkOWLModel.URI_isSystemOperationOf, testable.getObjectProperty_isSystemOperationOf().getURI());
		assertEquals(NodeNetworkOWLModel.URI_isBeginNodeOf, testable.getObjectProperty_isBeginNodeOf().getURI());
		assertEquals(NodeNetworkOWLModel.URI_isEndNodeOf, testable.getObjectProperty_isEndNodeOf().getURI());
	}

	@Test
	public void connectOntologyModel() {
		final NodeNetworkOWLModel newOwlModel = new NodeNetworkOWLModel();
		newOwlModel.createOntologyModel();

		testable.connectOntologyModel(newOwlModel.getOntologyModel());

		assertEquals(NodeNetworkOWLModel.URI_Attribute, testable.getClass_Attribute().getURI());
		assertEquals(NodeNetworkOWLModel.URI_Edge, testable.getClass_Edge().getURI());
		assertEquals(NodeNetworkOWLModel.URI_Link, testable.getClass_Link().getURI());
		assertEquals(NodeNetworkOWLModel.URI_Node, testable.getClass_Node().getURI());
		assertEquals(NodeNetworkOWLModel.URI_NodeNetwork, testable.getClass_NodeNetwork().getURI());
		assertEquals(NodeNetworkOWLModel.URI_Parameter, testable.getClass_Parameter().getURI());
		assertEquals(NodeNetworkOWLModel.URI_Process, testable.getClass_Process().getURI());
		assertEquals(NodeNetworkOWLModel.URI_System, testable.getClass_System().getURI());
		assertEquals(NodeNetworkOWLModel.URI_SystemObject, testable.getClass_SystemObject().getURI());
		assertEquals(NodeNetworkOWLModel.URI_SystemOperation, testable.getClass_SystemOperation().getURI());

		assertEquals(NodeNetworkOWLModel.URI_beginNodeId, testable.getDataProperty_beginNodeId().getURI());
		assertEquals(NodeNetworkOWLModel.URI_checked, testable.getDataProperty_checked().getURI());
		assertEquals(NodeNetworkOWLModel.URI_endNodeId, testable.getDataProperty_endNodeId().getURI());
		assertEquals(NodeNetworkOWLModel.URI_id, testable.getDataProperty_id().getURI());
		assertEquals(NodeNetworkOWLModel.URI_key, testable.getDataProperty_key().getURI());
		assertEquals(NodeNetworkOWLModel.URI_name, testable.getDataProperty_name().getURI());
		assertEquals(NodeNetworkOWLModel.URI_objectId1, testable.getDataProperty_objectId1().getURI());
		assertEquals(NodeNetworkOWLModel.URI_objectId2, testable.getDataProperty_objectId2().getURI());
		assertEquals(NodeNetworkOWLModel.URI_value, testable.getDataProperty_value().getURI());

		assertEquals(NodeNetworkOWLModel.URI_hasAttribute, testable.getObjectProperty_hasAttribute().getURI());
		assertEquals(NodeNetworkOWLModel.URI_hasEdge, testable.getObjectProperty_hasEdge().getURI());
		assertEquals(NodeNetworkOWLModel.URI_hasLink, testable.getObjectProperty_hasLink().getURI());
		assertEquals(NodeNetworkOWLModel.URI_hasNode, testable.getObjectProperty_hasNode().getURI());
		assertEquals(NodeNetworkOWLModel.URI_hasParameter, testable.getObjectProperty_hasParameter().getURI());
		assertEquals(NodeNetworkOWLModel.URI_hasSystem, testable.getObjectProperty_hasSystem().getURI());
		assertEquals(NodeNetworkOWLModel.URI_hasSystemObject, testable.getObjectProperty_hasSystemObject().getURI());
		assertEquals(NodeNetworkOWLModel.URI_hasSystemObject1, testable.getObjectProperty_hasSystemObject1().getURI());
		assertEquals(NodeNetworkOWLModel.URI_hasSystemObject2, testable.getObjectProperty_hasSystemObject2().getURI());
		assertEquals(NodeNetworkOWLModel.URI_hasSystemOperation, testable.getObjectProperty_hasSystemOperation().getURI());
		assertEquals(NodeNetworkOWLModel.URI_hasBeginNode, testable.getObjectProperty_hasBeginNode().getURI());
		assertEquals(NodeNetworkOWLModel.URI_hasEndNode, testable.getObjectProperty_hasEndNode().getURI());
		assertEquals(NodeNetworkOWLModel.URI_isAttributeOf, testable.getObjectProperty_isAttributeOf().getURI());
		assertEquals(NodeNetworkOWLModel.URI_isEdgeOf, testable.getObjectProperty_isEdgeOf().getURI());
		assertEquals(NodeNetworkOWLModel.URI_isLinkOf, testable.getObjectProperty_isLinkOf().getURI());
		assertEquals(NodeNetworkOWLModel.URI_isNodeOf, testable.getObjectProperty_isNodeOf().getURI());
		assertEquals(NodeNetworkOWLModel.URI_isParameterOf, testable.getObjectProperty_isParameterOf().getURI());
		assertEquals(NodeNetworkOWLModel.URI_isSystemObjectOf, testable.getObjectProperty_isSystemObjectOf().getURI());
		assertEquals(NodeNetworkOWLModel.URI_isSystemObject1Of, testable.getObjectProperty_isSystemObject1Of().getURI());
		assertEquals(NodeNetworkOWLModel.URI_isSystemObject2Of, testable.getObjectProperty_isSystemObject2Of().getURI());
		assertEquals(NodeNetworkOWLModel.URI_isSystemOf, testable.getObjectProperty_isSystemOf().getURI());
		assertEquals(NodeNetworkOWLModel.URI_isSystemOperationOf, testable.getObjectProperty_isSystemOperationOf().getURI());
		assertEquals(NodeNetworkOWLModel.URI_isBeginNodeOf, testable.getObjectProperty_isBeginNodeOf().getURI());
		assertEquals(NodeNetworkOWLModel.URI_isEndNodeOf, testable.getObjectProperty_isEndNodeOf().getURI());
	}

	@Test
	public void getUniqueURI() {
		assertNotEquals(testable.getUniqueURI(), testable.getUniqueURI());
	}

	@Test
	public void getUniqueURI_startsWith_NameSpace() {
		assertTrue(testable.getUniqueURI().startsWith(NodeNetworkOWLModel.NS + "#"));
	}

	@Test
	public void newIndividual() {
		testable.createOntologyModel();

		Individual attribute = testable.newIndividual_Attribute();
		assertEquals(attribute.getOntClass(), testable.getClass_Attribute());
		assertNotEquals(attribute.getURI(), testable.newIndividual_Attribute().getURI());

		Individual edge = testable.newIndividual_Edge();
		assertEquals(edge.getOntClass(), testable.getClass_Edge());
		assertNotEquals(edge.getURI(), testable.newIndividual_Edge().getURI());

		Individual link = testable.newIndividual_Link();
		assertEquals(link.getOntClass(), testable.getClass_Link());
		assertNotEquals(link.getURI(), testable.newIndividual_Link().getURI());

		Individual node = testable.newIndividual_Node();
		assertEquals(node.getOntClass(), testable.getClass_Node());
		assertNotEquals(node.getURI(), testable.newIndividual_Node().getURI());

		Individual nodeNetwork = testable.newIndividual_NodeNetwork();
		assertEquals(nodeNetwork.getOntClass(), testable.getClass_NodeNetwork());
		assertNotEquals(nodeNetwork.getURI(), testable.newIndividual_NodeNetwork().getURI());

		Individual parameter = testable.newIndividual_Parameter();
		assertEquals(parameter.getOntClass(), testable.getClass_Parameter());
		assertNotEquals(parameter.getURI(), testable.newIndividual_Parameter().getURI());

		Individual process = testable.newIndividual_Process();
		assertEquals(process.getOntClass(), testable.getClass_Process());
		assertNotEquals(process.getURI(), testable.newIndividual_Process().getURI());

		Individual system = testable.newIndividual_System();
		assertEquals(system.getOntClass(), testable.getClass_System());
		assertNotEquals(system.getURI(), testable.newIndividual_System().getURI());

		Individual systemObject = testable.newIndividual_SystemObject();
		assertEquals(systemObject.getOntClass(), testable.getClass_SystemObject());
		assertNotEquals(systemObject.getURI(), testable.newIndividual_SystemObject().getURI());

		Individual systemOperation = testable.newIndividual_SystemOperation();
		assertEquals(systemOperation.getOntClass(), testable.getClass_SystemOperation());
		assertNotEquals(systemOperation.getURI(), testable.newIndividual_SystemOperation().getURI());
	}
}

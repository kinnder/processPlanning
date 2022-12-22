package planning.method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.jgrapht.GraphPath;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.System;
import planning.model.SystemOperation;

public class NodeNetworkTest {

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

	NodeNetwork testable;

	@BeforeEach
	public void setup() {
		testable = new NodeNetwork();
	}

	@Test
	public void createNode() {
		final System system_mock = context.mock(System.class);

		Node node = testable.createNode(system_mock);
		assertNotNull(node);
		assertEquals(system_mock, node.getSystem());
	}

	@Test
	public void createEdge() {
		final System beginSystem_mock = context.mock(System.class, "begin-system");
		final System endSystem_mock = context.mock(System.class, "end-system");

		final Node beginNode = testable.createNode(beginSystem_mock);
		final Node endNode = testable.createNode(endSystem_mock);
		final SystemOperation systemOperation_mock = context.mock(SystemOperation.class);

		testable.createEdge(beginNode, endNode, systemOperation_mock);
	}

	@Test
	public void hasNextUncheckedNode() {
		assertFalse(testable.hasNextUncheckedNode());

		final System system_mock = context.mock(System.class);
		testable.createNode(system_mock);

		assertTrue(testable.hasNextUncheckedNode());
	}

	@Test
	public void nextUncheckedNode() {
		final System system_mock = context.mock(System.class);
		Node node = testable.createNode(system_mock);

		assertEquals(node, testable.nextUncheckedNode());
		assertTrue(node.getChecked());
	}

	@Test
	public void getShortestPath() {
		final System system_1_mock = context.mock(System.class, "system-1");
		final System system_2_mock = context.mock(System.class, "system-2");

		final Node beginNode = testable.createNode(system_1_mock);
		final Node endNode = testable.createNode(system_2_mock);
		final SystemOperation systemOperation_mock = context.mock(SystemOperation.class);

		testable.createEdge(beginNode, endNode, systemOperation_mock);

		GraphPath<Node, Edge> path = testable.getShortestPath(beginNode, endNode);
		assertNotNull(path);
	}

	@Test
	public void findNode() {
		final System system_1_mock = context.mock(System.class, "system-1");
		final System system_2_mock = context.mock(System.class, "system-2");

		testable.createNode(system_1_mock);
		Node node_2 = testable.createNode(system_2_mock);

		assertEquals(node_2, testable.findNode(system_2_mock));
	}

	@Test
	public void findNode_not_found() {
		final System system_mock = context.mock(System.class);

		assertNull(testable.findNode(system_mock));
	}

	@Test
	public void findNodeById() {
		final System system_1_mock = context.mock(System.class, "system-1");
		final System system_2_mock = context.mock(System.class, "system-2");

		testable.createNode(system_1_mock);
		Node node_2 = testable.createNode(system_2_mock);

		assertEquals(node_2, testable.findNodeById(node_2.getId()));
	}

	@Test
	public void findNodeById_not_found() {
		final System system_1_mock = context.mock(System.class, "system-1");

		testable.createNode(system_1_mock);

		assertNull(testable.findNodeById("wrong-id"));
	}

	@Test
	public void getNodes() {
		assertTrue(testable.getNodes() instanceof Collection);
	}

	@Test
	public void getEdges() {
		assertTrue(testable.getEdges() instanceof Collection);
	}

	@Test
	public void addNode() {
		final System system_mock = context.mock(System.class, "system-1");
		final Node node = new Node(system_mock);

		testable.addNode(node);

		assertTrue(testable.hasNextUncheckedNode());
		assertEquals(node, testable.nextUncheckedNode());
	}

	@Test
	public void addNode_checked() {
		final System system_mock = context.mock(System.class, "system-1");
		final Node node = new Node(system_mock);
		node.setChecked(true);

		testable.addNode(node);

		assertFalse(testable.hasNextUncheckedNode());
	}

	@Test
	public void addEdge() {
		final System system_1_mock = context.mock(System.class, "system-1");
		final System system_2_mock = context.mock(System.class, "system-2");
		final Node node_1 = new Node(system_1_mock);
		final Node node_2 = new Node(system_2_mock);
		final SystemOperation systemOperation_mock = context.mock(SystemOperation.class);
		final Edge edge = new Edge(node_1.getId(), node_2.getId(), systemOperation_mock);

		testable.addNode(node_1);
		testable.addNode(node_2);

		testable.addEdge(edge);
	}

	@Test
	public void toString_test() {
		assertEquals("NodeNetwork", testable.toString());
	}
}

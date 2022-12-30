package application.ui.gui.editor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.method.Edge;
import planning.method.Node;

public class EdgesDataModelTest {

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

	EditorDataModel editorDataModel_mock;

	EdgesDataModel testable;

	@BeforeEach
	public void setup() {
		editorDataModel_mock = context.mock(EditorDataModel.class);

		testable = new EdgesDataModel(editorDataModel_mock);
	}

	@Test
	public void newInstance() {
		assertEquals(0, testable.getRowCount());
	}

	@Test
	public void clear() {
		testable.clear();
	}

	@Test
	public void getColumnClass() {
		assertEquals(String.class, testable.getColumnClass(EdgesDataModel.COLUMN_IDX_END_NODE_ID));
		assertEquals(String.class, testable.getColumnClass(EdgesDataModel.COLUMN_IDX_ID));
	}

	@Test
	public void getColumnName() {
		assertEquals("endNodeId", testable.getColumnName(EdgesDataModel.COLUMN_IDX_END_NODE_ID));
		assertEquals("id", testable.getColumnName(EdgesDataModel.COLUMN_IDX_ID));
	}

	@Test
	public void loadEdges() {
		final Node selectedObject_mock = context.mock(Node.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Edge> edges = new ArrayList<Edge>();
		final Edge edge_1_mock = context.mock(Edge.class, "edge-1");
		final Edge edge_2_mock = context.mock(Edge.class, "edge-2");
		edges.add(edge_1_mock);
		edges.add(edge_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(editorDataModel_mock).selectEdges(selectedObject_mock);
				will(returnValue(edges));
			}
		});

		testable.loadEdges(selectedObject_mock, selectedNode_mock);
		assertEquals(2, testable.getRowCount());
	}

	@Test
	public void setValueAt_id() {
		final Node selectedObject_mock = context.mock(Node.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Edge> edges = new ArrayList<Edge>();
		final Edge edge_1_mock = context.mock(Edge.class, "edge-1");
		final Edge edge_2_mock = context.mock(Edge.class, "edge-2");
		edges.add(edge_1_mock);
		edges.add(edge_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(editorDataModel_mock).selectEdges(selectedObject_mock);
				will(returnValue(edges));
			}
		});
		testable.loadEdges(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(edge_1_mock).setId("value");

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("value", 0, EdgesDataModel.COLUMN_IDX_ID);
	}

	@Test
	public void setValueAt_endNodeId() {
		final Node selectedObject_mock = context.mock(Node.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Edge> edges = new ArrayList<Edge>();
		final Edge edge_1_mock = context.mock(Edge.class, "edge-1");
		final Edge edge_2_mock = context.mock(Edge.class, "edge-2");
		edges.add(edge_1_mock);
		edges.add(edge_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(editorDataModel_mock).selectEdges(selectedObject_mock);
				will(returnValue(edges));
			}
		});
		testable.loadEdges(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(edge_1_mock).setEndNodeId("value");

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("value", 0, EdgesDataModel.COLUMN_IDX_END_NODE_ID);
	}

	@Test
	public void setValueAt() {
		final Node selectedObject_mock = context.mock(Node.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Edge> edges = new ArrayList<Edge>();
		final Edge edge_1_mock = context.mock(Edge.class, "edge-1");
		final Edge edge_2_mock = context.mock(Edge.class, "edge-2");
		edges.add(edge_1_mock);
		edges.add(edge_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(editorDataModel_mock).selectEdges(selectedObject_mock);
				will(returnValue(edges));
			}
		});
		testable.loadEdges(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("value", 0, -1);
	}

	@Test
	public void getValueAt_id() {
		final Node selectedObject_mock = context.mock(Node.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Edge> edges = new ArrayList<Edge>();
		final Edge edge_1_mock = context.mock(Edge.class, "edge-1");
		final Edge edge_2_mock = context.mock(Edge.class, "edge-2");
		edges.add(edge_1_mock);
		edges.add(edge_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(editorDataModel_mock).selectEdges(selectedObject_mock);
				will(returnValue(edges));
			}
		});
		testable.loadEdges(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(edge_1_mock).getId();
				will(returnValue("value"));
			}
		});

		assertEquals("value", testable.getValueAt(0, EdgesDataModel.COLUMN_IDX_ID));
	}

	@Test
	public void getValueAt_endNodeId() {
		final Node selectedObject_mock = context.mock(Node.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Edge> edges = new ArrayList<Edge>();
		final Edge edge_1_mock = context.mock(Edge.class, "edge-1");
		final Edge edge_2_mock = context.mock(Edge.class, "edge-2");
		edges.add(edge_1_mock);
		edges.add(edge_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(editorDataModel_mock).selectEdges(selectedObject_mock);
				will(returnValue(edges));
			}
		});
		testable.loadEdges(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(edge_1_mock).getEndNodeId();
				will(returnValue("value"));
			}
		});

		assertEquals("value", testable.getValueAt(0, EdgesDataModel.COLUMN_IDX_END_NODE_ID));
	}

	@Test
	public void getValueAt() {
		final Node selectedObject_mock = context.mock(Node.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Edge> edges = new ArrayList<Edge>();
		final Edge edge_1_mock = context.mock(Edge.class, "edge-1");
		final Edge edge_2_mock = context.mock(Edge.class, "edge-2");
		edges.add(edge_1_mock);
		edges.add(edge_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(editorDataModel_mock).selectEdges(selectedObject_mock);
				will(returnValue(edges));
			}
		});
		testable.loadEdges(selectedObject_mock, selectedNode_mock);

		assertEquals(null, testable.getValueAt(0, -1));
	}
}

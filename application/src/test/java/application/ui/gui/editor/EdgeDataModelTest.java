package application.ui.gui.editor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.method.Edge;

public class EdgeDataModelTest {

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

	JTextField jtfEdgeId_mock;

	JTextField jtfBeginNodeId_mock;

	JTextField jtfEndNodeId_mock;

	EditorDataModel editorDataModel_mock;

	EdgeDataModel testable;

	@BeforeEach
	public void setup() {
		jtfEdgeId_mock = context.mock(JTextField.class, "edgeId");
		jtfBeginNodeId_mock = context.mock(JTextField.class, "beginNodeId");
		jtfEndNodeId_mock = context.mock(JTextField.class, "endNodeId");
		editorDataModel_mock = context.mock(EditorDataModel.class);

		context.checking(new Expectations() {
			{
				oneOf(jtfEdgeId_mock).addKeyListener(with(any(KeyListener.class)));

				oneOf(jtfBeginNodeId_mock).addKeyListener(with(any(KeyListener.class)));

				oneOf(jtfEndNodeId_mock).addKeyListener(with(any(KeyListener.class)));
			}
		});

		testable = new EdgeDataModel(jtfEdgeId_mock, jtfBeginNodeId_mock, jtfEndNodeId_mock, editorDataModel_mock);
	}

	@Test
	public void clear() {
		testable.clear();
	}

	@Test
	public void loadEdge() {
		final Edge selectedObject_mock = context.mock(Edge.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getId();
				will(returnValue("edge-id"));

				oneOf(selectedObject_mock).getBeginNodeId();
				will(returnValue("begin-node-id"));

				oneOf(selectedObject_mock).getEndNodeId();
				will(returnValue("end-node-id"));

				oneOf(jtfEdgeId_mock).setText("edge-id");

				oneOf(jtfBeginNodeId_mock).setText("begin-node-id");

				oneOf(jtfEndNodeId_mock).setText("end-node-id");
			}
		});

		testable.loadEdge(selectedObject_mock, selectedNode_mock);
	}

	@Test
	public void edgeId_keyReleased() {
		final Edge selectedObject_mock = context.mock(Edge.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getId();
				will(returnValue("edge-id"));

				oneOf(selectedObject_mock).getBeginNodeId();
				will(returnValue("begin-node-id"));

				oneOf(selectedObject_mock).getEndNodeId();
				will(returnValue("end-node-id"));

				oneOf(jtfEdgeId_mock).setText("edge-id");

				oneOf(jtfBeginNodeId_mock).setText("begin-node-id");

				oneOf(jtfEndNodeId_mock).setText("end-node-id");
			}
		});
		testable.loadEdge(selectedObject_mock, selectedNode_mock);

		final KeyEvent keyEvent_mock = context.mock(KeyEvent.class);
		context.checking(new Expectations() {
			{
				oneOf(jtfEdgeId_mock).getText();
				will(returnValue("new-edge-id"));

				oneOf(selectedObject_mock).setId("new-edge-id");

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.jtfEdgeIdKeyAdapter.keyReleased(keyEvent_mock);
	}

	@Test
	public void beginNodeId_keyReleased() {
		final Edge selectedObject_mock = context.mock(Edge.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getId();
				will(returnValue("edge-id"));

				oneOf(selectedObject_mock).getBeginNodeId();
				will(returnValue("begin-node-id"));

				oneOf(selectedObject_mock).getEndNodeId();
				will(returnValue("end-node-id"));

				oneOf(jtfEdgeId_mock).setText("edge-id");

				oneOf(jtfBeginNodeId_mock).setText("begin-node-id");

				oneOf(jtfEndNodeId_mock).setText("end-node-id");
			}
		});
		testable.loadEdge(selectedObject_mock, selectedNode_mock);

		final KeyEvent keyEvent_mock = context.mock(KeyEvent.class);
		context.checking(new Expectations() {
			{
				oneOf(jtfBeginNodeId_mock).getText();
				will(returnValue("new-begin-node-id"));

				oneOf(selectedObject_mock).setBeginNodeId("new-begin-node-id");

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.jtfBeginNodeIdKeyAdapter.keyReleased(keyEvent_mock);
	}

	@Test
	public void endNodeId_keyReleased() {
		final Edge selectedObject_mock = context.mock(Edge.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getId();
				will(returnValue("edge-id"));

				oneOf(selectedObject_mock).getBeginNodeId();
				will(returnValue("begin-node-id"));

				oneOf(selectedObject_mock).getEndNodeId();
				will(returnValue("end-node-id"));

				oneOf(jtfEdgeId_mock).setText("edge-id");

				oneOf(jtfBeginNodeId_mock).setText("begin-node-id");

				oneOf(jtfEndNodeId_mock).setText("end-node-id");
			}
		});
		testable.loadEdge(selectedObject_mock, selectedNode_mock);

		final KeyEvent keyEvent_mock = context.mock(KeyEvent.class);
		context.checking(new Expectations() {
			{
				oneOf(jtfEndNodeId_mock).getText();
				will(returnValue("new-end-node-id"));

				oneOf(selectedObject_mock).setEndNodeId("new-end-node-id");

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.jtfEndNodeIdKeyAdapter.keyReleased(keyEvent_mock);
	}
}

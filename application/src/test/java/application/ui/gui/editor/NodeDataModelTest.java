package application.ui.gui.editor;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.method.Node;

public class NodeDataModelTest {

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

	JTextField jtfNodeId_mock;

	JCheckBox jcbNodeChecked_mock;

	EditorDataModel editorDataModel_mock;

	NodeDataModel testable;

	@BeforeEach
	public void setup() {
		jtfNodeId_mock = context.mock(JTextField.class);
		jcbNodeChecked_mock = context.mock(JCheckBox.class);
		editorDataModel_mock = context.mock(EditorDataModel.class);

		context.checking(new Expectations() {
			{
				oneOf(jtfNodeId_mock).addKeyListener(with(any(KeyListener.class)));

				oneOf(jcbNodeChecked_mock).addItemListener(with(any(ItemListener.class)));
			}
		});

		testable = new NodeDataModel(jtfNodeId_mock, jcbNodeChecked_mock, editorDataModel_mock);
	}

	@Test
	public void clear() {
		testable.clear();
	}

	@Test
	public void loadNode() {
		final Node selectedObject_mock = context.mock(Node.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getId();
				will(returnValue("node-id"));

				oneOf(selectedObject_mock).getChecked();
				will(returnValue(true));

				oneOf(jtfNodeId_mock).setText("node-id");

				oneOf(jcbNodeChecked_mock).setSelected(true);
			}
		});

		testable.loadNode(selectedObject_mock, selectedNode_mock);
	}

	@Test
	public void nodeId_keyReleased() {
		final Node selectedObject_mock = context.mock(Node.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getId();
				will(returnValue("node-id"));

				oneOf(selectedObject_mock).getChecked();
				will(returnValue(true));

				oneOf(jtfNodeId_mock).setText("node-id");

				oneOf(jcbNodeChecked_mock).setSelected(true);
			}
		});
		testable.loadNode(selectedObject_mock, selectedNode_mock);

		final KeyEvent keyEvent_mock = context.mock(KeyEvent.class);
		context.checking(new Expectations() {
			{
				oneOf(jtfNodeId_mock).getText();
				will(returnValue("new-node-id"));

				oneOf(selectedObject_mock).setId("new-node-id");

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.jtfNodeIdeKeyAdapter.keyReleased(keyEvent_mock);
	}

	@Test
	public void nodeChecked_itemStateChanged_SELECTED() {
		final Node selectedObject_mock = context.mock(Node.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getId();
				will(returnValue("node-id"));

				oneOf(selectedObject_mock).getChecked();
				will(returnValue(true));

				oneOf(jtfNodeId_mock).setText("node-id");

				oneOf(jcbNodeChecked_mock).setSelected(true);
			}
		});
		testable.loadNode(selectedObject_mock, selectedNode_mock);

		final ItemEvent itemEvent_mock = context.mock(ItemEvent.class);
		context.checking(new Expectations() {
			{
				oneOf(itemEvent_mock).getStateChange();
				will(returnValue(ItemEvent.SELECTED));

				oneOf(selectedObject_mock).setChecked(true);

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.jcbNodeCheckedItemListener.itemStateChanged(itemEvent_mock);
	}

	@Test
	public void nodeChecked_itemStateChanged_DESELECTED() {
		final Node selectedObject_mock = context.mock(Node.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getId();
				will(returnValue("node-id"));

				oneOf(selectedObject_mock).getChecked();
				will(returnValue(true));

				oneOf(jtfNodeId_mock).setText("node-id");

				oneOf(jcbNodeChecked_mock).setSelected(true);
			}
		});
		testable.loadNode(selectedObject_mock, selectedNode_mock);

		final ItemEvent itemEvent_mock = context.mock(ItemEvent.class);
		context.checking(new Expectations() {
			{
				oneOf(itemEvent_mock).getStateChange();
				will(returnValue(ItemEvent.DESELECTED));

				oneOf(selectedObject_mock).setChecked(false);

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.jcbNodeCheckedItemListener.itemStateChanged(itemEvent_mock);
	}
}

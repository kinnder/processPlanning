package application.ui.gui.editor;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import planning.method.Node;

public class NodeDataModel {

	private JTextField jtfNodeId;

	private JCheckBox jcbNodeChecked;

	private EditorDataModel editorDataModel;

	ItemListener jcbNodeCheckedItemListener = new ItemListener() {
		@Override
		public void itemStateChanged(ItemEvent e) {
			final Boolean checked = e.getStateChange() == ItemEvent.SELECTED;
			node.setChecked(checked);
			editorDataModel.nodeChanged(nodeNode);
		}
	};

	KeyAdapter jtfNodeIdeKeyAdapter = new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
			final String nodeId = jtfNodeId.getText();
			node.setId(nodeId);
			editorDataModel.nodeChanged(nodeNode);
		};
	};

	public NodeDataModel(JTextField jtfNodeId, JCheckBox jcbNodeChecked, EditorDataModel editorDataModel) {
		this.jtfNodeId = jtfNodeId;
		this.jcbNodeChecked = jcbNodeChecked;
		this.editorDataModel = editorDataModel;

		jtfNodeId.addKeyListener(jtfNodeIdeKeyAdapter);
		jcbNodeChecked.addItemListener(jcbNodeCheckedItemListener);
	}

	public void clear() {
		node = null;
		nodeNode = null;
	}

	private Node node;

	private DefaultMutableTreeNode nodeNode;

	public void loadNode(Node selectedObject, DefaultMutableTreeNode selectedNode) {
		this.node = selectedObject;
		this.nodeNode = selectedNode;

		final String nodeId = node.getId();
		final Boolean checked = node.getChecked();
		jtfNodeId.setText(nodeId);
		jcbNodeChecked.setSelected(checked);
	}
}

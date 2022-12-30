package application.ui.gui.editor;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import planning.method.Edge;

public class EdgeDataModel {

	private JTextField jtfEdgeId;

	private JTextField jtfBeginNodeId;

	private JTextField jtfEndNodeId;

	private EditorDataModel editorDataModel;

	KeyAdapter jtfEdgeIdKeyAdapter = new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
			String edgeId = jtfEdgeId.getText();
			edge.setId(edgeId);
			editorDataModel.nodeChanged(edgeNode);
		};
	};

	KeyAdapter jtfBeginNodeIdKeyAdapter = new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
			String beginNodeId = jtfBeginNodeId.getText();
			edge.setBeginNodeId(beginNodeId);
			editorDataModel.nodeChanged(edgeNode);
		};
	};

	KeyAdapter jtfEndNodeIdKeyAdapter = new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
			String endNodeId = jtfEndNodeId.getText();
			edge.setEndNodeId(endNodeId);
			editorDataModel.nodeChanged(edgeNode);
		};
	};

	public EdgeDataModel(JTextField jtfEdgeId, JTextField jtfBeginNodeId, JTextField jtfEndNodeId,
			EditorDataModel editorDataModel) {
		this.jtfEdgeId = jtfEdgeId;
		this.jtfBeginNodeId = jtfBeginNodeId;
		this.jtfEndNodeId = jtfEndNodeId;
		this.editorDataModel = editorDataModel;

		jtfEdgeId.addKeyListener(jtfEdgeIdKeyAdapter);
		jtfBeginNodeId.addKeyListener(jtfBeginNodeIdKeyAdapter);
		jtfEndNodeId.addKeyListener(jtfEndNodeIdKeyAdapter);
	}

	public void clear() {
		this.edge = null;
		this.edgeNode = null;
	}

	private Edge edge;

	private DefaultMutableTreeNode edgeNode;

	public void loadEdge(Edge selectedObject, DefaultMutableTreeNode selectedNode) {
		this.edge = selectedObject;
		this.edgeNode = selectedNode;

		String edgeId = edge.getId();
		String beginNodeId = edge.getBeginNodeId();
		String endNodeId = edge.getEndNodeId();

		jtfEdgeId.setText(edgeId);
		jtfBeginNodeId.setText(beginNodeId);
		jtfEndNodeId.setText(endNodeId);
	}
}

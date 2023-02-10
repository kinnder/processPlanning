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

	private ParametersDataModel parametersDataModel;

	public ParametersDataModel getParametersDataModel() {
		return this.parametersDataModel;
	}

	private OperationDataModel operationDataModel;

	KeyAdapter jtfEdgeIdKeyAdapter = new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
			final String edgeId = jtfEdgeId.getText();
			edge.setId(edgeId);
			editorDataModel.nodeChanged(edgeNode);
		};
	};

	KeyAdapter jtfBeginNodeIdKeyAdapter = new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
			final String beginNodeId = jtfBeginNodeId.getText();
			edge.setBeginNodeId(beginNodeId);
			editorDataModel.nodeChanged(edgeNode);
		};
	};

	KeyAdapter jtfEndNodeIdKeyAdapter = new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
			final String endNodeId = jtfEndNodeId.getText();
			edge.setEndNodeId(endNodeId);
			editorDataModel.nodeChanged(edgeNode);
		};
	};

	public EdgeDataModel(JTextField jtfEdgeId, JTextField jtfBeginNodeId, JTextField jtfEndNodeId,
			JTextField jtfOperationName, EditorDataModel editorDataModel) {
		this(jtfEdgeId, jtfBeginNodeId, jtfEndNodeId, new ParametersDataModel(editorDataModel),
				new OperationDataModel(jtfOperationName, editorDataModel), editorDataModel);
	}

	EdgeDataModel(JTextField jtfEdgeId, JTextField jtfBeginNodeId, JTextField jtfEndNodeId,
			ParametersDataModel parametersDataModel, OperationDataModel operationDataModel,
			EditorDataModel editorDataModel) {
		this.jtfEdgeId = jtfEdgeId;
		this.jtfBeginNodeId = jtfBeginNodeId;
		this.jtfEndNodeId = jtfEndNodeId;
		this.editorDataModel = editorDataModel;

		this.parametersDataModel = parametersDataModel;
		this.operationDataModel = operationDataModel;

		jtfEdgeId.addKeyListener(jtfEdgeIdKeyAdapter);
		jtfBeginNodeId.addKeyListener(jtfBeginNodeIdKeyAdapter);
		jtfEndNodeId.addKeyListener(jtfEndNodeIdKeyAdapter);
	}

	public void clear() {
		this.edge = null;
		this.edgeNode = null;
		this.operationDataModel.clear();
		this.parametersDataModel.clear();
	}

	private Edge edge;

	private DefaultMutableTreeNode edgeNode;

	public void loadEdge(Edge selectedObject, DefaultMutableTreeNode selectedNode) {
		this.edge = selectedObject;
		this.edgeNode = selectedNode;

		final String edgeId = edge.getId();
		final String beginNodeId = edge.getBeginNodeId();
		final String endNodeId = edge.getEndNodeId();

		jtfEdgeId.setText(edgeId);
		jtfBeginNodeId.setText(beginNodeId);
		jtfEndNodeId.setText(endNodeId);

		operationDataModel.loadOperation(selectedObject, selectedNode);
		parametersDataModel.loadParameters(selectedObject, selectedNode);
	}
}

package application.ui.gui.editor;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import planning.method.Edge;
import planning.model.SystemOperation;

public class OperationDataModel {

	private JTextField jtfOperationName;

	private EditorDataModel editorDataModel;

	KeyAdapter jtfOperationNameKeyAdapter = new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
			final String operationName = jtfOperationName.getText();
			operation.setName(operationName);
			editorDataModel.nodeChanged(edgeNode);
		}
	};

	public OperationDataModel(JTextField jtfOperationName, EditorDataModel editorDataModel) {
		this.jtfOperationName = jtfOperationName;
		this.editorDataModel = editorDataModel;

		jtfOperationName.addKeyListener(jtfOperationNameKeyAdapter);
	}

	public void clear() {
		operation = null;
		edgeNode = null;
	}

	private SystemOperation operation;

	private DefaultMutableTreeNode edgeNode;

	public void loadOperation(Edge selectedObject, DefaultMutableTreeNode selectedNode) {
		this.operation = selectedObject.getSystemOperation();
		this.edgeNode = selectedNode;

		final String operationName = operation.getName();
		jtfOperationName.setText(operationName);
	}
}

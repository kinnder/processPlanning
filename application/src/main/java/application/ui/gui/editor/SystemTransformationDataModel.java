package application.ui.gui.editor;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import planning.model.SystemTransformation;

public class SystemTransformationDataModel {

	private JTextField jtfSystemTransformationName;

	private EditorDataModel editorDataModel;

	KeyAdapter jtfSystemTransformationNameKeyAdapter = new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
			final String systemTransformationName = jtfSystemTransformationName.getText();
			systemTransformation.setName(systemTransformationName);
			editorDataModel.nodeChanged(treeNode);
		}
	};

	public SystemTransformationDataModel(JTextField jtfSystemTransformationName, EditorDataModel editorDataModel) {
		this.jtfSystemTransformationName = jtfSystemTransformationName;
		this.editorDataModel = editorDataModel;

		jtfSystemTransformationName.addKeyListener(jtfSystemTransformationNameKeyAdapter);
	}

	private DefaultMutableTreeNode treeNode;

	private SystemTransformation systemTransformation;

	public void loadSystemTransformation(SystemTransformation selectedSystemTransformation, DefaultMutableTreeNode selectedNode) {
		jtfSystemTransformationName.setText(selectedSystemTransformation.getName());

		systemTransformation = selectedSystemTransformation;
		treeNode = selectedNode;
	}

	public void clear() {
		systemTransformation = null;
		treeNode = null;
	}
}

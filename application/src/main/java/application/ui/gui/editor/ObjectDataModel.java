package application.ui.gui.editor;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import planning.model.SystemObject;

public class ObjectDataModel {

	private JTextField jtfObjectName;

	private JTextField jtfObjectId;

//	private EditorDataModel editorDataModel;

	private SystemObject object;

	public ObjectDataModel(JTextField jtfObjectName, JTextField jtfObjectId, EditorDataModel editorDataModel) {
		this.jtfObjectName = jtfObjectName;
		this.jtfObjectId = jtfObjectId;
//		this.editorDataModel = editorDataModel;

		jtfObjectName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String objectName = jtfObjectName.getText();
				object.setName(objectName);
				editorDataModel.nodeChanged(treeNode);
			}
		});

		jtfObjectId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String objectId = jtfObjectId.getText();
				object.setId(objectId);
				editorDataModel.nodeChanged(treeNode);
			}
		});
	}

	public void clear() {
		object = null;
		treeNode = null;
	}

	private DefaultMutableTreeNode treeNode;

	public void loadSystemObject(SystemObject selectedObject, DefaultMutableTreeNode selectedNode) {
		String name = selectedObject.getName();
		String id = selectedObject.getId();
		jtfObjectName.setText(name);
		jtfObjectId.setText(id);

		object = selectedObject;
		treeNode = selectedNode;
	}
}

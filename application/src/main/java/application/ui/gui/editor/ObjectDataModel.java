package application.ui.gui.editor;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import planning.model.SystemObject;

public class ObjectDataModel {

	private JTextField jtfObjectName;

	private JTextField jtfObjectId;

	private EditorDataModel editorDataModel;

	private SystemObject object;

	KeyAdapter jtfObjectNameKeyAdapter = new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
			final String objectName = jtfObjectName.getText();
			object.setName(objectName);
			editorDataModel.nodeChanged(treeNode);
		}
	};

	KeyAdapter jtfObjectIdKeyAdapter = new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
			final String objectId = jtfObjectId.getText();
			object.setId(objectId);
			editorDataModel.nodeChanged(treeNode);
		}
	};

	private AttributesDataModel attributesDataModel;

	public AttributesDataModel getAttributesDataModel() {
		return this.attributesDataModel;
	}

	public ObjectDataModel(JTextField jtfObjectName, JTextField jtfObjectId, EditorDataModel editorDataModel) {
		this(jtfObjectName, jtfObjectId, editorDataModel, new AttributesDataModel(editorDataModel));
	}

	ObjectDataModel(JTextField jtfObjectName, JTextField jtfObjectId, EditorDataModel editorDataModel,
			AttributesDataModel attributesDataModel) {
		this.jtfObjectName = jtfObjectName;
		this.jtfObjectId = jtfObjectId;
		this.editorDataModel = editorDataModel;

		this.attributesDataModel = attributesDataModel;

		jtfObjectName.addKeyListener(jtfObjectNameKeyAdapter);
		jtfObjectId.addKeyListener(jtfObjectIdKeyAdapter);
	}

	public void clear() {
		object = null;
		treeNode = null;

		attributesDataModel.clear();
	}

	private DefaultMutableTreeNode treeNode;

	public void loadSystemObject(SystemObject selectedObject, DefaultMutableTreeNode selectedNode) {
		final String name = selectedObject.getName();
		final String id = selectedObject.getId();
		jtfObjectName.setText(name);
		jtfObjectId.setText(id);

		object = selectedObject;
		treeNode = selectedNode;

		attributesDataModel.loadAttributes(selectedObject, selectedNode);
	}

	public void insertAttribute() {
		attributesDataModel.insertAttribute();
	}

	public void deleteAttribute(int idx) {
		attributesDataModel.deleteAttribute(idx);
	}
}

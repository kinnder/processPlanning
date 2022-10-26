package application.ui.gui.editor;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import planning.model.System;
import planning.model.SystemObject;

public class ObjectsDataModel extends DefaultTableModel {

	private static final long serialVersionUID = 375415280617365587L;

	public ObjectsDataModel() {
		super(new String[] { "name", "id" }, 0);
	}

	Class<?>[] types = new Class[] { String.class, String.class };

	List<SystemObject> objects = new ArrayList<SystemObject>();

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return types[columnIndex];
	}

	private System system;

	private DefaultMutableTreeNode treeNode;

	public void loadObjects(System selectedSystem, DefaultMutableTreeNode selectedNode) {
		objects.clear();
		this.setRowCount(0);

		for (SystemObject object : selectedSystem.getObjects()) {
			objects.add(object);
			this.addRow(new Object[] {});
		}

		system = selectedSystem;
		treeNode = selectedNode;
	}

	public void insertObject() {
		SystemObject object = new SystemObject("new object");
		system.addObject(object);
		objects.add(object);
		this.addRow(new Object[] {});
		editorDataModel.updateTreeNode(treeNode);
	}

	public void deleteObject(int idx) {
		if (idx < 0) {
			return;
		}

		SystemObject object = objects.get(idx);
		system.deleteObject(object);
		objects.remove(object);

		this.removeRow(idx);
		editorDataModel.updateTreeNode(treeNode);
	}

	public void moveDown(int idx) {
		if (idx > this.getRowCount() - 1) {
			return;
		}
		move(idx, idx + 1);
	}

	public void moveUp(int idx) {
		if (idx < 1) {
			return;
		}
		move(idx, idx - 1);
	}

	private void move(int idxA, int idxB) {
		this.moveRow(idxA, idxA, idxB);
		SystemObject objectA = objects.get(idxA);
		SystemObject objectB = objects.get(idxB);
		objects.set(idxA, objectB);
		objects.set(idxB, objectA);
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		SystemObject object = objects.get(row);
		if (column == 0) {
			object.setName((String) aValue);
			editorDataModel.updateTreeNode(treeNode);
		} else if (column == 1) {
			object.setId((String) aValue);
			editorDataModel.updateTreeNode(treeNode);
		}
	}

	@Override
	public Object getValueAt(int row, int column) {
		SystemObject object = objects.get(row);
		if (column == 0) {
			return object.getName();
		} else if (column == 1) {
			return object.getId();
		} else {
			return null;
		}
	}

	private EditorDataModel editorDataModel;

	// TODO (2022-10-26 #72): перенести в конструктор
	public void setEditorDataModel(EditorDataModel editorDataModel) {
		this.editorDataModel = editorDataModel;
	}
}

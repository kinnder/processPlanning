package application.ui.gui.editor;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import planning.model.System;
import planning.model.SystemObject;

public class ObjectsDataModel extends DefaultTableModel {

	private static final long serialVersionUID = 375415280617365587L;

	private EditorDataModel editorDataModel;

	public ObjectsDataModel(EditorDataModel editorDataModel) {
		super(new String[] { "name", "id" }, 0);
		this.editorDataModel = editorDataModel;
	}

	Class<?>[] types = new Class[] { String.class, String.class };

	List<SystemObject> objects = new ArrayList<SystemObject>();

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return types[columnIndex];
	}

	private System system;

	private DefaultMutableTreeNode systemNode;

	public void loadObjects(System selectedSystem, DefaultMutableTreeNode selectedNode) {
		objects.clear();
		this.setRowCount(0);

		for (SystemObject object : selectedSystem.getObjects()) {
			objects.add(object);
			this.addRow(new Object[] {});
		}

		system = selectedSystem;
		systemNode = selectedNode;
	}

	public void insertObject() {
		SystemObject object = new SystemObject("new object");
		system.addObject(object);
		objects.add(object);
		this.addRow(new Object[] {});
		DefaultMutableTreeNode objectNode = editorDataModel.createObjectNode(object);
		systemNode.add(objectNode);
		editorDataModel.insertNodeInto(objectNode, systemNode, objects.size() - 1);
	}

	public void deleteObject(int idx) {
		if (idx < 0) {
			return;
		}

		SystemObject object = objects.get(idx);
		system.deleteObject(object);
		objects.remove(object);

		this.removeRow(idx);
		DefaultMutableTreeNode objectNode = (DefaultMutableTreeNode) systemNode.getChildAt(idx);
		editorDataModel.removeNodeFromParent(objectNode);
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		SystemObject object = objects.get(row);
		if (column == 0) {
			object.setName((String) aValue);
			editorDataModel.nodesChanged(systemNode, new int[] { row });
		} else if (column == 1) {
			object.setId((String) aValue);
			editorDataModel.nodesChanged(systemNode, new int[] { row });
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
}

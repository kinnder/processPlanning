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

	public static final int COLUMN_IDX_NAME = 0;

	public static final int COLUMN_IDX_ID = 1;

	public ObjectsDataModel(EditorDataModel editorDataModel) {
		super(new String[] { "name", "id" }, 0);
		this.editorDataModel = editorDataModel;
	}

	private Class<?>[] types = new Class[] { String.class, String.class };

	private List<SystemObject> objects = new ArrayList<SystemObject>();

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
		// TODO (2022-11-11 #72): перенести в SystemObject
		final SystemObject object = new SystemObject("new object");
		system.addObject(object);
		objects.add(object);
		this.addRow(new Object[] {});
		final DefaultMutableTreeNode objectNode = editorDataModel.createObjectNode(object);
		// TODO (2023-01-09 #78): проверить количество вставляемых узлов
		systemNode.add(objectNode);
		editorDataModel.insertNodeInto(objectNode, systemNode, objects.size() - 1);
	}

	public void deleteObject(int idx) {
		if (idx < 0) {
			return;
		}

		final SystemObject object = objects.get(idx);
		system.removeObject(object);
		objects.remove(object);

		this.removeRow(idx);
		final DefaultMutableTreeNode objectNode = (DefaultMutableTreeNode) systemNode.getChildAt(idx);
		editorDataModel.removeNodeFromParent(objectNode);
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		final SystemObject object = objects.get(row);
		switch (column) {
		case COLUMN_IDX_NAME:
			object.setName((String) aValue);
			break;
		case COLUMN_IDX_ID:
			object.setId((String) aValue);
			break;
		default:
			break;
		}
		editorDataModel.nodesChanged(systemNode, new int[] { row });
	}

	@Override
	public Object getValueAt(int row, int column) {
		final SystemObject object = objects.get(row);
		switch (column) {
		case COLUMN_IDX_NAME:
			return object.getName();
		case COLUMN_IDX_ID:
			return object.getId();
		default:
			return null;
		}
	}
}

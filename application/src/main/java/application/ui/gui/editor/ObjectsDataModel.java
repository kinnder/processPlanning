package application.ui.gui.editor;

import javax.swing.table.DefaultTableModel;

import planning.model.System;
import planning.model.SystemObject;

public class ObjectsDataModel extends DefaultTableModel {

	private static final long serialVersionUID = 375415280617365587L;

	public ObjectsDataModel() {
		super(new String[] { "name", "id" }, 0);
	}

	Class<?>[] types = new Class[] { String.class, String.class };

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return types[columnIndex];
	}

	private System system;

	public void loadObjects(System selectedSystem) {
		this.setRowCount(0);

		for (SystemObject object : selectedSystem.getObjects()) {
			String name = object.getName();
			String id = object.getId();
			this.addRow(new Object[] { name, id });
		}

		system = selectedSystem;
	}

	public void insertObject() {
		// TODO (2022-10-12 #72): хранить список индекс=объект
		SystemObject object = new SystemObject("new object");
		system.addObject(object);
		this.addRow(new Object[] { object.getName(), object.getId() });
	}

	public void deleteObject(int idx) {
		if (idx < 0) {
			return;
		}

		String id = (String) this.getValueAt(idx, 1);
		SystemObject object = system.getObjectById(id);
		// TODO (2022-10-12): удаление объекта по id deleteObjectById
		system.deleteObject(object);

		this.removeRow(idx);
	}

	public void moveDown(int idx) {
		if (idx > this.getRowCount() - 1) {
			return;
		}
		this.moveRow(idx, idx, idx + 1);
	}

	public void moveUp(int idx) {
		if (idx < 1) {
			return;
		}
		this.moveRow(idx, idx, idx - 1);
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		String id = (String) this.getValueAt(row, 1);
		SystemObject object = system.getObjectById(id);
		if (column == 0) {
			object.setName((String) aValue);
		} else if (column == 1) {
			object.setId((String) aValue);
		}
		fireTableDataChanged();
	}
}

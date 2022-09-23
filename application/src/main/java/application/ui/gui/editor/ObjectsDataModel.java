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

	public void loadObjects(System selectedObject) {
		this.setRowCount(0);

		for (SystemObject object : selectedObject.getObjects()) {
			String name = object.getName();
			String id = object.getId();
			this.addRow(new Object[] { name, id });
		}
	}
}

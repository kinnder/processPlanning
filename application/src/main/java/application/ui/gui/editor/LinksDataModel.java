package application.ui.gui.editor;

import javax.swing.table.DefaultTableModel;

import planning.model.Link;
import planning.model.System;

public class LinksDataModel extends DefaultTableModel {

	private static final long serialVersionUID = 5763927106033461565L;

	public LinksDataModel() {
		super(new String[] { "name", "id1", "id2" }, 0);
	}

	Class<?>[] types = new Class[] { String.class, String.class, String.class };

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return types[columnIndex];
	}

	public void loadLinks(System selectedObject) {
		this.setRowCount(0);

		for (Link link : selectedObject.getLinks()) {
			String name = link.getName();
			String id1 = link.getId1();
			String id2 = link.getId2();
			this.addRow(new Object[] { name, id1, id2 });
		}
	}
}

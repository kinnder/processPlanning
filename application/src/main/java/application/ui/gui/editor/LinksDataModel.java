package application.ui.gui.editor;

import java.util.UUID;

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

	private System system;

	public void loadLinks(System selectedSystem) {
		this.setRowCount(0);

		for (Link link : selectedSystem.getLinks()) {
			String name = link.getName();
			String id1 = link.getId1();
			String id2 = link.getId2();
			this.addRow(new Object[] { name, id1, id2 });
		}

		system = selectedSystem;
	}

	public void insertLink() {
		// TODO (2022-10-23): хранить список индекс=link
		// TODO (2022-10-23): перенести создание пустых ссылок в класс Link
		String name = "link-" + UUID.randomUUID().toString();
		String id1 = "";
		String id2 = "";
		Link link = new Link(name, id1, id2);
		system.addLink(link);
		this.addRow(new Object[] { name, id1, id2 });
	}

	public void deleteLink(int idx) {
		if (idx < 0) {
			return;
		}

		String name = (String) this.getValueAt(idx, 0);
		String id1 = (String) this.getValueAt(idx, 1);
		String id2 = (String) this.getValueAt(idx, 2);

		Link link = system.getLink(name, id1, id2);
		// TODO (2022-10-23): удаление link по name, id1, id2
		system.removeLink(link);

		this.removeRow(idx);
	}

	public void moveUp(int idx) {
		if (idx < 1) {
			return;
		}
		this.moveRow(idx, idx, idx - 1);
	}

	public void moveDown(int idx) {
		if (idx > this.getRowCount() - 1) {
			return;
		}
		this.moveRow(idx, idx, idx + 1);
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		String name = (String) this.getValueAt(row, 0);
		String id1 = (String) this.getValueAt(row, 1);
		String id2 = (String) this.getValueAt(row, 2);
		Link link = system.getLink(name, id1, id2);
		if (column == 0) {
			link.setName((String) aValue);
		} else if (column == 1) {
			link.setId1((String) aValue);
		} else if (column == 2) {
			link.setId2((String) aValue);
		}
		fireTableDataChanged();
	}
}

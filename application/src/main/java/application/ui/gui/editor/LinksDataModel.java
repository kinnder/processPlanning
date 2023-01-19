package application.ui.gui.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import planning.model.Link;
import planning.model.System;

public class LinksDataModel extends DefaultTableModel {

	private static final long serialVersionUID = 5763927106033461565L;

	private EditorDataModel editorDataModel;

	public static final int COLUMN_IDX_NAME = 0;

	public static final int COLUMN_IDX_ID1 = 1;

	public static final int COLUMN_IDX_ID2 = 2;

	public LinksDataModel(EditorDataModel editorDataModel) {
		super(new String[] { "name", "id1", "id2" }, 0);
		this.editorDataModel = editorDataModel;
	}

	private Class<?>[] types = new Class[] { String.class, String.class, String.class };

	private List<Link> links = new ArrayList<Link>();

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return types[columnIndex];
	}

	private System system;

	private DefaultMutableTreeNode treeNode;

	public void loadLinks(System selectedSystem, DefaultMutableTreeNode selectedNode) {
		links.clear();
		this.setRowCount(0);

		for (Link link : selectedSystem.getLinks()) {
			links.add(link);
			this.addRow(new Object[] {});
		}

		system = selectedSystem;
		treeNode = selectedNode;
	}

	public void insertLink() {
		// TODO (2022-10-23 #72): перенести создание пустых ссылок в класс Link
		final String name = "link-" + UUID.randomUUID().toString();
		final String id1 = "";
		final String id2 = "";
		final Link link = new Link(name, id1, id2);
		system.addLink(link);
		links.add(link);
		this.addRow(new Object[] {});
		editorDataModel.nodeChanged(treeNode);
	}

	public void deleteLink(int idx) {
		if (idx < 0) {
			return;
		}

		final Link link = links.get(idx);
		system.removeLink(link);
		links.remove(link);

		this.removeRow(idx);
		editorDataModel.nodeChanged(treeNode);
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		final Link link = links.get(row);
		switch (column) {
		case COLUMN_IDX_NAME:
			link.setName((String) aValue);
			break;
		case COLUMN_IDX_ID1:
			link.setId1((String) aValue);
			break;
		case COLUMN_IDX_ID2:
			link.setId2((String) aValue);
			break;
		default:
			break;
		}
		editorDataModel.nodeChanged(treeNode);
	}

	@Override
	public Object getValueAt(int row, int column) {
		final Link link = links.get(row);
		switch (column) {
		case COLUMN_IDX_NAME:
			return link.getName();
		case COLUMN_IDX_ID1:
			return link.getId1();
		case COLUMN_IDX_ID2:
			return link.getId2();
		default:
			return null;
		}
	}
}

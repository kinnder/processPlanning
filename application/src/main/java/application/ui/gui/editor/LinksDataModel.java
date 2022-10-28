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

	public LinksDataModel(EditorDataModel editorDataModel) {
		super(new String[] { "name", "id1", "id2" }, 0);
		this.editorDataModel = editorDataModel;
	}

	Class<?>[] types = new Class[] { String.class, String.class, String.class };

	List<Link> links = new ArrayList<Link>();

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
		String name = "link-" + UUID.randomUUID().toString();
		String id1 = "";
		String id2 = "";
		Link link = new Link(name, id1, id2);
		system.addLink(link);
		links.add(link);
		this.addRow(new Object[] {});
		editorDataModel.nodeChanged(treeNode);
	}

	public void deleteLink(int idx) {
		if (idx < 0) {
			return;
		}

		Link link = links.get(idx);
		system.removeLink(link);
		links.remove(link);

		this.removeRow(idx);
		editorDataModel.nodeChanged(treeNode);
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		Link link = links.get(row);
		if (column == 0) {
			link.setName((String) aValue);
			editorDataModel.nodeChanged(treeNode);
		} else if (column == 1) {
			link.setId1((String) aValue);
			editorDataModel.nodeChanged(treeNode);
		} else if (column == 2) {
			link.setId2((String) aValue);
			editorDataModel.nodeChanged(treeNode);
		}
	}

	@Override
	public Object getValueAt(int row, int column) {
		Link link = links.get(row);
		if (column == 0) {
			return link.getName();
		} else if (column == 1) {
			return link.getId1();
		} else if (column == 2) {
			return link.getId2();
		} else {
			return null;
		}
	}
}

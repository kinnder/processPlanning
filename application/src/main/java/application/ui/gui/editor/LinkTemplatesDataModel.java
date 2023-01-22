package application.ui.gui.editor;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import planning.model.LinkTemplate;
import planning.model.SystemTemplate;

public class LinkTemplatesDataModel extends DefaultTableModel {

	private static final long serialVersionUID = -6681920262779940696L;

	private EditorDataModel editorDataModel;

	public static final int COLUMN_IDX_NAME = 0;

	public static final int COLUMN_IDX_ID1 = 1;

	public static final int COLUMN_IDX_ID2 = 2;

	public LinkTemplatesDataModel(EditorDataModel editorDataModel) {
		super(new String[] { "name", "id1", "id2" }, 0);
		this.editorDataModel = editorDataModel;
	}

	private Class<?>[] types = new Class[] { String.class, String.class, String.class };

	private List<LinkTemplate> linkTemplates = new ArrayList<LinkTemplate>();

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return types[columnIndex];
	}

	private SystemTemplate systemTemplate;

	private DefaultMutableTreeNode systemTemplateNode;

	public void loadLinkTemplates(SystemTemplate selectedSystemTemplate, DefaultMutableTreeNode selectedNode) {
		linkTemplates.clear();
		this.setRowCount(0);

		for (LinkTemplate linkTemplate : selectedSystemTemplate.getLinkTemplates()) {
			linkTemplates.add(linkTemplate);
			this.addRow(new Object[] {});
		}

		systemTemplate = selectedSystemTemplate;
		systemTemplateNode = selectedNode;
	}

	public void insertLinkTemplate() {
		final LinkTemplate linkTemplate = new LinkTemplate();
		systemTemplate.addLinkTemplate(linkTemplate);
		linkTemplates.add(linkTemplate);
		this.addRow(new Object[] {});
		editorDataModel.nodeChanged(systemTemplateNode);
	}

	public void deleteLinkTemplate(int idx) {
		if (idx < 0) {
			return;
		}

		final LinkTemplate linkTemplate = linkTemplates.get(idx);
		systemTemplate.removeLinkTemplate(linkTemplate);
		linkTemplates.remove(linkTemplate);

		this.removeRow(idx);
		editorDataModel.nodeChanged(systemTemplateNode);
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		final LinkTemplate linkTemplate = linkTemplates.get(row);
		switch (column) {
		case COLUMN_IDX_NAME:
			linkTemplate.setName((String) aValue);
			break;
		case COLUMN_IDX_ID1:
			linkTemplate.setId1((String) aValue);
			break;
		case COLUMN_IDX_ID2:
			linkTemplate.setId2((String) aValue);
			break;
		default:
			break;
		}
		editorDataModel.nodeChanged(systemTemplateNode);
	}

	@Override
	public Object getValueAt(int row, int column) {
		final LinkTemplate linkTemplate = linkTemplates.get(row);
		switch (column) {
		case COLUMN_IDX_NAME:
			return linkTemplate.getName();
		case COLUMN_IDX_ID1:
			return linkTemplate.getId1();
		case COLUMN_IDX_ID2:
			return linkTemplate.getId2();
		default:
			return null;
		}
	}
}

package application.ui.gui.editor;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import planning.model.SystemObjectTemplate;
import planning.model.SystemTemplate;

public class ObjectTemplatesDataModel extends DefaultTableModel {

	private static final long serialVersionUID = 4569605859281139998L;

	private EditorDataModel editorDataModel;

	public static final int COLUMN_IDX_NAME = 0;

	public static final int COLUMN_IDX_ID = 1;

	public ObjectTemplatesDataModel(EditorDataModel editorDataModel) {
		super(new String[] { "name", "id" }, 0);
		this.editorDataModel = editorDataModel;
	}

	private Class<?>[] types = new Class[] { String.class, String.class };

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return types[columnIndex];
	}

	private SystemTemplate systemTemplate;

	private DefaultMutableTreeNode systemTemplateNode;

	private List<SystemObjectTemplate> objectTemplates = new ArrayList<SystemObjectTemplate>();

	public void loadObjectTemplates(SystemTemplate selectedSystemTemplate, DefaultMutableTreeNode selectedNode) {
		objectTemplates.clear();
		this.setRowCount(0);

		for (SystemObjectTemplate objectTemplate : selectedSystemTemplate.getObjectTemplates()) {
			objectTemplates.add(objectTemplate);
			this.addRow(new Object[] {});
		}

		systemTemplate = selectedSystemTemplate;
		systemTemplateNode = selectedNode;
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		final SystemObjectTemplate objectTemplate = objectTemplates.get(row);
		switch (column) {
		case COLUMN_IDX_NAME:
			break;
		case COLUMN_IDX_ID:
			objectTemplate.setId((String) aValue);
			break;
		default:
			break;
		}
		editorDataModel.nodesChanged(systemTemplateNode, new int[] { row });
	}

	@Override
	public Object getValueAt(int row, int column) {
		final SystemObjectTemplate objectTemplate = objectTemplates.get(row);
		switch (column) {
		case COLUMN_IDX_NAME:
			return "Object Template";
		case COLUMN_IDX_ID:
			return objectTemplate.getId();
		default:
			return null;
		}
	}

	public void insertObjectTemplate() {
		final SystemObjectTemplate objectTemplate = new SystemObjectTemplate();
		systemTemplate.addObjectTemplate(objectTemplate);
		objectTemplates.add(objectTemplate);
		this.addRow(new Object[] {});
		final DefaultMutableTreeNode objectTemplateNode = editorDataModel.createObjectTemplateNode(objectTemplate);
		systemTemplateNode.add(objectTemplateNode);
		editorDataModel.insertNodeInto(objectTemplateNode, systemTemplateNode, objectTemplates.size() - 1);
	}

	public void deleteObjectTemplate(int idx) {
		if (idx < 0) {
			return;
		}

		final SystemObjectTemplate objectTemplate = objectTemplates.get(idx);
		systemTemplate.removeObjectTemplate(objectTemplate);
		objectTemplates.remove(objectTemplate);

		this.removeRow(idx);
		final DefaultMutableTreeNode objectTemplateNode = (DefaultMutableTreeNode) systemTemplateNode.getChildAt(idx);
		editorDataModel.removeNodeFromParent(objectTemplateNode);
	}
}

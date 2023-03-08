package application.ui.gui.editor;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import planning.model.AttributeTemplate;
import planning.model.AttributeType;
import planning.model.SystemObjectTemplate;

public class AttributeTemplatesDataModel extends DefaultTableModel {

	private static final long serialVersionUID = -1944552811851244410L;

	private EditorDataModel editorDataModel;

	public static final int COLUMN_IDX_NAME = 0;

	public static final int COLUMN_IDX_VALUE = 1;

	public static final int COLUMN_IDX_TYPE = 2;

	public AttributeTemplatesDataModel(EditorDataModel editorDataModel) {
		super(new String[] { "name", "value", "type" }, 0);
		this.editorDataModel = editorDataModel;
	}

	private Class<?>[] types = new Class[] { String.class, String.class, String.class };

	private List<AttributeTemplate> attributeTemplates = new ArrayList<AttributeTemplate>();

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return types[columnIndex];
	}

	private SystemObjectTemplate objectTemplate;

	private DefaultMutableTreeNode objectTemplateNode;

	public void loadAttributeTemplates(SystemObjectTemplate selectedObjectTemplate,
			DefaultMutableTreeNode selectedNode) {
		attributeTemplates.clear();
		this.setRowCount(0);

		for (AttributeTemplate attributeTemplate : selectedObjectTemplate.getAttributeTemplates()) {
			attributeTemplates.add(attributeTemplate);
			this.addRow(new Object[] {});
		}

		objectTemplate = selectedObjectTemplate;
		objectTemplateNode = selectedNode;
	}

	public void insertAttributeTemplate() {
		final AttributeTemplate attributeTemplate = new AttributeTemplate();
		objectTemplate.addAttributeTemplate(attributeTemplate);
		attributeTemplates.add(attributeTemplate);
		this.addRow(new Object[] {});
		editorDataModel.nodeChanged(objectTemplateNode);
	}

	public void deleteAttributeTemplate(int idx) {
		if (idx < 0) {
			return;
		}

		final AttributeTemplate attributeTemplate = attributeTemplates.get(idx);
		objectTemplate.removeAttributeTemplate(attributeTemplate);
		attributeTemplates.remove(attributeTemplate);

		this.removeRow(idx);
		editorDataModel.nodeChanged(objectTemplateNode);
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		final AttributeTemplate attributeTemplate = attributeTemplates.get(row);
		switch (column) {
		case (COLUMN_IDX_NAME):
			attributeTemplate.setName((String) aValue);
			break;
		case (COLUMN_IDX_TYPE):
			attributeTemplate.setType(AttributeType.fromString((String) aValue));
			break;
		case (COLUMN_IDX_VALUE):
			attributeTemplate.setValue(aValue);
			break;
		default:
			break;
		}
		editorDataModel.nodeChanged(objectTemplateNode);
	}

	@Override
	public Object getValueAt(int row, int column) {
		final AttributeTemplate attributeTemplate = attributeTemplates.get(row);
		switch (column) {
		case COLUMN_IDX_NAME:
			return attributeTemplate.getName();
		case COLUMN_IDX_TYPE:
			return attributeTemplate.getType();
		case COLUMN_IDX_VALUE:
			return attributeTemplate.getValue();
		default:
			return null;
		}
	}

	public void clear() {
	}

	public void setColumnCellEditors(JTable table) {
		final JComboBox<String> comboBox = new JComboBox<>();
		for (AttributeType value : AttributeType.values()) {
			comboBox.addItem(value.toString());
		}
		table.getColumnModel().getColumn(AttributeTemplatesDataModel.COLUMN_IDX_TYPE)
				.setCellEditor(new DefaultCellEditor(comboBox));
	}
}

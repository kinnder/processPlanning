package application.ui.gui.editor;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import planning.model.AttributeTemplate;
import planning.model.SystemObjectTemplate;

public class AttributeTemplatesDataModel extends DefaultTableModel {

	private static final long serialVersionUID = -1944552811851244410L;

	private EditorDataModel editorDataModel;

	public static final int COLUMN_IDX_NAME = 0;

	public static final int COLUMN_IDX_TYPE = 1;

	public static final int COLUMN_IDX_VALUE = 2;

	public AttributeTemplatesDataModel(EditorDataModel editorDataModel) {
		super(new String[] { "name", "type", "value" }, 0);
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
			// TODO (2022-12-02 #73): перенести в AttributeTemplate
			if ("boolean".equals(aValue)) {
				Object valueObject = attributeTemplate.getValue();
				valueObject = valueObject != null ? Boolean.valueOf(valueObject.toString()) : null;
				attributeTemplate.setValue(valueObject);
			} else if ("integer".equals(aValue)) {
				Object valueObject = attributeTemplate.getValue();
				valueObject = valueObject != null ? Integer.valueOf(valueObject.toString()) : null;
				attributeTemplate.setValue(valueObject);
			} else if ("string".equals(aValue)) {
				Object valueObject = attributeTemplate.getValue();
				valueObject = valueObject != null ? valueObject.toString() : null;
				attributeTemplate.setValue(valueObject);
			} else {
				;
			}
			break;
		case (COLUMN_IDX_VALUE):
			// TODO (2022-12-02 #73): перенести в AttributeTemplate
			final String type = (String) dataVector.get(row).get(1);
			if ("boolean".equals(type)) {
				final Object valueObject = aValue != null ? Boolean.valueOf(aValue.toString()) : null;
				attributeTemplate.setValue(valueObject);
			} else if ("integer".equals(type)) {
				final Object valueObject = aValue != null ? Integer.valueOf(aValue.toString()) : null;
				attributeTemplate.setValue(valueObject);
			} else if ("string".equals(type)) {
				final Object valueObject = aValue != null ? aValue.toString() : null;
				attributeTemplate.setValue(valueObject);
			} else {
				final Object valueObject = aValue != null ? aValue : null;
				attributeTemplate.setValue(valueObject);
			}
			break;
		default:
			break;
		}
		editorDataModel.nodeChanged(objectTemplateNode);
	}

	@Override
	public Object getValueAt(int row, int column) {
		final AttributeTemplate attributeTemplate = attributeTemplates.get(row);
		Object valueObject;
		switch (column) {
		case COLUMN_IDX_NAME:
			return attributeTemplate.getName();
		case COLUMN_IDX_TYPE:
			// TODO (2022-12-02 #73): перенести в AttributeTemplate
			valueObject = attributeTemplate.getValue();
			if (valueObject instanceof Boolean) {
				return "boolean";
			} else if (valueObject instanceof Integer) {
				return "integer";
			} else if (valueObject instanceof String) {
				return "string";
			} else {
				return "";
			}
		case COLUMN_IDX_VALUE:
			// TODO (2022-12-02 #73): перенести в AttributeTemplate
			valueObject = attributeTemplate.getValue();
			if (valueObject instanceof Boolean) {
				return valueObject.toString();
			} else if (valueObject instanceof Integer) {
				return valueObject.toString();
			} else if (valueObject instanceof String) {
				return valueObject.toString();
			} else {
				return valueObject;
			}
		default:
			return null;
		}
	}

	public void clear() {
	}
}

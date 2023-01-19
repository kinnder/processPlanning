package application.ui.gui.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import planning.model.Attribute;
import planning.model.SystemObject;

public class AttributesDataModel extends DefaultTableModel {

	private static final long serialVersionUID = 4599213730293016122L;

	private EditorDataModel editorDataModel;

	public static final int COLUMN_IDX_NAME = 0;

	public static final int COLUMN_IDX_TYPE = 1;

	public static final int COLUMN_IDX_VALUE = 2;

	public AttributesDataModel(EditorDataModel editorDataModel) {
		super(new String[] { "name", "type", "value" }, 0);
		this.editorDataModel = editorDataModel;
	}

	private Class<?>[] types = new Class[] { String.class, String.class, String.class };

	private List<Attribute> attributes = new ArrayList<Attribute>();

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return types[columnIndex];
	}

	private SystemObject object;

	private DefaultMutableTreeNode treeNode;

	public void loadAttributes(SystemObject selectedObject, DefaultMutableTreeNode selectedNode) {
		attributes.clear();
		this.setRowCount(0);

		for (Attribute attribute : selectedObject.getAttributes()) {
			attributes.add(attribute);
			this.addRow(new Object[] {});
		}

		object = selectedObject;
		treeNode = selectedNode;
	}

	public void insertAttribute() {
		// TODO (2022-10-30 #72): перенести создание пустых атрибутов в класс Attribute
		final String name = "attribute-" + UUID.randomUUID().toString();
		final String value = "value";
		final Attribute attribute = new Attribute(name, value);
		object.addAttribute(attribute);
		attributes.add(attribute);
		this.addRow(new Object[] {});
		editorDataModel.nodeChanged(treeNode);
	}

	public void deleteAttribute(int idx) {
		if (idx < 0) {
			return;
		}

		final Attribute attribute = attributes.get(idx);
		object.removeAttribute(attribute);
		attributes.remove(attribute);

		this.removeRow(idx);
		editorDataModel.nodeChanged(treeNode);
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		final Attribute attribute = attributes.get(row);
		switch (column) {
		case (COLUMN_IDX_NAME):
			attribute.setName((String) aValue);
			break;
		case (COLUMN_IDX_TYPE):
			// TODO (2022-10-30 #72): перенести в Attribute
			if ("boolean".equals(aValue)) {
				Object valueObject = attribute.getValue();
				valueObject = valueObject != null ? Boolean.valueOf(valueObject.toString()) : null;
				attribute.setValue(valueObject);
			} else if ("integer".equals(aValue)) {
				Object valueObject = attribute.getValue();
				valueObject = valueObject != null ? Integer.valueOf(valueObject.toString()) : null;
				attribute.setValue(valueObject);
			} else if ("string".equals(aValue)) {
				Object valueObject = attribute.getValue();
				valueObject = valueObject != null ? valueObject.toString() : null;
				attribute.setValue(valueObject);
			} else {
				;
			}
			break;
		case (COLUMN_IDX_VALUE):
			// TODO (2022-10-30 #72): перенести в Attribute
			final String type = (String) dataVector.get(row).get(1);
			if ("boolean".equals(type)) {
				final Object valueObject = aValue != null ? Boolean.valueOf(aValue.toString()) : null;
				attribute.setValue(valueObject);
			} else if ("integer".equals(type)) {
				final Object valueObject = aValue != null ? Integer.valueOf(aValue.toString()) : null;
				attribute.setValue(valueObject);
			} else if ("string".equals(type)) {
				final Object valueObject = aValue != null ? aValue.toString() : null;
				attribute.setValue(valueObject);
			} else {
				final Object valueObject = aValue != null ? aValue : null;
				attribute.setValue(valueObject);
			}
			break;
		default:
			break;
		}
		editorDataModel.nodeChanged(treeNode);
	}

	@Override
	public Object getValueAt(int row, int column) {
		final Attribute attribute = attributes.get(row);
		Object valueObject;
		switch (column) {
		case COLUMN_IDX_NAME:
			return attribute.getName();
		case COLUMN_IDX_TYPE:
			// TODO (2022-09-24 #72): перенести в Attribute
			valueObject = attribute.getValue();
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
			// TODO (2022-09-24 #72): перенести в Attribute
			valueObject = attribute.getValue();
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
}

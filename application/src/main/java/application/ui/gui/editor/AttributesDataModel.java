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

	public AttributesDataModel(EditorDataModel editorDataModel) {
		super(new String[] { "name", "type", "value" }, 0);
		this.editorDataModel = editorDataModel;
	}

	Class<?>[] types = new Class[] { String.class, String.class, String.class };

	List<Attribute> attributes = new ArrayList<Attribute>();

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
		String name = "attribute-" + UUID.randomUUID().toString();
		String value = "value";
		Attribute attribute = new Attribute(name, value);
		object.addAttribute(attribute);
		attributes.add(attribute);
		this.addRow(new Object[] {});
		editorDataModel.nodeChanged(treeNode);
	}

	public void deteleAttribute(int idx) {
		if (idx < 0) {
			return;
		}

		Attribute attribute = attributes.get(idx);
		object.removeAttribute(attribute);
		attributes.remove(attribute);

		this.removeRow(idx);
		editorDataModel.nodeChanged(treeNode);
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		Attribute attribute = attributes.get(row);
		if (column == 0) {
			attribute.setName((String) aValue);
			editorDataModel.nodeChanged(treeNode);
		} else if (column == 1) {
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
			editorDataModel.nodeChanged(treeNode);
		} else if (column == 2) {
			// TODO (2022-10-30 #72): перенести в Attribute
			String type = (String) dataVector.get(row).get(1);
			if ("boolean".equals(type)) {
				Object valueObject = aValue != null ? Boolean.valueOf(aValue.toString()) : null;
				attribute.setValue(valueObject);
			} else if ("integer".equals(type)) {
				Object valueObject = aValue != null ? Integer.valueOf(aValue.toString()) : null;
				attribute.setValue(valueObject);
			} else if ("string".equals(type)) {
				Object valueObject = aValue != null ? aValue.toString() : null;
				attribute.setValue(valueObject);
			} else {
				Object valueObject = aValue != null ? aValue : null;
				attribute.setValue(valueObject);
			}
			editorDataModel.nodeChanged(treeNode);
		}
	}

	@Override
	public Object getValueAt(int row, int column) {
		Attribute attribute = attributes.get(row);
		if (column == 0) {
			return attribute.getName();
		} else if (column == 1) {
			// TODO (2022-09-24 #72): перенести в Attribute
			Object valueObject = attribute.getValue();
			if (valueObject instanceof Boolean) {
				return "boolean";
			} else if (valueObject instanceof Integer) {
				return "integer";
			} else if (valueObject instanceof String) {
				return "string";
			} else {
				return "";
			}
		} else if (column == 2) {
			// TODO (2022-09-24 #72): перенести в Attribute
			Object valueObject = attribute.getValue();
			if (valueObject instanceof Boolean) {
				return valueObject.toString();
			} else if (valueObject instanceof Integer) {
				return valueObject.toString();
			} else if (valueObject instanceof String) {
				return valueObject.toString();
			} else {
				return valueObject;
			}
		} else {
			return null;
		}
	}
}

package application.ui.gui.editor;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import planning.model.Attribute;
import planning.model.AttributeType;
import planning.model.SystemObject;

public class AttributesDataModel extends DefaultTableModel {

	private static final long serialVersionUID = 4599213730293016122L;

	private EditorDataModel editorDataModel;

	public static final int COLUMN_IDX_NAME = 0;

	public static final int COLUMN_IDX_VALUE = 1;

	public static final int COLUMN_IDX_TYPE = 2;

	public AttributesDataModel(EditorDataModel editorDataModel) {
		super(new String[] { "name", "value", "type" }, 0);
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
		final Attribute attribute = new Attribute();
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
			attribute.setType(AttributeType.fromString((String) aValue));
			break;
		case (COLUMN_IDX_VALUE): {
			attribute.setValue(aValue);
			break;
		}
		default:
			break;
		}
		editorDataModel.nodeChanged(treeNode);
	}

	@Override
	public Object getValueAt(int row, int column) {
		final Attribute attribute = attributes.get(row);
		switch (column) {
		case COLUMN_IDX_NAME:
			return attribute.getName();
		case COLUMN_IDX_TYPE:
			return attribute.getType();
		case COLUMN_IDX_VALUE:
			return attribute.getValue();
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
		table.getColumnModel().getColumn(AttributesDataModel.COLUMN_IDX_TYPE)
				.setCellEditor(new DefaultCellEditor(comboBox));
	}
}

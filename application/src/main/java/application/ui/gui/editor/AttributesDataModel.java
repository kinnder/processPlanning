package application.ui.gui.editor;

import javax.swing.table.DefaultTableModel;

import planning.model.Attribute;
import planning.model.SystemObject;

public class AttributesDataModel extends DefaultTableModel {

	private static final long serialVersionUID = 4599213730293016122L;

	public AttributesDataModel() {
		super(new String[] { "name", "type", "value" }, 0);
	}

	Class<?>[] types = new Class[] { String.class, String.class, String.class };

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return types[columnIndex];
	}

	public void loadAttributes(SystemObject selectedObject) {
		this.setRowCount(0);

		for (Attribute attribute : selectedObject.getAttributes()) {
			String name = attribute.getName();
			String type;
			String value;
			// TODO (2022-09-24 #72): перенести в Attribute
			Object valueObject = attribute.getValue();
			if (valueObject instanceof Boolean) {
				type = "boolean";
				value = valueObject.toString();
			} else if (valueObject instanceof Integer) {
				type = "integer";
				value = valueObject.toString();
			} else if (valueObject instanceof String) {
				type = "string";
				value = valueObject.toString();
			} else {
				type = "";
				value = "";
			}
			this.addRow(new Object[] { name, type, value });
		}
	}
}

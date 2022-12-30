package application.ui.gui.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import planning.method.Edge;
import planning.model.SystemOperation;

public class ParametersDataModel extends DefaultTableModel {

	private static final long serialVersionUID = -9127052421696216188L;

	static final int COLUMN_IDX_NAME = 0;

	static final int COLUMN_IDX_VALUE = 1;

//	private EditorDataModel editorDataModel;

	@SuppressWarnings("PMD.UnusedFormalParameter")
	public ParametersDataModel(EditorDataModel editorDataModel) {
		super(new String[] { "name", "value" }, 0);
//		this.editorDataModel = editorDataModel;
	}

	private Class<?>[] types = new Class[] { String.class, String.class };

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return types[columnIndex];
	}

	public void clear() {
		this.operation = null;
//		this.edgeNode = null;
	}

	private SystemOperation operation;

//	private DefaultMutableTreeNode edgeNode;

	private List<String> keys = new ArrayList<String>();
	private List<String> values = new ArrayList<String>();

	public void loadParameters(Edge selectedObject, DefaultMutableTreeNode selectedNode) {
		keys.clear();
		values.clear();
		this.setRowCount(0);

		this.operation = selectedObject.getSystemOperation();
//		this.edgeNode = selectedNode;

		for (Map.Entry<String, String> entry : operation.getActionParameters().entrySet()) {
			keys.add(entry.getKey());
			values.add(entry.getValue());
			this.addRow(new Object[] {});
		}
	}

	@Override
	public Object getValueAt(int row, int column) {
		switch (column) {
		case COLUMN_IDX_NAME:
			return keys.get(row);
		case COLUMN_IDX_VALUE:
			return values.get(row);
		default:
			return null;
		}
	}
}

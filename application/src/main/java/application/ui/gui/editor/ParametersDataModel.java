package application.ui.gui.editor;

import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import planning.method.Edge;

public class ParametersDataModel extends DefaultTableModel {
	private static final long serialVersionUID = -9127052421696216188L;

	private EditorDataModel editorDataModel;

	public ParametersDataModel(EditorDataModel editorDataModel) {
		super(new String[] { "name", "value" }, 0);
		this.editorDataModel = editorDataModel;
	}

	private Class<?>[] types = new Class[] { String.class, String.class };

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return types[columnIndex];
	}

	public void clear() {
		// TODO Auto-generated method stub
	}

	public void loadParameters(Edge selectedObject, DefaultMutableTreeNode selectedNode) {
		// TODO Auto-generated method stub
	}
}

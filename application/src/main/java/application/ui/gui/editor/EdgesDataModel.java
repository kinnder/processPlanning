package application.ui.gui.editor;

import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import planning.method.Node;

public class EdgesDataModel extends DefaultTableModel {

	private static final long serialVersionUID = -2360331617408949820L;

	private EditorDataModel editorDataModel;

	public EdgesDataModel(EditorDataModel editorDataModel) {
		super(new String[] { "id", "endNodeId" }, 0);
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

	public void loadEdges(Node selectedObject, DefaultMutableTreeNode selectedNode) {
		// TODO Auto-generated method stub
	}
}

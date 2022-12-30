package application.ui.gui.editor;

import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import planning.method.Edge;
import planning.method.Node;

public class EdgesDataModel extends DefaultTableModel {

	private static final long serialVersionUID = -2360331617408949820L;

	private EditorDataModel editorDataModel;

	public final static int COLUMN_IDX_ID = 0;

	public final static int COLUMN_IDX_END_NODE_ID = 1;

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
		treeNode = null;
	}

	private List<Edge> edges;

	DefaultMutableTreeNode treeNode;

	@SuppressWarnings("PMD.ForLoopCanBeForeach")
	public void loadEdges(Node selectedObject, DefaultMutableTreeNode selectedNode) {
		this.setRowCount(0);

		// TODO (2022-12-25 #74): пересмотреть положение метода selectEdges
		edges = editorDataModel.selectEdges(selectedObject);
		for (int i = 0; i < edges.size(); i++) {
			this.addRow(new Object[] {});
		}

		treeNode = selectedNode;
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		Edge edge = edges.get(row);
		switch (column) {
		case COLUMN_IDX_ID:
			edge.setId((String) aValue);
			break;
		case COLUMN_IDX_END_NODE_ID:
			edge.setEndNodeId((String) aValue);
			break;
		default:
			break;
		}
		editorDataModel.nodeChanged(treeNode);
	}

	@Override
	public Object getValueAt(int row, int column) {
		Edge edge = edges.get(row);
		switch (column) {
		case COLUMN_IDX_ID:
			return edge.getId();
		case COLUMN_IDX_END_NODE_ID:
			return edge.getEndNodeId();
		default:
			return null;
		}
	}
}

package application.ui.gui.editor;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import planning.method.SystemTransformations;
import planning.model.SystemTransformation;

public class SystemTransformationsDataModel extends DefaultTableModel {

	private static final long serialVersionUID = -8078761171506433611L;

	private EditorDataModel editorDataModel;

	public static final int COLUMN_IDX_NAME = 0;

	public SystemTransformationsDataModel(EditorDataModel editorDataModel) {
		super(new String[] { "name" }, 0);
		this.editorDataModel = editorDataModel;
	}

	private Class<?>[] types = new Class[] { java.lang.String.class };

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return types[columnIndex];
	}

	// TODO (2022-11-17 #73): проверить необходимость в этой и аналогичных коллекций
	private List<SystemTransformation> systemTransformationsList = new ArrayList<SystemTransformation>();

	private SystemTransformations systemTransformations;

	private DefaultMutableTreeNode systemTransformationsNode;

	public void loadSystemTransformations(SystemTransformations selectedSystemTransformations, DefaultMutableTreeNode selectedNode) {
		systemTransformationsList.clear();
		this.setRowCount(0);

		for (SystemTransformation systemTransformation : selectedSystemTransformations) {
			systemTransformationsList.add(systemTransformation);
			this.addRow(new Object[] {});
		}

		systemTransformations = selectedSystemTransformations;
		systemTransformationsNode = selectedNode;
	}

	public void insertSystemTransformation() {
		final SystemTransformation systemTransformation = new SystemTransformation();
		systemTransformations.add(systemTransformation);
		systemTransformationsList.add(systemTransformation);
		this.addRow(new Object[] {});
		final DefaultMutableTreeNode systemTransformationNode = editorDataModel.createSystemTransformationNode(systemTransformation);
		systemTransformationsNode.add(systemTransformationNode);
		editorDataModel.insertNodeInto(systemTransformationNode, systemTransformationsNode, systemTransformationsList.size() - 1);
	}

	public void deleteSystemTransformation(int idx) {
		if (idx < 0) {
			return;
		}

		final SystemTransformation systemTransformation = systemTransformationsList.get(idx);
		systemTransformations.remove(systemTransformation);
		systemTransformationsList.remove(systemTransformation);

		this.removeRow(idx);
		final DefaultMutableTreeNode systemTransformationNode = (DefaultMutableTreeNode) systemTransformationsNode.getChildAt(idx);
		editorDataModel.removeNodeFromParent(systemTransformationNode);
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		final SystemTransformation systemTransformation = systemTransformationsList.get(row);
		switch (column) {
		case COLUMN_IDX_NAME:
			systemTransformation.setName((String) aValue);
			break;
		default:
			break;
		}
		editorDataModel.nodesChanged(systemTransformationsNode, new int[] { row });
	}

	@Override
	public Object getValueAt(int row, int column) {
		final SystemTransformation systemTransformation = systemTransformationsList.get(row);
		switch (column) {
		case COLUMN_IDX_NAME:
			return systemTransformation.getName();
		default:
			return null;
		}
	}
}

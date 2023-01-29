package application.ui.gui.editor;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import planning.model.AttributeTransformation;
import planning.model.LinkTransformation;
import planning.model.Transformation;
import planning.model.Transformations;

public class TransformationsDataModel extends DefaultTableModel {

	private static final long serialVersionUID = 967671294783104767L;

	private Class<?>[] types = new Class[] { String.class, String.class, String.class };

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return types[columnIndex];
	}

	public static final int COLUMN_IDX_TYPE = 0;

	public static final int COLUMN_IDX_OBJECT_ID = 1;

	public static final int COLUMN_IDX_NAME = 2;

	private EditorDataModel editorDataModel;

	public TransformationsDataModel(EditorDataModel editorDataModel) {
		super(new String[] { "type", "object-id", "name" }, 0);
		this.editorDataModel = editorDataModel;
	}

	private Transformations transformations;

	private DefaultMutableTreeNode transformationsNode;

	private List<Transformation> transformationsList = new ArrayList<Transformation>();

	public void loadTransformations(Transformations selectedTransformations, DefaultMutableTreeNode selectedNode) {
		// TODO (2022-12-11 #73): перенести очистку компонентов в метод clear
		this.setRowCount(0);

		for (Transformation transformation : selectedTransformations) {
			transformationsList.add(transformation);
			this.addRow(new Object[] {});
		}

		transformations = selectedTransformations;
		transformationsNode = selectedNode;
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		final Transformation transformation = transformationsList.get(row);
		switch (column) {
		case COLUMN_IDX_TYPE:
			break;
		case COLUMN_IDX_NAME:
			break;
		case COLUMN_IDX_OBJECT_ID:
			transformation.setId((String) aValue);
		default:
			break;
		}
		editorDataModel.nodesChanged(transformationsNode, new int[] { row });
	}

	@Override
	public Object getValueAt(int row, int column) {
		final Transformation transformation = transformationsList.get(row);
		switch (column) {
		case COLUMN_IDX_TYPE:
			if (transformation instanceof AttributeTransformation) {
				return "attributeTransformation";
			}
			if (transformation instanceof LinkTransformation) {
				return "linkTransformation";
			}
			return "transformation";
		case COLUMN_IDX_NAME:
			return "unknown";
		case COLUMN_IDX_OBJECT_ID:
			return transformation.getId();
		default:
			return null;
		}
	}

	public void insertTransformation() {
		final Transformation transformation = new Transformation();
		transformations.add(transformation);
		transformationsList.add(transformation);
		this.addRow(new Object[] {});
		final DefaultMutableTreeNode transformationNode = editorDataModel.createTransformationNode(transformation);
		transformationsNode.add(transformationNode);
		editorDataModel.insertNodeInto(transformationNode, transformationsNode, transformationsList.size() - 1);
	}

	public void insertLinkTransformation() {
		final Transformation transformation = new LinkTransformation();
		transformations.add(transformation);
		transformationsList.add(transformation);
		this.addRow(new Object[] {});
		final DefaultMutableTreeNode transformationNode = editorDataModel.createTransformationNode(transformation);
		transformationsNode.add(transformationNode);
		editorDataModel.insertNodeInto(transformationNode, transformationsNode, transformationsList.size() - 1);
	}

	public void insertAttributeTransformation() {
		final Transformation transformation = new AttributeTransformation();
		transformations.add(transformation);
		transformationsList.add(transformation);
		this.addRow(new Object[] {});
		final DefaultMutableTreeNode transformationNode = editorDataModel.createTransformationNode(transformation);
		transformationsNode.add(transformationNode);
		editorDataModel.insertNodeInto(transformationNode, transformationsNode, transformationsList.size() - 1);
	}

	public void deleteTransformation(int idx) {
		if (idx < 0) {
			return;
		}
		final Transformation transformation = transformationsList.get(idx);
		transformations.remove(transformation);
		transformationsList.remove(transformation);
		this.removeRow(idx);
		final DefaultMutableTreeNode transformationNode = (DefaultMutableTreeNode) transformationsNode.getChildAt(idx);
		editorDataModel.removeNodeFromParent(transformationNode);
	}

	public void clear() {
		transformationsList.clear();
		transformationsNode = null;
	}
}

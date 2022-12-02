package application.ui.gui.editor;

import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import planning.model.SystemTemplate;

public class ObjectTemplatesDataModel extends DefaultTableModel {

	private static final long serialVersionUID = 4569605859281139998L;

	private EditorDataModel editorDataModel;

	public ObjectTemplatesDataModel(EditorDataModel editorDataModel) {
		super(new String[] { "name", "id" }, 0);
		this.editorDataModel = editorDataModel;
	}

	private Class<?>[] types = new Class[] { String.class, String.class };

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return types[columnIndex];
	}

	public void loadObjectTemplates(SystemTemplate selectedObject, DefaultMutableTreeNode selectedNode) {
		// TODO Auto-generated method stub
	}
}

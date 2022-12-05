package application.ui.gui.editor;

import javax.swing.table.DefaultTableModel;

public class ActionFunctionsDataModel extends DefaultTableModel {

	private static final long serialVersionUID = -8585018378905034830L;

	private EditorDataModel editorDataModel;

	public static final int COLUMN_IDX_NAME = 0;

	public static final int COLUMN_IDX_TYPE = 1;

	public ActionFunctionsDataModel(EditorDataModel editorDataModel) {
		super(new String[] { "name", "type" }, 0);
		this.editorDataModel = editorDataModel;
	}

	private Class<?>[] types = new Class[] { String.class, String.class };

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return types[columnIndex];
	}
}

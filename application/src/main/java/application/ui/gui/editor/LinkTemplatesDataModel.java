package application.ui.gui.editor;

import javax.swing.table.DefaultTableModel;

public class LinkTemplatesDataModel extends DefaultTableModel {

	private static final long serialVersionUID = -6681920262779940696L;

	private EditorDataModel editorDataModel;

	public LinkTemplatesDataModel(EditorDataModel editorDataModel) {
		super(new String[] { "name", "id1", "id2" }, 0);
		this.editorDataModel = editorDataModel;
	}

	private Class<?>[] types = new Class[] { String.class, String.class, String.class };

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return types[columnIndex];
	}
}

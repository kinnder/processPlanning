package application.ui.gui.editor;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

import planning.model.Action;
import planning.model.ActionFunction;

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

	// TODO (2022-12-07 #73): пересмотреть положение globals
	static Globals globals = JsePlatform.standardGlobals();

	public void insertActionFunction() {
		// TODO (2022-12-07 #73): перенести в ActionFunction
		final ActionFunction actionFunction = new ActionFunction(globals, "");
		action.addActionFunction(actionFunction);
		actionFunctions.add(actionFunction);
		this.addRow(new Object[] {});
		final DefaultMutableTreeNode actionFunctionNode = editorDataModel.createActionFunctionNode(actionFunction);
		actionNode.add(actionFunctionNode);
		editorDataModel.insertNodeInto(actionFunctionNode, actionNode, actionFunctions.size() - 1);
	}

	public void deleteActionFunction(int idx) {
		if (idx < 0) {
			return;
		}

		final ActionFunction actionFunction = actionFunctions.get(idx);
		actionFunctions.remove(actionFunction);
		action.removeActionFunction(actionFunction);

		this.removeRow(idx);
		final DefaultMutableTreeNode actionFunctionNode = (DefaultMutableTreeNode) actionNode.getChildAt(idx);
		editorDataModel.removeNodeFromParent(actionFunctionNode);
	}

	private Action action;

	private DefaultMutableTreeNode actionNode;

	private List<ActionFunction> actionFunctions = new ArrayList<ActionFunction>();

	public void loadActionFunctions(Action selectedAction, DefaultMutableTreeNode selectedNode) {
		actionFunctions.clear();
		this.setRowCount(0);

		for (ActionFunction actionFunction : selectedAction.getActionFunctions()) {
			actionFunctions.add(actionFunction);
			this.addRow(new Object[] {});
		}

		action = selectedAction;
		actionNode = selectedNode;
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		// TODO (2022-12-07 #73): ActionFunction не содержит редактируемых полей
		// TODO (2022-12-17 #73): проверить корректность использования nodesChanged и nodeChanged
		editorDataModel.nodesChanged(actionNode, new int[] { row });
	}

	@Override
	public Object getValueAt(int row, int column) {
		// TODO (2022-12-07 #73): ActionFunction не содержит редактируемых полей
		final ActionFunction actionFunction = actionFunctions.get(row);
		switch (column) {
		case COLUMN_IDX_NAME:
			return "unknown";
		case COLUMN_IDX_TYPE:
			// TODO (2022-12-17 #73): должно быть что-то вроде getType().toString();
			final int type = actionFunction.getType();
			switch (type) {
			case (ActionFunction.TYPE_PARAMETER_UPDATER):
				return "parameterUpdater";
			case (ActionFunction.TYPE_PRECONDITION_CHECKER):
				return "preConditionChecker";
			default:
				return "unknown";
			}
		default:
			return null;
		}
	}

	public void clear() {
		// TODO Auto-generated method stub
	}
}

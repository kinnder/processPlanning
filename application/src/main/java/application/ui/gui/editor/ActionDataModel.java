package application.ui.gui.editor;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import planning.model.Action;

public class ActionDataModel {

	KeyAdapter jtfActionNameKeyAdapter = new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
			final String actionName = jtfActionName.getText();
			action.setName(actionName);
			editorDataModel.nodeChanged(actionNode);
		}
	};

	private JTextField jtfActionName;

	private EditorDataModel editorDataModel;

	public ActionDataModel(JTextField jtfActionName, EditorDataModel editorDataModel) {
		this(jtfActionName, editorDataModel, new ActionFunctionsDataModel(editorDataModel));
	}

	ActionDataModel(JTextField jtfActionName, EditorDataModel editorDataModel,
			ActionFunctionsDataModel actionFunctionsDataModel) {
		this.jtfActionName = jtfActionName;
		this.editorDataModel = editorDataModel;

		this.actionFunctionsDataModel = actionFunctionsDataModel;

		jtfActionName.addKeyListener(jtfActionNameKeyAdapter);
	}

	private ActionFunctionsDataModel actionFunctionsDataModel;

	public ActionFunctionsDataModel getActionFunctionsDataModel() {
		return this.actionFunctionsDataModel;
	}

	public void clear() {
		action = null;
		actionNode = null;
		actionFunctionsDataModel.clear();
	}

	private Action action;

	private DefaultMutableTreeNode actionNode;

	public void loadAction(Action selectedAction, DefaultMutableTreeNode selectedNode) {
		final String name = selectedAction.getName();
		jtfActionName.setText(name);

		action = selectedAction;
		actionNode = selectedNode;

		actionFunctionsDataModel.loadActionFunctions(selectedAction, selectedNode);
	}

	public void insertActionFunction() {
		actionFunctionsDataModel.insertActionFunction();
	}

	public void deleteActionFunction(int idx) {
		actionFunctionsDataModel.deleteActionFunction(idx);
	}
}

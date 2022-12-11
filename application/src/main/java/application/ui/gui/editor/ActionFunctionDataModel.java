package application.ui.gui.editor;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.DefaultMutableTreeNode;

import planning.model.ActionFunction;

public class ActionFunctionDataModel {

	private JComboBox<String> jcbActionFunctionType;

	private JTextArea jtaActionFunctionLines;

	private EditorDataModel editorDataModel;

	ItemListener jcbActionFunctionTypeItemListener = new ItemListener() {
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				if (actionFunction == null) {
					return;
				}
				editorDataModel.nodeChanged(actionFunctionNode);
			}
		}
	};

	DocumentListener jtaActionFunctionLinesDocumentListener = new DocumentListener() {
		@Override
		public void insertUpdate(DocumentEvent e) {
			String lines = jtaActionFunctionLines.getText();
			actionFunction.setScript(lines);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			String lines = jtaActionFunctionLines.getText();
			actionFunction.setScript(lines);
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			String lines = jtaActionFunctionLines.getText();
			actionFunction.setScript(lines);
		}
	};

	public ActionFunctionDataModel(JComboBox<String> jcbActionFunctionType, JTextArea jtaActionFunctionLines,
			EditorDataModel editorDataModel) {
		this.jcbActionFunctionType = jcbActionFunctionType;
		this.jtaActionFunctionLines = jtaActionFunctionLines;
		this.editorDataModel = editorDataModel;

		jcbActionFunctionType.addItemListener(jcbActionFunctionTypeItemListener);
		jtaActionFunctionLines.getDocument().addDocumentListener(jtaActionFunctionLinesDocumentListener);
	}

	public void clear() {
		actionFunction = null;
		actionFunctionNode = null;
	}

	private ActionFunction actionFunction;

	private DefaultMutableTreeNode actionFunctionNode;

	public void loadActionFunction(ActionFunction selectedActionFunction, DefaultMutableTreeNode selectedNode) {
		this.actionFunction = selectedActionFunction;
		this.actionFunctionNode = selectedNode;

		String lines = actionFunction.getScript();
		// TODO (2022-12-08 #73): типы функций не поддерживаются
		int type = 0;

		jtaActionFunctionLines.setText(lines);
		jcbActionFunctionType.setSelectedIndex(type);
	}
}

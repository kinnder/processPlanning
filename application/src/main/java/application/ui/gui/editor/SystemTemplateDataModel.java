package application.ui.gui.editor;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import planning.model.SystemTemplate;

public class SystemTemplateDataModel {

	private JTextField jtfSystemTemplateName;

	private JComboBox<String> jcbSystemTemplateType;

	private EditorDataModel editorDataModel;

	public static final int SYSTEM_TYPE_INITIAL = 0;

	public static final int SYSTEM_TYPE_REGULAR = 1;

	public static final int SYSTEM_TYPE_FINAL = 2;

	ItemListener jcbSystemTemplateTypeItemListener = new ItemListener() {
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				editorDataModel.nodeChanged(treeNode);
			}
		}
	};

	KeyAdapter jtfSystemTemplateNameKeyAdapter = new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
			editorDataModel.nodeChanged(treeNode);
		}
	};

	public SystemTemplateDataModel(JTextField jtfSystemTemplateName, JComboBox<String> jcbSystemTemplateType,
			EditorDataModel editorDataModel) {
		this.jtfSystemTemplateName = jtfSystemTemplateName;
		this.jcbSystemTemplateType = jcbSystemTemplateType;
		this.editorDataModel = editorDataModel;

		jtfSystemTemplateName.addKeyListener(jtfSystemTemplateNameKeyAdapter);
		jcbSystemTemplateType.addItemListener(jcbSystemTemplateTypeItemListener);
	}

	public void clear() {
		treeNode = null;
	}

	private DefaultMutableTreeNode treeNode;

	public void loadSystemTemplate(SystemTemplate selectedSystemTemplate, DefaultMutableTreeNode selectedNode) {
		String name = "System Template";
		int type = SYSTEM_TYPE_REGULAR;

		jtfSystemTemplateName.setText(name);
		jcbSystemTemplateType.setSelectedIndex(type);

		treeNode = selectedNode;
	}
}

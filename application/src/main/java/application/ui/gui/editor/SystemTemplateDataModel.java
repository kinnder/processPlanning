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

	private LinkTemplatesDataModel linkTemplatesDataModel;

	public LinkTemplatesDataModel getLinkTemplatesDataModel() {
		return this.linkTemplatesDataModel;
	}

	private ObjectTemplatesDataModel objectTemplatesDataModel;

	public ObjectTemplatesDataModel getObjectTemplatesDataModel() {
		return this.objectTemplatesDataModel;
	}

	public SystemTemplateDataModel(JTextField jtfSystemTemplateName, JComboBox<String> jcbSystemTemplateType,
			EditorDataModel editorDataModel) {
		this(jtfSystemTemplateName, jcbSystemTemplateType, editorDataModel, new LinkTemplatesDataModel(editorDataModel),
				new ObjectTemplatesDataModel(editorDataModel));
	}

	SystemTemplateDataModel(JTextField jtfSystemTemplateName, JComboBox<String> jcbSystemTemplateType,
			EditorDataModel editorDataModel, LinkTemplatesDataModel linkTemplatesDataModel,
			ObjectTemplatesDataModel objectTemplatesDataModel) {
		this.jtfSystemTemplateName = jtfSystemTemplateName;
		this.jcbSystemTemplateType = jcbSystemTemplateType;
		this.editorDataModel = editorDataModel;

		this.linkTemplatesDataModel = linkTemplatesDataModel;
		this.objectTemplatesDataModel = objectTemplatesDataModel;

		jtfSystemTemplateName.addKeyListener(jtfSystemTemplateNameKeyAdapter);
		jcbSystemTemplateType.addItemListener(jcbSystemTemplateTypeItemListener);
	}

	public void clear() {
		treeNode = null;
		objectTemplatesDataModel.clear();
		linkTemplatesDataModel.clear();
	}

	private DefaultMutableTreeNode treeNode;

	public void loadSystemTemplate(SystemTemplate selectedSystemTemplate, DefaultMutableTreeNode selectedNode) {
		final String name = "System Template";
		final int type = SYSTEM_TYPE_REGULAR;

		jtfSystemTemplateName.setText(name);
		jcbSystemTemplateType.setSelectedIndex(type);

		treeNode = selectedNode;

		objectTemplatesDataModel.loadObjectTemplates(selectedSystemTemplate, selectedNode);
		linkTemplatesDataModel.loadLinkTemplates(selectedSystemTemplate, selectedNode);
	}

	public void insertObjectTemplate() {
		objectTemplatesDataModel.insertObjectTemplate();
	}

	public void deleteObjectTemplate(int idx) {
		objectTemplatesDataModel.deleteObjectTemplate(idx);
	}

	public void insertLinkTemplate() {
		linkTemplatesDataModel.insertLinkTemplate();
	}

	public void deleteLinkTemplate(int idx) {
		linkTemplatesDataModel.deleteLinkTemplate(idx);
	}
}

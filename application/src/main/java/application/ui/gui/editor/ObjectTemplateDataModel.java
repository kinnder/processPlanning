package application.ui.gui.editor;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import planning.model.SystemObjectTemplate;

public class ObjectTemplateDataModel {

	private JTextField jtfObjectTemplateName;

	private JTextField jtfObjectTemplateId;

	private EditorDataModel editorDataModel;

	private SystemObjectTemplate objectTemplate;

	KeyAdapter jtfObjectTemplateNameKeyAdapter = new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
			editorDataModel.nodeChanged(treeNode);
		}
	};

	KeyAdapter jtfObjectTemplateIdKeyAdapter = new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
			final String objectTemplateId = jtfObjectTemplateId.getText();
			objectTemplate.setId(objectTemplateId);
			editorDataModel.nodeChanged(treeNode);
		}
	};

	private AttributeTemplatesDataModel attributeTemplatesDataModel;

	public AttributeTemplatesDataModel getAttributeTemplatesDataModel() {
		return this.attributeTemplatesDataModel;
	}

	public ObjectTemplateDataModel(JTextField jtfObjectTemplateName, JTextField jtfObjectTemplateId, EditorDataModel editorDataModel) {
		this(jtfObjectTemplateName, jtfObjectTemplateId, editorDataModel,
				new AttributeTemplatesDataModel(editorDataModel));
	}

	ObjectTemplateDataModel(JTextField jtfObjectTemplateName, JTextField jtfObjectTemplateId,
			EditorDataModel editorDataModel, AttributeTemplatesDataModel attributeTemplatesDataModel) {
		this.jtfObjectTemplateName = jtfObjectTemplateName;
		this.jtfObjectTemplateId = jtfObjectTemplateId;
		this.editorDataModel = editorDataModel;

		this.attributeTemplatesDataModel = attributeTemplatesDataModel;

		jtfObjectTemplateName.addKeyListener(jtfObjectTemplateNameKeyAdapter);
		jtfObjectTemplateId.addKeyListener(jtfObjectTemplateIdKeyAdapter);
	}

	private DefaultMutableTreeNode treeNode;

	public void clear() {
		objectTemplate = null;
		treeNode = null;
		attributeTemplatesDataModel.clear();
	}

	public void loadSystemObjectTemplate(SystemObjectTemplate selectedObjectTemplate, DefaultMutableTreeNode selectedNode) {
		final String name = "Object Template";
		final String id = selectedObjectTemplate.getId();

		jtfObjectTemplateName.setText(name);
		jtfObjectTemplateId.setText(id);

		objectTemplate = selectedObjectTemplate;
		treeNode = selectedNode;

		attributeTemplatesDataModel.loadAttributeTemplates(selectedObjectTemplate, selectedNode);
	}

	public void insertAttributeTemplate() {
		attributeTemplatesDataModel.insertAttributeTemplate();
	}

	public void deleteAttributeTemplate(int idx) {
		attributeTemplatesDataModel.deleteAttributeTemplate(idx);
	}
}

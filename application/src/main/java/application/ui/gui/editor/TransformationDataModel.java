package application.ui.gui.editor;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import planning.model.AttributeTransformation;
import planning.model.LinkTransformation;
import planning.model.Transformation;

public class TransformationDataModel {

	private JRadioButton jrbTransformation;

	private JTextField jtfTransformationObjectId;

	private JRadioButton jrbAttributeTransformation;

	private JTextField jtfAttributeTransformationName;

	private JTextField jtfAttributeTransformationValue;

	private JRadioButton jrbLinkTransformation;

	private JTextField jtfLinkTransformationName;

	private JTextField jtfLinkTransformationId2old;

	private JTextField jtfLinkTransformationId2new;

	private EditorDataModel editorDataModel;

	KeyAdapter jtfTransformationObjectIdKeyAdapter = new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
			String objectId = jtfTransformationObjectId.getText();
			transformation.setId(objectId);
			editorDataModel.nodeChanged(transformationNode);
		}
	};

	KeyAdapter jtfLinkTransformationNameKeyAdapter = new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
			String linkName = jtfLinkTransformationName.getText();
			linkTransformation.setLinkName(linkName);
			editorDataModel.nodeChanged(transformationNode);
		}
	};

	KeyAdapter jtfLinkTransformationId2oldKeyAdapter = new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
			String id2old = jtfLinkTransformationId2old.getText();
			linkTransformation.setId2Old(id2old);
			editorDataModel.nodeChanged(transformationNode);
		}
	};

	KeyAdapter jtfLinkTransformationId2newKeyAdapter = new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
			String id2new = jtfLinkTransformationId2new.getText();
			linkTransformation.setId2New(id2new);
			editorDataModel.nodeChanged(transformationNode);
		}
	};

	KeyAdapter jtfAttributeTransformationNameKeyAdapter = new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
			String attributeName = jtfAttributeTransformationName.getText();
			attributeTransformation.setAttributeName(attributeName);
			editorDataModel.nodeChanged(transformationNode);
		}
	};

	KeyAdapter jtfAttributeTransformationValueKeyAdapter = new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
			String attributeValue = jtfAttributeTransformationValue.getText();
			attributeTransformation.setAttributeValue(attributeValue);
			editorDataModel.nodeChanged(transformationNode);
		}
	};

	public TransformationDataModel(JRadioButton jrbTransformation, JTextField jtfTransformationObjectId,
			JRadioButton jrbAttributeTransformation, JTextField jtfAttributeTransformationName,
			JTextField jtfAttributeTransformationValue, JRadioButton jrbLinkTransformation,
			JTextField jtfLinkTransformationName, JTextField jtfLinkTransformationId2old,
			JTextField jtfLinkTransformationId2new, EditorDataModel editorDataModel) {
		this.jrbTransformation = jrbTransformation;
		this.jtfTransformationObjectId = jtfTransformationObjectId;
		this.jrbAttributeTransformation = jrbAttributeTransformation;
		this.jtfAttributeTransformationName = jtfAttributeTransformationName;
		this.jtfAttributeTransformationValue = jtfAttributeTransformationValue;
		this.jrbLinkTransformation = jrbLinkTransformation;
		this.jtfLinkTransformationName = jtfLinkTransformationName;
		this.jtfLinkTransformationId2old = jtfLinkTransformationId2old;
		this.jtfLinkTransformationId2new = jtfLinkTransformationId2new;
		this.editorDataModel = editorDataModel;

		jtfTransformationObjectId.addKeyListener(jtfTransformationObjectIdKeyAdapter);
		jtfLinkTransformationName.addKeyListener(jtfLinkTransformationNameKeyAdapter);
		jtfLinkTransformationId2old.addKeyListener(jtfLinkTransformationId2oldKeyAdapter);
		jtfLinkTransformationId2new.addKeyListener(jtfLinkTransformationId2newKeyAdapter);
		jtfAttributeTransformationName.addKeyListener(jtfAttributeTransformationNameKeyAdapter);
		jtfAttributeTransformationValue.addKeyListener(jtfAttributeTransformationValueKeyAdapter);
	}

	public void clear() {
		transformation = null;
		linkTransformation = null;
		attributeTransformation = null;
		transformationNode = null;
	}

	private Transformation transformation;

	private LinkTransformation linkTransformation;

	private AttributeTransformation attributeTransformation;

	private DefaultMutableTreeNode transformationNode;

	public void loadTransformation(Transformation selectedObject, DefaultMutableTreeNode selectedNode) {
		String objectId = selectedObject.getId();
		String linkName = "";
		String linkId2old = "";
		String linkId2new = "";
		String attributeName = "";
		String attributeValue = "";

		if (selectedObject instanceof LinkTransformation) {
			linkTransformation = (LinkTransformation) selectedObject;

			linkName = linkTransformation.getLinkName();
			linkId2old = linkTransformation.getId2Old();
			linkId2new = linkTransformation.getId2New();
			jrbLinkTransformation.setSelected(true);

			jrbTransformation.setEnabled(false);
			jrbLinkTransformation.setEnabled(true);
			jrbAttributeTransformation.setEnabled(false);
			jtfLinkTransformationName.setEnabled(true);
			jtfLinkTransformationId2new.setEnabled(true);
			jtfLinkTransformationId2old.setEnabled(true);
			jtfAttributeTransformationName.setEnabled(false);
			jtfAttributeTransformationValue.setEnabled(false);
		} else if (selectedObject instanceof AttributeTransformation) {
			attributeTransformation = (AttributeTransformation) selectedObject;

			attributeName = attributeTransformation.getAttributeName();
			attributeValue = attributeTransformation.getAttributeValue().toString();
			jrbAttributeTransformation.setSelected(true);

			jrbTransformation.setEnabled(false);
			jrbLinkTransformation.setEnabled(false);
			jrbAttributeTransformation.setEnabled(true);
			jtfLinkTransformationName.setEnabled(false);
			jtfLinkTransformationId2new.setEnabled(false);
			jtfLinkTransformationId2old.setEnabled(false);
			jtfAttributeTransformationName.setEnabled(true);
			jtfAttributeTransformationValue.setEnabled(true);
		} else {
			jrbTransformation.setSelected(true);
			jrbTransformation.setEnabled(true);
			jrbLinkTransformation.setEnabled(false);
			jrbAttributeTransformation.setEnabled(false);
			jtfLinkTransformationName.setEnabled(false);
			jtfLinkTransformationId2new.setEnabled(false);
			jtfLinkTransformationId2old.setEnabled(false);
			jtfAttributeTransformationName.setEnabled(false);
			jtfAttributeTransformationValue.setEnabled(false);
		}
		jtfTransformationObjectId.setText(objectId);
		jtfLinkTransformationName.setText(linkName);
		jtfLinkTransformationId2old.setText(linkId2old);
		jtfLinkTransformationId2new.setText(linkId2new);
		jtfAttributeTransformationName.setText(attributeName);
		jtfAttributeTransformationValue.setText(attributeValue);

		transformation = selectedObject;
		transformationNode = selectedNode;
	}
}

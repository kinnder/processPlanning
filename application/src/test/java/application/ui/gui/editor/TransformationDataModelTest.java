package application.ui.gui.editor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.AttributeTransformation;
import planning.model.LinkTransformation;
import planning.model.Transformation;

public class TransformationDataModelTest {

	@RegisterExtension
	JUnit5Mockery context = new JUnit5Mockery() {
		{
			setImposteriser(ByteBuddyClassImposteriser.INSTANCE);
		}
	};

	@AfterEach
	public void teardown() {
		context.assertIsSatisfied();
	}

	EditorDataModel editorDataModel_mock;

	JRadioButton jrbTransformation_mock;

	JTextField jtfTransformationObjectId_mock;

	JRadioButton jrbAttributeTransformation_mock;

	JTextField jtfAttributeTransformationName_mock;

	JTextField jtfAttributeTransformationValue_mock;

	JRadioButton jrbLinkTransformation_mock;

	JTextField jtfLinkTransformationName_mock;

	JTextField jtfLinkTransformationId2old_mock;

	JTextField jtfLinkTransformationId2new_mock;

	TransformationDataModel testable;

	@BeforeEach
	public void setup() {
		editorDataModel_mock = context.mock(EditorDataModel.class);
		jrbTransformation_mock = context.mock(JRadioButton.class, "jrbTransformation");
		jtfTransformationObjectId_mock = context.mock(JTextField.class, "jtfTrasformationObjectId");
		jrbAttributeTransformation_mock = context.mock(JRadioButton.class, "jrbAttributeTransformation");
		jtfAttributeTransformationName_mock = context.mock(JTextField.class, "jtfAttributeTransformationName");
		jtfAttributeTransformationValue_mock = context.mock(JTextField.class, "jtfAttributeTransformationValue");
		jrbLinkTransformation_mock = context.mock(JRadioButton.class, "jrbLinkTransformation");
		jtfLinkTransformationName_mock = context.mock(JTextField.class, "jtfLinkTransformationName");
		jtfLinkTransformationId2old_mock = context.mock(JTextField.class, "jtfLinkTransformationId2old");
		jtfLinkTransformationId2new_mock = context.mock(JTextField.class, "jtfLinkTransformationId2new");

		context.checking(new Expectations() {
			{
				oneOf(jtfTransformationObjectId_mock).addKeyListener(with(any(KeyListener.class)));

				oneOf(jtfLinkTransformationName_mock).addKeyListener(with(any(KeyListener.class)));

				oneOf(jtfLinkTransformationId2old_mock).addKeyListener(with(any(KeyListener.class)));

				oneOf(jtfLinkTransformationId2new_mock).addKeyListener(with(any(KeyListener.class)));

				oneOf(jtfAttributeTransformationName_mock).addKeyListener(with(any(KeyListener.class)));

				oneOf(jtfAttributeTransformationValue_mock).addKeyListener(with(any(KeyListener.class)));
			}
		});

		testable = new TransformationDataModel(jrbTransformation_mock, jtfTransformationObjectId_mock,
				jrbAttributeTransformation_mock, jtfAttributeTransformationName_mock,
				jtfAttributeTransformationValue_mock, jrbLinkTransformation_mock, jtfLinkTransformationName_mock,
				jtfLinkTransformationId2old_mock, jtfLinkTransformationId2new_mock, editorDataModel_mock);
	}

	@Test
	public void clear() {
		testable.clear();
	}

	@Test
	public void loadTransformation_transformation() {
		final Transformation transformation_mock = context.mock(Transformation.class);
		final DefaultMutableTreeNode transformationNode_mock = context.mock(DefaultMutableTreeNode.class);

		context.checking(new Expectations() {
			{
				oneOf(transformation_mock).getId();
				will(returnValue("id-123"));

				// --

				oneOf(jrbTransformation_mock).setSelected(true);

				// --

				oneOf(jrbTransformation_mock).setEnabled(true);

				oneOf(jrbLinkTransformation_mock).setEnabled(false);

				oneOf(jrbAttributeTransformation_mock).setEnabled(false);

				oneOf(jtfLinkTransformationName_mock).setEnabled(false);

				oneOf(jtfLinkTransformationId2new_mock).setEnabled(false);

				oneOf(jtfLinkTransformationId2old_mock).setEnabled(false);

				oneOf(jtfAttributeTransformationName_mock).setEnabled(false);

				oneOf(jtfAttributeTransformationValue_mock).setEnabled(false);

				// --

				oneOf(jtfTransformationObjectId_mock).setText("id-123");

				oneOf(jtfLinkTransformationName_mock).setText("");

				oneOf(jtfLinkTransformationId2old_mock).setText("");

				oneOf(jtfLinkTransformationId2new_mock).setText("");

				oneOf(jtfAttributeTransformationName_mock).setText("");

				oneOf(jtfAttributeTransformationValue_mock).setText("");
			}
		});

		testable.loadTransformation(transformation_mock, transformationNode_mock);
	}

	@Test
	public void loadTransformation_linkTransformation() {
		final LinkTransformation transformation_mock = context.mock(LinkTransformation.class);
		final DefaultMutableTreeNode transformationNode_mock = context.mock(DefaultMutableTreeNode.class);

		context.checking(new Expectations() {
			{
				oneOf(transformation_mock).getId();
				will(returnValue("id-123"));

				// --

				oneOf(transformation_mock).getLinkName();
				will(returnValue("link-name"));

				oneOf(transformation_mock).getId2Old();
				will(returnValue("id2-old"));

				oneOf(transformation_mock).getId2New();
				will(returnValue("id2-new"));

				oneOf(jrbLinkTransformation_mock).setSelected(true);

				// --

				oneOf(jrbTransformation_mock).setEnabled(false);

				oneOf(jrbLinkTransformation_mock).setEnabled(true);

				oneOf(jrbAttributeTransformation_mock).setEnabled(false);

				oneOf(jtfLinkTransformationName_mock).setEnabled(true);

				oneOf(jtfLinkTransformationId2new_mock).setEnabled(true);

				oneOf(jtfLinkTransformationId2old_mock).setEnabled(true);

				oneOf(jtfAttributeTransformationName_mock).setEnabled(false);

				oneOf(jtfAttributeTransformationValue_mock).setEnabled(false);

				// --

				oneOf(jtfTransformationObjectId_mock).setText("id-123");

				oneOf(jtfLinkTransformationName_mock).setText("link-name");

				oneOf(jtfLinkTransformationId2old_mock).setText("id2-old");

				oneOf(jtfLinkTransformationId2new_mock).setText("id2-new");

				oneOf(jtfAttributeTransformationName_mock).setText("");

				oneOf(jtfAttributeTransformationValue_mock).setText("");
			}
		});

		testable.loadTransformation(transformation_mock, transformationNode_mock);
	}

	@Test
	public void loadTransformation_attributeTransformation() {
		final AttributeTransformation transformation_mock = context.mock(AttributeTransformation.class);
		final DefaultMutableTreeNode transformationNode_mock = context.mock(DefaultMutableTreeNode.class);

		context.checking(new Expectations() {
			{
				oneOf(transformation_mock).getId();
				will(returnValue("id-123"));

				// --

				oneOf(transformation_mock).getAttributeName();
				will(returnValue("attribute-name"));

				oneOf(transformation_mock).getAttributeValue();
				will(returnValue("attribute-value"));

				oneOf(jrbAttributeTransformation_mock).setSelected(true);

				// --

				oneOf(jrbTransformation_mock).setEnabled(false);

				oneOf(jrbLinkTransformation_mock).setEnabled(false);

				oneOf(jrbAttributeTransformation_mock).setEnabled(true);

				oneOf(jtfLinkTransformationName_mock).setEnabled(false);

				oneOf(jtfLinkTransformationId2new_mock).setEnabled(false);

				oneOf(jtfLinkTransformationId2old_mock).setEnabled(false);

				oneOf(jtfAttributeTransformationName_mock).setEnabled(true);

				oneOf(jtfAttributeTransformationValue_mock).setEnabled(true);

				// --

				oneOf(jtfTransformationObjectId_mock).setText("id-123");

				oneOf(jtfLinkTransformationName_mock).setText("");

				oneOf(jtfLinkTransformationId2old_mock).setText("");

				oneOf(jtfLinkTransformationId2new_mock).setText("");

				oneOf(jtfAttributeTransformationName_mock).setText("attribute-name");

				oneOf(jtfAttributeTransformationValue_mock).setText("attribute-value");
			}
		});

		testable.loadTransformation(transformation_mock, transformationNode_mock);
	}

	@Test
	public void transformationObjectId_keyReleased() {
		final Transformation transformation_mock = context.mock(Transformation.class);
		final DefaultMutableTreeNode transformationNode_mock = context.mock(DefaultMutableTreeNode.class);
		context.checking(new Expectations() {
			{
				oneOf(transformation_mock).getId();
				will(returnValue("id-123"));

				// --

				oneOf(jrbTransformation_mock).setSelected(true);

				// --

				oneOf(jrbTransformation_mock).setEnabled(true);

				oneOf(jrbLinkTransformation_mock).setEnabled(false);

				oneOf(jrbAttributeTransformation_mock).setEnabled(false);

				oneOf(jtfLinkTransformationName_mock).setEnabled(false);

				oneOf(jtfLinkTransformationId2new_mock).setEnabled(false);

				oneOf(jtfLinkTransformationId2old_mock).setEnabled(false);

				oneOf(jtfAttributeTransformationName_mock).setEnabled(false);

				oneOf(jtfAttributeTransformationValue_mock).setEnabled(false);

				// --

				oneOf(jtfTransformationObjectId_mock).setText("id-123");

				oneOf(jtfLinkTransformationName_mock).setText("");

				oneOf(jtfLinkTransformationId2old_mock).setText("");

				oneOf(jtfLinkTransformationId2new_mock).setText("");

				oneOf(jtfAttributeTransformationName_mock).setText("");

				oneOf(jtfAttributeTransformationValue_mock).setText("");
			}
		});
		testable.loadTransformation(transformation_mock, transformationNode_mock);

		final KeyEvent keyEvent_mock = context.mock(KeyEvent.class);
		context.checking(new Expectations() {
			{
				oneOf(jtfTransformationObjectId_mock).getText();
				will(returnValue("new-object-id"));

				oneOf(transformation_mock).setId("new-object-id");

				oneOf(editorDataModel_mock).nodeChanged(transformationNode_mock);
			}
		});

		testable.jtfTransformationObjectIdKeyAdapter.keyReleased(keyEvent_mock);
	}

	@Test
	public void linkTransformationName_keyReleased() {
		final LinkTransformation transformation_mock = context.mock(LinkTransformation.class);
		final DefaultMutableTreeNode transformationNode_mock = context.mock(DefaultMutableTreeNode.class);
		context.checking(new Expectations() {
			{
				oneOf(transformation_mock).getId();
				will(returnValue("id-123"));

				// --

				oneOf(transformation_mock).getLinkName();
				will(returnValue("link-name"));

				oneOf(transformation_mock).getId2Old();
				will(returnValue("id2-old"));

				oneOf(transformation_mock).getId2New();
				will(returnValue("id2-new"));

				oneOf(jrbLinkTransformation_mock).setSelected(true);

				// --

				oneOf(jrbTransformation_mock).setEnabled(false);

				oneOf(jrbLinkTransformation_mock).setEnabled(true);

				oneOf(jrbAttributeTransformation_mock).setEnabled(false);

				oneOf(jtfLinkTransformationName_mock).setEnabled(true);

				oneOf(jtfLinkTransformationId2new_mock).setEnabled(true);

				oneOf(jtfLinkTransformationId2old_mock).setEnabled(true);

				oneOf(jtfAttributeTransformationName_mock).setEnabled(false);

				oneOf(jtfAttributeTransformationValue_mock).setEnabled(false);

				// --

				oneOf(jtfTransformationObjectId_mock).setText("id-123");

				oneOf(jtfLinkTransformationName_mock).setText("link-name");

				oneOf(jtfLinkTransformationId2old_mock).setText("id2-old");

				oneOf(jtfLinkTransformationId2new_mock).setText("id2-new");

				oneOf(jtfAttributeTransformationName_mock).setText("");

				oneOf(jtfAttributeTransformationValue_mock).setText("");
			}
		});
		testable.loadTransformation(transformation_mock, transformationNode_mock);

		final KeyEvent keyEvent_mock = context.mock(KeyEvent.class);
		context.checking(new Expectations() {
			{
				oneOf(jtfLinkTransformationName_mock).getText();
				will(returnValue("new-link-name"));

				oneOf(transformation_mock).setLinkName("new-link-name");

				oneOf(editorDataModel_mock).nodeChanged(transformationNode_mock);
			}
		});

		testable.jtfLinkTransformationNameKeyAdapter.keyReleased(keyEvent_mock);
	}

	@Test
	public void linkTransformationId2old_keyReleased() {
		final LinkTransformation transformation_mock = context.mock(LinkTransformation.class);
		final DefaultMutableTreeNode transformationNode_mock = context.mock(DefaultMutableTreeNode.class);
		context.checking(new Expectations() {
			{
				oneOf(transformation_mock).getId();
				will(returnValue("id-123"));

				// --

				oneOf(transformation_mock).getLinkName();
				will(returnValue("link-name"));

				oneOf(transformation_mock).getId2Old();
				will(returnValue("id2-old"));

				oneOf(transformation_mock).getId2New();
				will(returnValue("id2-new"));

				oneOf(jrbLinkTransformation_mock).setSelected(true);

				// --

				oneOf(jrbTransformation_mock).setEnabled(false);

				oneOf(jrbLinkTransformation_mock).setEnabled(true);

				oneOf(jrbAttributeTransformation_mock).setEnabled(false);

				oneOf(jtfLinkTransformationName_mock).setEnabled(true);

				oneOf(jtfLinkTransformationId2new_mock).setEnabled(true);

				oneOf(jtfLinkTransformationId2old_mock).setEnabled(true);

				oneOf(jtfAttributeTransformationName_mock).setEnabled(false);

				oneOf(jtfAttributeTransformationValue_mock).setEnabled(false);

				// --

				oneOf(jtfTransformationObjectId_mock).setText("id-123");

				oneOf(jtfLinkTransformationName_mock).setText("link-name");

				oneOf(jtfLinkTransformationId2old_mock).setText("id2-old");

				oneOf(jtfLinkTransformationId2new_mock).setText("id2-new");

				oneOf(jtfAttributeTransformationName_mock).setText("");

				oneOf(jtfAttributeTransformationValue_mock).setText("");
			}
		});
		testable.loadTransformation(transformation_mock, transformationNode_mock);

		final KeyEvent keyEvent_mock = context.mock(KeyEvent.class);
		context.checking(new Expectations() {
			{
				oneOf(jtfLinkTransformationId2old_mock).getText();
				will(returnValue("new-id2old"));

				oneOf(transformation_mock).setId2Old("new-id2old");

				oneOf(editorDataModel_mock).nodeChanged(transformationNode_mock);
			}
		});

		testable.jtfLinkTransformationId2oldKeyAdapter.keyReleased(keyEvent_mock);
	}

	@Test
	public void linkTransformationId2new_keyReleased() {
		final LinkTransformation transformation_mock = context.mock(LinkTransformation.class);
		final DefaultMutableTreeNode transformationNode_mock = context.mock(DefaultMutableTreeNode.class);
		context.checking(new Expectations() {
			{
				oneOf(transformation_mock).getId();
				will(returnValue("id-123"));

				// --

				oneOf(transformation_mock).getLinkName();
				will(returnValue("link-name"));

				oneOf(transformation_mock).getId2Old();
				will(returnValue("id2-old"));

				oneOf(transformation_mock).getId2New();
				will(returnValue("id2-new"));

				oneOf(jrbLinkTransformation_mock).setSelected(true);

				// --

				oneOf(jrbTransformation_mock).setEnabled(false);

				oneOf(jrbLinkTransformation_mock).setEnabled(true);

				oneOf(jrbAttributeTransformation_mock).setEnabled(false);

				oneOf(jtfLinkTransformationName_mock).setEnabled(true);

				oneOf(jtfLinkTransformationId2new_mock).setEnabled(true);

				oneOf(jtfLinkTransformationId2old_mock).setEnabled(true);

				oneOf(jtfAttributeTransformationName_mock).setEnabled(false);

				oneOf(jtfAttributeTransformationValue_mock).setEnabled(false);

				// --

				oneOf(jtfTransformationObjectId_mock).setText("id-123");

				oneOf(jtfLinkTransformationName_mock).setText("link-name");

				oneOf(jtfLinkTransformationId2old_mock).setText("id2-old");

				oneOf(jtfLinkTransformationId2new_mock).setText("id2-new");

				oneOf(jtfAttributeTransformationName_mock).setText("");

				oneOf(jtfAttributeTransformationValue_mock).setText("");
			}
		});
		testable.loadTransformation(transformation_mock, transformationNode_mock);

		final KeyEvent keyEvent_mock = context.mock(KeyEvent.class);
		context.checking(new Expectations() {
			{
				oneOf(jtfLinkTransformationId2new_mock).getText();
				will(returnValue("new-id2new"));

				oneOf(transformation_mock).setId2New("new-id2new");

				oneOf(editorDataModel_mock).nodeChanged(transformationNode_mock);
			}
		});

		testable.jtfLinkTransformationId2newKeyAdapter.keyReleased(keyEvent_mock);
	}

	@Test
	public void attributeTransformationName_keyReleased() {
		final AttributeTransformation transformation_mock = context.mock(AttributeTransformation.class);
		final DefaultMutableTreeNode transformationNode_mock = context.mock(DefaultMutableTreeNode.class);
		context.checking(new Expectations() {
			{
				oneOf(transformation_mock).getId();
				will(returnValue("id-123"));

				// --

				oneOf(transformation_mock).getAttributeName();
				will(returnValue("attribute-name"));

				oneOf(transformation_mock).getAttributeValue();
				will(returnValue("attribute-value"));

				oneOf(jrbAttributeTransformation_mock).setSelected(true);

				// --

				oneOf(jrbTransformation_mock).setEnabled(false);

				oneOf(jrbLinkTransformation_mock).setEnabled(false);

				oneOf(jrbAttributeTransformation_mock).setEnabled(true);

				oneOf(jtfLinkTransformationName_mock).setEnabled(false);

				oneOf(jtfLinkTransformationId2new_mock).setEnabled(false);

				oneOf(jtfLinkTransformationId2old_mock).setEnabled(false);

				oneOf(jtfAttributeTransformationName_mock).setEnabled(true);

				oneOf(jtfAttributeTransformationValue_mock).setEnabled(true);

				// --

				oneOf(jtfTransformationObjectId_mock).setText("id-123");

				oneOf(jtfLinkTransformationName_mock).setText("");

				oneOf(jtfLinkTransformationId2old_mock).setText("");

				oneOf(jtfLinkTransformationId2new_mock).setText("");

				oneOf(jtfAttributeTransformationName_mock).setText("attribute-name");

				oneOf(jtfAttributeTransformationValue_mock).setText("attribute-value");
			}
		});
		testable.loadTransformation(transformation_mock, transformationNode_mock);

		final KeyEvent keyEvent_mock = context.mock(KeyEvent.class);
		context.checking(new Expectations() {
			{
				oneOf(jtfAttributeTransformationName_mock).getText();
				will(returnValue("new-attributeName"));

				oneOf(transformation_mock).setAttributeName("new-attributeName");

				oneOf(editorDataModel_mock).nodeChanged(transformationNode_mock);
			}
		});

		testable.jtfAttributeTransformationNameKeyAdapter.keyReleased(keyEvent_mock);
	}

	@Test
	public void attributeTransformationValue_keyReleased() {
		final AttributeTransformation transformation_mock = context.mock(AttributeTransformation.class);
		final DefaultMutableTreeNode transformationNode_mock = context.mock(DefaultMutableTreeNode.class);
		context.checking(new Expectations() {
			{
				oneOf(transformation_mock).getId();
				will(returnValue("id-123"));

				// --

				oneOf(transformation_mock).getAttributeName();
				will(returnValue("attribute-name"));

				oneOf(transformation_mock).getAttributeValue();
				will(returnValue("attribute-value"));

				oneOf(jrbAttributeTransformation_mock).setSelected(true);

				// --

				oneOf(jrbTransformation_mock).setEnabled(false);

				oneOf(jrbLinkTransformation_mock).setEnabled(false);

				oneOf(jrbAttributeTransformation_mock).setEnabled(true);

				oneOf(jtfLinkTransformationName_mock).setEnabled(false);

				oneOf(jtfLinkTransformationId2new_mock).setEnabled(false);

				oneOf(jtfLinkTransformationId2old_mock).setEnabled(false);

				oneOf(jtfAttributeTransformationName_mock).setEnabled(true);

				oneOf(jtfAttributeTransformationValue_mock).setEnabled(true);

				// --

				oneOf(jtfTransformationObjectId_mock).setText("id-123");

				oneOf(jtfLinkTransformationName_mock).setText("");

				oneOf(jtfLinkTransformationId2old_mock).setText("");

				oneOf(jtfLinkTransformationId2new_mock).setText("");

				oneOf(jtfAttributeTransformationName_mock).setText("attribute-name");

				oneOf(jtfAttributeTransformationValue_mock).setText("attribute-value");
			}
		});
		testable.loadTransformation(transformation_mock, transformationNode_mock);

		final KeyEvent keyEvent_mock = context.mock(KeyEvent.class);
		context.checking(new Expectations() {
			{
				oneOf(jtfAttributeTransformationValue_mock).getText();
				will(returnValue("new-attributeValue"));

				oneOf(transformation_mock).setAttributeValue("new-attributeValue");

				oneOf(editorDataModel_mock).nodeChanged(transformationNode_mock);
			}
		});

		testable.jtfAttributeTransformationValueKeyAdapter.keyReleased(keyEvent_mock);
	}
}

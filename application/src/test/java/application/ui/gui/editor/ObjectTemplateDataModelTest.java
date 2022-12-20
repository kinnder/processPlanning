package application.ui.gui.editor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.SystemObjectTemplate;

public class ObjectTemplateDataModelTest {

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

	JTextField jtfObjectTemplateName_mock;

	JTextField jtfObjectTemplateId_mock;

	EditorDataModel editorDataModel_mock;

	ObjectTemplateDataModel testable;

	@BeforeEach
	public void setup() {
		jtfObjectTemplateName_mock = context.mock(JTextField.class, "objectTemplateName");
		jtfObjectTemplateId_mock = context.mock(JTextField.class, "objectTemplateId");
		editorDataModel_mock = context.mock(EditorDataModel.class);

		context.checking(new Expectations() {
			{
				oneOf(jtfObjectTemplateName_mock).addKeyListener(with(any(KeyListener.class)));

				oneOf(jtfObjectTemplateId_mock).addKeyListener(with(any(KeyListener.class)));
			}
		});

		testable = new ObjectTemplateDataModel(jtfObjectTemplateName_mock, jtfObjectTemplateId_mock,
				editorDataModel_mock);
	}

	@Test
	public void loadSystemObjectTemplate() {
		final SystemObjectTemplate selectedObjectTemplate_mock = context.mock(SystemObjectTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);

		context.checking(new Expectations() {
			{
				oneOf(selectedObjectTemplate_mock).getId();
				will(returnValue("object-id"));

				oneOf(jtfObjectTemplateName_mock).setText("Object Template");

				oneOf(jtfObjectTemplateId_mock).setText("object-id");
			}
		});

		testable.loadSystemObjectTemplate(selectedObjectTemplate_mock, selectedNode_mock);
	}

	@Test
	public void clear() {
		testable.clear();
	}

	@Test
	public void objectTemplateName_keyReleased() {
		final SystemObjectTemplate selectedObjectTemplate_mock = context.mock(SystemObjectTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);

		context.checking(new Expectations() {
			{
				oneOf(selectedObjectTemplate_mock).getId();
				will(returnValue("object-id"));

				oneOf(jtfObjectTemplateName_mock).setText("Object Template");

				oneOf(jtfObjectTemplateId_mock).setText("object-id");
			}
		});
		testable.loadSystemObjectTemplate(selectedObjectTemplate_mock, selectedNode_mock);

		final KeyEvent keyEvent_mock = context.mock(KeyEvent.class);
		context.checking(new Expectations() {
			{
				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.jtfObjectTemplateNameKeyAdapter.keyReleased(keyEvent_mock);
	}

	@Test
	public void objectTemplateId_keyReleased() {
		final SystemObjectTemplate selectedObjectTemplate_mock = context.mock(SystemObjectTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);

		context.checking(new Expectations() {
			{
				oneOf(selectedObjectTemplate_mock).getId();
				will(returnValue("object-id"));

				oneOf(jtfObjectTemplateName_mock).setText("Object Template");

				oneOf(jtfObjectTemplateId_mock).setText("object-id");
			}
		});
		testable.loadSystemObjectTemplate(selectedObjectTemplate_mock, selectedNode_mock);

		final KeyEvent keyEvent_mock = context.mock(KeyEvent.class);
		context.checking(new Expectations() {
			{
				oneOf(jtfObjectTemplateId_mock).getText();
				will(returnValue("new-object-id"));

				oneOf(selectedObjectTemplate_mock).setId("new-object-id");

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.jtfObjectTemplateIdKeyAdapter.keyReleased(keyEvent_mock);
	}
}

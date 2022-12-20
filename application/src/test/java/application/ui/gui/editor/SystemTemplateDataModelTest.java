package application.ui.gui.editor;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.SystemTemplate;

public class SystemTemplateDataModelTest {

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

	SystemTemplateDataModel testable;

	JTextField jtfSystemTemplateName_mock;

	JComboBox<String> jcbSystemTemplateType_mock;

	@SuppressWarnings("unchecked")
	@BeforeEach
	public void setup() {
		editorDataModel_mock = context.mock(EditorDataModel.class);
		jtfSystemTemplateName_mock = context.mock(JTextField.class);
		jcbSystemTemplateType_mock = context.mock(JComboBox.class);

		context.checking(new Expectations() {
			{
				oneOf(jcbSystemTemplateType_mock).addItemListener(with(any(ItemListener.class)));

				oneOf(jtfSystemTemplateName_mock).addKeyListener(with(any(KeyListener.class)));
			}
		});

		testable = new SystemTemplateDataModel(jtfSystemTemplateName_mock, jcbSystemTemplateType_mock,
				editorDataModel_mock);
	}

	@Test
	public void loadSystemTemplate_regular() {
		final SystemTemplate selectedSystem_mock = context.mock(SystemTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);

		context.checking(new Expectations() {
			{
				oneOf(jtfSystemTemplateName_mock).setText("System Template");

				oneOf(jcbSystemTemplateType_mock).setSelectedIndex(SystemTemplateDataModel.SYSTEM_TYPE_REGULAR);
			}
		});

		testable.loadSystemTemplate(selectedSystem_mock, selectedNode_mock);
	}

	@Test
	public void clear() {
		testable.clear();
	}

	@Test
	public void systemType_ItemStateChanged_DESELECTED() {
		final ItemEvent itemEvent_mock = context.mock(ItemEvent.class);
		context.checking(new Expectations() {
			{
				oneOf(itemEvent_mock).getStateChange();
				will(returnValue(ItemEvent.DESELECTED));
			}
		});

		testable.jcbSystemTemplateTypeItemListener.itemStateChanged(itemEvent_mock);
	}

	@Test
	public void systemType_ItemStateChanged_SELECTED_regular() {
		final SystemTemplate selectedSystem_mock = context.mock(SystemTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);

		context.checking(new Expectations() {
			{
				oneOf(jtfSystemTemplateName_mock).setText("System Template");

				oneOf(jcbSystemTemplateType_mock).setSelectedIndex(SystemTemplateDataModel.SYSTEM_TYPE_REGULAR);
			}
		});
		testable.loadSystemTemplate(selectedSystem_mock, selectedNode_mock);

		final ItemEvent itemEvent_mock = context.mock(ItemEvent.class);
		context.checking(new Expectations() {
			{
				oneOf(itemEvent_mock).getStateChange();
				will(returnValue(ItemEvent.SELECTED));

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.jcbSystemTemplateTypeItemListener.itemStateChanged(itemEvent_mock);
	}

	@Test
	public void systemName_keyReleased() {
		final SystemTemplate selectedSystem_mock = context.mock(SystemTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);

		context.checking(new Expectations() {
			{
				oneOf(jtfSystemTemplateName_mock).setText("System Template");

				oneOf(jcbSystemTemplateType_mock).setSelectedIndex(SystemTemplateDataModel.SYSTEM_TYPE_REGULAR);
			}
		});
		testable.loadSystemTemplate(selectedSystem_mock, selectedNode_mock);

		final KeyEvent keyEvent_mock = context.mock(KeyEvent.class);
		context.checking(new Expectations() {
			{
				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.jtfSystemTemplateNameKeyAdapter.keyReleased(keyEvent_mock);
	}
}

package application.ui.gui.editor;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

	JTextField jtfSystemTemplateName_mock;

	JComboBox<String> jcbSystemTemplateType_mock;

	LinkTemplatesDataModel linkTemplatesDataModel_mock;

	ObjectTemplatesDataModel objectTemplatesDataModel_mock;

	SystemTemplateDataModel testable;

	@SuppressWarnings("unchecked")
	@BeforeEach
	public void setup() {
		editorDataModel_mock = context.mock(EditorDataModel.class);
		jtfSystemTemplateName_mock = context.mock(JTextField.class);
		jcbSystemTemplateType_mock = context.mock(JComboBox.class);
		linkTemplatesDataModel_mock = context.mock(LinkTemplatesDataModel.class);
		objectTemplatesDataModel_mock = context.mock(ObjectTemplatesDataModel.class);

		context.checking(new Expectations() {
			{
				oneOf(jcbSystemTemplateType_mock).addItemListener(with(any(ItemListener.class)));

				oneOf(jtfSystemTemplateName_mock).addKeyListener(with(any(KeyListener.class)));
			}
		});

		testable = new SystemTemplateDataModel(jtfSystemTemplateName_mock, jcbSystemTemplateType_mock,
				editorDataModel_mock, linkTemplatesDataModel_mock, objectTemplatesDataModel_mock);
	}

	@Test
	public void newInstance() {
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

				oneOf(linkTemplatesDataModel_mock).loadLinkTemplates(selectedSystem_mock, selectedNode_mock);

				oneOf(objectTemplatesDataModel_mock).loadObjectTemplates(selectedSystem_mock, selectedNode_mock);
			}
		});

		testable.loadSystemTemplate(selectedSystem_mock, selectedNode_mock);
	}

	@Test
	public void clear() {
		context.checking(new Expectations() {
			{
				oneOf(linkTemplatesDataModel_mock).clear();

				oneOf(objectTemplatesDataModel_mock).clear();
			}
		});

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

				oneOf(linkTemplatesDataModel_mock).loadLinkTemplates(selectedSystem_mock, selectedNode_mock);

				oneOf(objectTemplatesDataModel_mock).loadObjectTemplates(selectedSystem_mock, selectedNode_mock);
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

				oneOf(linkTemplatesDataModel_mock).loadLinkTemplates(selectedSystem_mock, selectedNode_mock);

				oneOf(objectTemplatesDataModel_mock).loadObjectTemplates(selectedSystem_mock, selectedNode_mock);
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

	@Test
	public void getLinkTemplatesDataModel() {
		assertEquals(linkTemplatesDataModel_mock, testable.getLinkTemplatesDataModel());
	}

	@Test
	public void getObjectTemplatesDataModel() {
		assertEquals(objectTemplatesDataModel_mock, testable.getObjectTemplatesDataModel());
	}

	@Test
	public void insertObjectTemplate() {
		context.checking(new Expectations() {
			{
				oneOf(objectTemplatesDataModel_mock).insertObjectTemplate();
			}
		});

		testable.insertObjectTemplate();
	}

	@Test
	public void deleteObjectTemplate() {
		context.checking(new Expectations() {
			{
				oneOf(objectTemplatesDataModel_mock).deleteObjectTemplate(2);
			}
		});

		testable.deleteObjectTemplate(2);
	}

	@Test
	public void insertLinkTemplate() {
		context.checking(new Expectations() {
			{
				oneOf(linkTemplatesDataModel_mock).insertLinkTemplate();
			}
		});

		testable.insertLinkTemplate();
	}

	@Test
	public void deleteLinkTemplate() {
		context.checking(new Expectations() {
			{
				oneOf(linkTemplatesDataModel_mock).deleteLinkTemplate(2);
			}
		});

		testable.deleteLinkTemplate(2);
	}
}

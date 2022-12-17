package application.ui.gui.editor;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.tree.DefaultMutableTreeNode;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.ActionFunction;

public class ActionFunctionDataModelTest {

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

	JComboBox<String> jcbActionFunctionType_mock;

	JTextArea jtaActionFunctionLines_mock;

	ActionFunctionDataModel testable;

	@SuppressWarnings("unchecked")
	@BeforeEach
	public void setup() {
		editorDataModel_mock = context.mock(EditorDataModel.class);
		jcbActionFunctionType_mock = context.mock(JComboBox.class);
		jtaActionFunctionLines_mock = context.mock(JTextArea.class);
		final Document document_mock = context.mock(Document.class);

		context.checking(new Expectations() {
			{
				oneOf(jcbActionFunctionType_mock).addItemListener(with(any(ItemListener.class)));

				oneOf(jtaActionFunctionLines_mock).getDocument();
				will(returnValue(document_mock));

				oneOf(document_mock).addDocumentListener(with(any(DocumentListener.class)));
			}
		});

		testable = new ActionFunctionDataModel(jcbActionFunctionType_mock, jtaActionFunctionLines_mock,
				editorDataModel_mock);
	}

	@Test
	public void clear() {
		testable.clear();
	}

	@Test
	public void loadActionFunction() {
		final ActionFunction actionFunction_mock = context.mock(ActionFunction.class);
		final DefaultMutableTreeNode treeNode_mock = context.mock(DefaultMutableTreeNode.class);

		context.checking(new Expectations() {
			{
				oneOf(actionFunction_mock).getScript();
				will(returnValue("function-code"));

				oneOf(actionFunction_mock).getType();
				will(returnValue(ActionFunction.TYPE_PARAMETER_UPDATER));

				oneOf(jtaActionFunctionLines_mock).setText("function-code");

				oneOf(jcbActionFunctionType_mock).setSelectedIndex(ActionFunction.TYPE_PARAMETER_UPDATER);
			}
		});

		testable.loadActionFunction(actionFunction_mock, treeNode_mock);
	}

	@Test
	public void actionFunctionType_ItemStateChanged_DESELECTED() {
		final ItemEvent itemEvent_mock = context.mock(ItemEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(itemEvent_mock).getStateChange();
				will(returnValue(ItemEvent.DESELECTED));
			}
		});

		testable.jcbActionFunctionTypeItemListener.itemStateChanged(itemEvent_mock);
	}

	@Test
	public void actionFunctionType_ItemStateChanged_SELECTED() {
		final ItemEvent itemEvent_mock = context.mock(ItemEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(itemEvent_mock).getStateChange();
				will(returnValue(ItemEvent.SELECTED));
			}
		});

		testable.jcbActionFunctionTypeItemListener.itemStateChanged(itemEvent_mock);
	}

	@Test
	public void actionFunctionType_ItemStateChanged_SELECTED_anyType() {
		final ActionFunction actionFunction_mock = context.mock(ActionFunction.class);
		final DefaultMutableTreeNode treeNode_mock = context.mock(DefaultMutableTreeNode.class);
		context.checking(new Expectations() {
			{
				oneOf(actionFunction_mock).getScript();
				will(returnValue("function-code"));

				oneOf(actionFunction_mock).getType();
				will(returnValue(ActionFunction.TYPE_PARAMETER_UPDATER));

				oneOf(jtaActionFunctionLines_mock).setText("function-code");

				oneOf(jcbActionFunctionType_mock).setSelectedIndex(ActionFunction.TYPE_PARAMETER_UPDATER);
			}
		});
		testable.loadActionFunction(actionFunction_mock, treeNode_mock);

		final ItemEvent itemEvent_mock = context.mock(ItemEvent.class);
		context.checking(new Expectations() {
			{
				oneOf(itemEvent_mock).getStateChange();
				will(returnValue(ItemEvent.SELECTED));

				oneOf(jcbActionFunctionType_mock).getSelectedIndex();
				will(returnValue(ActionFunction.TYPE_PRECONDITION_CHECKER));

				oneOf(actionFunction_mock).setType(ActionFunction.TYPE_PRECONDITION_CHECKER);

				oneOf(editorDataModel_mock).nodeChanged(treeNode_mock);
			}
		});

		testable.jcbActionFunctionTypeItemListener.itemStateChanged(itemEvent_mock);
	}

	@Test
	public void actionFunctionLines_insertUpdate() {
		final ActionFunction actionFunction_mock = context.mock(ActionFunction.class);
		final DefaultMutableTreeNode treeNode_mock = context.mock(DefaultMutableTreeNode.class);
		context.checking(new Expectations() {
			{
				oneOf(actionFunction_mock).getScript();
				will(returnValue("function-code"));

				oneOf(actionFunction_mock).getType();
				will(returnValue(ActionFunction.TYPE_PARAMETER_UPDATER));

				oneOf(jtaActionFunctionLines_mock).setText("function-code");

				oneOf(jcbActionFunctionType_mock).setSelectedIndex(ActionFunction.TYPE_PARAMETER_UPDATER);
			}
		});
		testable.loadActionFunction(actionFunction_mock, treeNode_mock);

		final DocumentEvent documentEvent_mock = context.mock(DocumentEvent.class);
		context.checking(new Expectations() {
			{
				oneOf(jtaActionFunctionLines_mock).getText();
				will(returnValue("new-function-code"));

				oneOf(actionFunction_mock).setScript("new-function-code");
			}
		});

		testable.jtaActionFunctionLinesDocumentListener.insertUpdate(documentEvent_mock);
	}

	@Test
	public void actionFunctionLines_removeUpdate() {
		final ActionFunction actionFunction_mock = context.mock(ActionFunction.class);
		final DefaultMutableTreeNode treeNode_mock = context.mock(DefaultMutableTreeNode.class);
		context.checking(new Expectations() {
			{
				oneOf(actionFunction_mock).getScript();
				will(returnValue("function-code"));

				oneOf(actionFunction_mock).getType();
				will(returnValue(ActionFunction.TYPE_PARAMETER_UPDATER));

				oneOf(jtaActionFunctionLines_mock).setText("function-code");

				oneOf(jcbActionFunctionType_mock).setSelectedIndex(ActionFunction.TYPE_PARAMETER_UPDATER);
			}
		});
		testable.loadActionFunction(actionFunction_mock, treeNode_mock);

		final DocumentEvent documentEvent_mock = context.mock(DocumentEvent.class);
		context.checking(new Expectations() {
			{
				oneOf(jtaActionFunctionLines_mock).getText();
				will(returnValue("new-function-code"));

				oneOf(actionFunction_mock).setScript("new-function-code");
			}
		});

		testable.jtaActionFunctionLinesDocumentListener.removeUpdate(documentEvent_mock);
	}

	@Test
	public void actionFunctionLines_changedUpdate() {
		final ActionFunction actionFunction_mock = context.mock(ActionFunction.class);
		final DefaultMutableTreeNode treeNode_mock = context.mock(DefaultMutableTreeNode.class);
		context.checking(new Expectations() {
			{
				oneOf(actionFunction_mock).getScript();
				will(returnValue("function-code"));

				oneOf(actionFunction_mock).getType();
				will(returnValue(ActionFunction.TYPE_PARAMETER_UPDATER));

				oneOf(jtaActionFunctionLines_mock).setText("function-code");

				oneOf(jcbActionFunctionType_mock).setSelectedIndex(ActionFunction.TYPE_PARAMETER_UPDATER);
			}
		});
		testable.loadActionFunction(actionFunction_mock, treeNode_mock);

		final DocumentEvent documentEvent_mock = context.mock(DocumentEvent.class);
		context.checking(new Expectations() {
			{
				oneOf(jtaActionFunctionLines_mock).getText();
				will(returnValue("new-function-code"));

				oneOf(actionFunction_mock).setScript("new-function-code");
			}
		});

		testable.jtaActionFunctionLinesDocumentListener.changedUpdate(documentEvent_mock);
	}
}

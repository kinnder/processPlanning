package application.ui.gui.editor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.Action;
import planning.model.ActionFunction;

public class ActionFunctionsDataModelTest {

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

	ActionFunctionsDataModel testable;

	@BeforeEach
	public void setup() {
		editorDataModel_mock = context.mock(EditorDataModel.class);

		testable = new ActionFunctionsDataModel(editorDataModel_mock);
	}

	@Test
	public void newInstance() {
		assertEquals(0, testable.getRowCount());
	}

	@Test
	public void getColumnClass() {
		assertEquals(String.class, testable.getColumnClass(ActionFunctionsDataModel.COLUMN_IDX_NAME));
		assertEquals(String.class, testable.getColumnClass(ActionFunctionsDataModel.COLUMN_IDX_TYPE));
	}

	@Test
	public void getColumnName() {
		assertEquals("name", testable.getColumnName(ActionFunctionsDataModel.COLUMN_IDX_NAME));
		assertEquals("type", testable.getColumnName(ActionFunctionsDataModel.COLUMN_IDX_TYPE));
	}

	@Test
	public void loadActionFunctions() {
		final Action action_mock = context.mock(Action.class);
		final DefaultMutableTreeNode treeNode_mock = context.mock(DefaultMutableTreeNode.class, "actionNode");
		final List<ActionFunction> actionFunctions = new ArrayList<ActionFunction>();
		final ActionFunction actionFunction_1_mock = context.mock(ActionFunction.class, "actionFunction-1");
		final ActionFunction actionFunction_2_mock = context.mock(ActionFunction.class, "actionFunction-2");
		actionFunctions.add(actionFunction_1_mock);
		actionFunctions.add(actionFunction_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(action_mock).getActionFunctions();
				will(returnValue(actionFunctions));
			}
		});

		testable.loadActionFunctions(action_mock, treeNode_mock);
		assertEquals(2, testable.getRowCount());
	}

	@Test
	public void insertActionFunction() {
		final Action action_mock = context.mock(Action.class);
		final DefaultMutableTreeNode treeNode_mock = context.mock(DefaultMutableTreeNode.class, "actionNode");
		final List<ActionFunction> actionFunctions = new ArrayList<ActionFunction>();
		context.checking(new Expectations() {
			{
				oneOf(action_mock).getActionFunctions();
				will(returnValue(actionFunctions));
			}
		});
		testable.loadActionFunctions(action_mock, treeNode_mock);

		final DefaultMutableTreeNode actionFunctionNode_mock = context.mock(DefaultMutableTreeNode.class,
				"actionFunctionNode");
		context.checking(new Expectations() {
			{
				// TODO (2022-12-17 #73): добавить Matcher для ActionFunction
				oneOf(action_mock).addActionFunction(with(any(ActionFunction.class)));

				oneOf(editorDataModel_mock).createActionFunctionNode(with(any(ActionFunction.class)));
				will(returnValue(actionFunctionNode_mock));

				oneOf(treeNode_mock).add(actionFunctionNode_mock);

				oneOf(editorDataModel_mock).insertNodeInto(actionFunctionNode_mock, treeNode_mock, 0);
			}
		});

		testable.insertActionFunction();
		assertEquals(1, testable.getRowCount());
	}

	@Test
	public void deleteActionFunction_no_row_selected() {
		testable.deleteActionFunction(-1);
	}

	@Test
	public void deleteActionFunction() {
		final Action action_mock = context.mock(Action.class);
		final DefaultMutableTreeNode treeNode_mock = context.mock(DefaultMutableTreeNode.class, "actionNode");
		final List<ActionFunction> actionFunctions = new ArrayList<ActionFunction>();
		final ActionFunction actionFunction_1_mock = context.mock(ActionFunction.class, "actionFunction-1");
		final ActionFunction actionFunction_2_mock = context.mock(ActionFunction.class, "actionFunction-2");
		actionFunctions.add(actionFunction_1_mock);
		actionFunctions.add(actionFunction_2_mock);
		context.checking(new Expectations() {
			{
				oneOf(action_mock).getActionFunctions();
				will(returnValue(actionFunctions));
			}
		});
		testable.loadActionFunctions(action_mock, treeNode_mock);

		final DefaultMutableTreeNode actionFunctionNode_mock = context.mock(DefaultMutableTreeNode.class,
				"actionFunctionNode");
		context.checking(new Expectations() {
			{
				oneOf(action_mock).removeActionFunction(actionFunction_1_mock);

				oneOf(treeNode_mock).getChildAt(0);
				will(returnValue(actionFunctionNode_mock));

				oneOf(editorDataModel_mock).removeNodeFromParent(actionFunctionNode_mock);
			}
		});

		testable.deleteActionFunction(0);
		assertEquals(1, testable.getRowCount());
	}

	@Test
	public void setValueAt() {
		final Action action_mock = context.mock(Action.class);
		final DefaultMutableTreeNode treeNode_mock = context.mock(DefaultMutableTreeNode.class, "actionNode");
		final List<ActionFunction> actionFunctions = new ArrayList<ActionFunction>();
		final ActionFunction actionFunction_1_mock = context.mock(ActionFunction.class, "actionFunction-1");
		final ActionFunction actionFunction_2_mock = context.mock(ActionFunction.class, "actionFunction-2");
		actionFunctions.add(actionFunction_1_mock);
		actionFunctions.add(actionFunction_2_mock);
		context.checking(new Expectations() {
			{
				oneOf(action_mock).getActionFunctions();
				will(returnValue(actionFunctions));
			}
		});
		testable.loadActionFunctions(action_mock, treeNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(editorDataModel_mock).nodesChanged(treeNode_mock, new int[] { 0 });
			}
		});

		testable.setValueAt("value", 0, ActionFunctionsDataModel.COLUMN_IDX_NAME);
	}

	@Test
	public void getValueAt() {
		final Action action_mock = context.mock(Action.class);
		final DefaultMutableTreeNode treeNode_mock = context.mock(DefaultMutableTreeNode.class, "actionNode");
		final List<ActionFunction> actionFunctions = new ArrayList<ActionFunction>();
		final ActionFunction actionFunction_1_mock = context.mock(ActionFunction.class, "actionFunction-1");
		final ActionFunction actionFunction_2_mock = context.mock(ActionFunction.class, "actionFunction-2");
		actionFunctions.add(actionFunction_1_mock);
		actionFunctions.add(actionFunction_2_mock);
		context.checking(new Expectations() {
			{
				oneOf(action_mock).getActionFunctions();
				will(returnValue(actionFunctions));
			}
		});
		testable.loadActionFunctions(action_mock, treeNode_mock);

		assertEquals(null, testable.getValueAt(0, -1));
	}

	@Test
	public void getValueAt_name() {
		final Action action_mock = context.mock(Action.class);
		final DefaultMutableTreeNode treeNode_mock = context.mock(DefaultMutableTreeNode.class, "actionNode");
		final List<ActionFunction> actionFunctions = new ArrayList<ActionFunction>();
		final ActionFunction actionFunction_1_mock = context.mock(ActionFunction.class, "actionFunction-1");
		final ActionFunction actionFunction_2_mock = context.mock(ActionFunction.class, "actionFunction-2");
		actionFunctions.add(actionFunction_1_mock);
		actionFunctions.add(actionFunction_2_mock);
		context.checking(new Expectations() {
			{
				oneOf(action_mock).getActionFunctions();
				will(returnValue(actionFunctions));
			}
		});
		testable.loadActionFunctions(action_mock, treeNode_mock);

		assertEquals("unknown", testable.getValueAt(0, ActionFunctionsDataModel.COLUMN_IDX_NAME));
	}

	@Test
	public void getValueAt_type_parameterUpdater() {
		final Action action_mock = context.mock(Action.class);
		final DefaultMutableTreeNode treeNode_mock = context.mock(DefaultMutableTreeNode.class, "actionNode");
		final List<ActionFunction> actionFunctions = new ArrayList<ActionFunction>();
		final ActionFunction actionFunction_1_mock = context.mock(ActionFunction.class, "actionFunction-1");
		final ActionFunction actionFunction_2_mock = context.mock(ActionFunction.class, "actionFunction-2");
		actionFunctions.add(actionFunction_1_mock);
		actionFunctions.add(actionFunction_2_mock);
		context.checking(new Expectations() {
			{
				oneOf(action_mock).getActionFunctions();
				will(returnValue(actionFunctions));
			}
		});
		testable.loadActionFunctions(action_mock, treeNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(actionFunction_1_mock).getType();
				will(returnValue(ActionFunction.TYPE_PARAMETER_UPDATER));
			}
		});

		assertEquals("parameterUpdater", testable.getValueAt(0, ActionFunctionsDataModel.COLUMN_IDX_TYPE));
	}

	@Test
	public void getValueAt_type_preConditionChecker() {
		final Action action_mock = context.mock(Action.class);
		final DefaultMutableTreeNode treeNode_mock = context.mock(DefaultMutableTreeNode.class, "actionNode");
		final List<ActionFunction> actionFunctions = new ArrayList<ActionFunction>();
		final ActionFunction actionFunction_1_mock = context.mock(ActionFunction.class, "actionFunction-1");
		final ActionFunction actionFunction_2_mock = context.mock(ActionFunction.class, "actionFunction-2");
		actionFunctions.add(actionFunction_1_mock);
		actionFunctions.add(actionFunction_2_mock);
		context.checking(new Expectations() {
			{
				oneOf(action_mock).getActionFunctions();
				will(returnValue(actionFunctions));
			}
		});
		testable.loadActionFunctions(action_mock, treeNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(actionFunction_1_mock).getType();
				will(returnValue(ActionFunction.TYPE_PRECONDITION_CHECKER));
			}
		});

		assertEquals("preConditionChecker", testable.getValueAt(0, ActionFunctionsDataModel.COLUMN_IDX_TYPE));
	}

	@Test
	public void getValueAt_type_unknown() {
		final Action action_mock = context.mock(Action.class);
		final DefaultMutableTreeNode treeNode_mock = context.mock(DefaultMutableTreeNode.class, "actionNode");
		final List<ActionFunction> actionFunctions = new ArrayList<ActionFunction>();
		final ActionFunction actionFunction_1_mock = context.mock(ActionFunction.class, "actionFunction-1");
		final ActionFunction actionFunction_2_mock = context.mock(ActionFunction.class, "actionFunction-2");
		actionFunctions.add(actionFunction_1_mock);
		actionFunctions.add(actionFunction_2_mock);
		context.checking(new Expectations() {
			{
				oneOf(action_mock).getActionFunctions();
				will(returnValue(actionFunctions));
			}
		});
		testable.loadActionFunctions(action_mock, treeNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(actionFunction_1_mock).getType();
				will(returnValue(ActionFunction.TYPE_UNKNOWN));
			}
		});

		assertEquals("unknown", testable.getValueAt(0, ActionFunctionsDataModel.COLUMN_IDX_TYPE));
	}
}

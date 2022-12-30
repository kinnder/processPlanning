package application.ui.gui.editor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.method.Edge;
import planning.model.SystemOperation;

public class ParametersDataModelTest {

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

	ParametersDataModel testable;

	@BeforeEach
	public void setup() {
		editorDataModel_mock = context.mock(EditorDataModel.class);

		testable = new ParametersDataModel(editorDataModel_mock);
	}

	@Test
	public void newInstance() {
		assertEquals(0, testable.getRowCount());
	}

	@Test
	public void getColumnClass() {
		assertEquals(String.class, testable.getColumnClass(ParametersDataModel.COLUMN_IDX_NAME));
		assertEquals(String.class, testable.getColumnClass(ParametersDataModel.COLUMN_IDX_VALUE));
	}

	@Test
	public void getColumnName() {
		assertEquals("name", testable.getColumnName(ParametersDataModel.COLUMN_IDX_NAME));
		assertEquals("value", testable.getColumnName(ParametersDataModel.COLUMN_IDX_VALUE));
	}

	@Test
	public void clear() {
		testable.clear();
	}

	@Test
	public void loadParameters() {
		final Edge selectedEdge_mock = context.mock(Edge.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		Map<String, String> parameters = new HashMap<>();
		parameters.put("parameter-1-name", "parameter-1-value");
		parameters.put("parameter-2-name", "parameter-2-value");
		final SystemOperation operation_mock = context.mock(SystemOperation.class);

		context.checking(new Expectations() {
			{
				oneOf(selectedEdge_mock).getSystemOperation();
				will(returnValue(operation_mock));

				oneOf(operation_mock).getActionParameters();
				will(returnValue(parameters));
			}
		});

		testable.loadParameters(selectedEdge_mock, selectedNode_mock);
		assertEquals(2, testable.getRowCount());
	}

	@Test
	public void getValueAt_name() {
		final Edge selectedEdge_mock = context.mock(Edge.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		Map<String, String> parameters = new HashMap<>();
		parameters.put("parameter-1-name", "parameter-1-value");
		final SystemOperation operation_mock = context.mock(SystemOperation.class);
		context.checking(new Expectations() {
			{
				oneOf(selectedEdge_mock).getSystemOperation();
				will(returnValue(operation_mock));

				oneOf(operation_mock).getActionParameters();
				will(returnValue(parameters));
			}
		});
		testable.loadParameters(selectedEdge_mock, selectedNode_mock);

		assertEquals("parameter-1-name", testable.getValueAt(0, ParametersDataModel.COLUMN_IDX_NAME));
	}

	@Test
	public void getValueAt_value() {
		final Edge selectedEdge_mock = context.mock(Edge.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		Map<String, String> parameters = new HashMap<>();
		parameters.put("parameter-1-name", "parameter-1-value");
		final SystemOperation operation_mock = context.mock(SystemOperation.class);
		context.checking(new Expectations() {
			{
				oneOf(selectedEdge_mock).getSystemOperation();
				will(returnValue(operation_mock));

				oneOf(operation_mock).getActionParameters();
				will(returnValue(parameters));
			}
		});
		testable.loadParameters(selectedEdge_mock, selectedNode_mock);

		assertEquals("parameter-1-value", testable.getValueAt(0, ParametersDataModel.COLUMN_IDX_VALUE));
	}

	@Test
	public void getValueAt() {
		final Edge selectedEdge_mock = context.mock(Edge.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		Map<String, String> parameters = new HashMap<>();
		parameters.put("parameter-1-name", "parameter-1-value");
		final SystemOperation operation_mock = context.mock(SystemOperation.class);
		context.checking(new Expectations() {
			{
				oneOf(selectedEdge_mock).getSystemOperation();
				will(returnValue(operation_mock));

				oneOf(operation_mock).getActionParameters();
				will(returnValue(parameters));
			}
		});
		testable.loadParameters(selectedEdge_mock, selectedNode_mock);

		assertEquals(null, testable.getValueAt(0, -1));
	}
}

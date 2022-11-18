package application.ui.gui.editor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.swing.tree.DefaultMutableTreeNode;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.method.SystemTransformations;
import planning.model.SystemTransformation;

public class SystemTransformationsDataModelTest {

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

	SystemTransformationsDataModel testable;

	@BeforeEach
	public void setup() {
		editorDataModel_mock = context.mock(EditorDataModel.class);

		testable = new SystemTransformationsDataModel(editorDataModel_mock);
	}

	@Test
	public void newInstance() {
		assertEquals(0, testable.getRowCount());
	}

	@Test
	public void getColumnClass() {
		assertEquals(String.class, testable.getColumnClass(SystemTransformationsDataModel.COLUMN_IDX_NAME));
	}

	@Test
	public void getColumnName() {
		assertEquals("name", testable.getColumnName(SystemTransformationsDataModel.COLUMN_IDX_NAME));
	}

	@Test
	public void loadSystemTransformations() {
		final SystemTransformations systemTransformations = new SystemTransformations();
		final SystemTransformation systemTransformation_1_mock = context.mock(SystemTransformation.class, "st-1");
		final SystemTransformation systemTransformation_2_mock = context.mock(SystemTransformation.class, "st-2");
		systemTransformations.add(systemTransformation_1_mock);
		systemTransformations.add(systemTransformation_2_mock);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);

		testable.loadSystemTransformations(systemTransformations, selectedNode_mock);
	}

	@Test
	public void insertSystemTransformations() {
		final SystemTransformations systemTransformations = new SystemTransformations();
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		testable.loadSystemTransformations(systemTransformations, selectedNode_mock);

		DefaultMutableTreeNode systemTransformationNode_mock = context.mock(DefaultMutableTreeNode.class,
				"systemTransformation-node");
		context.checking(new Expectations() {
			{
				oneOf(editorDataModel_mock).createSystemTransformationNode(with(any(SystemTransformation.class)));
				will(returnValue(systemTransformationNode_mock));

				oneOf(selectedNode_mock).add(systemTransformationNode_mock);

				oneOf(editorDataModel_mock).insertNodeInto(systemTransformationNode_mock, selectedNode_mock, 0);
			}
		});

		testable.insertSystemTransformation();
		assertEquals(1, testable.getRowCount());
		assertEquals(1, systemTransformations.size());
	}

	@Test
	public void deleteSystemTransformation_no_row_selected() {
		testable.deleteSystemTransformation(-1);
	}

	@Test
	public void deleteSystemTransformation() {
		final SystemTransformations systemTransformations = new SystemTransformations();
		final SystemTransformation systemTransformation_1_mock = context.mock(SystemTransformation.class, "st-1");
		final SystemTransformation systemTransformation_2_mock = context.mock(SystemTransformation.class, "st-2");
		systemTransformations.add(systemTransformation_1_mock);
		systemTransformations.add(systemTransformation_2_mock);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		testable.loadSystemTransformations(systemTransformations, selectedNode_mock);

		DefaultMutableTreeNode systemTransformationNode_mock = context.mock(DefaultMutableTreeNode.class,
				"systemTransformation-node");
		context.checking(new Expectations() {
			{
				oneOf(selectedNode_mock).getChildAt(0);
				will(returnValue(systemTransformationNode_mock));

				oneOf(editorDataModel_mock).removeNodeFromParent(systemTransformationNode_mock);
			}
		});

		testable.deleteSystemTransformation(0);
		assertEquals(1, testable.getRowCount());
		assertEquals(1, systemTransformations.size());
	}

	@Test
	public void setValueAt_name() {
		final SystemTransformations systemTransformations = new SystemTransformations();
		final SystemTransformation systemTransformation_1_mock = context.mock(SystemTransformation.class, "st-1");
		final SystemTransformation systemTransformation_2_mock = context.mock(SystemTransformation.class, "st-2");
		systemTransformations.add(systemTransformation_1_mock);
		systemTransformations.add(systemTransformation_2_mock);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		testable.loadSystemTransformations(systemTransformations, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(systemTransformation_1_mock).setName("new-name");

				oneOf(editorDataModel_mock).nodesChanged(selectedNode_mock, new int[] { 0 });
			}
		});

		testable.setValueAt("new-name", 0, SystemTransformationsDataModel.COLUMN_IDX_NAME);
	}

	@Test
	public void setValueAt() {
		final SystemTransformations systemTransformations = new SystemTransformations();
		final SystemTransformation systemTransformation_1_mock = context.mock(SystemTransformation.class, "st-1");
		final SystemTransformation systemTransformation_2_mock = context.mock(SystemTransformation.class, "st-2");
		systemTransformations.add(systemTransformation_1_mock);
		systemTransformations.add(systemTransformation_2_mock);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		testable.loadSystemTransformations(systemTransformations, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(editorDataModel_mock).nodesChanged(selectedNode_mock, new int[] { 0 });
			}
		});

		testable.setValueAt("value", 0, -1);
	}

	@Test
	public void getValueAt_name() {
		final SystemTransformations systemTransformations = new SystemTransformations();
		final SystemTransformation systemTransformation_1_mock = context.mock(SystemTransformation.class, "st-1");
		final SystemTransformation systemTransformation_2_mock = context.mock(SystemTransformation.class, "st-2");
		systemTransformations.add(systemTransformation_1_mock);
		systemTransformations.add(systemTransformation_2_mock);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		testable.loadSystemTransformations(systemTransformations, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(systemTransformation_1_mock).getName();
				will(returnValue("st-name"));
			}
		});

		assertEquals("st-name", testable.getValueAt(0, SystemTransformationsDataModel.COLUMN_IDX_NAME));
	}

	@Test
	public void getValueAt() {
		final SystemTransformations systemTransformations = new SystemTransformations();
		final SystemTransformation systemTransformation_1_mock = context.mock(SystemTransformation.class, "st-1");
		final SystemTransformation systemTransformation_2_mock = context.mock(SystemTransformation.class, "st-2");
		systemTransformations.add(systemTransformation_1_mock);
		systemTransformations.add(systemTransformation_2_mock);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		testable.loadSystemTransformations(systemTransformations, selectedNode_mock);

		assertEquals(null, testable.getValueAt(0, -1));
	}
}

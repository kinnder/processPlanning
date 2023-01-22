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

import planning.model.SystemObjectTemplate;
import planning.model.SystemTemplate;

public class ObjectTemplatesDataModelTest {

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

	ObjectTemplatesDataModel testable;

	@BeforeEach
	public void setup() {
		editorDataModel_mock = context.mock(EditorDataModel.class);

		testable = new ObjectTemplatesDataModel(editorDataModel_mock);
	}

	@Test
	public void newInstance() {
		assertEquals(0, testable.getRowCount());
	}

	@Test
	public void getColumnClass() {
		assertEquals(String.class, testable.getColumnClass(ObjectTemplatesDataModel.COLUMN_IDX_NAME));
		assertEquals(String.class, testable.getColumnClass(ObjectTemplatesDataModel.COLUMN_IDX_ID));
	}

	@Test
	public void getColumnName() {
		assertEquals("name", testable.getColumnName(ObjectTemplatesDataModel.COLUMN_IDX_NAME));
		assertEquals("id", testable.getColumnName(ObjectTemplatesDataModel.COLUMN_IDX_ID));
	}

	@Test
	public void loadObjectTemplates() {
		final SystemTemplate selectedSystemTemplate_mock = context.mock(SystemTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<SystemObjectTemplate> objectTemplates = new ArrayList<SystemObjectTemplate>();
		final SystemObjectTemplate objectTemplate_1_mock = context.mock(SystemObjectTemplate.class, "objectTemplate-1");
		final SystemObjectTemplate objectTemplate_2_mock = context.mock(SystemObjectTemplate.class, "objectTemplate-2");
		objectTemplates.add(objectTemplate_1_mock);
		objectTemplates.add(objectTemplate_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystemTemplate_mock).getObjectTemplates();
				will(returnValue(objectTemplates));
			}
		});

		testable.loadObjectTemplates(selectedSystemTemplate_mock, selectedNode_mock);
		assertEquals(2, testable.getRowCount());
	}

	@Test
	public void insertObjectTemplate() {
		final SystemTemplate selectedSystemTemplate_mock = context.mock(SystemTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<SystemObjectTemplate> objectTemplates = new ArrayList<SystemObjectTemplate>();

		context.checking(new Expectations() {
			{
				oneOf(selectedSystemTemplate_mock).getObjectTemplates();
				will(returnValue(objectTemplates));
			}
		});
		testable.loadObjectTemplates(selectedSystemTemplate_mock, selectedNode_mock);

		final DefaultMutableTreeNode objectTemplateNode_mock = context.mock(DefaultMutableTreeNode.class,
				"objectTemplate-node");
		context.checking(new Expectations() {
			{
				oneOf(selectedSystemTemplate_mock).addObjectTemplate(with(any(SystemObjectTemplate.class)));

				oneOf(editorDataModel_mock).createObjectTemplateNode(with(any(SystemObjectTemplate.class)));
				will(returnValue(objectTemplateNode_mock));

				oneOf(selectedNode_mock).add(objectTemplateNode_mock);

				oneOf(editorDataModel_mock).insertNodeInto(objectTemplateNode_mock, selectedNode_mock, 0);
			}
		});

		testable.insertObjectTemplate();
		assertEquals(1, testable.getRowCount());
	}

	@Test
	public void deleteobjectTemplate_no_row_selected() {
		testable.deleteObjectTemplate(-1);
	}

	@Test
	public void deleteObject() {
		final SystemTemplate selectedSystemTemplate_mock = context.mock(SystemTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<SystemObjectTemplate> objectTemplates = new ArrayList<SystemObjectTemplate>();
		final SystemObjectTemplate objectTemplate_1_mock = context.mock(SystemObjectTemplate.class, "objectTemplate-1");
		final SystemObjectTemplate objectTemplate_2_mock = context.mock(SystemObjectTemplate.class, "objectTemplate-2");
		objectTemplates.add(objectTemplate_1_mock);
		objectTemplates.add(objectTemplate_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystemTemplate_mock).getObjectTemplates();
				will(returnValue(objectTemplates));
			}
		});
		testable.loadObjectTemplates(selectedSystemTemplate_mock, selectedNode_mock);

		final DefaultMutableTreeNode objectTemplateNode_mock = context.mock(DefaultMutableTreeNode.class,
				"objectTemplate-node");
		context.checking(new Expectations() {
			{
				oneOf(selectedSystemTemplate_mock).removeObjectTemplate(objectTemplate_1_mock);

				oneOf(selectedNode_mock).getChildAt(0);
				will(returnValue(objectTemplateNode_mock));

				oneOf(editorDataModel_mock).removeNodeFromParent(objectTemplateNode_mock);
			}
		});

		testable.deleteObjectTemplate(0);
		assertEquals(1, testable.getRowCount());
	}

	@Test
	public void setValueAt_name() {
		final SystemTemplate selectedSystemTemplate_mock = context.mock(SystemTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<SystemObjectTemplate> objectTemplates = new ArrayList<SystemObjectTemplate>();
		final SystemObjectTemplate objectTemplate_1_mock = context.mock(SystemObjectTemplate.class, "objectTemplate-1");
		final SystemObjectTemplate objectTemplate_2_mock = context.mock(SystemObjectTemplate.class, "objectTemplate-2");
		objectTemplates.add(objectTemplate_1_mock);
		objectTemplates.add(objectTemplate_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystemTemplate_mock).getObjectTemplates();
				will(returnValue(objectTemplates));
			}
		});
		testable.loadObjectTemplates(selectedSystemTemplate_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(editorDataModel_mock).nodesChanged(selectedNode_mock, new int[] { 0 });
			}
		});

		testable.setValueAt("value", 0, ObjectTemplatesDataModel.COLUMN_IDX_NAME);
	}

	@Test
	public void setValueAt_id() {
		final SystemTemplate selectedSystemTemplate_mock = context.mock(SystemTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<SystemObjectTemplate> objectTemplates = new ArrayList<SystemObjectTemplate>();
		final SystemObjectTemplate objectTemplate_1_mock = context.mock(SystemObjectTemplate.class, "objectTemplate-1");
		final SystemObjectTemplate objectTemplate_2_mock = context.mock(SystemObjectTemplate.class, "objectTemplate-2");
		objectTemplates.add(objectTemplate_1_mock);
		objectTemplates.add(objectTemplate_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystemTemplate_mock).getObjectTemplates();
				will(returnValue(objectTemplates));
			}
		});
		testable.loadObjectTemplates(selectedSystemTemplate_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(objectTemplate_1_mock).setId("value");

				oneOf(editorDataModel_mock).nodesChanged(selectedNode_mock, new int[] { 0 });
			}
		});

		testable.setValueAt("value", 0, ObjectTemplatesDataModel.COLUMN_IDX_ID);
	}

	@Test
	public void setValueAt() {
		final SystemTemplate selectedSystemTemplate_mock = context.mock(SystemTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<SystemObjectTemplate> objectTemplates = new ArrayList<SystemObjectTemplate>();
		final SystemObjectTemplate objectTemplate_1_mock = context.mock(SystemObjectTemplate.class, "objectTemplate-1");
		final SystemObjectTemplate objectTemplate_2_mock = context.mock(SystemObjectTemplate.class, "objectTemplate-2");
		objectTemplates.add(objectTemplate_1_mock);
		objectTemplates.add(objectTemplate_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystemTemplate_mock).getObjectTemplates();
				will(returnValue(objectTemplates));
			}
		});
		testable.loadObjectTemplates(selectedSystemTemplate_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(editorDataModel_mock).nodesChanged(selectedNode_mock, new int[] { 0 });
			}
		});

		testable.setValueAt("value", 0, -1);
	}

	@Test
	public void getValueAt_name() {
		final SystemTemplate selectedSystemTemplate_mock = context.mock(SystemTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<SystemObjectTemplate> objectTemplates = new ArrayList<SystemObjectTemplate>();
		final SystemObjectTemplate objectTemplate_1_mock = context.mock(SystemObjectTemplate.class, "objectTemplate-1");
		final SystemObjectTemplate objectTemplate_2_mock = context.mock(SystemObjectTemplate.class, "objectTemplate-2");
		objectTemplates.add(objectTemplate_1_mock);
		objectTemplates.add(objectTemplate_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystemTemplate_mock).getObjectTemplates();
				will(returnValue(objectTemplates));
			}
		});
		testable.loadObjectTemplates(selectedSystemTemplate_mock, selectedNode_mock);

		assertEquals("Object Template", testable.getValueAt(0, ObjectTemplatesDataModel.COLUMN_IDX_NAME));
	}

	@Test
	public void getValueAt_id() {
		final SystemTemplate selectedSystemTemplate_mock = context.mock(SystemTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<SystemObjectTemplate> objectTemplates = new ArrayList<SystemObjectTemplate>();
		final SystemObjectTemplate objectTemplate_1_mock = context.mock(SystemObjectTemplate.class, "objectTemplate-1");
		final SystemObjectTemplate objectTemplate_2_mock = context.mock(SystemObjectTemplate.class, "objectTemplate-2");
		objectTemplates.add(objectTemplate_1_mock);
		objectTemplates.add(objectTemplate_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystemTemplate_mock).getObjectTemplates();
				will(returnValue(objectTemplates));
			}
		});
		testable.loadObjectTemplates(selectedSystemTemplate_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(objectTemplate_1_mock).getId();
				will(returnValue("value"));
			}
		});

		assertEquals("value", testable.getValueAt(0, ObjectTemplatesDataModel.COLUMN_IDX_ID));
	}

	@Test
	public void getValueAt() {
		final SystemTemplate selectedSystemTemplate_mock = context.mock(SystemTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<SystemObjectTemplate> objectTemplates = new ArrayList<SystemObjectTemplate>();
		final SystemObjectTemplate objectTemplate_1_mock = context.mock(SystemObjectTemplate.class, "objectTemplate-1");
		final SystemObjectTemplate objectTemplate_2_mock = context.mock(SystemObjectTemplate.class, "objectTemplate-2");
		objectTemplates.add(objectTemplate_1_mock);
		objectTemplates.add(objectTemplate_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystemTemplate_mock).getObjectTemplates();
				will(returnValue(objectTemplates));
			}
		});
		testable.loadObjectTemplates(selectedSystemTemplate_mock, selectedNode_mock);

		assertEquals(null, testable.getValueAt(0, -1));
	}
}

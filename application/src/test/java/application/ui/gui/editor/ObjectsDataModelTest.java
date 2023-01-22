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

import planning.model.System;
import planning.model.SystemObject;

public class ObjectsDataModelTest {

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

	ObjectsDataModel testable;

	@BeforeEach
	public void setup() {
		editorDataModel_mock = context.mock(EditorDataModel.class);

		testable = new ObjectsDataModel(editorDataModel_mock);
	}

	@Test
	public void newInstance() {
		assertEquals(0, testable.getRowCount());
	}

	@Test
	public void getColumnClass() {
		assertEquals(String.class, testable.getColumnClass(ObjectsDataModel.COLUMN_IDX_NAME));
		assertEquals(String.class, testable.getColumnClass(ObjectsDataModel.COLUMN_IDX_ID));
	}

	@Test
	public void getColumnName() {
		assertEquals("name", testable.getColumnName(ObjectsDataModel.COLUMN_IDX_NAME));
		assertEquals("id", testable.getColumnName(ObjectsDataModel.COLUMN_IDX_ID));
	}

	@Test
	public void loadObjects() {
		final System selectedSystem_mock = context.mock(System.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<SystemObject> objects = new ArrayList<SystemObject>();
		final SystemObject object_1_mock = context.mock(SystemObject.class, "object-1");
		final SystemObject object_2_mock = context.mock(SystemObject.class, "object-2");
		objects.add(object_1_mock);
		objects.add(object_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystem_mock).getObjects();
				will(returnValue(objects));
			}
		});

		testable.loadObjects(selectedSystem_mock, selectedNode_mock);
		assertEquals(2, testable.getRowCount());
	}

	@Test
	public void insertObject() {
		final System selectedSystem_mock = context.mock(System.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<SystemObject> objects = new ArrayList<SystemObject>();

		context.checking(new Expectations() {
			{
				oneOf(selectedSystem_mock).getObjects();
				will(returnValue(objects));
			}
		});
		testable.loadObjects(selectedSystem_mock, selectedNode_mock);

		final DefaultMutableTreeNode objectNode_mock = context.mock(DefaultMutableTreeNode.class, "object-node");
		context.checking(new Expectations() {
			{
				oneOf(selectedSystem_mock).addObject(with(any(SystemObject.class)));

				oneOf(editorDataModel_mock).createObjectNode(with(any(SystemObject.class)));
				will(returnValue(objectNode_mock));

				oneOf(selectedNode_mock).add(objectNode_mock);

				oneOf(editorDataModel_mock).insertNodeInto(objectNode_mock, selectedNode_mock, 0);
			}
		});

		testable.insertObject();
		assertEquals(1, testable.getRowCount());
	}

	@Test
	public void deleteObject_no_row_selected() {
		testable.deleteObject(-1);
	}

	@Test
	public void deleteObject() {
		final System selectedSystem_mock = context.mock(System.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<SystemObject> objects = new ArrayList<SystemObject>();
		final SystemObject object_1_mock = context.mock(SystemObject.class, "object-1");
		final SystemObject object_2_mock = context.mock(SystemObject.class, "object-2");
		objects.add(object_1_mock);
		objects.add(object_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystem_mock).getObjects();
				will(returnValue(objects));
			}
		});
		testable.loadObjects(selectedSystem_mock, selectedNode_mock);

		final DefaultMutableTreeNode objectNode_mock = context.mock(DefaultMutableTreeNode.class, "object-node");
		context.checking(new Expectations() {
			{
				oneOf(selectedSystem_mock).removeObject(object_1_mock);

				oneOf(selectedNode_mock).getChildAt(0);
				will(returnValue(objectNode_mock));

				oneOf(editorDataModel_mock).removeNodeFromParent(objectNode_mock);
			}
		});

		testable.deleteObject(0);
		assertEquals(1, testable.getRowCount());
	}

	@Test
	public void setValueAt_name() {
		final System selectedSystem_mock = context.mock(System.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<SystemObject> objects = new ArrayList<SystemObject>();
		final SystemObject object_1_mock = context.mock(SystemObject.class, "object-1");
		final SystemObject object_2_mock = context.mock(SystemObject.class, "object-2");
		objects.add(object_1_mock);
		objects.add(object_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystem_mock).getObjects();
				will(returnValue(objects));
			}
		});
		testable.loadObjects(selectedSystem_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(object_1_mock).setName("value");

				oneOf(editorDataModel_mock).nodesChanged(selectedNode_mock, new int[] { 0 });
			}
		});

		testable.setValueAt("value", 0, ObjectsDataModel.COLUMN_IDX_NAME);
	}

	@Test
	public void setValueAt_id() {
		final System selectedSystem_mock = context.mock(System.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<SystemObject> objects = new ArrayList<SystemObject>();
		final SystemObject object_1_mock = context.mock(SystemObject.class, "object-1");
		final SystemObject object_2_mock = context.mock(SystemObject.class, "object-2");
		objects.add(object_1_mock);
		objects.add(object_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystem_mock).getObjects();
				will(returnValue(objects));
			}
		});
		testable.loadObjects(selectedSystem_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(object_1_mock).setId("value");

				oneOf(editorDataModel_mock).nodesChanged(selectedNode_mock, new int[] { 0 });
			}
		});

		testable.setValueAt("value", 0, ObjectsDataModel.COLUMN_IDX_ID);
	}

	@Test
	public void setValueAt() {
		final System selectedSystem_mock = context.mock(System.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<SystemObject> objects = new ArrayList<SystemObject>();
		final SystemObject object_1_mock = context.mock(SystemObject.class, "object-1");
		final SystemObject object_2_mock = context.mock(SystemObject.class, "object-2");
		objects.add(object_1_mock);
		objects.add(object_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystem_mock).getObjects();
				will(returnValue(objects));
			}
		});
		testable.loadObjects(selectedSystem_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(editorDataModel_mock).nodesChanged(selectedNode_mock, new int[] { 0 });
			}
		});

		testable.setValueAt("value", 0, -1);
	}

	@Test
	public void getValueAt_name() {
		final System selectedSystem_mock = context.mock(System.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<SystemObject> objects = new ArrayList<SystemObject>();
		final SystemObject object_1_mock = context.mock(SystemObject.class, "object-1");
		final SystemObject object_2_mock = context.mock(SystemObject.class, "object-2");
		objects.add(object_1_mock);
		objects.add(object_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystem_mock).getObjects();
				will(returnValue(objects));
			}
		});
		testable.loadObjects(selectedSystem_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(object_1_mock).getName();
				will(returnValue("value"));
			}
		});

		assertEquals("value", testable.getValueAt(0, ObjectsDataModel.COLUMN_IDX_NAME));
	}

	@Test
	public void getValueAt_id() {
		final System selectedSystem_mock = context.mock(System.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<SystemObject> objects = new ArrayList<SystemObject>();
		final SystemObject object_1_mock = context.mock(SystemObject.class, "object-1");
		final SystemObject object_2_mock = context.mock(SystemObject.class, "object-2");
		objects.add(object_1_mock);
		objects.add(object_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystem_mock).getObjects();
				will(returnValue(objects));
			}
		});
		testable.loadObjects(selectedSystem_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(object_1_mock).getId();
				will(returnValue("value"));
			}
		});

		assertEquals("value", testable.getValueAt(0, ObjectsDataModel.COLUMN_IDX_ID));
	}

	@Test
	public void getValueAt() {
		final System selectedSystem_mock = context.mock(System.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<SystemObject> objects = new ArrayList<SystemObject>();
		final SystemObject object_1_mock = context.mock(SystemObject.class, "object-1");
		final SystemObject object_2_mock = context.mock(SystemObject.class, "object-2");
		objects.add(object_1_mock);
		objects.add(object_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystem_mock).getObjects();
				will(returnValue(objects));
			}
		});
		testable.loadObjects(selectedSystem_mock, selectedNode_mock);

		assertEquals(null, testable.getValueAt(0, -1));
	}
}

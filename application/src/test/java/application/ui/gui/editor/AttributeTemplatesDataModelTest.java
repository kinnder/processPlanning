package application.ui.gui.editor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.AttributeTemplate;
import planning.model.AttributeType;
import planning.model.SystemObjectTemplate;

public class AttributeTemplatesDataModelTest {

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

	AttributeTemplatesDataModel testable;

	@BeforeEach
	public void setup() {
		editorDataModel_mock = context.mock(EditorDataModel.class);

		testable = new AttributeTemplatesDataModel(editorDataModel_mock);
	}

	@Test
	public void newInstance() {
		assertEquals(0, testable.getRowCount());
	}

	@Test
	public void loadAttributeTemplates() {
		final SystemObjectTemplate selectedObjectTemplate_mock = context.mock(SystemObjectTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<AttributeTemplate> attributeTemplates = new ArrayList<AttributeTemplate>();
		final AttributeTemplate attributeTemplate_1_mock = context.mock(AttributeTemplate.class, "attributeTemplate-1");
		final AttributeTemplate attributeTemplate_2_mock = context.mock(AttributeTemplate.class, "attributeTemplate-2");
		attributeTemplates.add(attributeTemplate_1_mock);
		attributeTemplates.add(attributeTemplate_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObjectTemplate_mock).getAttributeTemplates();
				will(returnValue(attributeTemplates));
			}
		});

		testable.loadAttributeTemplates(selectedObjectTemplate_mock, selectedNode_mock);
		assertEquals(2, testable.getRowCount());
	}

	@Test
	public void getColumnClass() {
		assertEquals(String.class, testable.getColumnClass(AttributeTemplatesDataModel.COLUMN_IDX_NAME));
		assertEquals(String.class, testable.getColumnClass(AttributeTemplatesDataModel.COLUMN_IDX_TYPE));
		assertEquals(String.class, testable.getColumnClass(AttributeTemplatesDataModel.COLUMN_IDX_VALUE));
	}

	@Test
	public void getColumnName() {
		assertEquals("name", testable.getColumnName(AttributeTemplatesDataModel.COLUMN_IDX_NAME));
		assertEquals("type", testable.getColumnName(AttributeTemplatesDataModel.COLUMN_IDX_TYPE));
		assertEquals("value", testable.getColumnName(AttributeTemplatesDataModel.COLUMN_IDX_VALUE));
	}

	@Test
	public void insertAttributeTemplate() {
		final SystemObjectTemplate selectedObjectTemplate_mock = context.mock(SystemObjectTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<AttributeTemplate> attributeTemplates = new ArrayList<AttributeTemplate>();

		context.checking(new Expectations() {
			{
				oneOf(selectedObjectTemplate_mock).getAttributeTemplates();
				will(returnValue(attributeTemplates));
			}
		});
		testable.loadAttributeTemplates(selectedObjectTemplate_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObjectTemplate_mock).addAttributeTemplate(with(any(AttributeTemplate.class)));

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.insertAttributeTemplate();
		assertEquals(1, testable.getRowCount());
	}

	@Test
	public void deleteAttributeTemplate_no_row_selected() {
		testable.deleteAttributeTemplate(-1);
	}

	@Test
	public void deleteAttributeTemplate() {
		final SystemObjectTemplate selectedObjectTemplate_mock = context.mock(SystemObjectTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<AttributeTemplate> attributeTemplates = new ArrayList<AttributeTemplate>();
		final AttributeTemplate attributeTemplate_1_mock = context.mock(AttributeTemplate.class, "attributeTemplate-1");
		final AttributeTemplate attributeTemplate_2_mock = context.mock(AttributeTemplate.class, "attributeTemplate-2");
		attributeTemplates.add(attributeTemplate_1_mock);
		attributeTemplates.add(attributeTemplate_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObjectTemplate_mock).getAttributeTemplates();
				will(returnValue(attributeTemplates));
			}
		});
		testable.loadAttributeTemplates(selectedObjectTemplate_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObjectTemplate_mock).removeAttributeTemplate(attributeTemplate_1_mock);

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.deleteAttributeTemplate(0);
		assertEquals(1, testable.getRowCount());
	}

	@Test
	public void setValueAt_name() {
		final SystemObjectTemplate selectedObjectTemplate_mock = context.mock(SystemObjectTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<AttributeTemplate> attributeTemplates = new ArrayList<AttributeTemplate>();
		final AttributeTemplate attributeTemplate_1_mock = context.mock(AttributeTemplate.class, "attributeTemplate-1");
		final AttributeTemplate attributeTemplate_2_mock = context.mock(AttributeTemplate.class, "attributeTemplate-2");
		attributeTemplates.add(attributeTemplate_1_mock);
		attributeTemplates.add(attributeTemplate_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObjectTemplate_mock).getAttributeTemplates();
				will(returnValue(attributeTemplates));
			}
		});
		testable.loadAttributeTemplates(selectedObjectTemplate_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attributeTemplate_1_mock).setName("value");

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("value", 0, AttributeTemplatesDataModel.COLUMN_IDX_NAME);
	}

	@Test
	public void setValueAt_type() {
		final SystemObjectTemplate selectedObjectTemplate_mock = context.mock(SystemObjectTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<AttributeTemplate> attributeTemplates = new ArrayList<AttributeTemplate>();
		final AttributeTemplate attributeTemplate_1_mock = context.mock(AttributeTemplate.class, "attributeTemplate-1");
		final AttributeTemplate attributeTemplate_2_mock = context.mock(AttributeTemplate.class, "attributeTemplate-2");
		attributeTemplates.add(attributeTemplate_1_mock);
		attributeTemplates.add(attributeTemplate_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObjectTemplate_mock).getAttributeTemplates();
				will(returnValue(attributeTemplates));
			}
		});
		testable.loadAttributeTemplates(selectedObjectTemplate_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attributeTemplate_1_mock).setType(AttributeType.BOOLEAN);

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("boolean", 0, AttributeTemplatesDataModel.COLUMN_IDX_TYPE);
	}

	@Test
	public void setValueAt_value() {
		final SystemObjectTemplate selectedObjectTemplate_mock = context.mock(SystemObjectTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<AttributeTemplate> attributeTemplates = new ArrayList<AttributeTemplate>();
		final AttributeTemplate attributeTemplate_1_mock = context.mock(AttributeTemplate.class, "attributeTemplate-1");
		final AttributeTemplate attributeTemplate_2_mock = context.mock(AttributeTemplate.class, "attributeTemplate-2");
		attributeTemplates.add(attributeTemplate_1_mock);
		attributeTemplates.add(attributeTemplate_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObjectTemplate_mock).getAttributeTemplates();
				will(returnValue(attributeTemplates));
			}
		});
		testable.loadAttributeTemplates(selectedObjectTemplate_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attributeTemplate_1_mock).setValue("new-value");

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("new-value", 0, AttributeTemplatesDataModel.COLUMN_IDX_VALUE);
	}

	@Test
	public void setValueAt() {
		final SystemObjectTemplate selectedObjectTemplate_mock = context.mock(SystemObjectTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<AttributeTemplate> attributeTemplates = new ArrayList<AttributeTemplate>();
		final AttributeTemplate attributeTemplate_1_mock = context.mock(AttributeTemplate.class, "attributeTemplate-1");
		final AttributeTemplate attributeTemplate_2_mock = context.mock(AttributeTemplate.class, "attributeTemplate-2");
		attributeTemplates.add(attributeTemplate_1_mock);
		attributeTemplates.add(attributeTemplate_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObjectTemplate_mock).getAttributeTemplates();
				will(returnValue(attributeTemplates));
			}
		});
		testable.loadAttributeTemplates(selectedObjectTemplate_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("value", 0, -1);
	}

	@Test
	public void getValueAt_name() {
		final SystemObjectTemplate selectedObjectTemplate_mock = context.mock(SystemObjectTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<AttributeTemplate> attributeTemplates = new ArrayList<AttributeTemplate>();
		final AttributeTemplate attributeTemplate_1_mock = context.mock(AttributeTemplate.class, "attributeTemplate-1");
		final AttributeTemplate attributeTemplate_2_mock = context.mock(AttributeTemplate.class, "attributeTemplate-2");
		attributeTemplates.add(attributeTemplate_1_mock);
		attributeTemplates.add(attributeTemplate_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObjectTemplate_mock).getAttributeTemplates();
				will(returnValue(attributeTemplates));
			}
		});
		testable.loadAttributeTemplates(selectedObjectTemplate_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attributeTemplate_1_mock).getName();
				will(returnValue("attributeTemplate-name"));
			}
		});

		assertEquals("attributeTemplate-name", testable.getValueAt(0, AttributeTemplatesDataModel.COLUMN_IDX_NAME));
	}

	@Test
	public void getValueAt_type() {
		final SystemObjectTemplate selectedObjectTemplate_mock = context.mock(SystemObjectTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<AttributeTemplate> attributeTemplates = new ArrayList<AttributeTemplate>();
		final AttributeTemplate attributeTemplate_1_mock = context.mock(AttributeTemplate.class, "attributeTemplate-1");
		final AttributeTemplate attributeTemplate_2_mock = context.mock(AttributeTemplate.class, "attributeTemplate-2");
		attributeTemplates.add(attributeTemplate_1_mock);
		attributeTemplates.add(attributeTemplate_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObjectTemplate_mock).getAttributeTemplates();
				will(returnValue(attributeTemplates));
			}
		});
		testable.loadAttributeTemplates(selectedObjectTemplate_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attributeTemplate_1_mock).getType();
				will(returnValue(AttributeType.BOOLEAN));
			}
		});

		assertEquals(AttributeType.BOOLEAN, testable.getValueAt(0, AttributeTemplatesDataModel.COLUMN_IDX_TYPE));
	}

	@Test
	public void getValueAt_value() {
		final SystemObjectTemplate selectedObjectTemplate_mock = context.mock(SystemObjectTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<AttributeTemplate> attributeTemplates = new ArrayList<AttributeTemplate>();
		final AttributeTemplate attributeTemplate_1_mock = context.mock(AttributeTemplate.class, "attributeTemplate-1");
		final AttributeTemplate attributeTemplate_2_mock = context.mock(AttributeTemplate.class, "attributeTemplate-2");
		attributeTemplates.add(attributeTemplate_1_mock);
		attributeTemplates.add(attributeTemplate_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObjectTemplate_mock).getAttributeTemplates();
				will(returnValue(attributeTemplates));
			}
		});
		testable.loadAttributeTemplates(selectedObjectTemplate_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attributeTemplate_1_mock).getValue();
				will(returnValue("abc"));
			}
		});

		assertEquals("abc", testable.getValueAt(0, AttributeTemplatesDataModel.COLUMN_IDX_VALUE));
	}

	@Test
	public void getValueAt() {
		final SystemObjectTemplate selectedObjectTemplate_mock = context.mock(SystemObjectTemplate.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<AttributeTemplate> attributeTemplates = new ArrayList<AttributeTemplate>();
		final AttributeTemplate attributeTemplate_1_mock = context.mock(AttributeTemplate.class, "attributeTemplate-1");
		final AttributeTemplate attributeTemplate_2_mock = context.mock(AttributeTemplate.class, "attributeTemplate-2");
		attributeTemplates.add(attributeTemplate_1_mock);
		attributeTemplates.add(attributeTemplate_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObjectTemplate_mock).getAttributeTemplates();
				will(returnValue(attributeTemplates));
			}
		});
		testable.loadAttributeTemplates(selectedObjectTemplate_mock, selectedNode_mock);

		assertEquals(null, testable.getValueAt(0, -1));
	}

	@Test
	public void clear() {
		testable.clear();
	}

	@Test
	public void setColumnCellEditors() {
		final JTable jTable_mock = context.mock(JTable.class);
		final TableColumnModel tableColumnModel_mock = context.mock(TableColumnModel.class);
		final TableColumn tableColumn_mock = context.mock(TableColumn.class);

		context.checking(new Expectations() {
			{
				oneOf(jTable_mock).getColumnModel();
				will(returnValue(tableColumnModel_mock));

				oneOf(tableColumnModel_mock).getColumn(AttributeTemplatesDataModel.COLUMN_IDX_TYPE);
				will(returnValue(tableColumn_mock));

				// TODO (2023-02-22 #82): добавить Matcher для DefaultCellEditor
				oneOf(tableColumn_mock).setCellEditor(with(any(DefaultCellEditor.class)));
			}
		});

		testable.setColumnCellEditors(jTable_mock);
	}
}

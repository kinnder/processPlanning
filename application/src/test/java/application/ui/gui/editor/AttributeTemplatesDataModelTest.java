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

import planning.model.AttributeTemplate;
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

		testable.setValueAt("value", 0, AttributesDataModel.COLUMN_IDX_NAME);
	}

	@Test
	public void setValueAt_type_boolean() {
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
				will(returnValue("true"));

				oneOf(attributeTemplate_1_mock).setValue(Boolean.TRUE);

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("boolean", 0, AttributesDataModel.COLUMN_IDX_TYPE);
	}

	@Test
	public void setValueAt_type_boolean_null() {
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
				will(returnValue(null));

				oneOf(attributeTemplate_1_mock).setValue(null);

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("boolean", 0, AttributesDataModel.COLUMN_IDX_TYPE);
	}

	@Test
	public void setValueAt_type_integer() {
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
				will(returnValue("10"));

				oneOf(attributeTemplate_1_mock).setValue(Integer.valueOf(10));

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("integer", 0, AttributesDataModel.COLUMN_IDX_TYPE);
	}

	@Test
	public void setValueAt_type_integer_null() {
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
				will(returnValue(null));

				oneOf(attributeTemplate_1_mock).setValue(null);

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("integer", 0, AttributesDataModel.COLUMN_IDX_TYPE);
	}

	@Test
	public void setValueAt_type_string() {
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
				will(returnValue("test-string"));

				oneOf(attributeTemplate_1_mock).setValue("test-string");

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("string", 0, AttributesDataModel.COLUMN_IDX_TYPE);
	}

	@Test
	public void setValueAt_type_string_null() {
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
				will(returnValue(null));

				oneOf(attributeTemplate_1_mock).setValue(null);

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("string", 0, AttributesDataModel.COLUMN_IDX_TYPE);
	}

	@Test
	public void setValueAt_type_unknown() {
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

		testable.setValueAt("unknown", 0, AttributesDataModel.COLUMN_IDX_TYPE);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void setValueAt_value_boolean() {
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
				oneOf(attributeTemplate_1_mock).setValue(Boolean.TRUE);

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.getDataVector().get(0).set(1, "boolean");
		testable.setValueAt("true", 0, AttributesDataModel.COLUMN_IDX_VALUE);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void setValueAt_value_boolean_null() {
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
				oneOf(attributeTemplate_1_mock).setValue(null);

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.getDataVector().get(0).set(1, "boolean");
		testable.setValueAt(null, 0, AttributesDataModel.COLUMN_IDX_VALUE);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void setValueAt_value_integer() {
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
				oneOf(attributeTemplate_1_mock).setValue(Integer.valueOf(10));

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.getDataVector().get(0).set(1, "integer");
		testable.setValueAt("10", 0, AttributesDataModel.COLUMN_IDX_VALUE);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void setValueAt_value_integer_null() {
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
				oneOf(attributeTemplate_1_mock).setValue(null);

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.getDataVector().get(0).set(1, "integer");
		testable.setValueAt(null, 0, AttributesDataModel.COLUMN_IDX_VALUE);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void setValueAt_value_string() {
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
				oneOf(attributeTemplate_1_mock).setValue("test-string");

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.getDataVector().get(0).set(1, "string");
		testable.setValueAt("test-string", 0, AttributesDataModel.COLUMN_IDX_VALUE);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void setValueAt_value_string_null() {
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
				oneOf(attributeTemplate_1_mock).setValue(null);

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.getDataVector().get(0).set(1, "string");
		testable.setValueAt(null, 0, AttributesDataModel.COLUMN_IDX_VALUE);
	}

	@Test
	public void setValueAt_value_unknown() {
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
				oneOf(attributeTemplate_1_mock).setValue("unknown-value");

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("unknown-value", 0, AttributesDataModel.COLUMN_IDX_VALUE);
	}

	@Test
	public void setValueAt_value_unknown_null() {
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
				oneOf(attributeTemplate_1_mock).setValue(null);

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt(null, 0, AttributesDataModel.COLUMN_IDX_VALUE);
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

		assertEquals("attributeTemplate-name", testable.getValueAt(0, AttributesDataModel.COLUMN_IDX_NAME));
	}

	@Test
	public void getValueAt_type_boolean() {
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
				will(returnValue(Boolean.TRUE));
			}
		});

		assertEquals("boolean", testable.getValueAt(0, AttributesDataModel.COLUMN_IDX_TYPE));
	}

	@Test
	public void getValueAt_type_integer() {
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
				will(returnValue(Integer.valueOf(10)));
			}
		});

		assertEquals("integer", testable.getValueAt(0, AttributesDataModel.COLUMN_IDX_TYPE));
	}

	@Test
	public void getValueAt_type_string() {
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
				will(returnValue("value"));
			}
		});

		assertEquals("string", testable.getValueAt(0, AttributesDataModel.COLUMN_IDX_TYPE));
	}

	@Test
	public void getValueAt_type_unknown() {
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
				will(returnValue(null));
			}
		});

		assertEquals("", testable.getValueAt(0, AttributesDataModel.COLUMN_IDX_TYPE));
	}

	@Test
	public void getValueAt_value_boolean() {
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
				will(returnValue(Boolean.TRUE));
			}
		});

		assertEquals("true", testable.getValueAt(0, AttributesDataModel.COLUMN_IDX_VALUE));
	}

	@Test
	public void getValueAt_value_integer() {
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
				will(returnValue(Integer.valueOf(10)));
			}
		});

		assertEquals("10", testable.getValueAt(0, AttributesDataModel.COLUMN_IDX_VALUE));
	}

	@Test
	public void getValueAt_value_string() {
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
				will(returnValue("value"));
			}
		});

		assertEquals("value", testable.getValueAt(0, AttributesDataModel.COLUMN_IDX_VALUE));
	}

	@Test
	public void getValueAt_value_unknown() {
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
				will(returnValue(null));
			}
		});

		assertEquals(null, testable.getValueAt(0, AttributesDataModel.COLUMN_IDX_VALUE));
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
}

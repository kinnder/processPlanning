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

import planning.model.Attribute;
import planning.model.SystemObject;

public class AttributesDataModelTest {

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

	AttributesDataModel testable;

	@BeforeEach
	public void setup() {
		editorDataModel_mock = context.mock(EditorDataModel.class);

		testable = new AttributesDataModel(editorDataModel_mock);
	}

	@Test
	public void newInstance() {
		assertEquals(0, testable.getRowCount());
	}

	@Test
	public void loadAttributes() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});

		testable.loadAttributes(selectedObject_mock, selectedNode_mock);
		assertEquals(2, testable.getRowCount());
	}

	@Test
	public void getColumnClass() {
		assertEquals(String.class, testable.getColumnClass(AttributesDataModel.COLUMN_IDX_NAME));
		assertEquals(String.class, testable.getColumnClass(AttributesDataModel.COLUMN_IDX_TYPE));
		assertEquals(String.class, testable.getColumnClass(AttributesDataModel.COLUMN_IDX_VALUE));
	}

	@Test
	public void getColumnName() {
		assertEquals("name", testable.getColumnName(AttributesDataModel.COLUMN_IDX_NAME));
		assertEquals("type", testable.getColumnName(AttributesDataModel.COLUMN_IDX_TYPE));
		assertEquals("value", testable.getColumnName(AttributesDataModel.COLUMN_IDX_VALUE));
	}

	@Test
	public void insertAttribute() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				// TODO (2022-11-09 #72): добавить Matcher для Attribute
				oneOf(selectedObject_mock).addAttribute(with(any(Attribute.class)));

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.insertAttribute();
		assertEquals(1, testable.getRowCount());
	}

	@Test
	public void deleteAttribute_no_row_selected() {
		testable.deleteAttribute(-1);
	}

	@Test
	public void deleteAttribute() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).removeAttribute(attribute_1_mock);

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.deleteAttribute(0);
		assertEquals(1, testable.getRowCount());
	}

	@Test
	public void setValueAt_name() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).setName("value");

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("value", 0, AttributesDataModel.COLUMN_IDX_NAME);
	}

	@Test
	public void setValueAt_type_boolean() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).getValue();
				will(returnValue("true"));

				oneOf(attribute_1_mock).setValue(Boolean.TRUE);

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("boolean", 0, AttributesDataModel.COLUMN_IDX_TYPE);
	}

	@Test
	public void setValueAt_type_boolean_null() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).getValue();
				will(returnValue(null));

				oneOf(attribute_1_mock).setValue(null);

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("boolean", 0, AttributesDataModel.COLUMN_IDX_TYPE);
	}

	@Test
	public void setValueAt_type_integer() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).getValue();
				will(returnValue("10"));

				oneOf(attribute_1_mock).setValue(Integer.valueOf(10));

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("integer", 0, AttributesDataModel.COLUMN_IDX_TYPE);
	}

	@Test
	public void setValueAt_type_integer_null() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).getValue();
				will(returnValue(null));

				oneOf(attribute_1_mock).setValue(null);

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("integer", 0, AttributesDataModel.COLUMN_IDX_TYPE);
	}

	@Test
	public void setValueAt_type_string() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).getValue();
				will(returnValue("test-string"));

				oneOf(attribute_1_mock).setValue("test-string");

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("string", 0, AttributesDataModel.COLUMN_IDX_TYPE);
	}

	@Test
	public void setValueAt_type_string_null() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).getValue();
				will(returnValue(null));

				oneOf(attribute_1_mock).setValue(null);

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("string", 0, AttributesDataModel.COLUMN_IDX_TYPE);
	}

	@Test
	public void setValueAt_type_unknown() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

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
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).setValue(Boolean.TRUE);

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.getDataVector().get(0).set(1, "boolean");
		testable.setValueAt("true", 0, AttributesDataModel.COLUMN_IDX_VALUE);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void setValueAt_value_boolean_null() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).setValue(null);

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.getDataVector().get(0).set(1, "boolean");
		testable.setValueAt(null, 0, AttributesDataModel.COLUMN_IDX_VALUE);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void setValueAt_value_integer() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).setValue(Integer.valueOf(10));

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.getDataVector().get(0).set(1, "integer");
		testable.setValueAt("10", 0, AttributesDataModel.COLUMN_IDX_VALUE);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void setValueAt_value_integer_null() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).setValue(null);

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.getDataVector().get(0).set(1, "integer");
		testable.setValueAt(null, 0, AttributesDataModel.COLUMN_IDX_VALUE);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void setValueAt_value_string() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).setValue("test-string");

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.getDataVector().get(0).set(1, "string");
		testable.setValueAt("test-string", 0, AttributesDataModel.COLUMN_IDX_VALUE);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void setValueAt_value_string_null() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).setValue(null);

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.getDataVector().get(0).set(1, "string");
		testable.setValueAt(null, 0, AttributesDataModel.COLUMN_IDX_VALUE);
	}

	@Test
	public void setValueAt_value_unknown() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).setValue("unknown-value");

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("unknown-value", 0, AttributesDataModel.COLUMN_IDX_VALUE);
	}

	@Test
	public void setValueAt_value_unknown_null() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).setValue(null);

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt(null, 0, AttributesDataModel.COLUMN_IDX_VALUE);
	}

	@Test
	public void setValueAt() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("value", 0, -1);
	}

	@Test
	public void getValueAt_name() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).getName();
				will(returnValue("attribute-name"));
			}
		});

		assertEquals("attribute-name", testable.getValueAt(0, AttributesDataModel.COLUMN_IDX_NAME));
	}

	@Test
	public void getValueAt_type_boolean() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).getValue();
				will(returnValue(Boolean.TRUE));
			}
		});

		assertEquals("boolean", testable.getValueAt(0, AttributesDataModel.COLUMN_IDX_TYPE));
	}

	@Test
	public void getValueAt_type_integer() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).getValue();
				will(returnValue(Integer.valueOf(10)));
			}
		});

		assertEquals("integer", testable.getValueAt(0, AttributesDataModel.COLUMN_IDX_TYPE));
	}

	@Test
	public void getValueAt_type_string() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).getValue();
				will(returnValue("value"));
			}
		});

		assertEquals("string", testable.getValueAt(0, AttributesDataModel.COLUMN_IDX_TYPE));
	}

	@Test
	public void getValueAt_type_unknown() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).getValue();
				will(returnValue(null));
			}
		});

		assertEquals("", testable.getValueAt(0, AttributesDataModel.COLUMN_IDX_TYPE));
	}

	@Test
	public void getValueAt_value_boolean() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).getValue();
				will(returnValue(Boolean.TRUE));
			}
		});

		assertEquals("true", testable.getValueAt(0, AttributesDataModel.COLUMN_IDX_VALUE));
	}

	@Test
	public void getValueAt_value_integer() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).getValue();
				will(returnValue(Integer.valueOf(10)));
			}
		});

		assertEquals("10", testable.getValueAt(0, AttributesDataModel.COLUMN_IDX_VALUE));
	}

	@Test
	public void getValueAt_value_string() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).getValue();
				will(returnValue("value"));
			}
		});

		assertEquals("value", testable.getValueAt(0, AttributesDataModel.COLUMN_IDX_VALUE));
	}

	@Test
	public void getValueAt_value_unknown() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).getValue();
				will(returnValue(null));
			}
		});

		assertEquals(null, testable.getValueAt(0, AttributesDataModel.COLUMN_IDX_VALUE));
	}

	@Test
	public void getValueAt() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		assertEquals(null, testable.getValueAt(0, -1));
	}
}

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

import planning.model.AttributeTransformation;
import planning.model.LinkTransformation;
import planning.model.Transformation;
import planning.model.Transformations;

public class TransformationsDataModelTest {

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

	TransformationsDataModel testable;

	@BeforeEach
	public void setup() {
		editorDataModel_mock = context.mock(EditorDataModel.class);

		testable = new TransformationsDataModel(editorDataModel_mock);
	}

	@Test
	public void newInstance() {
		assertEquals(0, testable.getRowCount());
	}

	@Test
	public void getColumnClass() {
		assertEquals(String.class, testable.getColumnClass(TransformationsDataModel.COLUMN_IDX_TYPE));
		assertEquals(String.class, testable.getColumnClass(TransformationsDataModel.COLUMN_IDX_NAME));
		assertEquals(String.class, testable.getColumnClass(TransformationsDataModel.COLUMN_IDX_OBJECT_ID));
	}

	@Test
	public void getColumnName() {
		assertEquals("type", testable.getColumnName(TransformationsDataModel.COLUMN_IDX_TYPE));
		assertEquals("name", testable.getColumnName(TransformationsDataModel.COLUMN_IDX_NAME));
		assertEquals("object-id", testable.getColumnName(TransformationsDataModel.COLUMN_IDX_OBJECT_ID));
	}

	@Test
	public void loadTransformations() {
		final Transformations transformations = new Transformations();
		final Transformation transformation_1_mock = context.mock(Transformation.class, "transformation-1");
		final Transformation transformation_2_mock = context.mock(Transformation.class, "transformation-2");
		transformations.add(transformation_1_mock);
		transformations.add(transformation_2_mock);
		final DefaultMutableTreeNode transformationsNode_mock = context.mock(DefaultMutableTreeNode.class);

		testable.loadTransformations(transformations, transformationsNode_mock);
		assertEquals(2, testable.getRowCount());
	}

	@Test
	public void insertTransformation() {
		final Transformations transformations = new Transformations();
		final Transformation transformation_1_mock = context.mock(Transformation.class, "transformation-1");
		transformations.add(transformation_1_mock);
		final DefaultMutableTreeNode transformationsNode_mock = context.mock(DefaultMutableTreeNode.class);
		testable.loadTransformations(transformations, transformationsNode_mock);

		final DefaultMutableTreeNode transformationNode_mock = context.mock(DefaultMutableTreeNode.class,
				"transformationNode");
		context.checking(new Expectations() {
			{
				oneOf(editorDataModel_mock).createTransformationNode(with(any(Transformation.class)));
				will(returnValue(transformationNode_mock));

				oneOf(transformationsNode_mock).add(transformationNode_mock);

				oneOf(editorDataModel_mock).insertNodeInto(transformationNode_mock, transformationsNode_mock, 1);
			}
		});

		testable.insertTransformation();
		assertEquals(2, testable.getRowCount());
	}

	@Test
	public void insertLinkTransformation() {
		final Transformations transformations = new Transformations();
		final Transformation transformation_1_mock = context.mock(Transformation.class, "transformation-1");
		transformations.add(transformation_1_mock);
		final DefaultMutableTreeNode transformationsNode_mock = context.mock(DefaultMutableTreeNode.class);
		testable.loadTransformations(transformations, transformationsNode_mock);

		final DefaultMutableTreeNode transformationNode_mock = context.mock(DefaultMutableTreeNode.class,
				"transformationNode");
		context.checking(new Expectations() {
			{
				oneOf(editorDataModel_mock).createTransformationNode(with(any(LinkTransformation.class)));
				will(returnValue(transformationNode_mock));

				oneOf(transformationsNode_mock).add(transformationNode_mock);

				oneOf(editorDataModel_mock).insertNodeInto(transformationNode_mock, transformationsNode_mock, 1);
			}
		});

		testable.insertLinkTransformation();
		assertEquals(2, testable.getRowCount());
	}

	@Test
	public void insertAttributeTransformation() {
		final Transformations transformations = new Transformations();
		final Transformation transformation_1_mock = context.mock(Transformation.class, "transformation-1");
		transformations.add(transformation_1_mock);
		final DefaultMutableTreeNode transformationsNode_mock = context.mock(DefaultMutableTreeNode.class);
		testable.loadTransformations(transformations, transformationsNode_mock);

		final DefaultMutableTreeNode transformationNode_mock = context.mock(DefaultMutableTreeNode.class,
				"transformationNode");
		context.checking(new Expectations() {
			{
				// TODO (2022-12-21 #73): добавить Matcher для AttributeTransformation
				oneOf(editorDataModel_mock).createTransformationNode(with(any(AttributeTransformation.class)));
				will(returnValue(transformationNode_mock));

				oneOf(transformationsNode_mock).add(transformationNode_mock);

				oneOf(editorDataModel_mock).insertNodeInto(transformationNode_mock, transformationsNode_mock, 1);
			}
		});

		testable.insertAttributeTransformation();
		assertEquals(2, testable.getRowCount());
	}

	@Test
	public void deleteTransformation_no_row_selected() {
		testable.deleteTransformation(-1);
	}

	@Test
	public void deleteTransformation() {
		final Transformations transformations = new Transformations();
		final Transformation transformation_1_mock = context.mock(Transformation.class, "transformation-1");
		final Transformation transformation_2_mock = context.mock(Transformation.class, "transformation-2");
		transformations.add(transformation_1_mock);
		transformations.add(transformation_2_mock);
		final DefaultMutableTreeNode transformationsNode_mock = context.mock(DefaultMutableTreeNode.class);
		testable.loadTransformations(transformations, transformationsNode_mock);

		final DefaultMutableTreeNode transformationNode_mock = context.mock(DefaultMutableTreeNode.class,
				"transformationNode");
		context.checking(new Expectations() {
			{
				oneOf(transformationsNode_mock).getChildAt(0);
				will(returnValue(transformationNode_mock));

				oneOf(editorDataModel_mock).removeNodeFromParent(transformationNode_mock);
			}
		});

		testable.deleteTransformation(0);
		assertEquals(transformation_2_mock, transformations.get(0));
		assertEquals(1, testable.getRowCount());
	}

	@Test
	public void clear() {
		testable.clear();
	}

	@Test
	public void setValueAt_type() {
		final Transformations transformations = new Transformations();
		final Transformation transformation_1_mock = context.mock(Transformation.class, "transformation-1");
		transformations.add(transformation_1_mock);
		final DefaultMutableTreeNode transformationsNode_mock = context.mock(DefaultMutableTreeNode.class);
		testable.loadTransformations(transformations, transformationsNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(editorDataModel_mock).nodesChanged(transformationsNode_mock, new int[] { 0 });
			}
		});

		testable.setValueAt("value", 0, TransformationsDataModel.COLUMN_IDX_TYPE);
	}

	@Test
	public void setValueAt_name() {
		final Transformations transformations = new Transformations();
		final Transformation transformation_1_mock = context.mock(Transformation.class, "transformation-1");
		transformations.add(transformation_1_mock);
		final DefaultMutableTreeNode transformationsNode_mock = context.mock(DefaultMutableTreeNode.class);
		testable.loadTransformations(transformations, transformationsNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(editorDataModel_mock).nodesChanged(transformationsNode_mock, new int[] { 0 });
			}
		});

		testable.setValueAt("value", 0, TransformationsDataModel.COLUMN_IDX_NAME);
	}

	@Test
	public void setValueAt_objectId() {
		final Transformations transformations = new Transformations();
		final Transformation transformation_1_mock = context.mock(Transformation.class, "transformation-1");
		transformations.add(transformation_1_mock);
		final DefaultMutableTreeNode transformationsNode_mock = context.mock(DefaultMutableTreeNode.class);
		testable.loadTransformations(transformations, transformationsNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(transformation_1_mock).setId("value");

				oneOf(editorDataModel_mock).nodesChanged(transformationsNode_mock, new int[] { 0 });
			}
		});

		testable.setValueAt("value", 0, TransformationsDataModel.COLUMN_IDX_OBJECT_ID);
	}

	@Test
	public void setValueAt() {
		final Transformations transformations = new Transformations();
		final Transformation transformation_1_mock = context.mock(Transformation.class, "transformation-1");
		transformations.add(transformation_1_mock);
		final DefaultMutableTreeNode transformationsNode_mock = context.mock(DefaultMutableTreeNode.class);
		testable.loadTransformations(transformations, transformationsNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(editorDataModel_mock).nodesChanged(transformationsNode_mock, new int[] { 0 });
			}
		});

		testable.setValueAt("value", 0, -1);
	}

	@Test
	public void getValueAt_type_transformation() {
		final Transformations transformations = new Transformations();
		final Transformation transformation_1_mock = context.mock(Transformation.class, "transformation-1");
		transformations.add(transformation_1_mock);
		final DefaultMutableTreeNode transformationsNode_mock = context.mock(DefaultMutableTreeNode.class);
		testable.loadTransformations(transformations, transformationsNode_mock);

		assertEquals("transformation", testable.getValueAt(0, TransformationsDataModel.COLUMN_IDX_TYPE));
	}

	@Test
	public void getValueAt_type_attributeTransformation() {
		final Transformations transformations = new Transformations();
		final AttributeTransformation transformation_1_mock = context.mock(AttributeTransformation.class,
				"transformation-1");
		transformations.add(transformation_1_mock);
		final DefaultMutableTreeNode transformationsNode_mock = context.mock(DefaultMutableTreeNode.class);
		testable.loadTransformations(transformations, transformationsNode_mock);

		assertEquals("attributeTransformation", testable.getValueAt(0, TransformationsDataModel.COLUMN_IDX_TYPE));
	}

	@Test
	public void getValueAt_type_linkTransformation() {
		final Transformations transformations = new Transformations();
		final LinkTransformation transformation_1_mock = context.mock(LinkTransformation.class, "transformation-1");
		transformations.add(transformation_1_mock);
		final DefaultMutableTreeNode transformationsNode_mock = context.mock(DefaultMutableTreeNode.class);
		testable.loadTransformations(transformations, transformationsNode_mock);

		assertEquals("linkTransformation", testable.getValueAt(0, TransformationsDataModel.COLUMN_IDX_TYPE));
	}

	@Test
	public void getValueAt_name() {
		final Transformations transformations = new Transformations();
		final Transformation transformation_1_mock = context.mock(Transformation.class, "transformation-1");
		transformations.add(transformation_1_mock);
		final DefaultMutableTreeNode transformationsNode_mock = context.mock(DefaultMutableTreeNode.class);
		testable.loadTransformations(transformations, transformationsNode_mock);

		assertEquals("unknown", testable.getValueAt(0, TransformationsDataModel.COLUMN_IDX_NAME));
	}

	@Test
	public void getValueAt_objectId() {
		final Transformations transformations = new Transformations();
		final Transformation transformation_1_mock = context.mock(Transformation.class, "transformation-1");
		transformations.add(transformation_1_mock);
		final DefaultMutableTreeNode transformationsNode_mock = context.mock(DefaultMutableTreeNode.class);
		testable.loadTransformations(transformations, transformationsNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(transformation_1_mock).getId();
				will(returnValue("id-123"));
			}
		});

		assertEquals("id-123", testable.getValueAt(0, TransformationsDataModel.COLUMN_IDX_OBJECT_ID));
	}

	@Test
	public void getValueAt() {
		final Transformations transformations = new Transformations();
		final Transformation transformation_1_mock = context.mock(Transformation.class, "transformation-1");
		transformations.add(transformation_1_mock);
		final DefaultMutableTreeNode transformationsNode_mock = context.mock(DefaultMutableTreeNode.class);
		testable.loadTransformations(transformations, transformationsNode_mock);

		assertEquals(null, testable.getValueAt(0, -1));
	}
}

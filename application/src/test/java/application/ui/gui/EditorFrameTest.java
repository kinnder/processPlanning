package application.ui.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.MutableTreeNode;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import org.junit.jupiter.api.extension.RegisterExtension;

import application.Application;
import application.ui.gui.editor.ActionDataModel;
import application.ui.gui.editor.ActionFunctionsDataModel;
import application.ui.gui.editor.AttributeTemplatesDataModel;
import application.ui.gui.editor.AttributesDataModel;
import application.ui.gui.editor.EdgeDataModel;
import application.ui.gui.editor.EdgesDataModel;
import application.ui.gui.editor.EditorDataModel;
import application.ui.gui.editor.LinkTemplatesDataModel;
import application.ui.gui.editor.LinksDataModel;
import application.ui.gui.editor.NodeDataModel;
import application.ui.gui.editor.ObjectDataModel;
import application.ui.gui.editor.ObjectTemplateDataModel;
import application.ui.gui.editor.ObjectTemplatesDataModel;
import application.ui.gui.editor.ObjectsDataModel;
import application.ui.gui.editor.ParametersDataModel;
import application.ui.gui.editor.SystemDataModel;
import application.ui.gui.editor.SystemTemplateDataModel;
import application.ui.gui.editor.SystemTransformationsDataModel;
import application.ui.gui.editor.TransformationsDataModel;
import planning.method.NodeNetwork;
import planning.method.SystemTransformations;
import planning.method.TaskDescription;
import planning.model.SystemProcess;

@DisabledIf("isHeadless")
public class EditorFrameTest {

	@RegisterExtension
	JUnit5Mockery context = new JUnit5Mockery() {
		{
			setImposteriser(ByteBuddyClassImposteriser.INSTANCE);
			setThreadingPolicy(new Synchroniser());
		}
	};

	static boolean isHeadless() {
		return GraphicsEnvironment.isHeadless();
	}

	@AfterEach
	public void teardown() {
		context.assertIsSatisfied();
	}

	EditorDataModel editorDataModel_mock;

	ObjectsDataModel objectsDataModel_mock;

	LinksDataModel linksDataModel_mock;

	AttributesDataModel attributesDataModel_mock;

	SystemTransformationsDataModel systemTransformationsDataModel_mock;

	ObjectTemplatesDataModel objectTemplatesDataModel_mock;

	LinkTemplatesDataModel linkTemplatesDataModel_mock;

	AttributeTemplatesDataModel attributeTemplatesDataModel_mock;

	ActionFunctionsDataModel actionFunctionsDataModel_mock;

	TransformationsDataModel transformationsDataModel_mock;

	ObjectTemplateDataModel objectTemplateDataModel_mock;

	ActionDataModel actionDataModel_mock;

	NodeDataModel nodeDataModel_mock;

	EdgeDataModel edgeDataModel_mock;

	ParametersDataModel parametersDataModel_mock;

	EdgesDataModel edgesDataModel_mock;

	SystemTemplateDataModel systemTemplateDataModel_mock;

	Application application_mock;

	ObjectDataModel objectDataModel_mock;

	SystemDataModel systemDataModel_mock;

	EditorFrame testable;

	@BeforeEach
	public void setup() {
		application_mock = context.mock(Application.class);
		editorDataModel_mock = context.mock(EditorDataModel.class);
		objectsDataModel_mock = context.mock(ObjectsDataModel.class);
		linksDataModel_mock = context.mock(LinksDataModel.class);
		attributesDataModel_mock = context.mock(AttributesDataModel.class);
		systemTransformationsDataModel_mock = context.mock(SystemTransformationsDataModel.class);
		objectTemplatesDataModel_mock = context.mock(ObjectTemplatesDataModel.class);
		linkTemplatesDataModel_mock = context.mock(LinkTemplatesDataModel.class);
		attributeTemplatesDataModel_mock = context.mock(AttributeTemplatesDataModel.class);
		actionFunctionsDataModel_mock = context.mock(ActionFunctionsDataModel.class);
		transformationsDataModel_mock = context.mock(TransformationsDataModel.class);
		objectTemplateDataModel_mock = context.mock(ObjectTemplateDataModel.class);
		actionDataModel_mock = context.mock(ActionDataModel.class);
		nodeDataModel_mock = context.mock(NodeDataModel.class);
		edgeDataModel_mock = context.mock(EdgeDataModel.class);
		parametersDataModel_mock = context.mock(ParametersDataModel.class);
		edgesDataModel_mock = context.mock(EdgesDataModel.class);
		systemTemplateDataModel_mock = context.mock(SystemTemplateDataModel.class);
		objectDataModel_mock = context.mock(ObjectDataModel.class);
		systemDataModel_mock = context.mock(SystemDataModel.class);
		final MutableTreeNode node_mock = context.mock(MutableTreeNode.class);

		context.checking(new Expectations() {
			{
				allowing(editorDataModel_mock).addTreeModelListener(with(any(TreeModelListener.class)));

				allowing(editorDataModel_mock).getRoot();
				will(returnValue(node_mock));

				allowing(editorDataModel_mock).isLeaf(node_mock);
				will(returnValue(true));

				allowing(systemTransformationsDataModel_mock).addTableModelListener(with(any(TableModelListener.class)));

				allowing(systemTransformationsDataModel_mock).getColumnCount();
				will(returnValue(0));

				allowing(systemTransformationsDataModel_mock).getRowCount();
				will(returnValue(0));

				allowing(transformationsDataModel_mock).addTableModelListener(with(any(TableModelListener.class)));

				allowing(transformationsDataModel_mock).getColumnCount();
				will(returnValue(0));

				allowing(transformationsDataModel_mock).getRowCount();
				will(returnValue(0));

				oneOf(edgeDataModel_mock).getParametersDataModel();
				will(returnValue(parametersDataModel_mock));

				// <-- setModel()

				allowing(parametersDataModel_mock).addTableModelListener(with(any(TableModelListener.class)));

				allowing(parametersDataModel_mock).getColumnCount();
				will(returnValue(0));

				allowing(parametersDataModel_mock).getRowCount();
				will(returnValue(0));

				// -->

				oneOf(nodeDataModel_mock).getEdgesDataModel();
				will(returnValue(edgesDataModel_mock));

				// <-- setModel()

				allowing(edgesDataModel_mock).addTableModelListener(with(any(TableModelListener.class)));

				allowing(edgesDataModel_mock).getColumnCount();
				will(returnValue(0));

				allowing(edgesDataModel_mock).getRowCount();
				will(returnValue(0));

				// -->

				oneOf(actionDataModel_mock).getActionFunctionsDataModel();
				will(returnValue(actionFunctionsDataModel_mock));

				// <-- setModel()
				allowing(actionFunctionsDataModel_mock).addTableModelListener(with(any(TableModelListener.class)));

				allowing(actionFunctionsDataModel_mock).getColumnCount();
				will(returnValue(0));

				allowing(actionFunctionsDataModel_mock).getRowCount();
				will(returnValue(0));

				// -->

				oneOf(objectTemplateDataModel_mock).getAttributeTemplatesDataModel();
				will(returnValue(attributeTemplatesDataModel_mock));

				// <-- setModel()

				allowing(attributeTemplatesDataModel_mock).addTableModelListener(with(any(TableModelListener.class)));

				allowing(attributeTemplatesDataModel_mock).getColumnCount();
				will(returnValue(0));

				allowing(attributeTemplatesDataModel_mock).getRowCount();
				will(returnValue(0));

				// -->

				oneOf(systemTemplateDataModel_mock).getObjectTemplatesDataModel();
				will(returnValue(objectTemplatesDataModel_mock));

				// <-- setModel()

				allowing(objectTemplatesDataModel_mock).addTableModelListener(with(any(TableModelListener.class)));

				allowing(objectTemplatesDataModel_mock).getColumnCount();
				will(returnValue(0));

				allowing(objectTemplatesDataModel_mock).getRowCount();
				will(returnValue(0));

				// -->

				oneOf(systemTemplateDataModel_mock).getLinkTemplatesDataModel();
				will(returnValue(linkTemplatesDataModel_mock));

				// <-- setModel()

				allowing(linkTemplatesDataModel_mock).addTableModelListener(with(any(TableModelListener.class)));

				allowing(linkTemplatesDataModel_mock).getColumnCount();
				will(returnValue(0));

				allowing(linkTemplatesDataModel_mock).getRowCount();
				will(returnValue(0));

				// -->

				oneOf(objectDataModel_mock).getAttributesDataModel();
				will(returnValue(attributesDataModel_mock));

				// <-- setModel()

				allowing(attributesDataModel_mock).addTableModelListener(with(any(TableModelListener.class)));

				allowing(attributesDataModel_mock).getColumnCount();
				will(returnValue(0));

				allowing(attributesDataModel_mock).getRowCount();
				will(returnValue(0));

				// -->

				oneOf(systemDataModel_mock).getObjectsDataModel();
				will(returnValue(objectsDataModel_mock));

				// <-- setModel()

				allowing(objectsDataModel_mock).addTableModelListener(with(any(TableModelListener.class)));

				allowing(objectsDataModel_mock).getColumnCount();
				will(returnValue(0));

				allowing(objectsDataModel_mock).getRowCount();
				will(returnValue(0));

				// -->

				oneOf(systemDataModel_mock).getLinksDataModel();
				will(returnValue(linksDataModel_mock));

				// <-- setModel()

				allowing(linksDataModel_mock).addTableModelListener(with(any(TableModelListener.class)));

				allowing(linksDataModel_mock).getColumnCount();
				will(returnValue(0));

				allowing(linksDataModel_mock).getRowCount();
				will(returnValue(0));

				// -->
			}
		});

		testable = new EditorFrame(application_mock, editorDataModel_mock, systemDataModel_mock, objectDataModel_mock,
				systemTransformationsDataModel_mock, systemTemplateDataModel_mock, transformationsDataModel_mock,
				objectTemplateDataModel_mock, actionDataModel_mock, nodeDataModel_mock, edgeDataModel_mock);
	}

	@Test
	public void newInstance() {
		testable = new EditorFrame(application_mock);
	}

	@Test
	public void taskDescriptionLoadAction_name() {
		assertEquals("Load", testable.taskDescriptionLoadAction.getValue(Action.NAME));
	}

	@Test
	public void taskDescriptionLoadAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);
		final TaskDescription taskDescription_mock = context.mock(TaskDescription.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).loadTaskDescription();
				will(returnValue(taskDescription_mock));

				oneOf(editorDataModel_mock).loadTaskDescription(taskDescription_mock);
			}
		});

		testable.taskDescriptionLoadAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void taskDescriptionSaveAction_name() {
		assertEquals("Save", testable.taskDescriptionSaveAction.getValue(Action.NAME));
	}

	@Test
	public void taskDescriptionSaveAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);
		final TaskDescription taskDescription_mock = context.mock(TaskDescription.class);

		context.checking(new Expectations() {
			{
				oneOf(editorDataModel_mock).saveTaskDescription();
				will(returnValue(taskDescription_mock));

				oneOf(application_mock).saveTaskDescription(taskDescription_mock);
			}
		});

		testable.taskDescriptionSaveAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void systemTransformationsLoadAction_name() {
		assertEquals("Load", testable.systemTransformationsLoadAction.getValue(Action.NAME));
	}

	@Test
	public void systemTransformationsLoadAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);
		final SystemTransformations systemTransformations_mock = context.mock(SystemTransformations.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).loadSystemTransformations();
				will(returnValue(systemTransformations_mock));

				oneOf(editorDataModel_mock).loadSystemTransformations(systemTransformations_mock);
			}
		});

		testable.systemTransformationsLoadAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void systemTransformationsSaveAction_name() {
		assertEquals("Save", testable.systemTransformationsSaveAction.getValue(Action.NAME));
	}

	@Test
	public void systemTransformationsSaveAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);
		final SystemTransformations systemTransformations_mock = context.mock(SystemTransformations.class);

		context.checking(new Expectations() {
			{
				oneOf(editorDataModel_mock).saveSystemTransformations();
				will(returnValue(systemTransformations_mock));

				oneOf(application_mock).saveSystemTransformations(systemTransformations_mock);
			}
		});

		testable.systemTransformationsSaveAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void nodeNetworkLoadAction_name() {
		assertEquals("Load", testable.nodeNetworkLoadAction.getValue(Action.NAME));
	}

	@Test
	public void nodeNetworkLoadAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);
		final NodeNetwork nodeNetwork_mock = context.mock(NodeNetwork.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).loadNodeNetwork();
				will(returnValue(nodeNetwork_mock));

				oneOf(editorDataModel_mock).loadNodeNetwork(nodeNetwork_mock);
			}
		});

		testable.nodeNetworkLoadAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void processLoadAction_name() {
		assertEquals("Load", testable.processLoadAction.getValue(Action.NAME));
	}

	@Test
	public void processLoadAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);
		final SystemProcess systemProcess_mock = context.mock(SystemProcess.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).loadSystemProcess();
				will(returnValue(systemProcess_mock));

				oneOf(editorDataModel_mock).loadSystemProcess(systemProcess_mock);
			}
		});

		testable.processLoadAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void objectsInsertAction_name() {
		assertEquals("Insert", testable.objectsInsertAction.getValue(Action.NAME));
	}

	@Test
	public void objectsInsertAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(systemDataModel_mock).insertObject();
			}
		});

		testable.objectsInsertAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void objectsDeleteAction_name() {
		assertEquals("Delete", testable.objectsDeleteAction.getValue(Action.NAME));
	}

	@Test
	public void objectsDeleteAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(systemDataModel_mock).deleteObject(-1);
			}
		});

		testable.objectsDeleteAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void linksInsertAction_name() {
		assertEquals("Insert", testable.linksInsertAction.getValue(Action.NAME));
	}

	@Test
	public void linksInsertAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(systemDataModel_mock).insertLink();
			}
		});

		testable.linksInsertAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void linksDeleteAction_name() {
		assertEquals("Delete", testable.linksDeleteAction.getValue(Action.NAME));
	}

	@Test
	public void linksDeleteAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(systemDataModel_mock).deleteLink(-1);
			}
		});

		testable.linksDeleteAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void attributesInsertAction_name() {
		assertEquals("Insert", testable.attributesInsertAction.getValue(Action.NAME));
	}

	@Test
	public void attributesInsertAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(objectDataModel_mock).insertAttribute();
			}
		});

		testable.attributesInsertAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void attributesDeleteAction_name() {
		assertEquals("Delete", testable.attributesDeleteAction.getValue(Action.NAME));
	}

	@Test
	public void attributesDeleteAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(objectDataModel_mock).deleteAttribute(-1);
			}
		});

		testable.attributesDeleteAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void systemTransformationsInsertAction_name() {
		assertEquals("Insert", testable.systemTransformationsInsertAction.getValue(Action.NAME));
	}

	@Test
	public void systemTransformationsInsertAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(systemTransformationsDataModel_mock).insertSystemTransformation();
			}
		});

		testable.systemTransformationsInsertAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void systemTransformationsDeleteAction_name() {
		assertEquals("Delete", testable.systemTransformationsDeleteAction.getValue(Action.NAME));
	}

	@Test
	public void systemTransformationsDeleteAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(systemTransformationsDataModel_mock).deleteSystemTransformation(-1);
			}
		});

		testable.systemTransformationsDeleteAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void objectTemplateInsertAction_name() {
		assertEquals("Insert", testable.objectTemplateInsertAction.getValue(Action.NAME));
	}

	@Test
	public void objectTemplateInsertAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(systemTemplateDataModel_mock).insertObjectTemplate();
			}
		});

		testable.objectTemplateInsertAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void objectTemplateDeleteAction_name() {
		assertEquals("Delete", testable.objectTemplateDeleteAction.getValue(Action.NAME));
	}

	@Test
	public void objectTemplateDeleteAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(systemTemplateDataModel_mock).deleteObjectTemplate(-1);
			}
		});

		testable.objectTemplateDeleteAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void linkTemplatesInsertAction_name() {
		assertEquals("Insert", testable.linkTemplatesInsertAction.getValue(Action.NAME));
	}

	@Test
	public void linkTemplatesInsertAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(systemTemplateDataModel_mock).insertLinkTemplate();
			}
		});

		testable.linkTemplatesInsertAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void linkTemplatesDeleteAction_name() {
		assertEquals("Delete", testable.linkTemplatesDeleteAction.getValue(Action.NAME));
	}

	@Test
	public void linkTemplatesDeleteAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(systemTemplateDataModel_mock).deleteLinkTemplate(-1);
			}
		});

		testable.linkTemplatesDeleteAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void attributeTemplateInsertAction_name() {
		assertEquals("Insert", testable.attributeTemplateInsertAction.getValue(Action.NAME));
	}

	@Test
	public void attributeTemplateInserAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(objectTemplateDataModel_mock).insertAttributeTemplate();
			}
		});

		testable.attributeTemplateInsertAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void attributeTemplateDeleteAction_name() {
		assertEquals("Delete", testable.attributeTemplateDeleteAction.getValue(Action.NAME));
	}

	@Test
	public void attributeTemplateDeleteAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(objectTemplateDataModel_mock).deleteAttributeTemplate(-1);
			}
		});

		testable.attributeTemplateDeleteAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void actionFunctionsInsertAction_name() {
		assertEquals("Insert", testable.actionFunctionsInsertAction.getValue(Action.NAME));
	}

	@Test
	public void actionFunctionsInsertAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(actionDataModel_mock).insertActionFunction();
			}
		});

		testable.actionFunctionsInsertAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void acitonFunctionsDeleteAction_name() {
		assertEquals("Delete", testable.actionFunctionsDeleteAction.getValue(Action.NAME));
	}

	@Test
	public void actionFunctionsDeleteAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(actionDataModel_mock).deleteActionFunction(-1);
			}
		});

		testable.actionFunctionsDeleteAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void transformationsInsertTransformationAction_name() {
		assertEquals("Insert", testable.transformationsInsertTransformationAction.getValue(Action.NAME));
	}

	@Test
	public void transformationsInsertTransformationAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(transformationsDataModel_mock).insertTransformation();
			}
		});

		testable.transformationsInsertTransformationAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void transformationsInsertLinkTransformationAction_name() {
		assertEquals("Insert Link", testable.transformationsInsertLinkTransformationAction.getValue(Action.NAME));
	}

	@Test
	public void transformationsInsertLinkTransformationAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(transformationsDataModel_mock).insertLinkTransformation();
			}
		});

		testable.transformationsInsertLinkTransformationAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void transformationsInsertAttributeTransformationAction_name() {
		assertEquals("Insert Attribute", testable.transformationsInsertAttributeTransformationAction.getValue(Action.NAME));
	}

	@Test
	public void transformationsInsertAttributeTransformationAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(transformationsDataModel_mock).insertAttributeTransformation();
			}
		});

		testable.transformationsInsertAttributeTransformationAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void transformationsDeleteTransformationAction_name() {
		assertEquals("Delete", testable.transformationsDeleteTransformationAction.getValue(Action.NAME));
	}

	@Test
	public void transformationsDeleteTransformationAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(transformationsDataModel_mock).deleteTransformation(-1);
			}
		});

		testable.transformationsDeleteTransformationAction.actionPerformed(actionEvent_mock);
	}
}

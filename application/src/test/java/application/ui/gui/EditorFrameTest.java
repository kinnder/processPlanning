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
import application.ui.gui.editor.ObjectTemplateDataModel;
import application.ui.gui.editor.ObjectTemplatesDataModel;
import application.ui.gui.editor.ObjectsDataModel;
import application.ui.gui.editor.ParametersDataModel;
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

	EditorFrame testable;

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

	Application application_mock;

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
		final MutableTreeNode node_mock = context.mock(MutableTreeNode.class);

		context.checking(new Expectations() {
			{
				allowing(editorDataModel_mock).addTreeModelListener(with(any(TreeModelListener.class)));

				allowing(editorDataModel_mock).getRoot();
				will(returnValue(node_mock));

				allowing(editorDataModel_mock).isLeaf(node_mock);
				will(returnValue(true));

				allowing(objectsDataModel_mock).addTableModelListener(with(any(TableModelListener.class)));

				allowing(objectsDataModel_mock).getColumnCount();
				will(returnValue(0));

				allowing(objectsDataModel_mock).getRowCount();
				will(returnValue(0));

				allowing(linksDataModel_mock).addTableModelListener(with(any(TableModelListener.class)));

				allowing(linksDataModel_mock).getColumnCount();
				will(returnValue(0));

				allowing(linksDataModel_mock).getRowCount();
				will(returnValue(0));

				allowing(attributesDataModel_mock).addTableModelListener(with(any(TableModelListener.class)));

				allowing(attributesDataModel_mock).getColumnCount();
				will(returnValue(0));

				allowing(attributesDataModel_mock).getRowCount();
				will(returnValue(0));

				allowing(systemTransformationsDataModel_mock).addTableModelListener(with(any(TableModelListener.class)));

				allowing(systemTransformationsDataModel_mock).getColumnCount();
				will(returnValue(0));

				allowing(systemTransformationsDataModel_mock).getRowCount();
				will(returnValue(0));

				allowing(objectTemplatesDataModel_mock).addTableModelListener(with(any(TableModelListener.class)));

				allowing(objectTemplatesDataModel_mock).getColumnCount();
				will(returnValue(0));

				allowing(objectTemplatesDataModel_mock).getRowCount();
				will(returnValue(0));

				allowing(linkTemplatesDataModel_mock).addTableModelListener(with(any(TableModelListener.class)));

				allowing(linkTemplatesDataModel_mock).getColumnCount();
				will(returnValue(0));

				allowing(linkTemplatesDataModel_mock).getRowCount();
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
			}
		});

		testable = new EditorFrame(application_mock, editorDataModel_mock, objectsDataModel_mock, linksDataModel_mock,
				attributesDataModel_mock, systemTransformationsDataModel_mock, objectTemplatesDataModel_mock,
				linkTemplatesDataModel_mock, transformationsDataModel_mock, objectTemplateDataModel_mock,
				actionDataModel_mock, nodeDataModel_mock, edgeDataModel_mock);
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
	public void objectInsertAction_name() {
		assertEquals("Insert", testable.objectInsertAction.getValue(Action.NAME));
	}

	@Test
	public void objectInsertAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(objectsDataModel_mock).insertObject();
			}
		});

		testable.objectInsertAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void objectDeleteAction_name() {
		assertEquals("Delete", testable.objectDeleteAction.getValue(Action.NAME));
	}

	@Test
	public void objectDeleteAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(objectsDataModel_mock).deleteObject(-1);
			}
		});

		testable.objectDeleteAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void linkInsertAction_name() {
		assertEquals("Insert", testable.linkInsertAction.getValue(Action.NAME));
	}

	@Test
	public void linkInsertAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(linksDataModel_mock).insertLink();
			}
		});

		testable.linkInsertAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void linkDeleteAction_name() {
		assertEquals("Delete", testable.linkDeleteAction.getValue(Action.NAME));
	}

	@Test
	public void linkDeleteAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(linksDataModel_mock).deleteLink(-1);
			}
		});

		testable.linkDeleteAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void attributeInsertAction_name() {
		assertEquals("Insert", testable.attributeInsertAction.getValue(Action.NAME));
	}

	@Test
	public void attributeInsertAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(attributesDataModel_mock).insertAttribute();
			}
		});

		testable.attributeInsertAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void attributeDeleteAction_name() {
		assertEquals("Delete", testable.attributeDeleteAction.getValue(Action.NAME));
	}

	@Test
	public void attributeDeleteAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(attributesDataModel_mock).deleteAttribute(-1);
			}
		});

		testable.attributeDeleteAction.actionPerformed(actionEvent_mock);
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
				oneOf(objectTemplatesDataModel_mock).insertObjectTemplate();
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
				oneOf(objectTemplatesDataModel_mock).deleteObjectTemplate(-1);
			}
		});

		testable.objectTemplateDeleteAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void linkTemplateInsertAction_name() {
		assertEquals("Insert", testable.linkTemplateInsertAction.getValue(Action.NAME));
	}

	@Test
	public void linkTemplateInsertAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(linkTemplatesDataModel_mock).insertLinkTemplate();
			}
		});

		testable.linkTemplateInsertAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void linkTemplateDeleteAction_name() {
		assertEquals("Delete", testable.linkTemplateDeleteAction.getValue(Action.NAME));
	}

	@Test
	public void linkTemplateDeletAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(linkTemplatesDataModel_mock).deleteLinkTemplate(-1);
			}
		});

		testable.linkTemplateDeleteAction.actionPerformed(actionEvent_mock);
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
	public void actionFunctionsInsertActionFunction_name() {
		assertEquals("Insert", testable.actionFunctionsInsertActionFunction.getValue(Action.NAME));
	}

	@Test
	public void actionFunctionsInsertActionFunction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(actionDataModel_mock).insertActionFunction();
			}
		});

		testable.actionFunctionsInsertActionFunction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void acitonFunctionsDeleteActionFunction_name() {
		assertEquals("Delete", testable.actionFunctionsDeleteActionFunction.getValue(Action.NAME));
	}

	@Test
	public void actionFunctionsDeleteActionFunction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(actionDataModel_mock).deleteActionFunction(-1);
			}
		});

		testable.actionFunctionsDeleteActionFunction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void transformationsInsertTransformation_name() {
		assertEquals("Insert", testable.transformationsInsertTransformation.getValue(Action.NAME));
	}

	@Test
	public void transformationsInsertTransformation_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(transformationsDataModel_mock).insertTransformation();
			}
		});

		testable.transformationsInsertTransformation.actionPerformed(actionEvent_mock);
	}

	@Test
	public void transformationsInsertLinkTransformation_name() {
		assertEquals("Insert Link", testable.transformationsInsertLinkTransformation.getValue(Action.NAME));
	}

	@Test
	public void transformationsInsertLinkTransformation_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(transformationsDataModel_mock).insertLinkTransformation();
			}
		});

		testable.transformationsInsertLinkTransformation.actionPerformed(actionEvent_mock);
	}

	@Test
	public void transformationsInsertAttributeTransformation_name() {
		assertEquals("Insert Attribute", testable.transformationsInsertAttributeTransformation.getValue(Action.NAME));
	}

	@Test
	public void transformationsInsertAttributeTransformation_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(transformationsDataModel_mock).insertAttributeTransformation();
			}
		});

		testable.transformationsInsertAttributeTransformation.actionPerformed(actionEvent_mock);
	}

	@Test
	public void transformationsDeleteTransformation_name() {
		assertEquals("Delete", testable.transformationsDeleteTransformation.getValue(Action.NAME));
	}

	@Test
	public void transformationsDeleteTransformation_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(transformationsDataModel_mock).deleteTransformation(-1);
			}
		});

		testable.transformationsDeleteTransformation.actionPerformed(actionEvent_mock);
	}
}

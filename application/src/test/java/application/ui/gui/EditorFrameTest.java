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
import application.ui.gui.editor.AttributesDataModel;
import application.ui.gui.editor.EditorDataModel;
import application.ui.gui.editor.LinksDataModel;
import application.ui.gui.editor.ObjectsDataModel;
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

	Application application_mock;

	@BeforeEach
	public void setup() {
		application_mock = context.mock(Application.class);
		editorDataModel_mock = context.mock(EditorDataModel.class);
		objectsDataModel_mock = context.mock(ObjectsDataModel.class);
		linksDataModel_mock = context.mock(LinksDataModel.class);
		attributesDataModel_mock = context.mock(AttributesDataModel.class);
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
			}
		});

		testable = new EditorFrame(application_mock, editorDataModel_mock, objectsDataModel_mock, linksDataModel_mock,
				attributesDataModel_mock);
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
}

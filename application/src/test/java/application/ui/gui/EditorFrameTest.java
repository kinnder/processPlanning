package application.ui.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.MutableTreeNode;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import org.junit.jupiter.api.extension.RegisterExtension;

import application.Application;
import application.ui.gui.editor.EditorDataModel;
import planning.method.TaskDescription;

@DisabledIf("isHeadless")
public class EditorFrameTest {

	@RegisterExtension
	JUnit5Mockery context = new JUnit5Mockery() {
		{
			setImposteriser(ByteBuddyClassImposteriser.INSTANCE);
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

	Application application_mock;

	@BeforeEach
	public void setup() {
		application_mock = context.mock(Application.class);
		editorDataModel_mock = context.mock(EditorDataModel.class);
		final MutableTreeNode node_mock = context.mock(MutableTreeNode.class);

		context.checking(new Expectations() {
			{
				allowing(editorDataModel_mock).addTreeModelListener(with(any(TreeModelListener.class)));

				allowing(editorDataModel_mock).getRoot();
				will(returnValue(node_mock));

				allowing(editorDataModel_mock).isLeaf(node_mock);
				will(returnValue(true));
			}
		});

		testable = new EditorFrame(application_mock, editorDataModel_mock);
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
}

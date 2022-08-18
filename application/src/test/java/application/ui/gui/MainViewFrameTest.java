package application.ui.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;

import javax.swing.Action;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import org.junit.jupiter.api.extension.RegisterExtension;

import application.Application;
import application.ui.UserInterfaceFactory;

@DisabledIf("isHeadless")
public class MainViewFrameTest {

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

	MainViewFrame testable;

	Application application_mock;

	UserInterfaceFactory userInterfaceFactory_mock;

	@BeforeEach
	public void setup() {
		application_mock = context.mock(Application.class);
		userInterfaceFactory_mock = context.mock(UserInterfaceFactory.class);

		testable = new MainViewFrame(application_mock, userInterfaceFactory_mock);
	}

	@Test
	public void newInstance() {
		testable = new MainViewFrame(application_mock);
	}

	@Test
	public void aboutAction_name() {
		assertEquals("About", testable.aboutAction.getValue(Action.NAME));
	}

	@Test
	public void aboutAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);
		final AboutFrame frame_mock = context.mock(AboutFrame.class);

		context.checking(new Expectations() {
			{
				oneOf(userInterfaceFactory_mock).createAboutView();
				will(returnValue(frame_mock));

				oneOf(frame_mock).setLocationRelativeTo(testable);

				oneOf(frame_mock).setVisible(true);
			}
		});

		testable.aboutAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void convertAction_name() {
		assertEquals("Convert", testable.convertAction.getValue(Action.NAME));
	}

	@Test
	public void convertAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).convert();
			}
		});

		testable.convertAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void exitAction_name() {
		assertEquals("Exit", testable.exitAction.getValue(Action.NAME));
	}

	@Test
	public void exitAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).stopApplication();
			}
		});

		testable.exitAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void newSystemTransformationAction_name() {
		assertEquals("New Transformations", testable.newSystemTransformationsAction.getValue(Action.NAME));
	}

	@Test
	public void newSystemTransformationAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).newSystemTransformations();
			}
		});

		testable.newSystemTransformationsAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void newTaskDescriptionAction_name() {
		assertEquals("New Task", testable.newTaskDescriptionAction.getValue(Action.NAME));
	}

	@Test
	public void newTaskDescriptionAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).newTaskDescription();
			}
		});

		testable.newTaskDescriptionAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void optionsAction_name() {
		assertEquals("Options...", testable.optionsAction.getValue(Action.NAME));
	}

	@Test
	public void optionsAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);
		final OptionsFrame frame_mock = context.mock(OptionsFrame.class);

		context.checking(new Expectations() {
			{
				oneOf(userInterfaceFactory_mock).createOptionsView(application_mock);
				will(returnValue(frame_mock));

				oneOf(frame_mock).updateComponents();

				oneOf(frame_mock).setVisible(true);
			}
		});

		testable.optionsAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void planAction_name() {
		assertEquals("Plan", testable.planAction.getValue(Action.NAME));
	}

	@Test
	public void planAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).plan();
			}
		});

		testable.planAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void usageAction_name() {
		assertEquals("Usage", testable.usageAction.getValue(Action.NAME));
	}

	@Test
	public void usageAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).usageHelp();
			}
		});

		testable.usageAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void verifyAction_name() {
		assertEquals("Verify", testable.verifyAction.getValue(Action.NAME));
	}

	@Test
	public void verifyAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).verify();
			}
		});

		testable.verifyAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void taskDescriptionEditorAction_name() {
		assertEquals("Task Description Editor", testable.taskDescriptionEditorAction.getValue(Action.NAME));
	}

	@Test
	public void taskDescriptionEditorAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);
		final TaskDescriptionEditorFrame frame_mock = context.mock(TaskDescriptionEditorFrame.class);

		context.checking(new Expectations() {
			{
				oneOf(userInterfaceFactory_mock).createTaskDescriptionEditorView();
				will(returnValue(frame_mock));

				oneOf(frame_mock).setVisible(true);
			}
		});

		testable.taskDescriptionEditorAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void nodeNetworkEditorAction_name() {
		assertEquals("Node Network Editor", testable.nodeNetworkEditorAction.getValue(Action.NAME));
	}

	@Test
	public void nodeNetworkEditorAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);
		final NodeNetworkEditorFrame frame_mock = context.mock(NodeNetworkEditorFrame.class);

		context.checking(new Expectations() {
			{
				oneOf(userInterfaceFactory_mock).createNodeNetworkEditorView();
				will(returnValue(frame_mock));

				oneOf(frame_mock).setVisible(true);
			}
		});

		testable.nodeNetworkEditorAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void systemTransformationsEditorAction_name() {
		assertEquals("Transformations Editor", testable.systemTransformationsEditorAction.getValue(Action.NAME));
	}

	@Test
	public void systemTransformationsEditorAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);
		final SystemTransformationsEditorFrame frame_mock = context.mock(SystemTransformationsEditorFrame.class);

		context.checking(new Expectations() {
			{
				oneOf(userInterfaceFactory_mock).createSystemTransformationsEditorView();
				will(returnValue(frame_mock));

				oneOf(frame_mock).setVisible(true);
			}
		});

		testable.systemTransformationsEditorAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void displayMessage() {
		testable.displayMessage("user message");
	}

	@Test
	public void stop() {
		testable.stop();
	}
}

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
import org.junit.jupiter.api.extension.RegisterExtension;

import application.Application;
import application.Arguments;

public class OptionsFrameTest {

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

	OptionsFrame testable;

	Application application_mock;

	@BeforeEach
	public void setup() {
		application_mock = context.mock(Application.class);

		testable = new OptionsFrame(application_mock);
	}

	@Test
	public void cancelAction_name() {
		assertEquals("Cancel", testable.cancelAction.getValue(Action.NAME));
	}

	@Test
	public void cancelAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);

		testable.cancelAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void okAction_name() {
		assertEquals("Ok", testable.okAction.getValue(Action.NAME));
	}

	@Test
	public void okAction_actionPerformed() {
		final ActionEvent actionEvent_mock = context.mock(ActionEvent.class);
		final Arguments arguments_mock = context.mock(Arguments.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).getArguments();
				will(returnValue(arguments_mock));

				oneOf(arguments_mock).setArgument_d("argument_d");

				oneOf(arguments_mock).setArgument_st("argument_st");

				oneOf(arguments_mock).setArgument_td("argument_td");

				oneOf(arguments_mock).setArgument_p("argument_p");

				oneOf(arguments_mock).setArgument_nn("argument_nn");
			}
		});

		testable.okAction.actionPerformed(actionEvent_mock);
	}

	@Test
	public void updateComponents() {
		final Arguments arguments_mock = context.mock(Arguments.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).getArguments();
				will(returnValue(arguments_mock));

				oneOf(arguments_mock).getArgument_d("unknown");
				will(returnValue("argument_d"));

				oneOf(arguments_mock).getArgument_st("systemTransformations.xml");
				will(returnValue("argument_st"));

				oneOf(arguments_mock).getArgument_td("taskDescription.xml");
				will(returnValue("argument_td"));

				oneOf(arguments_mock).getArgument_p("process.xml");
				will(returnValue("argument_p"));

				oneOf(arguments_mock).getArgument_nn("nodeNetwork.xml");
				will(returnValue("argument_nn"));
			}
		});

		testable.updateComponents();
	}
}

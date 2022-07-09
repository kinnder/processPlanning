package application.ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.GraphicsEnvironment;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import org.junit.jupiter.api.extension.RegisterExtension;

import application.Application;
import application.ui.UserInterfaceFactory.UserInterfaceType;
import application.ui.cli.MainViewShell;
import application.ui.gui.AboutFrame;
import application.ui.gui.MainViewFrame;
import application.ui.gui.NodeNetworkEditorFrame;
import application.ui.gui.OptionsFrame;
import application.ui.gui.SystemTransformationsEditorFrame;
import application.ui.gui.TaskDescriptionEditorFrame;

public class UserInterfaceFactoryTest {

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

	UserInterfaceFactory testable;

	@BeforeEach
	public void setup() {
		testable = new UserInterfaceFactory();
	}

	@Test
	@DisabledIf("isHeadless")
	public void createMainView_gui() {
		final Application application_mock = context.mock(Application.class);

		UserInterface result = testable.createMainView(application_mock, UserInterfaceType.gui);
		assertTrue(result instanceof MainViewFrame);
	}

	@Test
	public void createMainView_cli() {
		final Application application_mock = context.mock(Application.class);

		UserInterface result = testable.createMainView(application_mock, UserInterfaceType.cli);
		assertTrue(result instanceof MainViewShell);
	}

	@Test
	@DisabledIf("isHeadless")
	public void createOptionsView_gui() {
		final Application application_mock = context.mock(Application.class);

		OptionsFrame result = testable.createOptionsView(application_mock);
		assertTrue(result instanceof OptionsFrame);
	}

	@Test
	@DisabledIf("isHeadless")
	public void createAboutView_gui() {
		AboutFrame result = testable.createAboutView();
		assertTrue(result instanceof AboutFrame);
	}

	@Test
	@DisabledIf("isHeadless")
	public void createNodeNetworkEditorView_gui() {
		NodeNetworkEditorFrame result = testable.createNodeNetworkEditorView();
		assertTrue(result instanceof NodeNetworkEditorFrame);
	}

	@Test
	@DisabledIf("isHeadless")
	public void createSystemTransformationsEditorView_gui() {
		SystemTransformationsEditorFrame result = testable.createSystemTransformationsEditorView();
		assertTrue(result instanceof SystemTransformationsEditorFrame);
	}

	@Test
	@DisabledIf("isHeadless")
	public void createTaskDescriptionEditorView_gui() {
		TaskDescriptionEditorFrame result = testable.createTaskDescriptionEditorView();
		assertTrue(result instanceof TaskDescriptionEditorFrame);
	}
}

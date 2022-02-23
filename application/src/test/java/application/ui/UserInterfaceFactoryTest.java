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

import application.ui.UserInterfaceFactory.UserInterfaceType;
import application.ui.cli.MainShell;
import application.ui.gui.AboutFrame;
import application.ui.gui.MainViewFrame;
import application.ui.gui.OptionsFrame;

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
		UserInterface result = testable.createMainView(UserInterfaceType.gui);
		assertTrue(result instanceof MainViewFrame);
	}

	@Test
	public void createMainView_cli() {
		UserInterface result = testable.createMainView(UserInterfaceType.cli);
		assertTrue(result instanceof MainShell);
	}

	@Test
	@DisabledIf("isHeadless")
	public void createOptionsView_gui() {
		OptionsFrame result = testable.createOptionsView();
		assertTrue(result instanceof OptionsFrame);
	}

	@Test
	@DisabledIf("isHeadless")
	public void createAboutView_gui() {
		AboutFrame result = testable.createAboutView();
		assertTrue(result instanceof AboutFrame);
	}
}

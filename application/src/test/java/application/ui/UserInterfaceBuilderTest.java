package application.ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import application.ui.UserInterfaceBuilder.UserInterfaceType;
import application.ui.cli.MainShell;
import application.ui.gui.MainViewFrame;

public class UserInterfaceBuilderTest {

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

	UserInterfaceBuilder testable;

	@BeforeEach
	public void setup() {
		testable = new UserInterfaceBuilder();
	}

	@Test
	@Disabled
	public void build_gui() {
		// TODO (2022-02-01 #61): при выполнении теста travis-ci выдает ошибку
//		java.awt.HeadlessException:
//			No X11 DISPLAY variable was set,
//			but this program performed an operation which requires it.
		UserInterface result = testable.build(UserInterfaceType.gui);
		assertTrue(result instanceof MainViewFrame);
	}

	@Test
	public void build_cli() {
		UserInterface result = testable.build(UserInterfaceType.cli);
		assertTrue(result instanceof MainShell);
	}
}

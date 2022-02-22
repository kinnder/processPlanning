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

	static boolean isHeadless() {
		return GraphicsEnvironment.isHeadless();
	}

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
	@DisabledIf("isHeadless")
	public void build_gui() {
		UserInterface result = testable.build(UserInterfaceType.gui);
		assertTrue(result instanceof MainViewFrame);
	}

	@Test
	public void build_cli() {
		UserInterface result = testable.build(UserInterfaceType.cli);
		assertTrue(result instanceof MainShell);
	}
}

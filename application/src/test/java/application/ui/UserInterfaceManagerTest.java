package application.ui;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import application.Application;
import application.event.CommandStatusEvent;
import application.event.UserMessageEvent;
import application.ui.UserInterfaceFactory.UserInterfaceType;

public class UserInterfaceManagerTest {

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

	UserInterfaceManager testable;

	Application application_mock;

	UserInterfaceFactory userInterfaceFactory_mock;

	@BeforeEach
	public void setup() {
		application_mock = context.mock(Application.class);
		userInterfaceFactory_mock = context.mock(UserInterfaceFactory.class);

		testable = new UserInterfaceManager(application_mock, userInterfaceFactory_mock);
	}

	@Test
	public void newInstance() {
		testable = new UserInterfaceManager(application_mock);
	}

	@Test
	public void registerUserInterface() {
		final UserInterface ui_mock = context.mock(UserInterface.class);

		testable.registerUserInterface(ui_mock);
	}

	@Test
	public void notifyUserMessage() {
		final UserInterface ui_mock = context.mock(UserInterface.class);
		final UserMessageEvent event_mock = context.mock(UserMessageEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(ui_mock).notifyUserMessage(event_mock);
			}
		});
		testable.registerUserInterface(ui_mock);

		testable.notifyUserMessage(event_mock);
	}

	@Test
	public void notifyCommandStatus() {
		final UserInterface ui_mock = context.mock(UserInterface.class);
		final CommandStatusEvent event_mock = context.mock(CommandStatusEvent.class);

		context.checking(new Expectations() {
			{
				oneOf(ui_mock).notifyCommandStatus(event_mock);
			}
		});
		testable.registerUserInterface(ui_mock);

		testable.notifyCommandStatus(event_mock);
	}

	@Test
	public void runUserInterfaces() throws Exception {
		final UserInterface ui_mock = context.mock(UserInterface.class);

		context.checking(new Expectations() {
			{
				oneOf(ui_mock).run();
			}
		});
		testable.registerUserInterface(ui_mock);

		testable.runUserInterfaces();
	}

	@Test
	public void createUserInterface() {
		final UserInterface ui_mock = context.mock(UserInterface.class);

		context.checking(new Expectations() {
			{
				oneOf(userInterfaceFactory_mock).createMainView(application_mock, UserInterfaceType.cli);
				will(returnValue(ui_mock));
			}
		});

		testable.createUserInterface(UserInterfaceType.cli);
	}

	@Test
	public void stop() throws Exception {
		final UserInterface ui_mock = context.mock(UserInterface.class);

		context.checking(new Expectations() {
			{
				oneOf(ui_mock).stop();
			}
		});
		testable.registerUserInterface(ui_mock);

		testable.stop();
	}
}

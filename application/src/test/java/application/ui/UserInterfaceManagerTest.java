package application.ui;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import application.Application;
import application.event.CommandEvent;
import application.event.CommandEvent.Type;
import application.event.UserEvent;
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
	public void notifyEvent_CommandEvent() {
		final UserInterface ui_mock = context.mock(UserInterface.class);
		final CommandEvent event_mock = context.mock(CommandEvent.class);
		event_mock.message = "event-message";
		event_mock.type = Type.Status;

		context.checking(new Expectations() {
			{
				oneOf(ui_mock).displayMessage("event-message");
			}
		});
		testable.registerUserInterface(ui_mock);

		testable.notifyEvent(event_mock);
	}

	@Test
	public void notifyEvent_UserEvent() {
		final UserInterface ui_mock = context.mock(UserInterface.class);
		final UserEvent event_mock = context.mock(UserEvent.class);
		event_mock.message = "event-message";

		context.checking(new Expectations() {
			{
				oneOf(ui_mock).displayMessage("event-message");
			}
		});
		testable.registerUserInterface(ui_mock);

		testable.notifyEvent(event_mock);
	}

	@Test
	public void notifyUserEvent() {
		final UserInterface ui_mock = context.mock(UserInterface.class);
		final UserEvent event_mock = context.mock(UserEvent.class);
		event_mock.message = "event-message";

		context.checking(new Expectations() {
			{
				oneOf(ui_mock).displayMessage("event-message");
			}
		});
		testable.registerUserInterface(ui_mock);

		testable.notifyUserEvent(event_mock);
	}

	@Test
	public void notifyCommandEvent_Cancel() {
		final UserInterface ui_mock = context.mock(UserInterface.class);
		final CommandEvent event_mock = context.mock(CommandEvent.class);
		event_mock.message = "event-message";
		event_mock.type = Type.Cancel;

		context.checking(new Expectations() {
			{
			}
		});
		testable.registerUserInterface(ui_mock);

		testable.notifyCommandEvent(event_mock);
	}

	@Test
	public void notifyCommandEvent_Cancelled() {
		final UserInterface ui_mock = context.mock(UserInterface.class);
		final CommandEvent event_mock = context.mock(CommandEvent.class);
		event_mock.message = "event-message";
		event_mock.type = Type.Cancelled;

		context.checking(new Expectations() {
			{
				oneOf(ui_mock).displayMessage("event-message");
			}
		});
		testable.registerUserInterface(ui_mock);

		testable.notifyCommandEvent(event_mock);
	}

	@Test
	public void notifyCommandEvent_Errored() {
		final UserInterface ui_mock = context.mock(UserInterface.class);
		final CommandEvent event_mock = context.mock(CommandEvent.class);
		event_mock.message = "event-message";
		event_mock.type = Type.Errored;

		context.checking(new Expectations() {
			{
				oneOf(ui_mock).displayMessage("event-message");
			}
		});
		testable.registerUserInterface(ui_mock);

		testable.notifyCommandEvent(event_mock);
	}

	@Test
	public void notifyCommandEvent_Finished() {
		final UserInterface ui_mock = context.mock(UserInterface.class);
		final CommandEvent event_mock = context.mock(CommandEvent.class);
		event_mock.message = "event-message";
		event_mock.type = Type.Finished;

		context.checking(new Expectations() {
			{
				oneOf(ui_mock).displayMessage("event-message");
			}
		});
		testable.registerUserInterface(ui_mock);

		testable.notifyCommandEvent(event_mock);
	}

	@Test
	public void notifyCommandEvent_Start() {
		final UserInterface ui_mock = context.mock(UserInterface.class);
		final CommandEvent event_mock = context.mock(CommandEvent.class);
		event_mock.message = "event-message";
		event_mock.type = Type.Start;

		context.checking(new Expectations() {
			{
			}
		});
		testable.registerUserInterface(ui_mock);

		testable.notifyCommandEvent(event_mock);
	}

	@Test
	public void notifyCommandEvent_Started() {
		final UserInterface ui_mock = context.mock(UserInterface.class);
		final CommandEvent event_mock = context.mock(CommandEvent.class);
		event_mock.message = "event-message";
		event_mock.type = Type.Started;

		context.checking(new Expectations() {
			{
				oneOf(ui_mock).displayMessage("event-message");
			}
		});
		testable.registerUserInterface(ui_mock);

		testable.notifyCommandEvent(event_mock);
	}

	@Test
	public void notifyCommandEvent_Status() {
		final UserInterface ui_mock = context.mock(UserInterface.class);
		final CommandEvent event_mock = context.mock(CommandEvent.class);
		event_mock.message = "event-message";
		event_mock.type = Type.Status;

		context.checking(new Expectations() {
			{
				oneOf(ui_mock).displayMessage("event-message");
			}
		});
		testable.registerUserInterface(ui_mock);

		testable.notifyCommandEvent(event_mock);
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

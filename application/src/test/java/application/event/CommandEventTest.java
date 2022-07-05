package application.event;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import application.command.CommandData;
import application.event.CommandEvent.Type;

public class CommandEventTest {

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

	CommandEvent testable;

	@BeforeEach
	public void setup() {
	}

	@Test
	public void newInstance() {
		testable = new CommandEvent("message");

		assertEquals("message", testable.message);
	}

	@Test
	public void cancelled() {
		testable = CommandEvent.cancelled("test-command");

		assertEquals(Type.Cancelled, testable.type);
		assertEquals("test-command", testable.commandName);
		assertEquals("cancelled", testable.message);
	}

	@Test
	public void errored() {
		testable = CommandEvent.errored("test-command");

		assertEquals(Type.Errored, testable.type);
		assertEquals("test-command", testable.commandName);
		assertEquals("error", testable.message);
	}

	@Test
	public void finished() {
		testable = CommandEvent.finished("test-command");

		assertEquals(Type.Finished, testable.type);
		assertEquals("test-command", testable.commandName);
		assertEquals("done", testable.message);
	}

	@Test
	public void started() {
		testable = CommandEvent.started("test-command");

		assertEquals(Type.Started, testable.type);
		assertEquals("test-command", testable.commandName);
		assertEquals("executing command: \"test-command\"...", testable.message);
	}

	@Test
	public void status() {
		testable = CommandEvent.status("test-command", "test-message");

		assertEquals(Type.Status, testable.type);
		assertEquals("test-command", testable.commandName);
		assertEquals("test-message", testable.message);
	}

	@Test
	public void start() {
		final CommandData commandData_mock = context.mock(CommandData.class);

		testable = CommandEvent.start("test-command", commandData_mock);

		assertEquals(Type.Start, testable.type);
		assertEquals("test-command", testable.commandName);
		assertEquals("start", testable.message);
		assertEquals(commandData_mock, testable.commandData);
	}
}

package application.event;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import application.event.CommandStatusEvent.Type;

public class CommandStatusEventTest {

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

	CommandStatusEvent testable;

	@BeforeEach
	public void setup() {
	}

	@Test
	public void newInstance() {
		testable = new CommandStatusEvent("message");

		assertEquals("message", testable.message);
	}

	@Test
	public void newInstance_2() {
		testable = new CommandStatusEvent(Type.Cancelled, "command-name", "command-message");

		assertEquals(Type.Cancelled, testable.type);
		assertEquals("command-name", testable.commandName);
		assertEquals("command-message", testable.message);
	}

	@Test
	public void cancelled() {
		testable = CommandStatusEvent.cancelled("test-command");

		assertEquals(Type.Cancelled, testable.type);
		assertEquals("test-command", testable.commandName);
		assertEquals("cancelled", testable.message);
	}

	@Test
	public void errored() {
		testable = CommandStatusEvent.errored("test-command");

		assertEquals(Type.Errored, testable.type);
		assertEquals("test-command", testable.commandName);
		assertEquals("error", testable.message);
	}

	@Test
	public void finished() {
		testable = CommandStatusEvent.finished("test-command");

		assertEquals(Type.Finished, testable.type);
		assertEquals("test-command", testable.commandName);
		assertEquals("done", testable.message);
	}

	@Test
	public void started() {
		testable = CommandStatusEvent.started("test-command");

		assertEquals(Type.Started, testable.type);
		assertEquals("test-command", testable.commandName);
		assertEquals("executing command: \"test-command\"...", testable.message);
	}

	@Test
	public void status() {
		testable = CommandStatusEvent.status("test-command");

		assertEquals(Type.Status, testable.type);
		assertEquals("test-command", testable.commandName);
		assertEquals("status", testable.message);
	}
}

package application.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.Future;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import application.Application;
import application.event.CommandStatusEvent.Type;
import application.event.CommandStatusEventMatcher;

public class CommandTest {

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

	Command testable;

	Application application_mock;

	@BeforeEach
	public void setup() {
		application_mock = context.mock(Application.class);

		testable = new Command(application_mock) {
			@Override
			public void execute(CommandData data) throws Exception {
			}
		};
	}

	@Test
	public void execute() throws Exception {
		final CommandData data_mock = context.mock(CommandData.class);

		testable.execute(data_mock);
	}

	@Test
	public void getName() {
		assertEquals("unknown", testable.getName());
	}

	@Test
	public void prepare() {
		final CommandData data_mock = context.mock(CommandData.class);

		testable.prepare(data_mock);
	}

	@Test
	public void run() {
		final CommandData data_mock = context.mock(CommandData.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).notifyEvent(
						with(new CommandStatusEventMatcher().expectType(Type.Started).expectCommandName("unknown")));

				// execute

				oneOf(application_mock).notifyEvent(
						with(new CommandStatusEventMatcher().expectType(Type.Finished).expectCommandName("unknown")));
			}
		});
		testable.prepare(data_mock);

		testable.run();
	}

	@Test
	public void run_throwException() {
		testable = new Command(application_mock) {
			@Override
			public void execute(CommandData data) throws Exception {
				throw new Exception("runtime exception");
			}
		};

		final CommandData data_mock = context.mock(CommandData.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).notifyEvent(
						with(new CommandStatusEventMatcher().expectType(Type.Started).expectCommandName("unknown")));

				// execute

				oneOf(application_mock).notifyEvent(
						with(new CommandStatusEventMatcher().expectType(Type.Errored).expectCommandName("unknown")));
			}
		});
		testable.prepare(data_mock);

		testable.run();
	}

	@Test
	public void started() {
		context.checking(new Expectations() {
			{
				oneOf(application_mock).notifyEvent(
						with(new CommandStatusEventMatcher().expectType(Type.Started).expectCommandName("unknown")));
			}
		});

		testable.started();
	}

	@Test
	public void finished() {
		context.checking(new Expectations() {
			{
				oneOf(application_mock).notifyEvent(
						with(new CommandStatusEventMatcher().expectType(Type.Finished).expectCommandName("unknown")));
			}
		});

		testable.finished();
	}

	@Test
	public void cancelled() {
		context.checking(new Expectations() {
			{
				oneOf(application_mock).notifyEvent(
						with(new CommandStatusEventMatcher().expectType(Type.Cancelled).expectCommandName("unknown")));
			}
		});

		testable.cancelled();
	}

	@Test
	public void errored() {
		context.checking(new Expectations() {
			{
				oneOf(application_mock).notifyEvent(
						with(new CommandStatusEventMatcher().expectType(Type.Errored).expectCommandName("unknown")));
			}
		});

		testable.errored();
	}

	@Test
	public void status() {
		context.checking(new Expectations() {
			{
				oneOf(application_mock).notifyEvent(
						with(new CommandStatusEventMatcher().expectType(Type.Status).expectCommandName("unknown")));
			}
		});

		testable.status();
	}

	@Test
	public void cancel() {
		final Future<?> future_mock = context.mock(Future.class);

		context.checking(new Expectations() {
			{
				oneOf(future_mock).isDone();
				will(returnValue(false));

				oneOf(application_mock).notifyEvent(
						with(new CommandStatusEventMatcher().expectType(Type.Cancelled).expectCommandName("unknown")));

				oneOf(future_mock).cancel(true);
			}
		});
		testable.thisFuture = future_mock;

		testable.cancel();
	}

	@Test
	public void cancel_isDone() {
		final Future<?> future_mock = context.mock(Future.class);

		context.checking(new Expectations() {
			{
				oneOf(future_mock).isDone();
				will(returnValue(true));
			}
		});
		testable.thisFuture = future_mock;

		testable.cancel();
	}
}

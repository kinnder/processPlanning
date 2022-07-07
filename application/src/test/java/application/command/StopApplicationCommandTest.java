package application.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import application.Application;
import application.event.CommandEvent;
import application.event.CommandEventMatcher;

public class StopApplicationCommandTest {

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

	StopApplicationCommand testable;

	Application application_mock;

	@BeforeEach
	public void setup() {
		application_mock = context.mock(Application.class);

		testable = new StopApplicationCommand(application_mock);
	}

	@Test
	public void getName() {
		assertEquals("stopApplication", testable.getName());
	}

	@Test
	public void execute() throws Exception {
		final StopApplicationCommandData data_mock = context.mock(StopApplicationCommandData.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).stop();
			}
		});

		testable.execute(data_mock);
	}

	@Test
	public void run() {
		final StopApplicationCommandData data_mock = context.mock(StopApplicationCommandData.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).stop();
			}
		});
		testable.prepare(data_mock);

		testable.run();
	}

	@Test
	public void run_throwExcepton() {
		final StopApplicationCommandData data_mock = context.mock(StopApplicationCommandData.class);

		context.checking(new Expectations() {
			{
				oneOf(application_mock).stop();
				will(throwException(new Exception("error")));

				oneOf(application_mock).pushEvent(
						with(new CommandEventMatcher().expectType(CommandEvent.Type.Errored).expectMessage("error")));
			}
		});
		testable.prepare(data_mock);

		testable.run();
	}
}

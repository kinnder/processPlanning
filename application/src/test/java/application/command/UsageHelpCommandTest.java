package application.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import application.Application;
import application.event.CommandEventMatcher;
import application.event.CommandEvent;
import application.event.UserEvent.Type;
import application.event.UserEventMatcher;

public class UsageHelpCommandTest {

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

	UsageHelpCommand testable;

	Application application_mock;

	@BeforeEach
	public void setup() {
		application_mock = context.mock(Application.class);

		testable = new UsageHelpCommand(application_mock);
	}

	@Test
	public void execute() throws Exception {
		final Option option_mock = new Option("short", "long", false, "description");
		final Options options = new Options();
		options.addOption(option_mock);
		final UsageHelpCommandData data = context.mock(UsageHelpCommandData.class);
		data.options = options;

		final String message = new StringBuilder().append(
				"application builds plan for [taskDescription] with [systemTransformations] and puts result in [process]\n")
				.append("usage:\n").append(String.format("%7s, %-26s %s\n", "short", "long", "description")).toString();

		context.checking(new Expectations() {
			{
				oneOf(application_mock).pushEvent(with(new UserEventMatcher().expectType(Type.Info).expectMessage(message)));
			}
		});

		testable.execute((CommandData) data);
	}

	@Test
	public void execute_option_without_long() throws Exception {
		final Option option_mock = new Option("short", "description");
		final Options options = new Options();
		options.addOption(option_mock);
		final UsageHelpCommandData data = context.mock(UsageHelpCommandData.class);
		data.options = options;

		final String message = new StringBuilder().append(
				"application builds plan for [taskDescription] with [systemTransformations] and puts result in [process]\n")
				.append("usage:\n").append(String.format("%7s, %-26s %s\n", "short", "", "description")).toString();

		context.checking(new Expectations() {
			{
				oneOf(application_mock).pushEvent(with(new UserEventMatcher().expectType(Type.Info).expectMessage(message)));
			}
		});

		testable.execute((CommandData) data);
	}

	@Test
	public void getName() {
		assertEquals("usageHelp", testable.getName());
	}

	@Test
	public void run() {
		final Option option_mock = new Option("short", "long", false, "description");
		final Options options = new Options();
		options.addOption(option_mock);
		final UsageHelpCommandData data = context.mock(UsageHelpCommandData.class);
		data.options = options;

		final String message = new StringBuilder().append(
				"application builds plan for [taskDescription] with [systemTransformations] and puts result in [process]\n")
				.append("usage:\n").append(String.format("%7s, %-26s %s\n", "short", "long", "description")).toString();

		context.checking(new Expectations() {
			{
				oneOf(application_mock).pushEvent(with(new UserEventMatcher().expectType(Type.Info).expectMessage(message)));
			}
		});
		testable.prepare(data);

		testable.run();
	}

	@Test
	public void run_throwException() {
		final Option option_mock = new Option("short", "long", false, "description");
		final Options options = new Options();
		options.addOption(option_mock);
		final UsageHelpCommandData data = context.mock(UsageHelpCommandData.class);
		data.options = options;

		final String message = new StringBuilder().append(
				"application builds plan for [taskDescription] with [systemTransformations] and puts result in [process]\n")
				.append("usage:\n").append(String.format("%7s, %-26s %s\n", "short", "long", "description")).toString();

		context.checking(new Expectations() {
			{
				oneOf(application_mock).pushEvent(with(new UserEventMatcher().expectType(Type.Info).expectMessage(message)));
				will(throwException(new Exception("error")));

				oneOf(application_mock).pushEvent(with(new CommandEventMatcher().expectType(CommandEvent.Type.Errored).expectMessage("error")));
			}
		});
		testable.prepare(data);

		testable.run();
	}
}

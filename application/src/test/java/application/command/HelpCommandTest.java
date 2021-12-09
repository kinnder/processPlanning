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
import application.event.HelpMessageEventMatcher;

public class HelpCommandTest {

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

	HelpCommand testable;

	Application application_mock;

	@BeforeEach
	public void setup() {
		application_mock = context.mock(Application.class);

		testable = new HelpCommand(application_mock);
	}

	@Test
	public void execute() throws Exception {
		final Option option_mock = new Option("short", "long", false, "description");
		final Options options = new Options();
		options.addOption(option_mock);
		final HelpCommandData data = context.mock(HelpCommandData.class);
		data.options = options;

		final StringBuilder sb = new StringBuilder();
		sb.append("application builds plan for [taskDescription] with [systemTransformations] and puts result in [process]\n");
		sb.append("usage:\n");
		sb.append(String.format("%7s, %-26s %s\n", "short", "long", "description"));

		context.checking(new Expectations() {
			{
				oneOf(application_mock).notifyHelpMessage(with(new HelpMessageEventMatcher().expectMessage(sb.toString())));
			}
		});

		testable.execute((CommandData) data);
	}

	@Test
	public void execute_option_without_long() throws Exception {
		final Option option_mock = new Option("short", "description");
		final Options options = new Options();
		options.addOption(option_mock);
		final HelpCommandData data = context.mock(HelpCommandData.class);
		data.options = options;

		final StringBuilder sb = new StringBuilder();
		sb.append("application builds plan for [taskDescription] with [systemTransformations] and puts result in [process]\n");
		sb.append("usage:\n");
		sb.append(String.format("%7s, %-26s %s\n", "short", "", "description"));

		context.checking(new Expectations() {
			{
				oneOf(application_mock).notifyHelpMessage(with(new HelpMessageEventMatcher().expectMessage(sb.toString())));
			}
		});

		testable.execute((CommandData) data);
	}

	@Test
	public void getName() {
		assertEquals("help", testable.getName());
	}
}

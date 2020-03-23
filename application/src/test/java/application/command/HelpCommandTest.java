package application.command;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import application.UserInterface;
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

	@BeforeEach
	public void setup() {
		testable = new HelpCommand();
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
		sb.append("usage:");
		sb.append(String.format("%2s, %-21s %s\n", "short", "long", "description"));

		final UserInterface ui_mock = context.mock(UserInterface.class);

		context.checking(new Expectations() {
			{
				oneOf(ui_mock).notifyHelpMessage(with(new HelpMessageEventMatcher().expectMessage(sb.toString())));
			}
		});
		testable.registerUserInterface(ui_mock);

		testable.execute((CommandData) data);
	}
}

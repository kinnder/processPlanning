package application.command;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import testtools.Matcher;

public class UsageHelpCommandDataMatcher extends Matcher<UsageHelpCommandData> {

	public UsageHelpCommandDataMatcher expectOptions(Options options) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(UsageHelpCommandData arg) {
				compare("options.getOptions().size()", options.getOptions().size(), arg.options.getOptions().size());
				for (Option expected : options.getOptions()) {
					Option actual = arg.options.getOption(expected.getOpt());
					compare(expected.getOpt(), true, expected.equals(actual));
				}
			}
		});
		return this;
	}
}

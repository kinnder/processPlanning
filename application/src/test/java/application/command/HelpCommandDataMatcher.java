package application.command;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import utility.Matcher;

public class HelpCommandDataMatcher extends Matcher<HelpCommandData> {

	public HelpCommandDataMatcher expectOptions(Options options) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(HelpCommandData arg) throws MatcherException {
				for (Option expected : options.getOptions()) {
					Option actual = arg.options.getOption(expected.getOpt());
					compare(expected.getOpt(), true, expected.equals(actual));
				}
			}
		});
		return this;
	}
}

package application.event;

import utility.Matcher;

public class UsageHelpMessageEventMatcher extends Matcher<UsageHelpMessageEvent> {

	public UsageHelpMessageEventMatcher expectMessage(String message) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(UsageHelpMessageEvent arg) throws MatcherException {
				compare("message", message, arg.message);
			}
		});
		return this;
	}
}

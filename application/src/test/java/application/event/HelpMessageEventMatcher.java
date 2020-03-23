package application.event;

import utility.Matcher;

public class HelpMessageEventMatcher extends Matcher<HelpMessageEvent> {

	public HelpMessageEventMatcher expectMessage(String message) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(HelpMessageEvent arg) throws MatcherException {
				compare("message", message, arg.message);
			}
		});
		return this;
	}
}

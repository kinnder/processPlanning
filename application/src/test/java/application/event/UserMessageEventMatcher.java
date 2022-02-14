package application.event;

import utility.Matcher;

public class UserMessageEventMatcher extends Matcher<UserMessageEvent> {

	public UserMessageEventMatcher expectMessage(String message) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(UserMessageEvent arg) throws MatcherException {
				compare("message", message, arg.message);
			}
		});
		return this;
	}
}

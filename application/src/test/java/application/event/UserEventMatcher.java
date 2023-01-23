package application.event;

import testtools.Matcher;

public class UserEventMatcher extends Matcher<UserEvent> {

	public UserEventMatcher expectMessage(String message) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(UserEvent arg) throws MatcherException {
				compare("message", message, arg.message);
			}
		});
		return this;
	}

	public UserEventMatcher expectType(UserEvent.Type type) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(UserEvent arg) throws MatcherException {
				compare("type", type, arg.type);
			}
		});
		return this;
	}
}

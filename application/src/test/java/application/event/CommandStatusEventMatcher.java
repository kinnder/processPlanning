package application.event;

import utility.Matcher;

public class CommandStatusEventMatcher extends Matcher<CommandStatusEvent> {

	public CommandStatusEventMatcher expectMessage(String message) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(CommandStatusEvent arg) throws MatcherException {
				compare("message", message, arg.message);
			}
		});
		return this;
	}
}

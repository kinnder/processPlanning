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

	public CommandStatusEventMatcher expectCommandName(String commandName) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(CommandStatusEvent arg) throws MatcherException {
				compare("commandName", commandName, arg.commandName);
			}
		});
		return this;
	}

	public CommandStatusEventMatcher expectType(CommandStatusEvent.Type type) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(CommandStatusEvent arg) throws MatcherException {
				compare("type", type, arg.type);
			}
		});
		return this;
	}
}

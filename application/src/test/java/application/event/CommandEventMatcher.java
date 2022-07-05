package application.event;

import utility.Matcher;

public class CommandEventMatcher extends Matcher<CommandEvent> {

	public CommandEventMatcher expectMessage(String message) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(CommandEvent arg) throws MatcherException {
				compare("message", message, arg.message);
			}
		});
		return this;
	}

	public CommandEventMatcher expectCommandName(String commandName) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(CommandEvent arg) throws MatcherException {
				compare("commandName", commandName, arg.commandName);
			}
		});
		return this;
	}

	public CommandEventMatcher expectType(CommandEvent.Type type) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(CommandEvent arg) throws MatcherException {
				compare("type", type, arg.type);
			}
		});
		return this;
	}

	// TODO (2022-07-05 #69): добавить сравнение по commandData
}

package application.event;

import application.command.CommandData;
import testtools.Matcher;

public class CommandEventMatcher extends Matcher<CommandEvent> {

	public CommandEventMatcher expectMessage(String message) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(CommandEvent arg) {
				compare("message", message, arg.message);
			}
		});
		return this;
	}

	public CommandEventMatcher expectCommandName(String commandName) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(CommandEvent arg) {
				compare("commandName", commandName, arg.commandName);
			}
		});
		return this;
	}

	public CommandEventMatcher expectType(CommandEvent.Type type) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(CommandEvent arg) {
				compare("type", type, arg.type);
			}
		});
		return this;
	}

	public CommandEventMatcher expectCommandData(CommandData data) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(CommandEvent arg) {
				compare("commandData", data, arg.commandData);
			}
		});
		return this;
	}
}

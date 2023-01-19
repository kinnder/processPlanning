package application.event;

import application.command.CommandData;

public class CommandEvent extends Event {

	public enum Type {
		Cancel, Cancelled, Errored, Finished, Start, Started, Status
	};

	public static CommandEvent cancelled(String commandName) {
		return new CommandEvent(Type.Cancelled, commandName, "cancelled");
	}

	public static CommandEvent errored(String commandName) {
		return new CommandEvent(Type.Errored, commandName, "error");
	}

	public static CommandEvent finished(String commandName) {
		return new CommandEvent(Type.Finished, commandName, "done");
	}

	public static CommandEvent started(String commandName) {
		final String message = String.format("executing command: \"%s\"...", commandName);
		return new CommandEvent(Type.Started, commandName, message);
	}

	public static CommandEvent status(String commandName, String message) {
		return new CommandEvent(Type.Status, commandName, message);
	}

	public static CommandEvent start(String commandName, CommandData commandData) {
		return new CommandEvent(Type.Start, commandName, commandData, "start");
	}

	public String commandName;

	public CommandData commandData;

	public Type type;

	public CommandEvent(String message) {
		super(message);
	}

	public CommandEvent(Type type, String commandName, String message) {
		super(message);
		this.type = type;
		this.commandName = commandName;
	}

	public CommandEvent(Type type, String commandName, CommandData commandData, String message) {
		super(message);
		this.type = type;
		this.commandName = commandName;
		this.commandData = commandData;
	}
}

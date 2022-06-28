package application.event;

public class CommandStatusEvent extends Event {

	public enum Type {
		Cancelled, Errored, Finished, Started, Status
	};

	Type type;

	String commandName;

	public static CommandStatusEvent cancelled(String commandName) {
		return new CommandStatusEvent(Type.Cancelled, commandName, "cancelled");
	}

	public static CommandStatusEvent errored(String commandName) {
		return new CommandStatusEvent(Type.Errored, commandName, "error");
	}

	public static CommandStatusEvent finished(String commandName) {
		return new CommandStatusEvent(Type.Finished, commandName, "done");
	}

	public static CommandStatusEvent started(String commandName) {
		String message = String.format("executing command: \"%s\"...", commandName);
		return new CommandStatusEvent(Type.Started, commandName, message);
	}

	public static CommandStatusEvent status(String commandName) {
		return new CommandStatusEvent(Type.Status, commandName, "status");
	}

	public CommandStatusEvent(String message) {
		super(message);
	}

	public CommandStatusEvent(Type type, String commandName, String message) {
		super(message);
		this.type = type;
		this.commandName = commandName;
	}
}

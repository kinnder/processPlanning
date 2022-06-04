package application.command;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {

	private Map<String, Command> commands = new HashMap<>();

	public void registerCommand(Command command) {
		commands.put(command.getName(), command);
	}

	public void runCommand(String commandName, CommandData commandData) {
		Command command = commands.get(commandName);
		command.run(commandData);
	}
}

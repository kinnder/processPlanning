package application.command;

import java.util.HashMap;
import java.util.Map;

import application.Application;

public class CommandManager {

	private Map<String, Command> commands = new HashMap<>();

	public CommandManager(Application application) {
		registerCommand(new UsageHelpCommand(application));
		registerCommand(new PlanCommand(application));
		registerCommand(new NewSystemTransformationsCommand(application));
		registerCommand(new NewTaskDescriptionCommand(application));
		registerCommand(new VerifyCommand(application));
		registerCommand(new ConvertCommand(application));
	}

	public void registerCommand(Command command) {
		commands.put(command.getName(), command);
	}

	public void runCommand(String commandName, CommandData commandData) {
		Command command = commands.get(commandName);
		command.run(commandData);
	}
}

package application.command;

import java.util.HashMap;
import java.util.Map;

import application.Application;
import application.event.CommandEvent;
import application.event.Event;
import application.event.UserEvent;

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
		command.prepare(commandData);
		command.run();
	}

	public void notifyEvent(Event event) {
		if (event instanceof CommandEvent) {
			notifyCommandEvent((CommandEvent) event);
		}
		if (event instanceof UserEvent) {
			notifyUserEvent((UserEvent) event);
		}
	}

	public void notifyUserEvent(UserEvent event) {
	}

	public void notifyCommandEvent(CommandEvent event) {
		switch (event.type) {
		case Cancel:
			break;
		case Cancelled:
			break;
		case Errored:
			break;
		case Finished:
			break;
		case Start:
			runCommand(event.commandName, event.commandData);
			break;
		case Started:
			break;
		case Status:
			break;
		default:
			break;
		}
	}

	public void stop() {
	}
}

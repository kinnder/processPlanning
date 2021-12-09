package application.command;

import application.Application;
import application.event.CommandStatusEvent;

public abstract class Command {

	private String name;

	protected Application application;

	public Command(Application application) {
		this(application, "unknown");
	}

	public Command(Application application, String name) {
		this.application = application;
		this.name = name;
	}

	public void run(CommandData data) {
		try {
			application.notifyCommandStatus(new CommandStatusEvent(String.format("executing command: \"%s\"...", name)));
			execute(data);
			application.notifyCommandStatus(new CommandStatusEvent("done"));
		} catch (Exception e) {
			application.notifyCommandStatus(new CommandStatusEvent(e.toString()));
			application.notifyCommandStatus(new CommandStatusEvent("error"));
		}
	}

	public abstract void execute(CommandData data) throws Exception;

	public String getName() {
		return name;
	}
}

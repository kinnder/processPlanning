package application.command;

import application.Application;

public abstract class Command {

	protected Application application;

	public Command(Application application) {
		this.application = application;
	}

	public abstract void execute(CommandData data) throws Exception;
}

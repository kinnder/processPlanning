package application.command;

import org.slf4j.LoggerFactory;

import application.Application;
import application.event.CommandStatusEvent;

public abstract class Command implements Runnable {

	private String name;

	protected Application application;

	public Command(Application application) {
		this(application, "unknown");
	}

	public Command(Application application, String name) {
		this.application = application;
		this.name = name;
	}

	protected CommandData data;

	public void prepare(CommandData data) {
		this.data = data;
	}

	@Override
	public void run() {
		try {
			application.notifyCommandStatus(new CommandStatusEvent(String.format("executing command: \"%s\"...", name)));
			execute(data);
			application.notifyCommandStatus(new CommandStatusEvent("done"));
		} catch (Exception e) {
			application.notifyCommandStatus(new CommandStatusEvent("error"));
			LoggerFactory.getLogger(getClass()).error("", e);
		}
	}

	public abstract void execute(CommandData data) throws Exception;

	public String getName() {
		return name;
	}
}

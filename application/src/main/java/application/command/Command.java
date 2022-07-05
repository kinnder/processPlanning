package application.command;

import java.util.concurrent.Future;

import org.slf4j.LoggerFactory;

import application.Application;
import application.event.CommandEvent;

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
			started();
			execute(data);
			finished();
		} catch (Exception e) {
			errored();
			LoggerFactory.getLogger(getClass()).error("", e);
		}
	}

	public abstract void execute(CommandData data) throws Exception;

	public Future<?> thisFuture;

	public void cancel() {
		if (!thisFuture.isDone()) {
			cancelled();
			thisFuture.cancel(true);
		}
	}

	public void started() {
		application.notifyEvent(CommandEvent.started(name));
	}

	public void finished() {
		application.notifyEvent(CommandEvent.finished(name));
	}

	public void cancelled() {
		application.notifyEvent(CommandEvent.cancelled(name));
	}

	public void errored() {
		application.notifyEvent(CommandEvent.errored(name));
	}

	public void status(String message) {
		application.notifyEvent(CommandEvent.status(name, message));
	}

	public String getName() {
		return name;
	}
}

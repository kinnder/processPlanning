package application.event;

import java.util.ArrayList;
import java.util.List;

public abstract class EventQueue implements Runnable {

	private boolean isRunning = true;

	public synchronized boolean isRunning() {
		return isRunning;
	}

	@Override
	public void run() {
		while (isRunning) {
			Event event = pullEvent();
			if (event instanceof CommandEvent) {
				processEvent((CommandEvent) event);
			} else if (event instanceof UserEvent) {
				processEvent((UserEvent) event);
			} else {
				processEvent(event);
			}
		}
	}

	private void processEvent(Event event) {
		switch (event.type) {
		case StopApplication:
			isRunning = false;
			break;
		default:
			break;
		}
	}

	private List<Event> queue = new ArrayList<Event>();

	public synchronized void pushEvent(Event event) {
		queue.add(event);
		notifyAll();
	}

	private synchronized Event pullEvent() {
		while (queue.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		return queue.remove(0);
	}

	abstract protected void processEvent(CommandEvent event);

	abstract protected void processEvent(UserEvent event);

	public synchronized void stop() {
		pushEvent(Event.stopApplication());
	}
}

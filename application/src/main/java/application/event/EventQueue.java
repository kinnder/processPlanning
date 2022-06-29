package application.event;

import java.util.ArrayList;
import java.util.List;

public class EventQueue implements Runnable {

	private boolean isRunning = true;

	public synchronized boolean isRunning() {
		return isRunning;
	}

	@Override
	public void run() {
		while (isRunning) {
			Event event = pullEvent();
			if (event instanceof CommandStatusEvent) {
				processEvent((CommandStatusEvent) event);
			} else if (event instanceof UserMessageEvent) {
				processEvent((UserMessageEvent) event);
			} else {
				processEvent(event);
			}
		}
	}

	protected void processEvent(Event event) {
		switch (event.type) {
		case StopApplication:
			isRunning = false;
			break;
		default:
			break;
		}
	}

	protected void processEvent(CommandStatusEvent event) {
	}

	protected void processEvent(UserMessageEvent event) {
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

	public synchronized void stop() {
		pushEvent(Event.stopApplication());
	}
}

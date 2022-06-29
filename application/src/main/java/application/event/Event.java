package application.event;

public class Event {

	public enum Type {
		StopApplication, Unknown;
	};

	public Type type;

	public String message;

	public Event(String message) {
		this.message = message;
		this.type = Type.Unknown;
	}

	public Event(Type type) {
		this.type = type;
	}

	public static Event stopApplication() {
		return new Event(Type.StopApplication);
	}
}

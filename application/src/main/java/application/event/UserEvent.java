package application.event;

public class UserEvent extends Event {

	public enum Type {
		Info, Error
	};

	public static UserEvent info(String message) {
		return new UserEvent(Type.Info, message);
	}

	public static UserEvent error(String message) {
		return new UserEvent(Type.Error, message);
	}

	public Type type;

	public UserEvent(String message) {
		super(message);
	}

	public UserEvent(Type type, String message) {
		super(message);
		this.type = type;
	}
}

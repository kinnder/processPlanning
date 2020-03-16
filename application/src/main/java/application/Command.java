package application;

public interface Command<Type extends CommandData> {

	public void execute(Type data);
}

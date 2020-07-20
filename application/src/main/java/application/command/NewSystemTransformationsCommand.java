package application.command;

public class NewSystemTransformationsCommand extends Command {

	@Override
	public void execute(CommandData data) throws Exception {
		execute((NewSystemTransformationsData) data);
	}

	private void execute(NewSystemTransformationsData data) {
	}
}

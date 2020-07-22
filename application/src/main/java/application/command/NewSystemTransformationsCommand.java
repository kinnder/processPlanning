package application.command;

import application.event.CommandStatusEvent;
import planning.storage.SystemTransformationsXMLFile;

public class NewSystemTransformationsCommand extends Command {

	public static final String NAME = "new-st";

	SystemTransformationsXMLFile transformationsXMLFile = new SystemTransformationsXMLFile();

	@Override
	public void execute(CommandData data) throws Exception {
		execute((NewSystemTransformationsData) data);
	}

	private void execute(NewSystemTransformationsData data) {
		notifyCommandStatus(new CommandStatusEvent("executing command: \"new system transformation\"..."));
		notifyCommandStatus(new CommandStatusEvent("done"));
	}
}

package application.command;

import application.event.CommandStatusEvent;
import planning.storage.SystemTransformationsXMLFile;

public class NewSystemTransformationsCommand extends Command {

	public static final String NAME = "new_st";

	SystemTransformationsXMLFile transformationsXMLFile = new SystemTransformationsXMLFile();

	@Override
	public void execute(CommandData data) throws Exception {
		execute((NewSystemTransformationsCommandData) data);
	}

	private void execute(NewSystemTransformationsCommandData data) {
		notifyCommandStatus(new CommandStatusEvent("executing command: \"new system transformation\"..."));
		data.systemTransformationsFile = "";
		notifyCommandStatus(new CommandStatusEvent("done"));
	}
}

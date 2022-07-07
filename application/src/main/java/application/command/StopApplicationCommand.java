package application.command;

import org.slf4j.LoggerFactory;

import application.Application;

public class StopApplicationCommand extends Command {

	public static final String NAME = "stopApplication";

	public StopApplicationCommand(Application application) {
		super(application, NAME);
	}

	@Override
	public void execute(CommandData data) throws Exception {
		execute((StopApplicationCommandData) data);
	}

	@SuppressWarnings("PMD.UnusedFormalParameter")
	private void execute(StopApplicationCommandData data) {
		application.stop();
	}

	@Override
	public void run() {
		try {
			execute(data);
		} catch (Exception e) {
			errored();
			LoggerFactory.getLogger(getClass()).error("", e);
		}
	}
}

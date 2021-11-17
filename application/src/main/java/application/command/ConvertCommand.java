package application.command;

import org.apache.commons.io.FilenameUtils;

import application.Application;
import application.event.CommandStatusEvent;
import planning.method.NodeNetwork;
import planning.method.SystemTransformations;
import planning.method.TaskDescription;
import planning.model.SystemProcess;

public class ConvertCommand extends Command {

	public static final String NAME = "convert";

	public ConvertCommand(Application application) {
		super(application);
	}

	@Override
	public void execute(CommandData data) throws Exception {
		execute((ConvertCommandData) data);
	}

	private void execute(ConvertCommandData data) throws Exception {
		application.notifyCommandStatus(new CommandStatusEvent("executing command: \"convert\"..."));

		if (data.taskDescriptionFile != null) {
			TaskDescription taskDescription = application.loadTaskDescription(data.taskDescriptionFile);
			String targetFile = changeFileExtension(data.taskDescriptionFile);
			application.saveTaskDescription(taskDescription, targetFile);
		}

		if (data.systemTransformationsFile != null) {
			SystemTransformations systemTransformations = application.loadSystemTransformations(data.systemTransformationsFile);
			String targetFile = changeFileExtension(data.systemTransformationsFile);
			application.saveSystemTransformations(systemTransformations, targetFile);
		}

		if (data.nodeNetworkFile != null) {
			NodeNetwork nodeNetwork = application.loadNodeNetwork(data.nodeNetworkFile);
			String targetFile = changeFileExtension(data.nodeNetworkFile);
			application.saveNodeNetwork(nodeNetwork, targetFile);
		}

		if (data.processFile != null) {
			SystemProcess systemProcess = application.loadSystemProcess(data.processFile);
			String targetFile = changeFileExtension(data.processFile);
			application.saveSystemProcess(systemProcess, targetFile);
		}

		application.notifyCommandStatus(new CommandStatusEvent("done"));
	}

	public String changeFileExtension(String sourceFile) {
		String targetFile = sourceFile.substring(0, sourceFile.length() - 3);
		if ("owl".equals(FilenameUtils.getExtension(sourceFile))) {
			return targetFile.concat("xml");
		}
		return targetFile.concat("owl");
	}
}

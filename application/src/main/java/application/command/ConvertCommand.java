package application.command;

import org.apache.commons.io.FilenameUtils;

import application.Application;
import planning.method.NodeNetwork;
import planning.method.SystemTransformations;
import planning.method.TaskDescription;
import planning.model.SystemProcess;

public class ConvertCommand extends Command {

	public static final String NAME = "convert";

	public ConvertCommand(Application application) {
		super(application, NAME);
	}

	@Override
	public void execute(CommandData data) throws Exception {
		execute((ConvertCommandData) data);
	}

	private void execute(ConvertCommandData data) throws Exception {
		if (data.taskDescriptionFile != null) {
			final TaskDescription taskDescription = application.loadTaskDescription(data.taskDescriptionFile);
			final String targetFile = changeFileExtension(data.taskDescriptionFile);
			application.saveTaskDescription(taskDescription, targetFile);
		}

		if (data.systemTransformationsFile != null) {
			final SystemTransformations systemTransformations = application.loadSystemTransformations(data.systemTransformationsFile);
			final String targetFile = changeFileExtension(data.systemTransformationsFile);
			application.saveSystemTransformations(systemTransformations, targetFile);
		}

		if (data.nodeNetworkFile != null) {
			final NodeNetwork nodeNetwork = application.loadNodeNetwork(data.nodeNetworkFile);
			final String targetFile = changeFileExtension(data.nodeNetworkFile);
			application.saveNodeNetwork(nodeNetwork, targetFile);
		}

		if (data.processFile != null) {
			final SystemProcess systemProcess = application.loadSystemProcess(data.processFile);
			final String targetFile = changeFileExtension(data.processFile);
			application.saveSystemProcess(systemProcess, targetFile);
		}
	}

	public String changeFileExtension(String sourceFile) {
		final String targetFile = sourceFile.substring(0, sourceFile.length() - 3);
		if ("owl".equals(FilenameUtils.getExtension(sourceFile))) {
			return targetFile.concat("xml");
		}
		return targetFile.concat("owl");
	}
}

package application.domain.cuttingProcess;

import java.io.IOException;
import java.net.URISyntaxException;

import application.domain.CuttingProcess;
import application.domain.assemblyLine.AcceptanceTests;
import planning.method.TaskDescription;
import planning.storage.TaskDescriptionXMLFile;

public class GenerateTaskDescription extends CuttingProcess {

	public static void main(String args[]) {
		TaskDescription taskDescription = CuttingProcess.getTaskDescription();

		TaskDescriptionXMLFile xmlFile = new TaskDescriptionXMLFile();
		xmlFile.setObject(taskDescription);
		try {
			xmlFile.save(AcceptanceTests.class.getResource("/cuttingProcess/taskDescription.xml"));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
}

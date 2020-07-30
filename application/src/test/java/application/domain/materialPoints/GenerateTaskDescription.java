package application.domain.materialPoints;

import java.io.IOException;
import java.net.URISyntaxException;

import application.domain.MaterialPoints;
import application.domain.assemblyLine.AcceptanceTests;
import planning.method.TaskDescription;
import planning.storage.TaskDescriptionXMLFile;

public class GenerateTaskDescription extends MaterialPoints {

	public static void main(String args[]) {
		TaskDescription taskDescription = MaterialPoints.getTaskDescription();

		TaskDescriptionXMLFile xmlFile = new TaskDescriptionXMLFile();
		xmlFile.setObject(taskDescription);
		try {
			xmlFile.save(AcceptanceTests.class.getResource("/materialPoints/taskDescription.xml"));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
}

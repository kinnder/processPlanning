package application.domain.assemblyLine;

import java.io.IOException;
import java.net.URISyntaxException;

import application.domain.AssemblyLine;
import planning.method.TaskDescription;
import planning.storage.TaskDescriptionXMLFile;

public class GenerateTaskDescription {

	public static void main(String args[]) {
		TaskDescription taskDescription = AssemblyLine.getTaskDescription();

		TaskDescriptionXMLFile xmlFile = new TaskDescriptionXMLFile();
		xmlFile.setObject(taskDescription);
		try {
			xmlFile.save(AcceptanceTests.class.getResource("/assemblyLine/taskDescription.xml"));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
}

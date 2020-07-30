package application.domain.assemblyLine;

import java.io.IOException;
import java.net.URISyntaxException;

import application.domain.AssemblyLine;
import planning.method.SystemTransformations;
import planning.storage.SystemTransformationsXMLFile;

public class GenerateSystemTransformations {

	public static void main(String args[]) {
		SystemTransformations assemblyLineTransformations = AssemblyLine.getSystemTransformations();

		SystemTransformationsXMLFile xmlFile = new SystemTransformationsXMLFile();
		xmlFile.setObject(assemblyLineTransformations);
		try {
			xmlFile.save(GenerateSystemTransformations.class.getResource("/assemblyLine/systemTransformations.xml"));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
}

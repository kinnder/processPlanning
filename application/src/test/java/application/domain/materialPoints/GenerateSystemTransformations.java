package application.domain.materialPoints;

import java.io.IOException;
import java.net.URISyntaxException;

import application.domain.MaterialPoints;
import planning.method.SystemTransformations;
import planning.storage.SystemTransformationsXMLFile;

public class GenerateSystemTransformations extends MaterialPoints {

	public static void main(String args[]) {
		SystemTransformations materialPointsTransformations = MaterialPoints.getSystemTransformations();

		SystemTransformationsXMLFile xmlFile = new SystemTransformationsXMLFile();
		xmlFile.setObject(materialPointsTransformations);
		try {
			xmlFile.save(GenerateSystemTransformations.class.getResource("/materialPoints/systemTransformations.xml"));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
}

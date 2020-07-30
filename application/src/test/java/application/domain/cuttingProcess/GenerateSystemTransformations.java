package application.domain.cuttingProcess;

import java.io.IOException;
import java.net.URISyntaxException;

import application.domain.CuttingProcess;
import planning.method.SystemTransformations;
import planning.storage.SystemTransformationsXMLFile;

public class GenerateSystemTransformations extends CuttingProcess {

	public static void main(String args[]) {
		SystemTransformations cuttingProcessTransformations = CuttingProcess.getSystemTransformations();

		SystemTransformationsXMLFile xmlFile = new SystemTransformationsXMLFile();
		xmlFile.setObject(cuttingProcessTransformations);
		try {
			xmlFile.save(GenerateSystemTransformations.class.getResource("/cuttingProcess/systemTransformations.xml"));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
}

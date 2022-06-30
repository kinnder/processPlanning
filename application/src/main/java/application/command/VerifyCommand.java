package application.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import application.Application;
import application.event.CommandStatusEvent;
import application.storage.PersistanceStorage;

public class VerifyCommand extends Command {

	public static final String NAME = "verify";

	public VerifyCommand(Application application) {
		super(application, NAME);
	}

	@Override
	public void execute(CommandData data) throws Exception {
		execute((VerifyCommandData) data);
	}

	SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

	private void execute(VerifyCommandData data) throws Exception {
		Map<String, String> filesToValidate = new HashMap<String, String>();
		if (data.taskDescriptionFile != null) {
			filesToValidate.put(data.taskDescriptionFile, PersistanceStorage.TASK_DESCRIPTION_XSD);
		}
		if (data.systemTransformationsFile != null) {
			filesToValidate.put(data.systemTransformationsFile, PersistanceStorage.SYSTEM_TRANSFORMATIONS_XSD);
		}
		if (data.nodeNetworkFile != null) {
			filesToValidate.put(data.nodeNetworkFile, PersistanceStorage.NODE_NETWORK_XSD);
		}
		if (data.processFile != null) {
			filesToValidate.put(data.processFile, PersistanceStorage.PROCESS_XSD);
		}

		for (String xmlPath : filesToValidate.keySet()) {
			String xsdPath = filesToValidate.get(xmlPath);
			Source xml = new StreamSource(xmlPath);
			Source xsd = new StreamSource(application.getResourceAsStream(xsdPath));
			application.notifyEvent(new CommandStatusEvent(String.format("verification of %s ...", xmlPath)));
			if (verifyXMLSchema(xml, xsd)) {
				application.notifyEvent(new CommandStatusEvent(String.format("SUCCESS: %s is correct", xmlPath)));
			} else {
				application.notifyEvent(new CommandStatusEvent(String.format("FAIL: %s is not correct", xmlPath)));
			}
		}
	}

	private boolean verifyXMLSchema(Source xml, Source xsd) throws IOException {
		try {
			Validator validator = factory.newSchema(xsd).newValidator();
			validator.validate(xml);
		} catch (SAXParseException e) {
			application.notifyEvent(new CommandStatusEvent(String.format("lineNumber: %d; columnNumber: %d; %s", e.getLineNumber(), e.getColumnNumber(), e.getMessage())));
			return false;
		} catch (SAXException e) {
			application.notifyEvent(new CommandStatusEvent(e.getMessage()));
			return false;
		}
		return true;
	}
}

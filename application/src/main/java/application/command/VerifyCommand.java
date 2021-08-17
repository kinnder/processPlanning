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

import application.event.CommandStatusEvent;
import application.event.HelpMessageEvent;
import application.storage.PersistanceStorage;

public class VerifyCommand extends Command {

	public static final String NAME = "verify";

	@Override
	public void execute(CommandData data) throws Exception {
		execute((VerifyCommandData) data);
	}

	PersistanceStorage persistanceStorage = new PersistanceStorage();

	SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

	private void execute(VerifyCommandData data) throws Exception {
		notifyCommandStatus(new CommandStatusEvent("executing command: \"verify\"..."));

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
			Source xsd = new StreamSource(persistanceStorage.getResourceAsStream(xsdPath));
			// TODO (2021-08-16 #43): вместо HelpMessageEvent использовать CommandStatusEvent
			notifyHelpMessage(new HelpMessageEvent(String.format("verification of %s ...", xmlPath)));
			if (verifyXMLSchema(xml, xsd)) {
				notifyHelpMessage(new HelpMessageEvent(String.format("SUCCESS: %s is correct", xmlPath)));
			} else {
				notifyHelpMessage(new HelpMessageEvent(String.format("FAIL: %s is not correct", xmlPath)));
			}
		}

		notifyCommandStatus(new CommandStatusEvent("done"));
	}

	private boolean verifyXMLSchema(Source xml, Source xsd) throws IOException {
		try {
			Validator validator = factory.newSchema(xsd).newValidator();
			validator.validate(xml);
		} catch (SAXParseException e) {
			notifyHelpMessage(new HelpMessageEvent(String.format("lineNumber: %d; columnNumber: %d; %s", e.getLineNumber(), e.getColumnNumber(), e.getMessage())));
			return false;
		} catch (SAXException e) {
			notifyHelpMessage(new HelpMessageEvent(e.getMessage()));
			return false;
		}
		return true;
	}
}

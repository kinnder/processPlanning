package planning.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.jdom2.Namespace;

import planning.model.SystemProcess;
import planning.model.SystemOperation;

public class SystemProcessXMLSchema extends ValueXMLSchema implements XMLSchema {

	private SystemProcess process = new SystemProcess();

	@Override
	public Element combine() {
		return combineProcess(process);
	}

	public Element combineProcess(SystemProcess process) {
		List<Element> elements = new ArrayList<>();
		for (SystemOperation systemOperation : process) {
			Element element = combineSystemOperation(systemOperation);
			elements.add(element);
		}
		Element root = new Element("process");
		Namespace xsiNamespace = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		root.setAttribute("noNamespaceSchemaLocation", "../process.xsd", xsiNamespace);
		root.addContent(elements);
		return root;
	}

	public Element combineSystemOperation(SystemOperation systemOperation) {
		Element name = new Element("name");
		name.setText(systemOperation.getName());
		Element parameters = combineActionParameters(systemOperation.getActionParameters());
		Element root = new Element("operation");
		root.addContent(name);
		root.addContent(parameters);
		return root;
	}

	public Element combineActionParameters(Map<String, String> actionParameters) {
		Element root = new Element("parameters");
		for (String key : actionParameters.keySet()) {
			Element name = new Element("name");
			name.setText(key);
			Element value = combineValue(actionParameters.get(key));
			Element parameter = new Element("parameter");
			parameter.addContent(name);
			parameter.addContent(value);
			root.addContent(parameter);
		}
		return root;
	}

	public void setSystemProcess(SystemProcess process) {
		this.process = process;
	}

	public SystemProcess getSystemProcess() {
		return this.process;
	}

	@Override
	public void parse(Element element) throws DataConversionException {
		throw new UnsupportedOperationException("Parsing xml-files is not supported");
	}
}

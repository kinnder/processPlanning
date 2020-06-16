package planning.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.jdom2.Namespace;

import planning.model.SystemProcess;
import planning.model.SystemOperation;

public class SystemProcessXMLSchema implements XMLSchema<SystemProcess> {

	final private static String TAG_schema = "process";

	@Override
	public String getSchemaName() {
		return TAG_schema;
	}

	private ValueXMLSchema valueSchema = new ValueXMLSchema();

	@Override
	public Element combine(SystemProcess process) {
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
			Element value = valueSchema.combine(actionParameters.get(key));
			Element parameter = new Element("parameter");
			parameter.addContent(name);
			parameter.addContent(value);
			root.addContent(parameter);
		}
		return root;
	}

	@Override
	public SystemProcess parse(Element element) throws DataConversionException {
		throw new UnsupportedOperationException("Parsing xml-files is not supported");
	}
}

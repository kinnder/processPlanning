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

	final private static String TAG_process = "process";

	final private static String TAG_name = "name";

	final private static String TAG_operation = "operation";

	final private static String TAG_parameters = "parameters";

	final private static String TAG_parameter = "parameter";

	@Override
	public String getSchemaName() {
		return TAG_process;
	}

	public SystemProcessXMLSchema() {
		this(new ValueXMLSchema());
	}

	SystemProcessXMLSchema(ValueXMLSchema valueXMLSchema) {
		this.valueSchema = valueXMLSchema;
	}

	private ValueXMLSchema valueSchema;

	@Override
	public Element combine(SystemProcess process) {
		List<Element> elements = new ArrayList<>();
		for (SystemOperation systemOperation : process) {
			Element element = combineSystemOperation(systemOperation);
			elements.add(element);
		}
		Element root = new Element(TAG_process);
		Namespace xsiNamespace = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		root.setAttribute("noNamespaceSchemaLocation", "../process.xsd", xsiNamespace);
		root.addContent(elements);
		return root;
	}

	// TODO (2020-06-18 #22): перенести работу со схемой SystemOperation в отдельный файл

	public Element combineSystemOperation(SystemOperation systemOperation) {
		Element name = new Element(TAG_name);
		name.setText(systemOperation.getName());
		Element parameters = combineActionParameters(systemOperation.getActionParameters());
		Element root = new Element(TAG_operation);
		root.addContent(name);
		root.addContent(parameters);
		return root;
	}

	// TODO (2020-06-18 #22): перенести работу со схемой ActionParameters в отдельный файл

	public Element combineActionParameters(Map<String, String> actionParameters) {
		Element root = new Element(TAG_parameters);
		for (String key : actionParameters.keySet()) {
			Element name = new Element(TAG_name);
			name.setText(key);
			Element value = valueSchema.combine(actionParameters.get(key));
			Element parameter = new Element(TAG_parameter);
			parameter.addContent(name);
			parameter.addContent(value);
			root.addContent(parameter);
		}
		return root;
	}

	@Override
	public SystemProcess parse(Element element) throws DataConversionException {
		// TODO (2020-07-05 #22): добавить реализацию метода parse
		throw new UnsupportedOperationException("Parsing xml-files is not supported");
	}
}

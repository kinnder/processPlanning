package planning.storage;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.jdom2.Namespace;
import planning.method.SystemTransformations;
import planning.model.SystemTransformation;

public class SystemTransformationsXMLSchema implements XMLSchema<SystemTransformations> {

	final private static String TAG_schema = "systemTransformations";

	@Override
	public String getSchemaName() {
		return TAG_schema;
	}

	private SystemTransformationXMLSchema systemTransformationXMLSchema = new SystemTransformationXMLSchema();

	@Override
	public SystemTransformations parse(Element element) throws DataConversionException {
		SystemTransformations systemTransformations = new SystemTransformations();
		List<Element> elements = element.getChildren("systemTransformation");
		for (Element e : elements) {
			SystemTransformation systemTransformation = systemTransformationXMLSchema.parse(e);
			systemTransformations.add(systemTransformation);
		}
		return systemTransformations;
	}

	@Override
	public Element combine(SystemTransformations systemTransformations) {
		List<Element> elements = new ArrayList<>();
		for (SystemTransformation systemTransformation : systemTransformations) {
			Element element = systemTransformationXMLSchema.combine(systemTransformation);
			elements.add(element);
		}
		Element root = new Element("systemTransformations");
		Namespace xsiNamespace = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		root.setAttribute("noNamespaceSchemaLocation", "../systemTransformations.xsd", xsiNamespace);
		root.addContent(elements);
		return root;
	}
}

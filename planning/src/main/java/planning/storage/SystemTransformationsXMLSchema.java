package planning.storage;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.jdom2.Namespace;
import planning.method.SystemTransformations;
import planning.model.SystemTransformation;

public class SystemTransformationsXMLSchema implements XMLSchema<SystemTransformations> {

	final private static String TAG_systemTransformations = "systemTransformations";

	@Override
	public String getSchemaName() {
		return TAG_systemTransformations;
	}

	public SystemTransformationsXMLSchema() {
		this(new SystemTransformationXMLSchema());
	}

	SystemTransformationsXMLSchema(SystemTransformationXMLSchema systemTransformationXMLSchema) {
		this.systemTransformationSchema = systemTransformationXMLSchema;
	}

	private SystemTransformationXMLSchema systemTransformationSchema;

	@Override
	public SystemTransformations parse(Element element) throws DataConversionException {
		SystemTransformations systemTransformations = new SystemTransformations();
		List<Element> elements = element.getChildren(systemTransformationSchema.getSchemaName());
		for (Element e : elements) {
			SystemTransformation systemTransformation = systemTransformationSchema.parse(e);
			systemTransformations.add(systemTransformation);
		}
		return systemTransformations;
	}

	@Override
	public Element combine(SystemTransformations systemTransformations) {
		// TODO (2020-07-03 #22): добавлять элементы сразу в корневой узел
		List<Element> elements = new ArrayList<>();
		for (SystemTransformation systemTransformation : systemTransformations) {
			Element element = systemTransformationSchema.combine(systemTransformation);
			elements.add(element);
		}
		Element root = new Element(TAG_systemTransformations);
		Namespace xsiNamespace = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		root.setAttribute("noNamespaceSchemaLocation", "../systemTransformations.xsd", xsiNamespace);
		root.addContent(elements);
		return root;
	}
}
